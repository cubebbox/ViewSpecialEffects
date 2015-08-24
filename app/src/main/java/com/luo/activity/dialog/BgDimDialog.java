package com.luo.activity.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.luo.R;
import com.luo.ViewSpecialEffects.Interface.OnViewChangeListener;
import com.luo.ViewSpecialEffects.base.BaseBgDimDialog;
import com.luo.ViewSpecialEffects.utils.L;
import com.luo.activity.MainActivity;

/**
 * Created by luozi on 2015/8/23.
 */
public class BgDimDialog extends BaseBgDimDialog implements OnViewChangeListener {
    private Button exitBtn, anim;

    public BgDimDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exitBtn = (Button) findViewById(R.id.dialog_bg_dim_exit);
        anim = (Button) findViewById(R.id.dialog_bg_dim_anim);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        anim.setText("开始动画");
        anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.isPlaying) {
                    MainActivity.isPlaying = false;
                    anim.setText("开始动画");

                } else {
                    MainActivity.isPlaying = true;
                    anim.setText("关闭动画");
                }
                MainActivity.startAnim();
            }
        });

        MainActivity.setOnViewChangeListener(this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.dialog_bg_dim;
    }

    @Override
    public void onChange() {
        updateBgBluringView();
    }

    @Override
    protected void startAnimExit() {
        L.d("dialog 开始播放动画退出");
    }
}
