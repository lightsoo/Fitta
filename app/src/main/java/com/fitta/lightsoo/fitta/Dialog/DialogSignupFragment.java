package com.fitta.lightsoo.fitta.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fitta.lightsoo.fitta.R;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;


/**
 * 이후 폰트수정 +
 */

public class DialogSignupFragment extends DialogFragment {
    private RelativeLayout layoutPlace;
    private ImageView logo;

    /**
     * Colors
     */
    private int startColor;
    private int endColor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        //여기서 전체 배경을 정한다음에 onCreateView에서 커스텀 로딩바를 만든다.
        setStyle(STYLE_NO_TITLE, R.style.custom_signup_dialog);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_signup, container, false);
//        background = (ImageView)view.findViewById(R.id.signup_background);
//        Glide.with(getContext())
//                .load(R.drawable.body100cf)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(background);

//        RubberLoaderView rubberLoaderView = (RubberLoaderView)view.findViewById(R.id.loaderview);
//        rubberLoaderView.startLoading();

        DotProgressBar dotProgressBar = (DotProgressBar)view.findViewById(R.id.dot_progress_bar);

        startColor = getResources().getColor(com.github.silvestrpredko.dotprogressbar.R.color.black);
        endColor = getResources().getColor(com.github.silvestrpredko.dotprogressbar.R.color.black);
        dotProgressBar.setStartColor(startColor);
        dotProgressBar.setEndColor(endColor);
        dotProgressBar.setDotAmount(5);
        dotProgressBar.setVisibility(View.VISIBLE);

//        layoutPlace = (LinearLayout) view.findViewById(R.id.signup_background);
//        layoutPlace = (RelativeLayout)view.findViewById(R.id.signup_background);
//        Glide.with(getContext())
//                .load(R.drawable.body100cf)
//                .asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Drawable drawable = new BitmapDrawable(resource);
//                layoutPlace.setBackground(drawable);
//            }
//        });


        return view;

    }
}
