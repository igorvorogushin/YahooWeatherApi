package com.vorogushinigor.weatherapiyahoo.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.model.YahooApi;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ViewModelMain extends BaseObservable implements ViewModel {

    private static final String TAG_LOG = ViewModelMain.class.getName();
    private static final String URL = "https://QUERY.yahooapis.com/";
    private static final String CITY = "novosibirsk";
    private static final String UNIT = "c";
    private static final String QUERY = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + UNIT + "'", CITY);

    private YahooApi yahooApi;
    private Weather weather;
    private Observable<Weather> observable;
    private Subscription subscription;
    private Subscriber<Weather> subscriber;

    private boolean loading = false;


    public interface CallBack {

        void updateWeather(Weather weather);

        void message(String message);
    }

    private static ViewModelMain viewModel;
    private CallBack callBack;
    private Context context;


    public static ViewModelMain getInstance(Context context) {
        if (viewModel == null)
            viewModel = new ViewModelMain();
        viewModel.setContext(context);
        return viewModel;
    }

    private ViewModelMain() {

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void updateWeather() {
        createRetrofit();
        createSubscriber();
        createObservable();
        Log.i(TAG_LOG, QUERY);
    }

    public boolean isLoading() {
        return loading;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    private void createRetrofit() {
        yahooApi = new Retrofit.Builder()
                .baseUrl(URL).client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(YahooApi.class);
    }

    private void createSubscriber() {
        subscriber = new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                Log.v(TAG_LOG, "onCompleted");
                loading = false;
                if (callBack != null) callBack.message(context.getString(R.string.menu_update));

            }

            @Override
            public void onStart() {
                super.onStart();
                Log.v(TAG_LOG, "onStart");
                loading = true;
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAG_LOG, "onError " + e.getMessage());
                loading = false;
                if (callBack != null)
                    callBack.message(context.getString(R.string.error) + ": " + e.getMessage());


            }

            @Override
            public void onNext(Weather _weather) {
                Log.v(TAG_LOG, "onNext");
                weather = _weather;
                weather.setTimeUpdate(getCurrentTime());
                if (callBack != null)
                    callBack.updateWeather(weather);
                saveWeather(weather);

            }
        };

    }

    private void createObservable() {
        observable = yahooApi.getWeather(QUERY, "json");
        subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(subscriber);
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        return df.format(c.getTime());
    }

    public void loadData() {
        if (callBack != null) {
            if (weather == null)
                weather = loadWeather();
            if (weather != null) callBack.updateWeather(weather);
        }

    }

    private void saveWeather(Weather weather) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(weather);
        ed.putString(Weather.class.getName(), json);
        ed.commit();
    }

    private Weather loadWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Weather.class.getName(), "");
        return gson.fromJson(json, Weather.class);
    }

    @Override
    public void onDestroy() {
        callBack = null;
        context = null;
    }
}