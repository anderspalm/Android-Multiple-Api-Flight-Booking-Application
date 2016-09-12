package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class AgentWebView extends BottomBarAbstractClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_web_view);

        Intent intent = getIntent();
        String bookingUrl = intent.getStringExtra("url");
        WebView view = (WebView) findViewById(R.id.agent_web_view);
        view.loadUrl(bookingUrl);

    }

    @Override
    public void bottomBar(Bundle SavedState, Context context) {

    }
}
