package com.fitta.lightsoo.fitta.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitta.lightsoo.fitta.R;

public class ClothesDialogFragment extends DialogFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //다이얼로그의 툴바를 없애고, custom_dialog상속
        setStyle(STYLE_NO_TITLE, R.style.custom_dialog);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_clothes, container, false);
        return view;
    }
}

