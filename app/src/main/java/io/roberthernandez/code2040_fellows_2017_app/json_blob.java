package io.roberthernandez.code2040_fellows_2017_app;


import java.util.ArrayList;

/**
 * Created by robert on 9/25/16.
 */

public class json_blob {
    // Arbitrary field names according to what the API endpoint expects
    private String token;
    private String needleToSearchFor;
    private ArrayList<String> string_array;
    private int needle;

    public String getToken () {
        return this.token;
    }

    public String getNeedle() {
        return this.needleToSearchFor;
    }

    public ArrayList<String> getString_Array() { return this.string_array; }

    // Gson Best practices
    public json_blob() { }

    public json_blob(String string, ArrayList<String> string_array) {
        this.needleToSearchFor = string;
        this.string_array = string_array;
    }

    public json_blob(String token, int location) {
        this.token = token;
        this.needle = location;
    }

    public json_blob(String token, String needleToSearchFor) {
        this.token = token;
        this.needleToSearchFor = needleToSearchFor;
    }

    public json_blob(String token) { this.token = token; }
}
