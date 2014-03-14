package com.jayway.mykanbanboard.app.tasks;

import android.os.AsyncTask;

import com.jayway.mykanbanboard.app.MyKanbanApplication;
import com.jayway.mykanbanboard.app.model.Item;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MoveFormPoster extends AsyncTask<Item.Move, Integer, Void> {

    private Callback callback;

    public interface Callback{
        public void onMoveFormPosterComplete();
    }

    public MoveFormPoster(Callback callback){
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Item.Move... moves) {
        Item.Move move = moves[0];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(MyKanbanApplication.API_URL + move.getAction());
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("id", move.getId()));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpClient.execute(httpPost);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(callback != null){
            callback.onMoveFormPosterComplete();
        }
    }
}