package android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class PythonActivity extends Activity {
    static org.python.Object _app;

    static public void setApp(org.python.Object app) {
        _app = app;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Python", "Starting Python app...");
        try {
            // Load the app module
            java.lang.Class app_module = java.lang.Class.forName("python.{{ cookiecutter.app_name }}.app.__init__");

            // Find the main method in the app module...
            java.lang.reflect.Method main = app_module.getMethod("main", java.lang.String[].class);

            // ... and invoke it.
            main.invoke(null, new java.lang.Object [] {
                new java.lang.String [] {"{{ cookiecutter.app_name }}"}
            });

            if (_app == null) {
                Log.e("Python", "{{ cookiecutter.app_name }} didn't configure an app reference.");
            } else {
                _app.__setattr_null("_impl", new org.python.java.Object(this));
            }
            Log.d("Python", "Python app started.");
        } catch (java.lang.ClassNotFoundException e) {
            Log.e("Python", "Couldn't load Python app module.");
        } catch (java.lang.NoSuchMethodException e) {
            Log.e("Python", "Couldn't find main method.");
        } catch (java.lang.IllegalAccessException e) {
            Log.e("Python", "Couldn't access main method.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Log.e("Python", "Couldn't invoke main method.");
        }
    }

    /**
     * Invoke an interface method on the Python
     */
    void invoke_app_method(
                java.lang.String method_name,
                org.python.Object [] args,
                java.util.Map<java.lang.String, org.python.Object> kwargs
            ) {
        if (_app == null) {
            Log.e("Python", "{{ cookiecutter.app_name }} didn't configure an app reference at creation.");
        } else {
            try {
                org.python.Object method = _app.__getattribute_null(method_name);
                if (method != null) {
                    ((org.python.Callable) method).invoke(args, kwargs);
                } else {
                    Log.d("Python", "No " + method_name + " method on Python app.");
                }
            } catch (java.lang.ClassCastException e) {
                Log.e("Python", method_name + " method isn't callable");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Python", "onStart");
        this.invoke_app_method("onStart", null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Python", "onResume");
        this.invoke_app_method("onResume", null, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Python", "onPause");
        this.invoke_app_method("onPause", null, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Python", "onStop");
        this.invoke_app_method("onStop", null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Python", "onDestroy");
        this.invoke_app_method("onDestroy", null, null);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("Python", "onRestart");
        this.invoke_app_method("onRestart", null, null);
    }
}
