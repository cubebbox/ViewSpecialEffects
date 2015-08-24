package com.luo.activity;

import android.os.Bundle;
import android.view.View;

import com.luo.R;
import com.luo.ViewSpecialEffects.base.BaseSlideActivity;


public class RotationSlideActivity extends BaseSlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutID() {
        return R.layout.act_rotation_slide;
    }

    public void onclick(View view) {
        finish();
    }
}
