package com.vorogushinigor.weatherapiyahoo.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.databinding.FragmentTodayBinding;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Weather;

public class TodayFragment extends Fragment {
    private FragmentTodayBinding binding;
    private Weather model;
    private boolean visible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void setModel(Weather model) {
        this.model = model;
        update();
    }

    public void update() {
        if (visible && model != null)
            binding.setModel(model);
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
