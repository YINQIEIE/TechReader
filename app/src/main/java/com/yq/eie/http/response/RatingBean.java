package com.yq.eie.http.response;

import java.io.Serializable;

public class RatingBean implements Serializable {

    private static final long serialVersionUID = 3992111650402362169L;
    /**
     * max : 10
     * average : 5.9
     * stars : 30
     * min : 0
     */

    private int max;
    private double average;
    private String stars;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}