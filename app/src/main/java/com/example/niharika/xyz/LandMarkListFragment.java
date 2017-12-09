package com.example.niharika.xyz;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by niharika on 10/31/17.
 */

public class LandMarkListFragment extends ListFragment {

    private ListSelectionListener mListener = null;
    private static final String TAG = "Monument List Fragment";

    //Interface that MainActivity has to implement
    public interface ListSelectionListener {
        public void onListSelection(int index);

    }

    //when the user selects some landmark, MainActivity has to communicate
    //that with the web fragment to display the website
    public void onListItemClick(ListView l, View v, int pos, long id) {
        getListView().setItemChecked(pos, true);
        mListener.onListSelection(pos);
    }

    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement ListSelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        setRetainInstance(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        //Set List adapter to use monuments_list string array from main Activity

        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.landmark_item, MainActivity.monuments_list));

        // Make list view to allow only single selection
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }}