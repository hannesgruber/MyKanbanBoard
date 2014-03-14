package com.jayway.mykanbanboard.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannes Gruber on 2014-03-12.
 */
public class Model {

    List<Column> columns;


    public Model(){
        columns = new ArrayList<Column>();
    }

    public void addColumn(Column column){
        columns.add(column);
    }

    public List<Column> getColumns(){
        return columns;
    }

    public Column getColumn(int i){
        return columns.get(i);
    }
}
