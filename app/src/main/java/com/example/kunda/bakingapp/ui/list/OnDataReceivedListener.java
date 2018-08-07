package com.example.kunda.bakingapp.ui.list;

import org.json.JSONArray;

/**
 * Created by Kundan on 07-08-2018.
 */
public interface OnDataReceivedListener {

    public void onResponse(JSONArray response);
    public void onError(String error);
}
