package com.example.kunda.bakingapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Kundan on 12-08-2018.
 * <p>
 * Gives back an instance of RecipeViewModel
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public ViewModelFactory() {

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel();
    }
}
