package com.vorogushinigor.weatherapiyahoo.model;

import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface YahooApi {
    @GET("v1/public/yql")
    Observable<Weather> getWeather(@Query("q") String query, @Query("format") String format);

}
