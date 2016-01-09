package com.sporthorsetech.horseshoepad;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.nbarraille.loom.Loom;
import com.nbarraille.loom.Task;
import com.nbarraille.loom.events.FailureEvent;
import com.nbarraille.loom.events.ProgressEvent;
import com.nbarraille.loom.events.SuccessEvent;
import com.nbarraille.loom.listeners.GenericUiThreadListener;
import com.nbarraille.loom.listeners.LoomListener;
import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.Gait;
import com.sporthorsetech.horseshoepad.utility.equine.GaitActivity;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.Step;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GaitMonitorFragment extends Fragment implements BeanDiscoveryListener, BeanListener
{
    private OnFragmentInteractionListener mListener;
    private List<Bean> beans = new ArrayList<>();
    private EditText gaitDetected;
    private EditText averageStrideLength;
    private EditText averageForce;
    private EditText averageAccelerationX;
    private EditText averageAccelerationY;
    private EditText averageAccelerationZ;
    private Spinner selectHorseSpinner;
    private Spinner selectGaitSpinner;
    private Spinner selectFootingSpinner;
    private Button beginMonitoringButton;
    private Button pauseMonitoringButton;
    private Button changeGaitButton;
    private Horse horse;
    private GaitActivity gaitActivity;
    private Gait gait;
    private Step step;
    private ProgressBar mProgressBar;

    public GaitMonitorFragment()
    {
    }

    public static GaitMonitorFragment newInstance()
    {
        GaitMonitorFragment fragment = new GaitMonitorFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_gait_monitor, container, false);
        setHasOptionsMenu(true);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        gaitDetected = (EditText) view.findViewById(R.id.gait_detected_edittext);
        averageStrideLength = (EditText) view.findViewById(R.id.average_stride_length_edittext);
        averageForce = (EditText) view.findViewById(R.id.average_force_edittext);
        averageAccelerationX = (EditText) view.findViewById(R.id.average_acceleration_x_edittext);
        averageAccelerationY = (EditText) view.findViewById(R.id.average_acceleration_y_edittext);
        averageAccelerationZ = (EditText) view.findViewById(R.id.average_acceleration_z_edittext);

        List<Horse> horseList = Database.with(getActivity().getApplicationContext()).load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();
        Horse[] horseArray = horseList.toArray(new Horse[horseList.size()]);

        selectHorseSpinner = (Spinner) view.findViewById(R.id.spinnerSelectHorse);

        final SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                horseArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectHorseSpinner.setAdapter(adapter);

        selectHorseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            // boolean variable that is used so that the onItemSelected method is not executed
            // on the first initializing round that happens when the class is created.
            boolean initializing = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (initializing == false)
                {
                    SpinnerAdapter spinnerAdapter = (SpinnerAdapter) selectHorseSpinner.getAdapter();
                    horse = spinnerAdapter.getHorse(position);
                    horse = spinnerAdapter.getHorse(position);

                    Toast.makeText(getActivity().getApplicationContext(), horse.getName(), Toast.LENGTH_SHORT).show();

                    String gaitActivityId = "-1";
                    ArrayList<String> gaitActivityIds = LittleDB.getInstance(getActivity().getApplicationContext()).getListString(Constant.GAIT_ACTIVITY_IDS);

                    if (gaitActivityIds == null || gaitActivityIds.size() == 0)
                    {
                        gaitActivityId = "1";
                        gaitActivityIds.add("1");
                        LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.GAIT_ACTIVITY_IDS, gaitActivityIds);
                    } else if (gaitActivityIds != null && gaitActivityIds.size() > 0)
                    {
                        String lastId = gaitActivityIds.get(gaitActivityIds.size() - 1);
                        gaitActivityId = String.valueOf(Integer.parseInt(lastId) + 1);
                        gaitActivityIds.add(lastId);
                        gaitActivityIds.add(gaitActivityId);
                        LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.GAIT_ACTIVITY_IDS, gaitActivityIds);
                    }

                    gaitActivity = new GaitActivity(gaitActivityId);

                    System.out.println("GAIT ACTIVITY ID: " + gaitActivity.getStoredObjectId());

                }
                initializing = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        selectGaitSpinner = (Spinner) view.findViewById(R.id.spinnerSelectGait);
        ArrayAdapter<CharSequence> adapterGait = ArrayAdapter.createFromResource(getActivity(),
                R.array.gaits_array, android.R.layout.simple_spinner_item);
        adapterGait.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGaitSpinner.setAdapter(adapterGait);
        selectGaitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String gaitId = "-1";
                ArrayList<String> gaitIds = LittleDB.getInstance(getActivity().getApplicationContext()).getListString(Constant.GAIT_IDS);

                if (gaitIds == null || gaitIds.size() == 0)
                {
                    gaitId = "1";
                    gaitIds.add("1");
                    LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.GAIT_IDS, gaitIds);
                } else if (gaitIds != null && gaitIds.size() > 0)
                {
                    String lastId = gaitIds.get(gaitIds.size() - 1);
                    gaitId = String.valueOf(Integer.parseInt(lastId) + 1);
                    gaitIds.add(lastId);
                    gaitIds.add(gaitId);
                    LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.GAIT_IDS, gaitIds);
                }

                gait = new Gait(gaitId, selectGaitSpinner.getSelectedItem().toString());
                System.out.println("GAIT: " + gait.getName());

                String stepId = "-1";
                ArrayList<String> stepIds = LittleDB.getInstance(getActivity().getApplicationContext()).getListString(Constant.STEP_IDS);

                if (stepIds == null || stepIds.size() == 0)
                {
                    stepId = "1";
                    stepIds.add("1");
                    LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.STEP_IDS, stepIds);
                } else if (stepIds != null && stepIds.size() > 0)
                {
                    String lastId = stepIds.get(stepIds.size() - 1);
                    stepId = String.valueOf(Integer.parseInt(lastId) + 1);
                    stepIds.add(lastId);
                    stepIds.add(stepId);
                    LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.STEP_IDS, stepIds);
                }

                step = new Step(stepId);
                System.out.println("STEP: " + step.getStoredObjectId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        selectFootingSpinner = (Spinner) view.findViewById(R.id.spinnerSelectFooting);
        ArrayAdapter<CharSequence> adapterFooting = ArrayAdapter.createFromResource(getActivity(),
                R.array.footings_array, android.R.layout.simple_spinner_item);
        adapterFooting.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectFootingSpinner.setAdapter(adapterFooting);
        selectFootingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        beginMonitoringButton = (Button) view.findViewById(R.id.begin_monitoring_button);
        beginMonitoringButton.setEnabled(false);
        beginMonitoringButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CommandTask commandTask = new CommandTask();
                commandTask.setBeans(beans);
                commandTask.setCommand("TAKE_READINGS");

                Loom.execute(commandTask);
            }
        });

        pauseMonitoringButton = (Button) view.findViewById(R.id.pause_monitoring_button);
        pauseMonitoringButton.setEnabled(false);
        pauseMonitoringButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CommandTask commandTask = new CommandTask();
                commandTask.setBeans(beans);
                commandTask.setCommand("PAUSE_READINGS");

                Loom.execute(commandTask);
            }
        });

        changeGaitButton = (Button) view.findViewById(R.id.change_gait_button);
        changeGaitButton.setEnabled(false);
        changeGaitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ArrayList<Step> steps = (ArrayList<Step>) gait.getSteps();
                steps.add(step);
                gait.setSteps(steps);

                ArrayList<Gait> gaits = (ArrayList<Gait>) gaitActivity.getGaits();
                gaits.add(gait);
                gaitActivity.setGaits(gaits);

                ArrayList<GaitActivity> gaitActivities = (ArrayList<GaitActivity>) horse.getGaitActivities();
                gaitActivities.add(gaitActivity);
                horse.setGaitActivities(gaitActivities);

                Database.with(getActivity().getApplicationContext()).saveObject(horse);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        BeanManager.getInstance().startDiscovery(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(getString(R.string.monitor_gait_activity));
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
    public void onResume()
    {
        super.onResume();

        Loom.registerListener(commandListener);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Loom.unregisterListener(commandListener);
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem detectHorseshoePads = menu.add(Menu.NONE, Constant.DETECT_HORSESHOE_PADS, 0, getString(R.string.detect_horseshoe_pads));
        detectHorseshoePads.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        // TEMPORARY...
        MenuItem bankData = menu.add(Menu.NONE, 200, 0, "Bank Data");
        bankData.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == Constant.DETECT_HORSESHOE_PADS)
        {
            BeanManager.getInstance().startDiscovery(this);
        } else if (item.getItemId() == 200)
        {
            CommandTask commandTask = new CommandTask();
            commandTask.setBeans(beans);
            commandTask.setCommand("BANK_DATA");

            Loom.execute(commandTask);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeanDiscovered(Bean bean, int rssi)
    {
        beans.add(bean);
        bean.connect(getActivity(), this);
    }

    @Override
    public void onDiscoveryComplete()
    {


    }

    @Override
    public void onConnected()
    {
        beginMonitoringButton.setEnabled(true);
        pauseMonitoringButton.setEnabled(true);
    }

    @Override
    public void onConnectionFailed()
    {

    }

    @Override
    public void onDisconnected()
    {

    }

    @Override
    public void onSerialMessageReceived(byte[] data)
    {
    }

    @Override
    public void onScratchValueChanged(ScratchBank bank, byte[] value)
    {
        System.out.println("-->Scratch value changed<--");

        String s = null;
        try
        {
            s = new String(value, "UTF-8");

            int bankNumber = bank.getRawValue();

            if (bankNumber == 0)
            {
                System.out.println("BANK 1: " + s);
            } else if (bankNumber == 1)
            {
                System.out.println("BANK 2: " + s);
            } else if (bankNumber == 2)
            {
                System.out.println("BANK 3: " + s);
            } else if (bankNumber == 3)
            {
                System.out.println("BANK 4: " + s);
            } else if (bankNumber == 4)
            {
                System.out.println("BANK 5: " + s);
            }

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(BeanError error)
    {

    }


    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(String title);
    }

    private LoomListener commandListener = new GenericUiThreadListener()
    {
        @Override
        public void onSuccess(SuccessEvent event)
        {
            Log.i("CommandTask", "Success Received for task Progress");
            mProgressBar.setProgress(100);


        }

        @Override
        public void onFailure(FailureEvent event)
        {
            Log.i("CommandTask", "Failure Received for task Progress");
            mProgressBar.setProgress(0);
        }

        @Override
        public void onProgress(ProgressEvent event)
        {
            Log.i("CommandTask", "Progress Received for task Progress: " + event.getProgress());
            mProgressBar.setProgress(event.getProgress());
        }

        @NonNull
        @Override
        public String taskName()
        {
            return Constant.TASK_NAME;
        }
    };

    public static class CommandTask extends Task
    {
        private String command;
        private List<Bean> beans = new ArrayList<>();

        @Override
        protected String name()
        {
            return Constant.TASK_NAME;
        }

        @Override
        protected void runTask() throws Exception
        {
            for (Bean bean : beans)
            {
                bean.setScratchData(ScratchBank.BANK_5, command);
                postProgress(100 / beans.size());
                Thread.sleep(50);
            }
        }

        public void setCommand(String command)
        {
            this.command = command;
        }

        public void setBeans(List<Bean> beans)
        {
            this.beans = beans;
        }
    }
}
