package com.example.niharika.xyz;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by niharika on 10/31/17.
 */

public class MonumentWebFragment extends Fragment {
    private static final String TAG = "MonumentWebFragment";
    private int mCurrIdx = -1;
    private int landmarkUrlsLen;
    WebView landmarkView;


    //retruns current index
    int getShownIndex() {
        return mCurrIdx;
    }


    // Show the web page of the landmark at position newIndex
    void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= landmarkUrlsLen)
            return;
        mCurrIdx = newIndex;
        landmarkView=(WebView) getActivity().findViewById(R.id.webView);
        WebSettings webSettings=landmarkView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        landmarkView.loadUrl(MainActivity.monument_urls_list[mCurrIdx]);
    }


    @Override
    public void onSaveInstanceState(final Bundle outState){
        super.onSaveInstanceState(outState);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Entered Web OnCreate()");
        super.onCreate(savedInstanceState);

        //saving the state using setRetainInstance
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.web_fragment, container, false);
    }


    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        landmarkView = (WebView) getActivity().findViewById(R.id.webView);
        landmarkView.setWebViewClient(new WebViewClient());

        landmarkUrlsLen = MainActivity.monument_urls_list.length;

    }



}