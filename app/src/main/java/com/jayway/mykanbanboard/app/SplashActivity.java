package com.jayway.mykanbanboard.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jayway.mykanbanboard.app.model.Model;

public class SplashActivity extends Activity implements MyKanbanApplication.ModelUpdateListener{

    MyKanbanApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        app = (MyKanbanApplication) getApplication();
        app.addModelUpdateListener(this);
        app.updateModel();
    }

    @Override
    public void onModelUpdated(Model model) {
        startActivity(new Intent(this, MainActivity.class));
        app.removeModelUpdateListener(this);
        finish();
    }
}
