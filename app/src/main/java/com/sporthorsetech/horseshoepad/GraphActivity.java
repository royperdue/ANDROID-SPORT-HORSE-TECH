package com.sporthorsetech.horseshoepad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.Gait;
import com.sporthorsetech.horseshoepad.utility.equine.GaitActivity;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.Step;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class GraphActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new ChartFragment()).commit();
        }
    }

    public static class ChartFragment extends Fragment
    {
        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasLabelForSelected = false;
        private int dataType = Constant.DEFAULT_DATA;
        private List<Horse> horseList;
        private Horse horse;

        public ChartFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_graph, container, false);

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

            chart = (ColumnChartView) rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());

            TextView xAcceleration = (TextView) rootView.findViewById(R.id.x_accel_tv);
            xAcceleration.setBackgroundColor(ChartUtils.COLOR_BLUE);
            TextView yAcceleration = (TextView) rootView.findViewById(R.id.y_accel_tv);
            yAcceleration.setBackgroundColor(ChartUtils.COLOR_GREEN);
            TextView zAcceleration = (TextView) rootView.findViewById(R.id.z_accel_tv);
            zAcceleration.setBackgroundColor(ChartUtils.COLOR_VIOLET);
            TextView force = (TextView) rootView.findViewById(R.id.force_tv);
            force.setBackgroundColor(ChartUtils.COLOR_ORANGE);

            generateData();

            return rootView;
        }

        // MENU
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
        {
            inflater.inflate(R.menu.column_chart, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if (id == R.id.action_reset)
            {
                reset();
                generateData();
                return true;
            }
            if (id == R.id.action_stacked)
            {
                dataType = Constant.STACKED_DATA;
                generateData();
                return true;
            }
            if (id == R.id.action_negative_subcolumns)
            {
                dataType = Constant.NEGATIVE_SUBCOLUMNS_DATA;
                generateData();
                return true;
            }
            if (id == R.id.action_negative_stacked)
            {
                dataType = Constant.NEGATIVE_STACKED_DATA;
                generateData();
                return true;
            }
            if (id == R.id.action_toggle_touch_zoom)
            {
                chart.setZoomEnabled(!chart.isZoomEnabled());
                Toast.makeText(getActivity(), "IsZoomEnabled " + chart.isZoomEnabled(), Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_zoom_both)
            {
                chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
                return true;
            }
            if (id == R.id.action_zoom_horizontal)
            {
                chart.setZoomType(ZoomType.HORIZONTAL);
                return true;
            }
            if (id == R.id.action_zoom_vertical)
            {
                chart.setZoomType(ZoomType.VERTICAL);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void reset()
        {
            hasLabelForSelected = false;
            dataType = Constant.DEFAULT_DATA;
            chart.setValueSelectionEnabled(hasLabelForSelected);

        }

        private void generateDefaultData()
        {
            List<GaitActivity> gaitActivities = horse.getGaitActivities();
            List<Step> steps = new ArrayList<>();

            for (GaitActivity gaitActivity : gaitActivities)
            {
                List<Gait> gaits = gaitActivity.getGaits();

                for (Gait gait : gaits)
                {
                    List<Step> s = gait.getSteps();

                    for (int i = 0; i < s.size(); i++)
                    {
                        steps.add(s.get(i));
                    }
                }
            }

            int numberSubColumns = 1;
            int numberColumns = steps.size();
            // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i = 0; i < numberColumns; ++i)
            {
                values = new ArrayList<SubcolumnValue>();

                for (int j = 0; j < numberSubColumns; ++j)
                {
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE).setLabel(steps.get(i).getHoof()));
                }

                Column column = new Column(values);

                column.setHasLabels(true);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            Axis axisX = new Axis();
            axisX.setName("Step Number");
            axisX.setHasTiltedLabels(false);
            Axis axisY = new Axis().setHasLines(true);
            axisY.setName("Values");
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

            chart.setColumnChartData(data);

        }

        /**
         * Generates columns with stacked subcolumns.
         */
        private void generateStackedData()
        {
            List<GaitActivity> gaitActivities = horse.getGaitActivities();
            List<Step> steps = new ArrayList<>();

            for (GaitActivity gaitActivity : gaitActivities)
            {
                List<Gait> gaits = gaitActivity.getGaits();

                for (Gait gait : gaits)
                {
                    List<Step> s = gait.getSteps();

                    for (int i = 0; i < s.size(); i++)
                    {
                        steps.add(s.get(i));
                    }
                }
            }

            int numberSubColumns = 1;
            int numberColumns = steps.size();
            // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i = 0; i < numberColumns; ++i)
            {
                values = new ArrayList<SubcolumnValue>();

                for (int j = 0; j < numberSubColumns; ++j)
                {
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE).setLabel(steps.get(i).getHoof()));
                }

                Column column = new Column(values);
                column.setHasLabels(true);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            // Set stacked flag.
            data.setStacked(true);

            Axis axisX = new Axis();
            axisX.setName("Step Number");
            axisX.setHasTiltedLabels(false);
            Axis axisY = new Axis().setHasLines(true);
            axisY.setName("Values");
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
            chart.setColumnChartData(data);
        }

        private void generateNegativeSubcolumnsData()
        {

            List<GaitActivity> gaitActivities = horse.getGaitActivities();
            List<Step> steps = new ArrayList<>();

            for (GaitActivity gaitActivity : gaitActivities)
            {
                List<Gait> gaits = gaitActivity.getGaits();

                for (Gait gait : gaits)
                {
                    List<Step> s = gait.getSteps();

                    for (int i = 0; i < s.size(); i++)
                    {
                        steps.add(s.get(i));
                    }
                }
            }

            int numberSubColumns = 1;
            int numberColumns = steps.size();
            // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i = 0; i < numberColumns; ++i)
            {
                values = new ArrayList<SubcolumnValue>();

                for (int j = 0; j < numberSubColumns; ++j)
                {
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE).setLabel(steps.get(i).getHoof()));
                }

                Column column = new Column(values);
                column.setHasLabels(true);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            Axis axisX = new Axis();
            axisX.setName("Step Number");
            axisX.setHasTiltedLabels(false);
            Axis axisY = new Axis().setHasLines(true);
            axisY.setName("Values");
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

            chart.setColumnChartData(data);
        }

        private void generateNegativeStackedData()
        {

            List<GaitActivity> gaitActivities = horse.getGaitActivities();
            List<Step> steps = new ArrayList<>();

            for (GaitActivity gaitActivity : gaitActivities)
            {
                List<Gait> gaits = gaitActivity.getGaits();

                for (Gait gait : gaits)
                {
                    List<Step> s = gait.getSteps();

                    for (int i = 0; i < s.size(); i++)
                    {
                        steps.add(s.get(i));
                    }
                }
            }

            int numberSubColumns = 1;
            int numberColumns = steps.size();
            // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i = 0; i < numberColumns; ++i)
            {
                values = new ArrayList<SubcolumnValue>();

                for (int j = 0; j < numberSubColumns; ++j)
                {
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET).setLabel(steps.get(i).getHoof()));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE).setLabel(steps.get(i).getHoof()));
                }

                Column column = new Column(values);
                column.setHasLabels(true);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            // Set stacked flag.
            data.setStacked(true);

            Axis axisX = new Axis();
            axisX.setName("Step Number");
            axisX.setHasTiltedLabels(false);
            Axis axisY = new Axis().setHasLines(true);
            axisY.setName("Values");
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

            chart.setColumnChartData(data);
        }

        private void generateData()
        {
            switch (dataType)
            {
                case Constant.DEFAULT_DATA:
                    generateDefaultData();
                    break;
                case Constant.STACKED_DATA:
                    generateStackedData();
                    break;
                case Constant.NEGATIVE_SUBCOLUMNS_DATA:
                    generateNegativeSubcolumnsData();
                    break;
                case Constant.NEGATIVE_STACKED_DATA:
                    generateNegativeStackedData();
                    break;
                default:
                    generateDefaultData();
                    break;
            }
        }

        private class ValueTouchListener implements ColumnChartOnValueSelectListener
        {
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value)
            {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected()
            {
            }
        }
    }
}
