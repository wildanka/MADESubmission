package com.wildanka.moviecatalogue.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReleaseTodayData {
    @SerializedName("results")
    private List<ReleaseTodayResult> releaseTodayResults;

    public List<ReleaseTodayResult> getReleaseTodayResults() {
        return releaseTodayResults;
    }
}
