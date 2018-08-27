package com.example.kunda.bakingapp.utils;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.kunda.bakingapp.ui.list.OnDataReceivedListener;

import org.json.JSONArray;

/**
 * Created by Kundan on 07-08-2018.
 */
public class NetworkUtils {

    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     *
     * @param context from where recipe data has been requested
     */
    public static void getRecipeData(Context context){
        requestData(context,BASE_URL);
    }

    /**
     *
     * @param context from where data has been requested
     * @param BASE_URL for the download
     */
    private static void requestData(Context context, String BASE_URL){

        final OnDataReceivedListener listener = (OnDataReceivedListener) context;

        AndroidNetworking.get(BASE_URL)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onError(anError.toString());
                    }
                });
    }

}
