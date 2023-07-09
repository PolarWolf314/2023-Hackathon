package com.example.hackoverflow.utils;

public class Plant {

    private String name;
    private String image;
    private String description;
    private boolean bookmark;

    public Plant(String name, String image, String description, boolean bookmark) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.bookmark = bookmark;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public boolean getBookmark() { return bookmark; }

    public void setBookmark(boolean favourite) {
        bookmark = favourite;
    }

    public boolean setBookmark(int intent) {
        if (intent > 0){
            bookmark = true;
        }
        else {
            bookmark = false;
        }
        return bookmark;
    }
}
