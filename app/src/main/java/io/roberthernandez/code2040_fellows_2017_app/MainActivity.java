package io.roberthernandez.code2040_fellows_2017_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static String API_ENDPOINT_URL;
    private Button btnOne;
    private json_blob blob;
    private static Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_ENDPOINT_URL = getResources().getString(R.string.register_api_endpoint);
        btnOne = (Button) findViewById(R.id.btnOne);

        gson = new Gson();

        blob = createBlob();

        final String blob_as_json = gson.toJson(blob);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeHttpPostRequest(API_ENDPOINT_URL, blob_as_json);
                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
            }
        });
    }
    public void makeHttpPostRequest(String endPoint, String json) throws IOException {

        HttpPostHandler handler = new HttpPostHandler();
        handler.execute(endPoint, json);

        // wait for potential network lag
        try { Thread.sleep(1000); } catch (Exception e){ e.printStackTrace(); }

        final String response = handler.getResponseCode();

        updateResponseBox(response);
    }
    public void updateResponseBox(String response) {
        TextView textView = (TextView) findViewById(R.id.responseBox);
        textView.setText(response);
    }

    public json_blob createBlob() {
        final String token_value = getResources().getString(R.string.token_value);
        final String github_url = getResources().getString(R.string.github_url);

        return new json_blob(token_value, github_url);
    }
}

