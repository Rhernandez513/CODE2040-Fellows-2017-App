package io.roberthernandez.code2040_fellows_2017_application;

/**
 * Created by robert on 9/25/16.
 */

public class json_blob {
    private String token;
    private String token_value;
    private String github;
    private String github_url;

    public json_blob(String token, String token_value, String github, String github_url) {
        this.token = token;
        this.token_value = token_value;
        this.github = github;
        this.github_url = github_url;
    }
}
