package android;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PythonVolleyJsonRequest extends com.android.volley.toolbox.JsonObjectRequest {
	private HashMap<String, String> mHeaders;

	public PythonVolleyJsonRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener,
            ErrorListener errorListener, HashMap<String, String> headers) {
	        super(method, url, jsonRequest, listener,
				errorListener);
		mHeaders = headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		mHeaders = headers;
	}

	public HashMap<String, String> getHeaders() {
		return mHeaders;
	}
}
