package io.roberthernandez.code2040_fellows_2017_app;

/**
 * Created by robert on 9/25/16.
 */

public class json_blob {
    private String token;
    private String string;  // Arbitrary field name according to what the API endpoint expects

    public json_blob(String token_value, String string) {
        this.token = token_value;
        this.string = string;
    }
    public json_blob(String token_value) {
        this.token = token_value;
    }
}
