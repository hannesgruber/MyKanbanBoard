package com.jayway.mykanbanboard.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannes Gruber on 2014-03-12.
 */
public class Column {
    private String name;
    private List<Item> items;
    public Column(String name){
        this.name = name;
        items = new ArrayList<Item>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    @Override
    public String toString() {
        return "Column name=" + name + " items=" + items.toString();
    }
}
