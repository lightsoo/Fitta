package com.fitta.lightsoo.fitta.ViewHolder;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fitta.lightsoo.fitta.R;


public class ClothesImageItemView extends FrameLayout{

//    ImageView imageView;

    TextView urlView;
    public ClothesImageItemView(Context context) {
        super(context);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.view_clothimage_item, this);
//        imageView = (ImageView)findViewById(R.id.image_clothes);
        urlView = (TextView)findViewById(R.id.url);
    }

    //일단 텍스트로 하고 이미지로 바꾼다.
    public void setClotheImageView(String url){
        urlView.setText(url);
//Glide


    }
}
