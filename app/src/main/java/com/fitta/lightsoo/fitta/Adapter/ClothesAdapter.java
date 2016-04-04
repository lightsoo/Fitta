package com.fitta.lightsoo.fitta.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fitta.lightsoo.fitta.View.ClothesImageItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-03-22.
 */
public class ClothesAdapter extends BaseAdapter {

    List<String> items = new ArrayList<String>();

    public void add(String url){
        items.add(url);
        notifyDataSetChanged();
    }

    public void addAll(List<String> url){
        items.addAll(url);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //뷰홀더에서다가 데이터를 입력해서 리턴함으로서 출력하는거야.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClothesImageItemView view;
        if(convertView == null){
            view = new ClothesImageItemView(parent.getContext());
        }else{
            view = (ClothesImageItemView)convertView;
        }
        //객체리턴했어
        view.setClotheImageView(items.get(position));
        return view;

    }
}
