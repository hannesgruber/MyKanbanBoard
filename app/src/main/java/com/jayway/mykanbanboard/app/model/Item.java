package com.jayway.mykanbanboard.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannes Gruber on 2014-03-12.
 */
public class Item {
    private String name;
    private String description;
    private List<Move> moves;

    public Item(String name, String description){
        this.name = name;
        this.description = description;
        moves = new ArrayList<Move>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addMove(String action, String title, String id){
        moves.add(new Move(action, title, id));
    }

    public List<Move> getMoves(){
        return moves;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Move {
        private String action;
        private String title;
        private String id;

        public Move(String action, String title, String id) {
            this.action = action;
            this.title = title;
            this.id = id;
        }

        public String getAction() {
            return action;
        }

        public String getTitle() {
            return title;
        }

        public String getId() {
            return id;
        }
    }
}
