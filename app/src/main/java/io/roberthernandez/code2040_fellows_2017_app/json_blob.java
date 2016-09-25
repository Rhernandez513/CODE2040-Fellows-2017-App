package io.roberthernandez.code2040_fellows_2017_app;

/**
 * Created by robert on 9/25/16.
 */

public class json_blob {
    private String token;
    private String github;

    public json_blob(String token_value, String github_url) {
        this.token = token_value;
        this.github = github_url;
    }
}
