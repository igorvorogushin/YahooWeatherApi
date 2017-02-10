package com.vorogushinigor.weatherapiyahoo.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.databinding.FragmentPastBinding;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Forecast;
import com.vorogushinigor.weatherapiyahoo.view.adapter.WeatherAdapter;

import java.util.List;


public class PastFragment extends Fragment {
    private FragmentPastBinding binding;
    private List<Forecast> list;
    private boolean visible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_past, container, false);
        View view = binding.getRoot();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerview.setLayoutManager(linearLayoutManager);
        return view;
    }

    public void showPast(List<Forecast> list) {
        this.list = list;
        update();
    }

    public void update() {
        if (visible && list != null)
            binding.recyclerview.setAdapter(new WeatherAdapter(list));
    }

    @Override
    public void onResume() {
        super.onResume();
        visible = true;
        update();
    }

    @Override
    public void onPause() {
        visible = false;
        super.onPause();
    }


}
