package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.sporthorsetech.horseshoepad.custom.CustomMarkerView;

import java.util.ArrayList;

public class GraphActivityFragmentAcceleration extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener
{
    private OnFragmentInteractionListener mListener;
    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private Typeface tf;

    public GraphActivityFragmentAcceleration()
    {
    }

    public static GraphActivityFragmentAcceleration newInstance()
    {
        GraphActivityFragmentAcceleration fragment = new GraphActivityFragmentAcceleration();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_graph_acceleration, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvX = (TextView) view.findViewById(R.id.tvXMax);
        tvY = (TextView) view.findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBarX.setOnSeekBarChangeListener(this);

        mSeekBarY = (SeekBar) view.findViewById(R.id.seekBar2);
        mSeekBarY.setOnSeekBarChangeListener(this);

        mChart = (BarChart) view.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDescription("");

//        mChart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.custom_marker_view);

        // define an offset to change the original position of the marker
        // (optional)
        // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        mChart.setMarkerView(mv);

        mSeekBarX.setProgress(10);
        mSeekBarY.setProgress(100);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setTypeface(tf);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xl = mChart.getXAxis();
        xl.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);

        mChart.getAxisRight().setEnabled(false);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction("title");
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.actionToggleValues:
            {
                for (BarDataSet set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionTogglePinch:
            {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax:
            {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleHighlight:
            {
                if (mChart.getData() != null)
                {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionToggleHighlightArrow:
            {
                if (mChart.isDrawHighlightArrowEnabled())
                    mChart.setDrawHighlightArrow(false);
                else
                    mChart.setDrawHighlightArrow(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStartzero:
            {
                mChart.getAxisLeft().setStartAtZero(!mChart.getAxisLeft().isStartAtZeroEnabled());
                mChart.getAxisRight().setStartAtZero(!mChart.getAxisRight().isStartAtZeroEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave:
            {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.animateX:
            {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY:
            {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY:
            {

                mChart.animateXY(3000, 3000);
                break;
            }
        }
        return true;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h)
    {

    }

    @Override
    public void onNothingSelected()
    {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        tvX.setText("" + (mSeekBarX.getProgress() * 3));
        tvY.setText("" + (mSeekBarY.getProgress()));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mSeekBarX.getProgress(); i++)
        {
            xVals.add((i + 1990) + "");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

        float mult = mSeekBarY.getProgress() * 1000f;

        for (int i = 0; i < mSeekBarX.getProgress(); i++)
        {
            float val = (float) (Math.random() * mult) + 3;
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 0; i < mSeekBarX.getProgress(); i++)
        {
            float val = (float) (Math.random() * mult) + 3;
            yVals2.add(new BarEntry(val, i));
        }

        for (int i = 0; i < mSeekBarX.getProgress(); i++)
        {
            float val = (float) (Math.random() * mult) + 3;
            yVals3.add(new BarEntry(val, i));
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "Company A");
        // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
        // ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(104, 241, 175));
        BarDataSet set2 = new BarDataSet(yVals2, "Company B");
        set2.setColor(Color.rgb(164, 228, 251));
        BarDataSet set3 = new BarDataSet(yVals3, "Company C");
        set3.setColor(Color.rgb(242, 247, 158));

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        BarData data = new BarData(xVals, dataSets);
//        data.setValueFormatter(new LargeValueFormatter());

        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(80f);
        data.setValueTypeface(tf);

        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
