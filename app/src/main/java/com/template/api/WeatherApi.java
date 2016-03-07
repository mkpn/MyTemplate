package com.template.api;

import com.template.entity.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by makoto on 2016/03/04.
 */
public interface WeatherApi {
    @GET("/forecast/webservice/json/v1")
    Observable<Weather> getWeather(@Query("city") final String city);
}
