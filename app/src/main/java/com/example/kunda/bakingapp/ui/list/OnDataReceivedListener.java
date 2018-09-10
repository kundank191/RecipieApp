package com.example.kunda.bakingapp.ui.list;

import org.json.JSONArray;

/**
 * Created by Kundan on 07-08-2018.
 * the activity will receive data through these interfaces
 */
public interface OnDataReceivedListener {

    void onResponse(JSONArray response);

    void onError(String error);
}
