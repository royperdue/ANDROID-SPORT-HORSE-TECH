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
import lecho.lib.hellocharts.view.Chart;
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
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = false;
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
            if (id == R.id.action_subcolumns)
            {
                dataType = Constant.SUBCOLUMNS_DATA;
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
            if (id == R.id.action_toggle_labels)
            {
                toggleLabels();
                return true;
            }
            if (id == R.id.action_toggle_axes)
            {
                toggleAxes();
                return true;
            }
            if (id == R.id.action_toggle_axes_names)
            {
                toggleAxesNames();
                return true;
            }
            if (id == R.id.action_animate)
            {
                prepareDataAnimation();
                chart.startDataAnimation();
                return true;
            }
            if (id == R.id.action_toggle_selection_mode)
            {
                toggleLabelForSelected();

                Toast.makeText(getActivity(),
                        "Selection mode set to " + chart.isValueSelectionEnabled() + " select any point.",
                        Toast.LENGTH_SHORT).show();
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
            hasAxes = true;
            hasAxesNames = true;
            hasLabels = false;
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
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            if (hasAxes)
            {
                Axis axisX = new Axis();
                axisX.setHasTiltedLabels(true);
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames)
                {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else
            {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setColumnChartData(data);

        }

        /**
         * Generates columns with subcolumns, columns have larger separation than subcolumns.
         */
        private void generateSubcolumnsData()
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
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            if (hasAxes)
            {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames)
                {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else
            {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

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
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            // Set stacked flag.
            data.setStacked(true);

            if (hasAxes)
            {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames)
                {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else
            {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

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
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            if (hasAxes)
            {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames)
                {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else
            {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

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
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationX().getAccelerationX(), ChartUtils.COLOR_BLUE));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationY().getAccelerationY(), ChartUtils.COLOR_GREEN));
                    values.add(new SubcolumnValue(steps.get(i).getAccelerationZ().getAccelerationZ(), ChartUtils.COLOR_VIOLET));
                    values.add(new SubcolumnValue(steps.get(i).getForce().getForce(), ChartUtils.COLOR_ORANGE));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            // Set stacked flag.
            data.setStacked(true);

            if (hasAxes)
            {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames)
                {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else
            {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setColumnChartData(data);
        }

        private int getSign()
        {
            int[] sign = new int[]{-1, 1};
            return sign[Math.round((float) Math.random())];
        }

        private void generateData()
        {
            switch (dataType)
            {
                case Constant.DEFAULT_DATA:
                    generateDefaultData();
                    break;
                case Constant.SUBCOLUMNS_DATA:
                    generateSubcolumnsData();
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

        private void toggleLabels()
        {
            hasLabels = !hasLabels;

            if (hasLabels)
            {
                hasLabelForSelected = false;
                chart.setValueSelectionEnabled(hasLabelForSelected);
            }

            generateData();
        }

        private void toggleLabelForSelected()
        {
            hasLabelForSelected = !hasLabelForSelected;
            chart.setValueSelectionEnabled(hasLabelForSelected);

            if (hasLabelForSelected)
            {
                hasLabels = false;
            }

            generateData();
        }

        private void toggleAxes()
        {
            hasAxes = !hasAxes;

            generateData();
        }

        private void toggleAxesNames()
        {
            hasAxesNames = !hasAxesNames;

            generateData();
        }

        /**
         * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
         * method(don't confuse with View.animate()).
         */
        private void prepareDataAnimation()
        {
            for (Column column : data.getColumns())
            {
                for (SubcolumnValue value : column.getValues())
                {
                    value.setTarget((float) Math.random() * 100);
                }
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
