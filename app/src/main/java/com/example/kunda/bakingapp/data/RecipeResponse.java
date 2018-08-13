package com.example.kunda.bakingapp.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kundan on 07-08-2018.
 * Recipe Response POJO for the data we get from internet
 */
public class RecipeResponse implements Serializable{

    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<Ingredient> ingredients;

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    private ArrayList<Step> steps;

    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    private int servings;

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    private String image;

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public class Ingredient implements Serializable{
        private double quantity;

        public double getQuantity() {
            return this.quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        private String measure;

        public String getMeasure() {
            return this.measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        private String ingredient;

        public String getIngredient() {
            return this.ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }
    }

    public class Step implements Serializable{
        private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private String shortDescription;

        public String getShortDescription() {
            return this.shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        private String description;

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        private String videoURL;

        public String getVideoURL() {
            return this.videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        private String thumbnailURL;

        public String getThumbnailURL() {
            return this.thumbnailURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
        }
    }

}
