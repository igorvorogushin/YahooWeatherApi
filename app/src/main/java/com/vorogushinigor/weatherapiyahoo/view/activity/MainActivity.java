package com.vorogushinigor.weatherapiyahoo.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.databinding.ActivityMainBinding;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Forecast;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Weather;
import com.vorogushinigor.weatherapiyahoo.view.fragment.MainFragment;
import com.vorogushinigor.weatherapiyahoo.viewmodel.ViewModelMain;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewModelMain.CallBack, MainFragment.CallBack {


    private ActivityMainBinding binding;
    private ViewModelMain viewModelMain;
    private MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        viewModelMain = ViewModelMain.getInstance(getApplicationContext());
        viewModelMain.setCallBack(this);
        startFragment();

        Handler handler = new Handler();
        if (savedInstanceState == null) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    viewModelMain.loadData();
                    viewModelMain.updateWeather();
                    invalidateOptionsMenu();
                }
            }, 100);
        } else {
            handler.postDelayed(new Runnable() {
                public void run() {
                    viewModelMain.loadData();
                }
            }, 100);
        }
    }


    @Override
    protected void onDestroy() {
        viewModelMain.onDestroy();
        if (mainFragment != null) mainFragment.removeCallBack();
        super.onDestroy();
    }

    private void startFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(MainFragment.class.getName());
        if (mainFragment == null)
            mainFragment = new MainFragment();
        mainFragment.setRetainInstance(true);
        mainFragment.setCallBack(this);

        fragmentTransaction.replace(R.id.frame, mainFragment, MainFragment.class.getName());
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (viewModelMain.isLoading()) {
            ViewGroup progressBar = (ViewGroup) getLayoutInflater().inflate(R.layout.menu_progressbar, null);
            menu.getItem(0).setActionView(progressBar);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            viewModelMain.updateWeather();
            invalidateOptionsMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateWeather(Weather weather) {
        if (mainFragment != null) mainFragment.updateWeather(weather);

    }

    @Override
    public void updateWeatherPast(List<Forecast> forecastList) {
        if (mainFragment != null) mainFragment.updateWeatherPast(forecastList);
    }

    @Override
    public void message(String message) {
        invalidateOptionsMenu();
        Snackbar snackbar = Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void initPageView(ViewPager viewPager) {
        binding.tab.setupWithViewPager(viewPager);
    }
}
