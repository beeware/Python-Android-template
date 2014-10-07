package org.pybee.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;


import org.pybee.rubicon.Python;

import java.io.File;

public class PythonActivity extends Activity
{
    static {
        System.loadLibrary("python2.7");
    }

    private static String TAG = "Python";
    public static Activity activity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        activity = this;

        Assets.unpackPayload(this, "python");
        Assets.unpackPayload(this, "app_packages");
        Assets.unpackPayload(this, "app");

        Log.i(TAG, "Start Python environment...");
        String installPath = getFilesDir().toString();
        String pythonHome = installPath + "/python";
        String pythonPath = installPath + "/app:" + installPath + "/app_packages:" + installPath + "/python/lib/python2.7/site-packages";
        String rubiconLib = getApplicationInfo().nativeLibraryDir + "/librubicon.so";

        if (Python.start(pythonHome, pythonPath, rubiconLib) != 0) {
            Log.e(TAG, "Got an error initializing Python");
        }

        Log.i(TAG, "Starting Python script...");
        if (Python.run(installPath + "/app/{{ cookiecutter.app_name }}/main.py") != 0) {
            Log.e(TAG, "Got an error running Python script");
        }

        Log.i(TAG, "Activity created.");
    }

    /** Called when the activity is destroyed. */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "Stopping Python environment...");
        Python.stop();
        Log.i(TAG, "Python environment finalized.");
    }
}
