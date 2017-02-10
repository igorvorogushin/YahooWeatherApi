package com.vorogushinigor.weatherapiyahoo.view.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.databinding.FragmentMainBinding;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Forecast;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Weather;
import com.vorogushinigor.weatherapiyahoo.view.adapter.TabsAdapter;

import java.util.List;


public class MainFragment extends Fragment {

    private CallBack callBack;

    public interface CallBack {
        void initPageView(ViewPager viewPager);
    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private TodayFragment todayFragment;
    private WeekFragment weekFragment;
    private PastFragment pastFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View view = binding.getRoot();

        if (todayFragment == null)
            todayFragment = new TodayFragment();
        todayFragment.setRetainInstance(true);

        if (weekFragment == null)
            weekFragment = new WeekFragment();
        weekFragment.setRetainInstance(true);

        if (pastFragment == null)
            pastFragment = new PastFragment();
        pastFragment.setRetainInstance(true);


        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addFrag(todayFragment, getString(R.string.tab_today));
        tabsAdapter.addFrag(weekFragment, getString(R.string.tab_week));
        tabsAdapter.addFrag(pastFragment, getString(R.string.tab_past));
        binding.viewpager.setAdapter(tabsAdapter);

        if (callBack != null) callBack.initPageView(binding.viewpager);

        return view;
    }


    public void updateWeather(Weather weather) {
        if (weekFragment != null)
            weekFragment.showWeek(weather.getQuery().getResults().getChannel().getItem().getForecast());
        if (todayFragment != null)
            todayFragment.setModel(weather);
    }

    public void updateWeatherPast(List<Forecast> forecastList) {
        if (pastFragment != null) pastFragment.showPast(forecastList);
    }

    public void removeCallBack() {
        callBack = null;
    }

}
