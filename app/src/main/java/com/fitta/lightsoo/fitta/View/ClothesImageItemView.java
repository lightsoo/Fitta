package com.fitta.lightsoo.fitta.View;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.R;

/**
 * viewHolder패턴을 사용하는 이유
 * 매번 findView를 해야되서 낭비가 심하지만
 */

public class ClothesImageItemView extends FrameLayout{

    ImageView imageView;

    TextView urlView;
    public ClothesImageItemView(Context context) {
        super(context);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.view_clothimage_item, this);
        imageView = (ImageView)findViewById(R.id.image_clothes);
//        urlView = (TextView)findViewById(R.id.url);
    }

    //일단 텍스트로 하고 이미지로 바꾼다.
    public void setClotheImageView(String url){
//        urlView.setText(url);

        //Glide
        Glide.with(getContext())
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);


    }
}
