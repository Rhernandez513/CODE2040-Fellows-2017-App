package io.roberthernandez.code2040_fellows_2017_app;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by robert on 9/24/16.
 */

public class HttpPostHandler extends AsyncTask<String, Void, String>{

    private String responseCode = "";

    public String getResponseCode() {
        return this.responseCode;
    }
    public void setResponseCode(String string) {
        this.responseCode = string;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            result = post(params[0], params[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setResponseCode(result);

        // I know I'm not actually using the result but for some reason I couldn't access the
        // returned data member from the UI thread
        // using a field is inelegant, but it works
        return result;
    }

    @Override
    protected void onPostExecute(String result) { }

    @Override
    protected void onPreExecute() { super.onPreExecute(); }

    @Override
    protected void onProgressUpdate(Void... values) { }
}

