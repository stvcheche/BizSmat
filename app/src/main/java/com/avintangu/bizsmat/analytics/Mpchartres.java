package com.avintangu.bizsmat.analytics;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class Mpchartres {
    private static Context mpchrtCtx;

    public Mpchartres(Context context) {
        mpchrtCtx = context;

    }


    /* bar chart */
    public void barChart(ArrayList<ArrayList> comblist, BarChart barChart, Integer color) {
        /* Set up bar chart */
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setFitBars(true);
        barChart.getLegend().setEnabled(false);
        barChart.setExtraBottomOffset(50);
        barChart.setXAxisRenderer(new Mpchartcusto(barChart.getViewPortHandler(), barChart.getXAxis(), barChart.getTransformer(YAxis.AxisDependency.LEFT)));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter((ArrayList<String>) comblist.get(0));
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        /* Set data */
        ArrayList<BarEntry> data = new ArrayList<>();

        for (int ghas = 0; ghas < comblist.get(1).size(); ghas++) {
            data.add(new BarEntry(ghas, (float) comblist.get(1).get(ghas)));

        }

        BarDataSet barDataSet = new BarDataSet(data, "Total revenue");
        barDataSet.setColors(color);
        barDataSet.setDrawValues(false);
        BarData barData = new BarData(barDataSet);
        barData.setValueFormatter(new LargeValueFormatter());

        barChart.setData(barData);
        barChart.animateXY(500, 500);
        barChart.invalidate();
    }

    /* Multi bars chart */
    public void multiBarchrt(ArrayList<ArrayList> comblist, ArrayList<String> legend, ArrayList<Integer> colox,
                             BarChart mbarChart) {
        /* Set up multi bar chart */
        mbarChart.getDescription().setEnabled(false);
        mbarChart.getAxisRight().setEnabled(false);
        mbarChart.setFitBars(true);
        mbarChart.setXAxisRenderer(new Mpchartcusto(mbarChart.getViewPortHandler(), mbarChart.getXAxis(), mbarChart.getTransformer(YAxis.AxisDependency.LEFT)));

        XAxis xAxis = mbarChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter((ArrayList<String>) comblist.get(0));
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);


        BarData barData = new BarData();
        for (int pghas = 1; pghas < comblist.size(); pghas++) {
            ArrayList<BarEntry> data = new ArrayList<>();

            for (int ghas = 0; ghas < comblist.get(pghas).size(); ghas++) {
                data.add(new BarEntry(ghas, (float) comblist.get(pghas).get(ghas)));

            }

            BarDataSet barDataSet = new BarDataSet(data, legend.get(pghas - 1));
            barDataSet.setColors(colox.get(pghas - 1));
            barDataSet.setDrawValues(false);
            barData.addDataSet(barDataSet);

        }
        mbarChart.setData(barData);

        float barspace = 0f;
        float groupsapce = 0.4f;
        mbarChart.getBarData().setBarWidth(0.2f);
        mbarChart.getXAxis().setAxisMinimum(0.1f);
        mbarChart.getXAxis().setAxisMaximum(0.1f + (mbarChart.getBarData().getGroupWidth(groupsapce, barspace) * comblist.get(0).size()));
        mbarChart.groupBars(0.1f, groupsapce, barspace);

        mbarChart.animateXY(500, 500);
        mbarChart.invalidate();

    }


    /* Stack chart */
    public void stackBarchrt(ArrayList<ArrayList> comblist, ArrayList<Integer> colox, BarChart mbarChart,
                             ArrayList<String> labels) {

        /* Set up multi bar chart */
        mbarChart.getDescription().setEnabled(false);
        mbarChart.getAxisRight().setEnabled(false);
        mbarChart.setFitBars(true);
        mbarChart.setXAxisRenderer(new Mpchartcusto(mbarChart.getViewPortHandler(), mbarChart.getXAxis(), mbarChart.getTransformer(YAxis.AxisDependency.LEFT)));

        XAxis xAxis = mbarChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter((ArrayList<String>) comblist.get(0));
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        Legend lgnd = mbarChart.getLegend();
        lgnd.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lgnd.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lgnd.setDrawInside(false);
        lgnd.setFormSize(8f);
        lgnd.setFormToTextSpace(4f);
        lgnd.setXEntrySpace(12f);

        ArrayList<BarEntry> dataentry = new ArrayList<>();

        for (int pghas = 1; pghas < comblist.size(); pghas++) {
            ArrayList<Float> drew = new ArrayList<>();

            for (int ghas = 0; ghas < comblist.get(1).size(); ghas++) {
                drew.add((Float) comblist.get(pghas).get(ghas));
            }

            float[] drewmod = new float[drew.size()];
            for (int i = 0; i < drewmod.length; i++) {
                drewmod[i] = drew.get(i);
            }

            dataentry.add(new BarEntry(pghas - 1, drewmod));

        }

        BarDataSet barDataSet = new BarDataSet(dataentry, "");
        barDataSet.setColors(colox);
        barDataSet.setDrawValues(false);

        String[] labelmod = new String[labels.size()];
        for (int i = 0; i < labels.size(); i++) {
            labelmod[i] = labels.get(i);
        }
        barDataSet.setStackLabels(labelmod);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData barData = new BarData(dataSets);
        barData.setValueFormatter(new StackedValueFormatter(false, "", 1));
        barData.setValueTextColor(Color.WHITE);

        mbarChart.setData(barData);
        mbarChart.animateXY(500, 500);
        mbarChart.invalidate();

    }

}


