package com.sporthorsetech.horseshoepad;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.punchthrough.bean.sdk.message.ScratchData;
import com.sporthorsetech.horseshoepad.service.CommandThread;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GaitMonitorFragment extends Fragment implements BeanDiscoveryListener, BeanListener
{
    private OnFragmentInteractionListener mListener;
    private final Map<String, Bean> beans = new HashMap<>();
    private EditText gaitDetected;
    private EditText averageStrideLength;
    private EditText averageForce;
    private EditText averageAccelerationX;
    private EditText averageAccelerationY;
    private EditText averageAccelerationZ;
    private Spinner selectHorseSpinner;
    private Spinner selectFootingSpinner;
    private Button beginMonitoringButton;
    private Button pauseMonitoringButton;
    private Horse horse;

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

                    Toast.makeText(getActivity().getApplicationContext(), horse.getName(), Toast.LENGTH_SHORT).show();

                }
                initializing = false;
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
                new CommandThread((HashMap<String, Bean>) beans, "TAKE_READINGS");
            }
        });

        pauseMonitoringButton = (Button) view.findViewById(R.id.pause_monitoring_button);
        pauseMonitoringButton.setEnabled(false);
        pauseMonitoringButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new CommandThread((HashMap<String, Bean>) beans, "PAUSE_READINGS");
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
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem detectHorseshoePads = menu.add(Menu.NONE, Constant.DETECT_HORSESHOE_PADS, 0, getString(R.string.detect_horseshoe_pads));
        detectHorseshoePads.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == Constant.DETECT_HORSESHOE_PADS)
        {
            BeanManager.getInstance().startDiscovery(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeanDiscovered(Bean bean, int rssi)
    {
        beans.put(bean.getDevice().getName(), bean);
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
        String s = null;
        try
        {
            s = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        System.out.println("data received: " + s);
    }

    @Override
    public void onScratchValueChanged(ScratchBank bank, byte[] value)
    {
        Iterator<Map.Entry<String, Bean>> entries = beans.entrySet().iterator();

        while (entries.hasNext())
        {
            Map.Entry<String, Bean> entry = entries.next();

            if (entry.getValue().isConnected())
            {
                entry.getValue().readScratchData(ScratchBank.BANK_1, new Callback<ScratchData>()
                {
                    @Override
                    public void onResult(ScratchData result)
                    {
                        try
                        {
                            String s = new String(result.data(), "UTF-8");

                            System.out.println("SCRATCH BANK 1: " + s);
                        } catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                entry.getValue().readScratchData(ScratchBank.BANK_2, new Callback<ScratchData>()
                {
                    @Override
                    public void onResult(ScratchData result)
                    {
                        try
                        {
                            String s = new String(result.data(), "UTF-8");

                            System.out.println("SCRATCH BANK 2: " + s);
                        } catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                entry.getValue().readScratchData(ScratchBank.BANK_3, new Callback<ScratchData>()
                {
                    @Override
                    public void onResult(ScratchData result)
                    {
                        try
                        {
                            String s = new String(result.data(), "UTF-8");

                            System.out.println("SCRATCH BANK 3: " + s);
                        } catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                entry.getValue().readScratchData(ScratchBank.BANK_4, new Callback<ScratchData>()
                {
                    @Override
                    public void onResult(ScratchData result)
                    {
                        try
                        {
                            String s = new String(result.data(), "UTF-8");

                            System.out.println("SCRATCH BANK 4: " + s);
                        } catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
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
}
