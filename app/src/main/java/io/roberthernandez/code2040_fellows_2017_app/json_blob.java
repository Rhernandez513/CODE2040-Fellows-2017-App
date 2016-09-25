package io.roberthernandez.code2040_fellows_2017_app;


import java.util.ArrayList;

/**
 * Created by robert on 9/25/16.
 */

public class json_blob {
    // Arbitrary field names according to what the API endpoint expects
    private String token;
    private String needle;
    private ArrayList<String> string_array;

    public json_blob(String string, ArrayList<String> string_array) {
        this.needle = string;
        this.string_array = string_array;
    }

    public json_blob(String token, String needle) {
        this.token = token;
        this.needle = needle;
    }
    public json_blob(String token) { this.token = token; }
}
