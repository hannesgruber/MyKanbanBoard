package com.jayway.mykanbanboard.app;

import android.app.Application;
import android.os.AsyncTask;

import com.jayway.mykanbanboard.app.model.Item;
import com.jayway.mykanbanboard.app.model.Model;
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
 * Created by Hannes Gruber on 2014-03-12.
 */
public class MyKanbanApplication extends Application {

    public static String API_URL="http://kanban-api.herokuapp.com/";

    Model model;
    List<ModelUpdateListener> modelListeners = new ArrayList<ModelUpdateListener>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Model getModel(){
        return model;
    }

    public void updateModel(){
        HTMLDownloader downloader = new HTMLDownloader();
        downloader.execute(API_URL);
    }

    public void addModelUpdateListener(ModelUpdateListener l){
        modelListeners.add(l);
    }

    public void removeModelUpdateListener(ModelUpdateListener l){
        modelListeners.remove(l);
    }

    private void onModelUpdated(Model newModel){
        model = newModel;
        for(ModelUpdateListener l : modelListeners){
            l.onModelUpdated(model);
        }
    }

    public interface ModelUpdateListener {
        public void onModelUpdated(Model model);
    }

    private class HTMLDownloader extends AsyncTask<String, Integer, Model>{

        @Override
        protected Model doInBackground(String... url) {
            HTMLParser parser = new HTMLParser();
            return parser.parse(url[0]);
        }

        @Override
        protected void onPostExecute(Model newModel) {
            super.onPostExecute(newModel);
            onModelUpdated(newModel);
        }
    }

}
