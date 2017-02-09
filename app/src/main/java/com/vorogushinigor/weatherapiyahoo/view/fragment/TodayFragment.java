package com.vorogushinigor.weatherapiyahoo.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.databinding.FragmentTodayBinding;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Weather;

public class TodayFragment extends Fragment {
    private FragmentTodayBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void setModel(Weather model) {
        binding.setModel(model);
    }

}
