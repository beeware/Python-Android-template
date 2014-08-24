package {{ cookiecutter.organization_id }}.{{ cookiecutter.app_name.lower() }};

import android.app.Activity;
import android.os.Bundle;

public class {{ cookiecutter.app_name }} extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
