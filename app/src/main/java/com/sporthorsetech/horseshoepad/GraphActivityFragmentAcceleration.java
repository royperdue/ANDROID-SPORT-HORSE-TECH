package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sporthorsetech.horseshoepad.custom.CustomMarkerView;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.Gait;
import com.sporthorsetech.horseshoepad.utility.equine.GaitActivity;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.Step;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.ArrayList;
import java.util.List;

public class GraphActivityFragmentAcceleration extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener
{
    private OnFragmentInteractionListener mListener;
    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private Typeface tf;
    private List<Horse> horseList;
    private Horse horse;

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

        horseList = Database.with(getActivity().getApplicationContext())
                .load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();

        String horseName = LittleDB.getInstance(getActivity().getApplicationContext()).getString(Constant.HORSE_NAME);

        if (horseName != null)
        {
            for (Horse horse : horseList)
            {
                if (horse.getName().equals(horseName))
                {
                    this.horse = horse;
                    break;
                }
            }
        }

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
        l.setTextSize(6f);

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

        List<GaitActivity> gaitActivities = horse.getGaitActivities();
        GaitActivity gaitActivity = gaitActivities.get(gaitActivities.size() - 1);
        List<Gait> gaits = gaitActivity.getGaits();
        Gait gait = gaits.get(gaits.size() - 1);
        List<Step> steps = gait.getSteps();

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

        for (int i = 0; i < mSeekBarX.getProgress(); i++)
        {
            if (i < steps.size())
            {
                xVals.add(steps.get(i).getHoof());
                yVals1.add(new BarEntry(steps.get(i).getAccelerationX().getAccelerationX(), i));
                yVals2.add(new BarEntry(steps.get(i).getAccelerationY().getAccelerationY(), i));
                yVals3.add(new BarEntry(steps.get(i).getAccelerationZ().getAccelerationZ(), i));
            }
        }

        float mult = mSeekBarY.getProgress() * 1000f;

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "Acceleration X");
        set1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet set2 = new BarDataSet(yVals2, "Acceleration Y");
        set2.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet set3 = new BarDataSet(yVals3, "Acceleration Z");
        set3.setColors(ColorTemplate.COLORFUL_COLORS);

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
