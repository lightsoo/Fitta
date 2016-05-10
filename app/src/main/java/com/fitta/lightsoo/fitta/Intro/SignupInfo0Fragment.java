package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitta.lightsoo.fitta.R;

public class SignupInfo0Fragment extends Fragment {

    Button btn_no, btn_yes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info0, container, false);
        init(view);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment info1 = new SignupInfo1Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, info1).commit();
            }
        });
        return view;
    }

    public void init(View view){
        btn_no =(Button)view.findViewById(R.id.btn_no);
        btn_yes =(Button)view.findViewById(R.id.btn_yes);
    }

    private void goLoginActivity(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
