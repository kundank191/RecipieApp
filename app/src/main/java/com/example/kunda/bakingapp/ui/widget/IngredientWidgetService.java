package com.example.kunda.bakingapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Kundan on 28-08-2018.
 */
public class IngredientWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new com.example.kunda.bakingapp.ui.widget.RemoteViewsFactory(intent,null);
    }
}
