package com.example.imoocpiechart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PieFragment extends Fragment implements OnChartValueSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATA_KEY = "piefragment_data_key";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MonthBean mData;
    //private String mParam2;
    private PieChart mChart;
    private TextView tvDes;
    private CharSequence centerText;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PieFragment newInstance(MonthBean data) {
        PieFragment fragment = new PieFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        TextView textView = new TextView(getContext());
//        textView.setText(mData);
//
//        return textView;
        View inflate = inflater.inflate(R.layout.fragment_pie,null);
        mChart = inflate.findViewById(R.id.pc_chart);
        tvDes = inflate.findViewById(R.id.tv_des);
        initView();
        return inflate;
    }

    private void initView() {
        setData();
        mChart.setCenterText(getCenterText());
        mChart.getData().getDataSet().setDrawValues(false);
        mChart.getLegend().setEnabled(false);
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setRotationEnabled(true);
        mChart.setOnChartValueSelectedListener(this);
    }

    private void setData() {

        //List<String> titles = new ArrayList<>();
        List<PieEntry> entrys = new ArrayList<>();
        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            //titles.add(pieBean.title);
            PieEntry pieEntry = new PieEntry(pieBean.value,i);
            pieEntry.setX(i);
            entrys.add(pieEntry);
        }
        PieDataSet dataSet = new PieDataSet(entrys,"");
        dataSet.setColors(new int[]{Color.rgb(216,77,200),
                Color.rgb(183,56,63), Color.rgb(247,85,47)});
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(22);
        mChart.setData(pieData);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        float proportion = 360f/mData.getSum();
        float angle = 90 - mData.obj.get((int) e.getX()).value*proportion/2 - mData.getSum((int) e.getX())*proportion;
        mChart.setRotationAngle(angle);
        upDateDesText((int) e.getX());

    }

    private void upDateDesText(int x) {
        tvDes.setText(mData.obj.get(x).title+":"+mData.obj.get(x).value);
    }

    @Override
    public void onNothingSelected() {

    }

    public CharSequence getCenterText() {
        CharSequence centerText = "总支出\n" + mData.getSum() + "元";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(64, true), 3, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}