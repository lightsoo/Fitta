package com.fitta.lightsoo.fitta.RadioLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class RadioButtonWithTableLayout extends TableLayout implements View.OnClickListener{
    private RadioButton mBtnCurrentRadio;

    public RadioButtonWithTableLayout(Context context) {
        super(context);
    }

    public RadioButtonWithTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {
        final RadioButton mBtnRadio = (RadioButton) v;

        // select only one radio button at any given time
        if (mBtnCurrentRadio != null) {
            mBtnCurrentRadio.setChecked(false);
        }
        mBtnRadio.setChecked(true);
        mBtnCurrentRadio = mBtnRadio;
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setChildrenOnClickListener((TableRow) child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        setChildrenOnClickListener((TableRow) child);
    }

    private void setChildrenOnClickListener(TableRow tr) {
        final int c = tr.getChildCount();
        for (int i = 0; i < c; i++) {
            final View v = tr.getChildAt(i);
            if (v instanceof RadioButton) {
                v.setOnClickListener(this);
            }
        }
    }

    //체크되어 있으면 true리턴
    public boolean getLayoutState(){
        if (mBtnCurrentRadio == null) {
            return false;
        }else{
            if(mBtnCurrentRadio.isChecked()){
                return true;
            }else {
                return false;
            }
        }

    }


    //현재 클릭된거 false로
    public void clearRadioButton(){
        if(mBtnCurrentRadio != null) {
            mBtnCurrentRadio.setChecked(false);
        }
    }
    //현재 체크되어있는지
    public int getCheckedRadioButtonId() {
        if ( mBtnCurrentRadio != null ) {
//            mBtnCurrentRadio.getText();
            return mBtnCurrentRadio.getId();
        }

        return -1;
    }

}
