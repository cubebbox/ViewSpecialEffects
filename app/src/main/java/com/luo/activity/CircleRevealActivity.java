package com.luo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.luo.R;
import com.luo.ViewSpecialEffects.base.BaseRevealActivity;


public class CircleRevealActivity extends BaseRevealActivity {

    private Button b1, b2, b3, b4, b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b1 = (Button) findViewById(R.id.cbutton);
        b2 = (Button) findViewById(R.id.cbutton1);
        b3 = (Button) findViewById(R.id.cbutton2);
        b4 = (Button) findViewById(R.id.cbutton3);
        b5 = (Button) findViewById(R.id.cbutton4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity((int) v.getX() + (v.getWidth() / 2), (int) v.getY() + (v.getHeight() / 2));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity((int) v.getX() + (v.getWidth() / 2), (int) v.getY() + (v.getHeight() / 2));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity((int) v.getX() + (v.getWidth() / 2), (int) v.getY() + (v.getHeight() / 2));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity((int) v.getX() + (v.getWidth() / 2), (int) v.getY() + (v.getHeight() / 2));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity((int) v.getX() + (v.getWidth() / 2), (int) v.getY() + (v.getHeight() / 2));
            }
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_circle_reveal;
    }

    public void onclick(View view) {
        finish();
    }
}
