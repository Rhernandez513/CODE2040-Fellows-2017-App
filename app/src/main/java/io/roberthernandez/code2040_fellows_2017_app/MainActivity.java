package io.roberthernandez.code2040_fellows_2017_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.strategicgains.util.date.DateAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> URLS = new ArrayList<String>();
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive;
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
        btnFive = (Button) findViewById(R.id.btnFive);

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

                    String key = getKey(response, 1);

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

                    String key = getKey(response, 1);

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

        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String response = "";
                json_blob blob  = new json_blob(getResources().getString(R.string.token_value));
                final String token_only = gson.toJson(blob);

                try {
                    response = makeHttpPostRequest(URLS.get(7), token_only);
                    updateUI("Initial API response: " + response);

                    // Should be the value corresponding to "datestamp" in the JSON
                    final String datestamp = getKey(response, 1);
                    final json_blob newblob = gson.fromJson(response, json_blob.class);
                    int interval = newblob.getInterval();
                    // interval is returned from the API in seconds, Java defaults to miliseconds
                    interval *= 1000;

                    final ISO8601DateParser parser = new ISO8601DateParser();
                    final Date date = ISO8601DateParser.parse(datestamp);
                    long time = date.getTime();
                    time += interval;
                    date.setTime(time);

                    final String formattedDate = formatDate(date);

                    final json_blob third_blob = new json_blob(getResources().getString(R.string.token_value), formattedDate);
                    response = makeHttpPostRequest(URLS.get(8), gson.toJson(third_blob));

                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
                updateUI(response);
            }
        });
    }
    public void populateURLList(ArrayList<String> list) {
        list.add(getResources().getString(R.string.register_api_endpoint));
        list.add(getResources().getString(R.string.reverse_api_endpoint));
        list.add(getResources().getString(R.string.reverse_validate_api_endpoint));
        list.add(getResources().getString(R.string.haystack_api_endpoint));
        list.add(getResources().getString(R.string.haystack_validate_api_endpoint));
        list.add(getResources().getString(R.string.prefix_api_endpoint));
        list.add(getResources().getString(R.string.prefix_validate_api_endpoint));
        list.add(getResources().getString(R.string.date_time_api_endpoint));
        list.add(getResources().getString(R.string.date_time_validate_api_endpoint));
    }
    public String makeHttpPostRequest(String endPoint, String json) throws IOException {
        HttpPostHandler handler = new HttpPostHandler();
        handler.execute(endPoint, json);
        // Wait for potential network lag
        try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
        return handler.getResponseCode();
    }
    public void updateUI(String text) {
        TextView textView = (TextView) findViewById(R.id.responseBox);
        textView.setText(text);
    }
    public String getKey(String JSON, int position) {
        // Match anything inside quotes
        // double escape sequence needed due to Java language, not regex
        String regex = "(?<=\\\").+?(?=\\\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(JSON);

        // should provide *really basic* ability to specify where in the JSON you want to
        // get the key out of, i'm sure mixed types will break this
        // negative 1 value in case someone wants to specific the first object with a 0
        // as programmers tend to do
        for (int i = -1; i < position; ++i) {
            matcher.find();
        }
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

    // Expects a Java Date object
    // Returns a ISO8601 formatted string
    // Not tested to work in all cases!!!
    public String formatDate(Date date) {

        String rawDate = date.toString();

        // Extract Time only
        // 11 chars then hour (2) + : + (2) + : + (2)
        final String rawTime = rawDate.substring(11, 19);
        final String rawHour = rawTime.substring(0, 2);
        final int intrawHour = Integer.parseInt(rawHour);
        int zuluTime = intrawHour - 8;
        if (zuluTime < 0) { zuluTime += 24; }


        // Procedure to actually do the string-fu
        char[] array = rawTime.toCharArray();
        // the char array will be populated with a null char if the original string starts
        // with a zero for some reason
        if (rawTime.startsWith("0")) {
            array[0] = '0';
        }
        char[] zuluTimeArray = String.valueOf(zuluTime).toCharArray();
        char[] zuluTimeArray2 = new char[] {'0', '0'};
        if (zuluTimeArray.length == 1) {
            zuluTimeArray2[0] = '0';
            zuluTimeArray2[1] = zuluTimeArray[0];
        } else {
            zuluTimeArray2 = zuluTimeArray;
        }
        array[0] = zuluTimeArray2[0];
        array[1] = zuluTimeArray2[1];
        final String zuluTimeArray3 = new String (array);


        DateAdapter adapter = new DateAdapter();
        String formattedDate = adapter.format(date);

        // Create final string
        formattedDate += "T";
        formattedDate += zuluTimeArray3;
        formattedDate += "Z";

        return formattedDate;
    }

}

