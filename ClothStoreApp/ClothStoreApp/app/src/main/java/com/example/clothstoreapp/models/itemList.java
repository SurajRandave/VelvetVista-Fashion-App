package com.example.clothstoreapp.models;

import java.util.List;

// Updated to use your custom 'Item' class
public class itemList {

    private List<item> item;  // List should hold 'Item' objects (your custom class)

    // Getter for 'items' list
    public List<item> getitem() {
        return item;
    }

    // Setter for 'items' list
    public void setitem(List<item> items) {
        this.item = items;
    }
}
