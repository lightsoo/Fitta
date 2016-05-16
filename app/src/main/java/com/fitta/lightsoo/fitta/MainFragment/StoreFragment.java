package com.fitta.lightsoo.fitta.MainFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.Dialog.DialogLoadingFragment;
import com.fitta.lightsoo.fitta.R;
public class StoreFragment extends Fragment {

    private Button btn_dialog;
    private ImageView iv_store;
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        iv_store = (ImageView)view.findViewById(R.id.iv_store);
        Glide.with(getContext())
                .load(R.drawable.iv_store)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_store);

//        background_store = (RelativeLayout)view.findViewById(R.id.background_store);
//        Glide.with(getContext())
//                .load(R.drawable.background_store)
//                .asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Drawable drawable = new BitmapDrawable(resource);
//                background_store.setBackground(drawable);
//            }
//        });

        btn_dialog = (Button)view.findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogLoadingFragment dialog = new DialogLoadingFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "loading");

                //로딩 다이얼로그
//                Toast.makeText(getActivity(), "버튼클릭!", Toast.LENGTH_SHORT).show();
                //지금 스레드로 해뒀는데, 나중에 통신으로 바꿔줘야된다.
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent intent = new Intent(getActivity(), FittingInfoActivity.class);
//                        intent.putExtra("flag", REQUEST_GALLERY);
//                        startActivityForResult(intent, FITTING_RESULT);
                        dialog.dismiss();
                    }
                }, 3500);
            }
        });
        return view;
    }
}
