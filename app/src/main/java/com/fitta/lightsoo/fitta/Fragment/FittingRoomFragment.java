package com.fitta.lightsoo.fitta.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitta.lightsoo.fitta.R;

/**
 * Created by LG on 2016-03-17.
 */
public class FittingRoomFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fittingroom, container, false);
        return view;
    }
}
