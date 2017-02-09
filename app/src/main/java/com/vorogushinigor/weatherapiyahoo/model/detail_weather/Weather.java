package com.vorogushinigor.weatherapiyahoo.model.detail_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Results;

public class Weather {
    @SerializedName("query")
    @Expose
    private Query query;

    private String timeUpdate;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }
}
