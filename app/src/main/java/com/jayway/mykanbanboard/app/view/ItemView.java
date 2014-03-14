package com.jayway.mykanbanboard.app.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jayway.mykanbanboard.app.MyKanbanApplication;
import com.jayway.mykanbanboard.app.R;
import com.jayway.mykanbanboard.app.model.Item;
import com.jayway.mykanbanboard.app.model.Item.Move;
import com.jayway.mykanbanboard.app.tasks.MoveFormPoster;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class ItemView extends LinearLayout {

    LinearLayout itemView;
    private TextView title;
    private TextView description;
    LinearLayout buttonContainer;
    private Item item;

    public ItemView(Context context, Item item) {
        super(context);
        this.item = item;
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        itemView = (LinearLayout)inflate(context, R.layout.item_layout, this);

        title = (TextView) itemView.findViewById(R.id.itemTitle);
        title.setText(item.getName());

        description = (TextView) itemView.findViewById(R.id.itemDescription);
        description.setText(item.getDescription());

        buttonContainer = (LinearLayout) itemView.findViewById(R.id.buttonContainer);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setDescription(String description){
        this.description.setText(description);
    }

    public void addMoveButton(Button button){
        buttonContainer.addView(button);
    }

}
