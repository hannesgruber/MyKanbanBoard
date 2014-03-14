package com.jayway.mykanbanboard.app;

import android.util.Log;

import com.jayway.mykanbanboard.app.model.Column;
import com.jayway.mykanbanboard.app.model.Item;
import com.jayway.mykanbanboard.app.model.Model;
import com.jayway.mykanbanboard.app.model.Item.Move;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Hannes Gruber on 2014-03-11.
 */
public class HTMLParser {



    public HTMLParser(){

    }

    public Model parse(String url){

        Model model = new Model();

        Document document = null;

        try {
            document = Jsoup.connect(url).timeout(1000*10).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(document != null){

            Elements columns = document.select(".h-column");
            Column column;
            for (Element columnElement : columns){
                String columnName = columnElement.select(".p-name").get(0).text();
                column = new Column(columnName);

                Elements items = columnElement.select(".h-item");
                for (Element itemElement : items){
                    String itemName = itemElement.select(".p-name").get(0).text();
                    String itemDesc = itemElement.select(".p-description").get(0).text();

                    Item item = new Item(itemName, itemDesc);

                    Elements forms = itemElement.select("form");
                    for(Element formElement : forms){
                        String action = formElement.attr("action");
                        String title = formElement.attr("title");
                        String id = formElement.select("input[name=id]").get(0).attr("value");
                        item.addMove(action, title, id);
                    }

                    column.addItem(item);
                }

                model.addColumn(column);
            }

        }


        return model;
    }

}
