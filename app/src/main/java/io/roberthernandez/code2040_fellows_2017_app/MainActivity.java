package io.roberthernandez.code2040_fellows_2017_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> URLS = new ArrayList<String>();
    private Button btnOne, btnTwo, btnThree, btnFour;
    private static Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateURLList(URLS);

        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);

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
                updateUI(response);
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
                    updateUI(response);

                    // Reverse string that was sent to us
                    response = new StringBuffer(response).reverse().toString();

                    // convert to POJO
                    blob  = new json_blob(getResources().getString(R.string.token_value), response);

                    // Send back reversed string to validation endpoint
                    response = makeHttpPostRequest(URLS.get(2), gson.toJson(blob));

                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateUI(response);
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
                    updateUI("Initial API response: " + response);

                    String key = getKey(response);

                    String[] contents = getConentsOfNestedJSONArray(response);

                    // Iterate over data, searching for match
                    int location = 0;
                    for (int i = 0; i < contents.length; ++i) {
                        if (contents[i].equals(key)) {
                            location = i;
                            break;
                        }
                    }

                    blob  = new json_blob(getResources().getString(R.string.token_value), location);

                    // Send back needle location and API token
                    response = makeHttpPostRequest(URLS.get(4), gson.toJson(blob));

                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateUI(response);
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String response = "";
                json_blob blob  = new json_blob(getResources().getString(R.string.token_value));
                final String token_only = gson.toJson(blob);

                try {
                    response = makeHttpPostRequest(URLS.get(5), token_only);
                    updateUI("Initial API response: " + response);

                    String key = getKey(response);

                    String[] contents = getConentsOfNestedJSONArray(response);
                    ArrayList<String> contentsWithoutPrefix = new ArrayList<String>();

                    for (int i = 0; i < contents.length; ++i) {
                        if (!contents[i].contains(key)) {
                            contentsWithoutPrefix.add(contents[i]);
                        }
                    }

                    blob  = new json_blob(getResources().getString(R.string.token_value), contentsWithoutPrefix);

                    // Send back needle location and API token
                    response = makeHttpPostRequest(URLS.get(6), gson.toJson(blob));

                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateUI(response);
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
        list.add(getResources().getString(R.string.prefix_api_endpoint));
        list.add(getResources().getString(R.string.prefix_validate_api_endpoint));

    }

    public String makeHttpPostRequest(String endPoint, String json) throws IOException {

        HttpPostHandler handler = new HttpPostHandler();
        handler.execute(endPoint, json);

        // wait for potential network lag
        try { Thread.sleep(3000); } catch (Exception e){ e.printStackTrace(); }

        return handler.getResponseCode();

    }
    public void updateUI(String text) {
        TextView textView = (TextView) findViewById(R.id.responseBox);
        textView.setText(text);
    }

    public String getKey(String JSON) {
        // Match anything inside quotes
        // double escape sequence needed due to Java language, not regex
        String regex = "(?<=\\\").+?(?=\\\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(JSON);

        // Should return the value of needle inside the JSON Dict
        // This is not an extensible solution, but we know that the needle
        // is the first key, value paid inside the dictionary
        // The regex finds anything inside quotes, but doesn't discriminate on quote pairs
        // that were already matched, e.g. the second match is actually the first colon, not
        // the needle that a human would expect
        matcher.find();
        matcher.find();
        return matcher.find() ? matcher.group(0) : "" ;

    }

    public String[] getConentsOfNestedJSONArray(String JSON) {
        // Match anything inside square brackets
        Pattern pattern = Pattern.compile("(?<=\\[).+?(?=\\])");
        Matcher matcher = pattern.matcher(JSON);

        // Should return the contents of the JSON array with elements encased in
        // quotes and separated by commas
        final String result = matcher.find() ? matcher.group(0) : "" ;

        // Remove commas
        String[] contents = result.split(",");

        // Match anything inside quotes
        pattern = Pattern.compile("(?<=\").+?(?=\")");

        // Strip quotes from elements
        for(int i = 0; i < contents.length; ++i) {
            matcher = pattern.matcher(contents[i]);
            contents[i] = matcher.find() ? matcher.group(0) : "" ;
        }

        return contents;
    }

}

