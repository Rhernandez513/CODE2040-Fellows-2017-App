package io.roberthernandez.code2040_fellows_2017_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> URLS = new ArrayList<String>();
    private Button btnOne, btnTwo, btnThree;
    private static Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateURLList(URLS);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);

        gson = new Gson();



        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String response = "";
                json_blob blob  = new json_blob(
                        getResources().getString(R.string.token_value),
                        getResources().getString(R.string.github_url));

                final String register_payload = gson.toJson(blob);

                try {
                    response = makeHttpPostRequest(URLS.get(0), register_payload);
                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateResponseBox(response);
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String response = "";
                json_blob blob  = new json_blob(getResources().getString(R.string.token_value));
                final String token_only = gson.toJson(blob);

                try {
                    response = makeHttpPostRequest(URLS.get(1), token_only);
                    updateResponseBox(response);

                    // Reverse string that was sent to us
                    response = new StringBuffer(response).reverse().toString();

                    // convert to POJO
                    blob  = new json_blob(getResources().getString(R.string.token_value), response);

                    // Send back reversed string to validation endpoint
                    response = makeHttpPostRequest(URLS.get(2), gson.toJson(blob));

                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateResponseBox(response);
            }
        });


        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String response = "";
                json_blob blob  = new json_blob(getResources().getString(R.string.token_value));
                final String token_only = gson.toJson(blob);

                try {
                    response = makeHttpPostRequest(URLS.get(3), token_only);
                    updateResponseBox(response);

                    // TODO Add actual logic to find the needle in the array by converting JSON to POJO, searching, then back to JSON

                    // Send back reversed string to validation endpoint
                    response = makeHttpPostRequest(URLS.get(4), gson.toJson(blob));

                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateResponseBox(response);
            }
        });
    }

    public void populateURLList(ArrayList<String> list) {
        // Used for testing only
        // list.add("http://posttestserver.com/post.php");

        list.add(getResources().getString(R.string.register_api_endpoint));
        list.add(getResources().getString(R.string.reverse_api_endpoint));
        list.add(getResources().getString(R.string.reverse_validate_api_endpoint));
        list.add(getResources().getString(R.string.haystack_api_endpoint));
        list.add(getResources().getString(R.string.haystack_validate_api_endpoint));
    }

    public String makeHttpPostRequest(String endPoint, String json) throws IOException {

        HttpPostHandler handler = new HttpPostHandler();
        handler.execute(endPoint, json);

        // wait for potential network lag
        try { Thread.sleep(3000); } catch (Exception e){ e.printStackTrace(); }

        return handler.getResponseCode();

    }
    public void updateResponseBox(String text) {
        TextView textView = (TextView) findViewById(R.id.responseBox);
        textView.setText(text);
    }

}

