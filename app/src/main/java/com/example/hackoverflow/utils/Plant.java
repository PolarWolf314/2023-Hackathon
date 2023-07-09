package com.example.hackoverflow.utils;

public class Plant {

    private String name;
    private String image;
    private String scientificName;
    private String familyName;
    private boolean bookmark;

    public Plant(String name, String scientificName, String familyName, String image, boolean bookmark) {
        this.name = name;
        this.scientificName = name;
        this.familyName = familyName;
        this.image = image;
        this.bookmark = bookmark;
    }

    public String getName() { return name; }
    public String getScientificName() { return scientificName; }
    public String getFamilyName() { return familyName; }
    public String getImage() { return image; }
    public boolean getBookmark() { return bookmark; }

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
