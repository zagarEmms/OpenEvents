package com.example.openevents.business;

import retrofit2.Response;

public class Statistic {

    private String avg_score;
    private int num_comments;
    private String percentage_commenters_below;

    public Statistic(String avg_score, int num_comments, String percentage_commenters_below) {
        this.avg_score = avg_score;
        this.num_comments = num_comments;
        this.percentage_commenters_below = percentage_commenters_below;
    }

    public String getAvg_score() {
        return avg_score;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public String getPercentage_commenters_below() {
        return percentage_commenters_below;
    }
}
