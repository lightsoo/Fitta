package com.fitta.lightsoo.fitta.ViewHolder;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fitta.lightsoo.fitta.R;

/**
 * Created by LG on 2016-03-22.
 */
public class ClothesImageItemView extends FrameLayout{

    ImageView imageView;
    public ClothesImageItemView(Context context) {
        super(context);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.view_clothimage_item, this);
        imageView = (ImageView)findViewById(R.id.image_clothes);
    }

    public void setClotheImageView(){

    }
}
