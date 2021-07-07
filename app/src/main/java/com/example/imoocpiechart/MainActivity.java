package com.example.imoocpiechart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.Month;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager vpMain;
    //private String mJson = "[{\"date\":\"2021年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]},{\"date\":\"2021年4月\",\"obj\":[{\"title\":\"奶茶\",\"value\":33},{\"title\":\"办公\",\"value\":26},{\"title\":\"其他\",\"value\":40}]}]";
    private String mJson = "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34}," +
            "{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]}," +
            "{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"外卖\",\"value\":32}," +
            "{\"title\":\"娱乐\",\"value\":22},{\"title\":\"其他\",\"value\":42}]}," +
            "{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"外卖\",\"value\":34}," +
            "{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":24}]}," +
            "{\"date\":\"2016年8月\",\"obj\":[{\"title\":\"外卖\",\"value\":145}," +
            "{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":124}]}]";

    private ArrayList<MonthBean> mData;
    private Button btPre;
    private Button btNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpMain = findViewById(R.id.vp_main);
        btPre = findViewById(R.id.bt_pre);
        btNext = (Button) findViewById(R.id.bt_next);
        btPre.setOnClickListener(this);
        btNext.setOnClickListener(this);

        initData();
        initView();
    }

    private void initData() {
        Gson gson = new Gson();
        mData = gson.fromJson(mJson,new TypeToken<ArrayList<MonthBean>>(){}.getType());
    }

    private void initView() {
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
        updateJumpText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pre:
                if (vpMain.getCurrentItem()!=0)
                    vpMain.setCurrentItem(vpMain.getCurrentItem()-1);
                break;
            case R.id.bt_next:
                if (vpMain.getCurrentItem()!=vpMain.getAdapter().getCount()-1)
                    vpMain.setCurrentItem(vpMain.getCurrentItem()+1);
                break;
        }
        updateJumpText();
    }

    private void updateJumpText() {
        if (vpMain.getCurrentItem()!=0)
            btPre.setText(handlerText(mData.get(vpMain.getCurrentItem()-1).date));
        else {

            btPre.setText("没有了！");
        }

        if (vpMain.getCurrentItem()!=vpMain.getAdapter().getCount()-1)
            btNext.setText(handlerText(mData.get(vpMain.getCurrentItem()+1).date));
        else {

            btNext.setText("没有了！");
        }
    }

    private CharSequence handlerText(String date) {
        return date.substring(date.indexOf("年")+1);
    }
}