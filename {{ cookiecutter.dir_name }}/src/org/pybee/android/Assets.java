package org.pybee.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPInputStream;

import android.app.Activity;
import android.util.Log;
import android.content.res.AssetManager;
import android.content.res.Resources;

import org.pybee.tar.TarEntry;
import org.pybee.tar.TarInputStream;


public class Assets {
    private static String TAG = "Python";

    public static void unpackPayload(Activity activity, String payload) {
        // The version of data in memory and on disk.
        Resources res = activity.getResources();
        int identifier = res.getIdentifier(payload + "_payload_timestamp", "string", activity.getPackageName());
        String data_version = res.getString(identifier);
        String disk_version = null;

        // If no version, no unpacking is necessary.
        if (data_version == null) {
            Log.v(TAG, "No payload timestamp available for '" + payload + "'.");
            return;
        }

        File filesDir = activity.getFilesDir();
        String filesDirName = filesDir.getAbsolutePath();

        // Check the current disk version, if any.
        File payloadDir = new File(activity.getFilesDir(), payload);
        String payloadDirName = payloadDir.getAbsolutePath();

        String disk_version_fn = payloadDirName + "/.payload.version";
        Log.v(TAG, payload + " version filename: " + disk_version_fn);

        try {
            byte buf[] = new byte[64];
            InputStream is = new FileInputStream(disk_version_fn);
            int len = is.read(buf);
            disk_version = new String(buf, 0, len);
            is.close();
        } catch (Exception e) {
            Log.e(TAG, "Problem reading file: " + e);
            disk_version = "";
        }

        Log.v(TAG, payload + " data version: " + data_version);
        Log.v(TAG, payload + " disk version: " + disk_version);

        // If the disk data is out of date, extract it and write the
        // version file.
        if (data_version.equals(disk_version)) {
            Log.v(TAG, payload + " payload is up to date.");
        } else {
            Log.v(TAG, "Extracting '" + payload + "' payload...");

            recursiveDelete(payloadDir);
            payloadDir.mkdirs();

            if (!extractTar(activity, payload + "-payload.tgz", filesDirName)) {
                Log.e(TAG, "Could not extract payload '" + payload + "'.");
            }

            try {
                // Write .nomedia.
                new File(payloadDir, ".nomedia").createNewFile();

                // Write version file.
                FileOutputStream os = new FileOutputStream(disk_version_fn);
                os.write(data_version.getBytes());
                os.close();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }

    private static void recursiveDelete(File f) {
        Log.d(TAG, "Recursive delete: " + f);
        if (f.isDirectory()) {
            for (File r : f.listFiles()) {
                recursiveDelete(r);
            }
        }
        f.delete();
    }

    public static boolean extractTar(Activity activity, String asset, String target) {

        byte buf[] = new byte[1024 * 1024];

        InputStream assetStream = null;
        TarInputStream tis = null;
        AssetManager assetManager = activity.getAssets();

        try {
            Log.d(TAG, "Opening asset " + asset);
            assetStream = assetManager.open(asset, AssetManager.ACCESS_STREAMING);
            Log.d(TAG, "Got asset stream " + assetStream);
            tis = new TarInputStream(new BufferedInputStream(new GZIPInputStream(new BufferedInputStream(assetStream, 8192)), 8192));
            Log.d(TAG, "Got input stream " + assetStream);
        } catch (IOException e) {
            Log.e(TAG, "Error opening tar", e);
            return false;
        }

        while (true) {
            TarEntry entry = null;

            try {
                entry = tis.getNextEntry();
            } catch (java.io.IOException e) {
                Log.e(TAG, "extracting tar", e);
                return false;
            }

            if ( entry == null ) {
                break;
            }

            Log.i(TAG, "extracting " + entry.getName());

            if (entry.isDirectory()) {

                try {
                    new File(target +"/" + entry.getName()).mkdirs();
                } catch ( SecurityException e ) { };

                continue;
            }

            OutputStream out = null;
            String path = target + "/" + entry.getName();

            try {
                out = new BufferedOutputStream(new FileOutputStream(path), 8192);
            } catch ( FileNotFoundException e ) {
            } catch ( SecurityException e ) { };

            if ( out == null ) {
                Log.e(TAG, "could not open " + path);
                return false;
            }

            try {
                while (true) {
                    int len = tis.read(buf);

                    if (len == -1) {
                        break;
                    }

                    out.write(buf, 0, len);
                }

                out.flush();
                out.close();
            } catch ( java.io.IOException e ) {
                Log.e(TAG, "extracting zip", e);
                return false;
            }
        }

        try {
            tis.close();
            assetStream.close();
        } catch (IOException e) {
            // pass
        }

        return true;
    }

}
