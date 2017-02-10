package com.vorogushinigor.weatherapiyahoo.view.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vorogushinigor.weatherapiyahoo.R;
import com.vorogushinigor.weatherapiyahoo.databinding.AdapterWeekBinding;
import com.vorogushinigor.weatherapiyahoo.model.detail_weather.Forecast;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Forecast> listForecasts;


    class ViewHolderWeek extends RecyclerView.ViewHolder {

        private AdapterWeekBinding binding;

        ViewHolderWeek(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }


    public WeatherAdapter(List<Forecast> listForecasts) {
        this.listForecasts = listForecasts;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolderWeek(AdapterWeekBinding.inflate(inflater, parent, false).getRoot());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Forecast forecast = listForecasts.get(position);
        ((ViewHolderWeek) holder).binding.setModel(forecast);
    }

    @Override
    public int getItemCount() {
        return listForecasts.size();
    }


    @BindingAdapter("bind:imageCode")
    public static void loadImageCode(ImageView imageView, String code) {
        String path = String.format("http://l.yimg.com/a/i/us/we/52/%s.gif", code);
        Picasso.with(imageView.getContext())
                .load(path)
                .error(R.mipmap.na3200)
                .placeholder(R.mipmap.na3200)
                .into(imageView);
    }

    @BindingAdapter("bind:text")
    public static void text(TextView textView, String text) {
        textView.setText(text);
    }

    @BindingAdapter("bind:temperature")
    public static void temperature(TextView textView, String text) {
        textView.setText(text + "\u00b0");
    }


}
