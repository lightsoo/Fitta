package com.fitta.lightsoo.fitta.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fitta.lightsoo.fitta.Data.ClothesItems;
import com.fitta.lightsoo.fitta.View.ClothesImageItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-03-22.
 */
public class ImageAdapter extends BaseAdapter {

    List<ClothesItems> items = new ArrayList<ClothesItems>();
//    private static final int top =0, bottom =1, etc =2;


    @Override
    public int getCount() {
        return items.size();
    }

    //item리턴
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    //item번호 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    //출력할 view를 설정후 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClothesImageItemView view;
        if(convertView == null){
            view = new ClothesImageItemView(parent.getContext());
        }else{
            view = (ClothesImageItemView)convertView;
        }
//        view.setClotheImageView(items.get(position));
        return view;
    }
}
