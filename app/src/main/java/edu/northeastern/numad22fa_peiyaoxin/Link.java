package edu.northeastern.numad22fa_peiyaoxin;

public class Link {
    private final String name;
    private final String URL;

    public Link(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {
        return this.name;
    }

    public String getURL() {
        return this.URL;
    }
}
