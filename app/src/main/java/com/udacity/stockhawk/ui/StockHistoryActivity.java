package com.udacity.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.StockHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockHistoryActivity extends AppCompatActivity {

    /* constants */
    private static final String EXTRA_HISTORY = "extraHistory";

    /* UI */
    @BindView(R.id.chart)
    LineChart mLineChart;

    /* fields */
    private StockHistory mStockHistory;

    public static Intent newIntent(Context context, StockHistory stockHistory) {
        Intent intent = new Intent();
        if (context != null)
            intent.setClass(context, StockHistoryActivity.class);
        intent.putExtra(EXTRA_HISTORY, stockHistory);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_history);
        ButterKnife.bind(this);

        // read the data from the intent
        mStockHistory = getIntent().getParcelableExtra(EXTRA_HISTORY);

        // setup the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mStockHistory.getName());

        // setup the chart
        initializeChart();
        setChartData();

    }

    private void setChartData() {
        // check if the history data is empty
        if (mStockHistory == null
                || mStockHistory.getHistory() == null
                || mStockHistory.getHistory().size() == 0) {
            mLineChart.setNoDataText(getString(R.string.no_history_found));
            return;
        }

        // add the entries
        List<StockHistory.Entry> stockEntries = mStockHistory.getHistory();
        Collections.sort(stockEntries);
        List<Entry> chartEntries = new ArrayList<>();
        final long minValue = TimeUnit.MILLISECONDS.toDays(stockEntries.get(0).getTime());
        for (StockHistory.Entry entry : stockEntries)
            chartEntries.add(new Entry(entry.getTime(), (float) entry.getValue()));

        // create line data from the entries
        LineDataSet dataSet = new LineDataSet(chartEntries, mStockHistory.getName());
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(ColorTemplate.getHoloBlue());
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setFillColor(ColorTemplate.getHoloBlue());
        final LineData data = new LineData(dataSet);

        // customize labels
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        final Calendar calendar = Calendar.getInstance();
        mLineChart.getXAxis().setValueFormatter(
                new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        calendar.setTimeInMillis((long) value);
                        return sdf.format(new Date((long) value));
                    }
                }
        );

        // set the line data to the chart after an animation
        // post it delayed so activity has time to load
        mLineChart.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLineChart.setData(data);
                mLineChart.animateX(700);
            }
        }, 300);


    }

    private void initializeChart() {
        // no description text
        mLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setHighlightPerDragEnabled(true);

        // disable no data set test
        mLineChart.setNoDataText("");

        // color the x and y axis text white
        mLineChart.getXAxis().setEnabled(true);
        mLineChart.getXAxis().setTextColor(Color.WHITE);
        mLineChart.getXAxis().setLabelRotationAngle(90);
        mLineChart.getAxisLeft().setTextColor(Color.WHITE);
        mLineChart.getAxisRight().setTextColor(Color.WHITE);

        // hide the legend
        mLineChart.getLegend().setEnabled(false);
    }

}
