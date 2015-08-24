package com.luo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.luo.R;
import com.luo.ViewSpecialEffects.Interface.OnActivityChangeListener;
import com.luo.ViewSpecialEffects.base.BaseSlideActivity;
import com.luo.ViewSpecialEffects.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TransSlideActivity extends BaseSlideActivity implements OnActivityChangeListener {
    private List<HashMap<String, String>> list;

    private ListView listView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.act_trans_listview);

        list = new ArrayList<HashMap<String, String>>();
        setOnActivityChangeListener(this);//页面变化监听
        initData();
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_trans_slide;
    }


    public void initData() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("t1", "1000");
        map.put("t2", "xinyongka");
        map.put("t3", "2013-12-5");
        map.put("type", "shouru");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("t1", "600");
        map.put("t2", "xinyongka");
        map.put("t3", "2014-12-5");
        map.put("type", "shouru");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("t1", "545");
        map.put("t2", "zuche");
        map.put("t3", "2014-12-16");
        map.put("type", "zhichu");
        list.add(map);
        for (int i = 0; i < 100; i++) {
            map = new HashMap<String, String>();
            map.put("t1", "545");
            map.put("t2", "rent");
            map.put("t3", "2014-12-16");
            map.put("type", "zhichu");
            list.add(map);
        }
        listView.setAdapter(new CWLSAdapter(list));

    }

    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();
                    L.d("xy =" + x + "  " + y);
                    if (Math.abs((int) x) > Math.abs((int) y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    public class CWLSAdapter extends BaseAdapter {
        private List<HashMap<String, String>> list;
        private LayoutInflater layoutInflater;
        ViewHolder holder = null;

        public CWLSAdapter(List<HashMap<String, String>> list) {
            this.list = list;
            layoutInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.row_caiwuliushui,
                        null);
                initView(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            initData(position);
            return convertView;
        }

        private void initView(View view) {
            holder.txt1 = (TextView) view.findViewById(R.id.row_cwls_txt1);
            holder.txt2 = (TextView) view.findViewById(R.id.row_cwls_txt2);
            holder.txt3 = (TextView) view.findViewById(R.id.row_cwls_txt3);

        }

        private void initData(int positio) {
            String type = list.get(positio).get("type");
            if (type.equals("shouru")) {
                holder.txt1.setText("input:" + list.get(positio).get("t1"));
                holder.txt2.setText("tupe:" + list.get(positio).get("t2"));
                holder.txt3.setText("time:" + list.get(positio).get("t3"));
            } else {
                holder.txt1.setText("output:" + list.get(positio).get("t1"));
                holder.txt2.setText("why:" + list.get(positio).get("t2"));
                holder.txt3.setText("time:" + list.get(positio).get("t3"));
            }
        }

        class ViewHolder {
            TextView txt1, txt2, txt3;
        }
    }

    @Override
    public void scrollStart() {
        Log.d("TransSlideActivity", "滚动开始");
    }

    @Override
    public void scrolling(float currX, float finalX) {
        Log.d("TransSlideActivity", "滚动中");
    }

    @Override
    public void scrollEnd(boolean isExit) {
        Log.d("TransSlideActivity", "滚动结束");
    }
}
