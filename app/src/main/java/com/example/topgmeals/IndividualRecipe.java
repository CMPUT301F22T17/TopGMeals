package com.example.topgmeals;

public class IndividualRecipe {

    private String recipe_title;
    private String prep_time;
    private String serving_count;
    private String category;
    private String comments;

    public IndividualRecipe(String recipe_title, String prep_time, int serving_count, String category, String comments) {
        this.recipe_title = recipe_title;
        this.prep_time = prep_time;
        this.serving_count = Integer.toString(serving_count);
        this.category = category;
        this.comments = comments;
    }

    public String getRecipe_title() {
        return recipe_title;
    }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }

    public String getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(String prep_time) {
        this.prep_time = prep_time;
    }

    public String getServing_count() {
        return serving_count;
    }

    public void setServing_count(String serving_count) {
        this.serving_count = serving_count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
