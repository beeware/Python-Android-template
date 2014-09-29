package org.pybee.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import org.pybee.Python;

import java.io.File;

public class PythonActivity extends Activity
{
    private static String TAG = "Python";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new LinearLayout(this));

        Assets.unpackPayload(this);

        Log.i(TAG, "Start Python environment...");
        if (Python.start(getFilesDir().toString()) != 0) {
            Log.e(TAG, "Got an error initializing Python");
        }

        Log.i(TAG, "Starting Python script...");
        if (Python.run("myfirstapp/main.py") != 0) {
            Log.e(TAG, "Got an error running Python script");
        }

        Log.i(TAG, "Activity created.");
    }

    /** Called when the activity is destroyed. */
    @Override
    public void onDestroy()
    {
        Log.i(TAG, "Stopping Python environment...");
        Python.stop();
        Log.i(TAG, "Python environment finalized.");
    }
}
