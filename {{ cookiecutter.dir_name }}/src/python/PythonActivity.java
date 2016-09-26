package python;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class PythonActivity extends Activity {

    static {
        Log.i("Python", "Starting python app...");
        try {
            java.lang.Class app_module = java.lang.Class.forName("python.{{ cookiecutter.app_name }}.app.__init__");
            java.lang.reflect.Method main = app_module.getMethod("main", java.lang.String[].class);

            main.invoke(null, new java.lang.Object [] {
                new java.lang.String [] {"{{ cookiecutter.app_name }}"}
            });
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Python", "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Python", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Python", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Python", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Python", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Python", "onDestroy");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("Python", "onRestart");
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    //     getMenuInflater().inflate(R.menu.main, menu);
    //     return true;
    // }
}
