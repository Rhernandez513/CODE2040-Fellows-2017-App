package io.roberthernandez.code2040_fellows_2017_app;


import java.util.ArrayList;

/**
 * Created by robert on 9/25/16.
 */

public class json_blob {
    // Arbitrary field names according to what the API endpoint expects
    private String token;
    private String needleToSearchFor;
    private String datestamp;
    private Integer interval;
    private ArrayList<String> array;
    private int needle;

    public Integer getInterval() { return this.interval; }

    public String getToken () {
        return this.token;
    }

    public String getNeedle() {
        return this.needleToSearchFor;
    }

    public ArrayList<String> getString_Array() { return this.array; }

    // Gson Best practices
    public json_blob() { }

    public json_blob(String string, ArrayList<String> array) {
        this.token = string;
        this.array = array;
    }

    public json_blob(String token, int location) {
        this.token = token;
        this.needle = location;
    }

    public json_blob(String token, String datestamp) {
        this.token = token;
        this.datestamp = datestamp;
    }

    public json_blob(String token) { this.token = token; }
}
