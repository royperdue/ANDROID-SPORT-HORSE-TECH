package com.sporthorsetech.horseshoepad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.sporthorsetech.horseshoepad.service.CommandThread;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.AccelerationX;
import com.sporthorsetech.horseshoepad.utility.equine.AccelerationY;
import com.sporthorsetech.horseshoepad.utility.equine.AccelerationZ;
import com.sporthorsetech.horseshoepad.utility.equine.Force;
import com.sporthorsetech.horseshoepad.utility.equine.Gait;
import com.sporthorsetech.horseshoepad.utility.equine.GaitActivity;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.HorseHoof;
import com.sporthorsetech.horseshoepad.utility.equine.Step;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import me.drakeet.materialdialog.MaterialDialog;

public class GaitMonitorFragment extends Fragment implements BeanDiscoveryListener, BeanListener
{
    private OnFragmentInteractionListener mListener;
    private List<Bean> beans = new ArrayList<>();
    private List<Horse> horseList;

    private EditText textView1;
    private EditText textView2;
    private EditText textView3;
    private EditText textView4;

    private TextView forceLH;
    private TextView forceLF;
    private TextView forceRH;
    private TextView forceRF;

    private TextView xAxisAccelerationLH;
    private TextView yAxisAccelerationLH;
    private TextView zAxisAccelerationLH;

    private TextView xAxisAccelerationLF;
    private TextView yAxisAccelerationLF;
    private TextView zAxisAccelerationLF;

    private TextView xAxisAccelerationRH;
    private TextView yAxisAccelerationRH;
    private TextView zAxisAccelerationRH;

    private TextView xAxisAccelerationRF;
    private TextView yAxisAccelerationRF;
    private TextView zAxisAccelerationRF;

    private EditText gaitDetected;
    private EditText averageStrideLength;

    private Spinner selectHorseSpinner;
    private Spinner selectGaitSpinner;
    private Spinner selectFootingSpinner;

    private Button viewAccelerationDataGraphs;
    private Button viewForceDataGraphs;
    private Button beginMonitoringButton;
    private Button pauseMonitoringButton;
    private Button changeGaitButton;

    private Horse horse;
    private GaitActivity gaitActivity;
    private Gait gait;
    private Step step;

    private Force forceReadingLH;
    private boolean forceReadLH = false;
    private AccelerationX xAccelerationReadingLH;
    private boolean xAccelerationReadLH = false;
    private AccelerationY yAccelerationReadingLH;
    private boolean yAccelerationReadLH = false;
    private AccelerationZ zAccelerationReadingLH;
    private boolean zAccelerationReadLH = false;

    private Force forceReadingLF;
    private boolean forceReadLF = false;
    private AccelerationX xAccelerationReadingLF;
    private boolean xAccelerationReadLF = false;
    private AccelerationY yAccelerationReadingLF;
    private boolean yAccelerationReadLF = false;
    private AccelerationZ zAccelerationReadingLF;
    private boolean zAccelerationReadLF = false;

    private Force forceReadingRH;
    private boolean forceReadRH = false;
    private AccelerationX xAccelerationReadingRH;
    private boolean xAccelerationReadRH = false;
    private AccelerationY yAccelerationReadingRH;
    private boolean yAccelerationReadRH = false;
    private AccelerationZ zAccelerationReadingRH;
    private boolean zAccelerationReadRH = false;

    private Force forceReadingRF;
    private boolean forceReadRF = false;
    private AccelerationX xAccelerationReadingRF;
    private boolean xAccelerationReadRF = false;
    private AccelerationY yAccelerationReadingRF;
    private boolean yAccelerationReadRF = false;
    private AccelerationZ zAccelerationReadingRF;
    private boolean zAccelerationReadRF = false;

    private AlertDialog dialog;
    private boolean horseSelected = false;

    public GaitMonitorFragment()
    {
    }

    public static Fragment newInstance()
    {
        return new GaitMonitorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_gait_monitor, container, false);
        setHasOptionsMenu(true);

        textView1 = (EditText) view.findViewById(R.id.textView1);
        textView2 = (EditText) view.findViewById(R.id.textView2);
        textView3 = (EditText) view.findViewById(R.id.textView3);
        textView4 = (EditText) view.findViewById(R.id.textView4);

        forceLH = (TextView) view.findViewById(R.id.force_lh_textview);
        forceLF = (TextView) view.findViewById(R.id.force_lf_textview);
        forceRH = (TextView) view.findViewById(R.id.force_rh_textview);
        forceRF = (TextView) view.findViewById(R.id.force_rf_textview);

        xAxisAccelerationLH = (TextView) view.findViewById(R.id.axis_x_lh_textview);
        yAxisAccelerationLH = (TextView) view.findViewById(R.id.axis_y_lh_textview);
        zAxisAccelerationLH = (TextView) view.findViewById(R.id.axis_z_lh_textview);

        xAxisAccelerationLF = (TextView) view.findViewById(R.id.axis_x_lf_textview);
        yAxisAccelerationLF = (TextView) view.findViewById(R.id.axis_y_lf_textview);
        zAxisAccelerationLF = (TextView) view.findViewById(R.id.axis_z_lf_textview);

        xAxisAccelerationRH = (TextView) view.findViewById(R.id.axis_x_rh_textview);
        yAxisAccelerationRH = (TextView) view.findViewById(R.id.axis_y_rh_textview);
        zAxisAccelerationRH = (TextView) view.findViewById(R.id.axis_z_rh_textview);

        xAxisAccelerationRF = (TextView) view.findViewById(R.id.axis_x_rf_textview);
        yAxisAccelerationRF = (TextView) view.findViewById(R.id.axis_y_rf_textview);
        zAxisAccelerationRF = (TextView) view.findViewById(R.id.axis_z_rf_textview);

        horseList = Database.with(getActivity().getApplicationContext())
                .load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();
        Horse[] horseArray = horseList.toArray(new Horse[horseList.size()]);

        selectHorseSpinner = (Spinner) view.findViewById(R.id.spinnerSelectHorse);

        final ArrayAdapter adapter = new SpinnerAdapter(getActivity(),
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
                    horseSelected = true;

                    SpinnerAdapter spinnerAdapter = (SpinnerAdapter) selectHorseSpinner.getAdapter();

                    horse = spinnerAdapter.getItem(position);
                    LittleDB.getInstance(getActivity().getApplicationContext()).putString(Constant.HORSE_NAME, horse.getName());

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

                    // NEED TO CHANGE WHEN ADD CALIBRATE GAITS FUNCTIONALITY.
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

                    gait = new Gait(gaitId, "Trot");
                    System.out.println("GAIT: " + gait.getName());

                    //startBeanDiscovery();

                }
                initializing = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        gaitDetected = (EditText) view.findViewById(R.id.gait_detected_edittext);
        averageStrideLength = (EditText) view.findViewById(R.id.average_stride_length_edittext);

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
                /*String gaitId = "-1";
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
                System.out.println("GAIT: " + gait.getName());*/

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
                if (gaitActivity != null)
                {
                    gaitActivity.setFooting(selectFootingSpinner.getSelectedItem().toString());
                }
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
                if (horseSelected == true)
                {
                    new CommandThread(beans, Constant.TAKE_READINGS);
                } else if (horseSelected == false)
                {
                    final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                    materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
                            .setPositiveButton("OK", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    materialDialog.dismiss();
                                }
                            }).setNegativeButton("CANCEL", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            materialDialog.dismiss();
                        }
                    }).show();
                }
            }
        });

        pauseMonitoringButton = (Button) view.findViewById(R.id.pause_monitoring_button);
        pauseMonitoringButton.setEnabled(false);
        pauseMonitoringButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*ArrayList<Gait> gaits = (ArrayList<Gait>) gaitActivity.getGaits();
                gaits.add(gait);
                gaitActivity.setGaits(gaits);

                ArrayList<GaitActivity> gaitActivities = (ArrayList<GaitActivity>) horse.getGaitActivities();
                gaitActivities.add(gaitActivity);
                horse.setGaitActivities(gaitActivities);

                Database.with(getActivity().getApplicationContext()).saveObject(horse);*/

                new CommandThread(beans, Constant.PAUSE_READINGS);
            }
        });

        changeGaitButton = (Button) view.findViewById(R.id.change_gait_button);
        changeGaitButton.setEnabled(false);
        changeGaitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                /*ArrayList<Gait> gaits = (ArrayList<Gait>) gaitActivity.getGaits();
                gaits.add(gait);
                gaitActivity.setGaits(gaits);

                ArrayList<GaitActivity> gaitActivities = (ArrayList<GaitActivity>) horse.getGaitActivities();
                gaitActivities.add(gaitActivity);
                horse.setGaitActivities(gaitActivities);

                Database.with(getActivity().getApplicationContext()).saveObject(horse);*/
            }
        });

        viewAccelerationDataGraphs = (Button) view.findViewById(R.id.view_acceleration_data_graphs_button);
        viewAccelerationDataGraphs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == true)
                {
                    if (gait.getSteps().size() > 0)
                    {
                        List<GaitActivity> gaitActivities = horse.getGaitActivities();
                        List<Gait> gaits = gaitActivity.getGaits();
                        gaits.add(gait);
                        gaitActivity.setGaits(gaits);

                        gaitActivities.add(gaitActivity);

                        horse.setGaitActivities(gaitActivities);

                        Database.with(getActivity().getApplicationContext()).saveObject(horse);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.GRAPH_FRAGMENT_INDICATOR, Constant.GRAPH_FRAGMENT_ACCELERATION);

                    Intent intent = new Intent(getActivity(), GraphActivity.class);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else if (horseSelected == false)
                {
                    final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                    materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
                            .setPositiveButton("OK", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    materialDialog.dismiss();
                                }
                            }).setNegativeButton("CANCEL", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            materialDialog.dismiss();
                        }
                    }).show();
                }
            }
        });

        viewForceDataGraphs = (Button) view.findViewById(R.id.view_force_data_graphs_button);
        viewForceDataGraphs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == true)
                {
                    if (gait.getSteps().size() > 0)
                    {
                        List<GaitActivity> gaitActivities = horse.getGaitActivities();
                        List<Gait> gaits = gaitActivity.getGaits();
                        gaits.add(gait);
                        gaitActivity.setGaits(gaits);

                        gaitActivities.add(gaitActivity);

                        horse.setGaitActivities(gaitActivities);

                        Database.with(getActivity().getApplicationContext()).saveObject(horse);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.GRAPH_FRAGMENT_INDICATOR, Constant.GRAPH_FRAGMENT_FORCE);

                    Intent intent = new Intent(getActivity(), GraphActivity.class);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else if (horseSelected == false)
                {
                    final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                    materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
                            .setPositiveButton("OK", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    materialDialog.dismiss();
                                }
                            }).setNegativeButton("CANCEL", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            materialDialog.dismiss();
                        }
                    }).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

        BeanManager.getInstance().cancelDiscovery();

        for (Bean bean : beans)
        {
            if (bean.isConnected())
            {
                bean.disconnect();
            }
        }

        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_monitor, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.detect_horseshoe_pads)
        {
            if (horseSelected == true)
            {
                BeanManager.getInstance().startDiscovery(this);
                this.dialog = new SpotsDialog(getActivity(), R.style.CustomProgressDialog);
                dialog.show();
            } else if (horseSelected == false)
            {
                final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
                        .setPositiveButton("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                materialDialog.dismiss();
                            }
                        }).setNegativeButton("CANCEL", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        materialDialog.dismiss();
                    }
                }).show();
            }
        } else if (item.getItemId() == R.id.bank_data)
        {
            new CommandThread(beans, Constant.BANK_DATA);
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
        ArrayList<HorseHoof> horseHooves = (ArrayList<HorseHoof>) horse.getHorseHooves();

        for (Bean bean : beans)
        {
            for (HorseHoof horseHoof : horseHooves)
            {
                if (bean.getDevice().getName().equals(horseHoof.getCurrentHorseShoePad()))
                {
                    if (TextUtils.isEmpty(textView1.getText().toString()))
                    {
                        textView1.setText(horseHoof.getCurrentHorseShoePad());
                    } else if (TextUtils.isEmpty(textView2.getText().toString()))
                    {
                        textView2.setText(horseHoof.getCurrentHorseShoePad());
                    } else if (TextUtils.isEmpty(textView3.getText().toString()))
                    {
                        textView3.setText(horseHoof.getCurrentHorseShoePad());
                    } else if (TextUtils.isEmpty(textView4.getText().toString()))
                    {
                        textView4.setText(horseHoof.getCurrentHorseShoePad());
                    }
                }

            }
        }
        dialog.dismiss();
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

        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        String s = null;
        try
        {
            s = new String(value, "UTF-8");

            int bankNumber = bank.getRawValue();

            if (bankNumber == 0)
            {
                String[] pad = s.split("-");
                System.out.println("BANK 1 PAD ID: " + pad[0]);

                if (pad[0].equals("LH"))
                {
                    System.out.println("BANK 1: " + s);
                    forceReadingLH = new Force(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    forceReadLH = true;
                } else if (pad[0].equals("LF"))
                {
                    System.out.println("BANK 1: " + s);
                    forceReadingLF = new Force(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    forceReadLF = true;
                } else if (pad[0].equals("RH"))
                {
                    System.out.println("BANK 1: " + s);
                    forceReadingRH = new Force(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    forceReadRH = true;
                } else if (pad[0].equals("RF"))
                {
                    System.out.println("BANK 1: " + s);
                    forceReadingRF = new Force(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    forceReadRF = true;
                }
            } else if (bankNumber == 1)
            {
                String[] pad = s.split("-");
                System.out.println("BANK 2 PAD ID: " + pad[0]);

                if (pad[0].equals("LH"))
                {
                    System.out.println("BANK 2: " + s);
                    xAccelerationReadingLH = new AccelerationX(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    xAccelerationReadLH = true;
                } else if (pad[0].equals("LF"))
                {
                    System.out.println("BANK 2: " + s);
                    xAccelerationReadingLF = new AccelerationX(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    xAccelerationReadLF = true;
                } else if (pad[0].equals("RH"))
                {
                    System.out.println("BANK 2: " + s);
                    xAccelerationReadingRH = new AccelerationX(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    xAccelerationReadRH = true;
                } else if (pad[0].equals("RF"))
                {
                    System.out.println("BANK 2: " + s);
                    xAccelerationReadingRF = new AccelerationX(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    xAccelerationReadRF = true;
                }
            } else if (bankNumber == 2)
            {
                String[] pad = s.split("-");
                System.out.println("BANK 3 PAD ID: " + pad[0]);

                if (pad[0].equals("LH"))
                {
                    System.out.println("BANK 3: " + s);
                    yAccelerationReadingLH = new AccelerationY(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    yAccelerationReadLH = true;
                } else if (pad[0].equals("LF"))
                {
                    System.out.println("BANK 3: " + s);
                    yAccelerationReadingLF = new AccelerationY(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    yAccelerationReadLF = true;
                } else if (pad[0].equals("RH"))
                {
                    System.out.println("BANK 3: " + s);
                    yAccelerationReadingRH = new AccelerationY(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    yAccelerationReadRH = true;
                } else if (pad[0].equals("RF"))
                {
                    System.out.println("BANK 3: " + s);
                    yAccelerationReadingRF = new AccelerationY(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    yAccelerationReadRF = true;
                }
            } else if (bankNumber == 3)
            {
                String[] pad = s.split("-");
                System.out.println("BANK 4 PAD ID: " + pad[0]);

                if (pad[0].equals("LH"))
                {
                    System.out.println("BANK 4: " + s);
                    zAccelerationReadingLH = new AccelerationZ(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    zAccelerationReadLH = true;
                } else if (pad[0].equals("LF"))
                {
                    System.out.println("BANK 4: " + s);
                    zAccelerationReadingLF = new AccelerationZ(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    zAccelerationReadLF = true;
                } else if (pad[0].equals("RH"))
                {
                    System.out.println("BANK 4: " + s);
                    zAccelerationReadingRH = new AccelerationZ(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    zAccelerationReadRH = true;
                } else if (pad[0].equals("RF"))
                {
                    System.out.println("BANK 4: " + s);
                    zAccelerationReadingRF = new AccelerationZ(String.valueOf(System.currentTimeMillis()), Long.parseLong(String.valueOf((long) Double.parseDouble(pad[1]))));
                    zAccelerationReadRF = true;
                }
            }

            if (forceReadLH == true && xAccelerationReadLH == true && yAccelerationReadLH == true
                    && zAccelerationReadLH == true)
            {
                System.out.println("-->ALL READINGS HAVE BEEN READ LH<--");

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

                step.setForce(forceReadingLH);
                step.setAccelerationX(xAccelerationReadingLH);
                step.setAccelerationY(yAccelerationReadingLH);
                step.setAccelerationZ(zAccelerationReadingLH);
                step.setHoof("LH");

                List<Step> steps = gait.getSteps();
                steps.add(step);

                ArrayList<Double> forceValues = new ArrayList<>();
                ArrayList<Double> xAccelerationValues = new ArrayList<>();
                ArrayList<Double> yAccelerationValues = new ArrayList<>();
                ArrayList<Double> zAccelerationValues = new ArrayList<>();

                for (Step step : steps)
                {
                    forceValues.add(Double.parseDouble(String.valueOf(step.getForce().getForce())));
                    xAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationX().getAccelerationX())));
                    yAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationY().getAccelerationY())));
                    zAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationZ().getAccelerationZ())));
                }

                forceLH.setText(decimalFormat.format(calculateAverage(forceValues)));
                xAxisAccelerationLH.setText(decimalFormat.format(calculateAverage(xAccelerationValues)));
                yAxisAccelerationLH.setText(decimalFormat.format(calculateAverage(yAccelerationValues)));
                zAxisAccelerationLH.setText(decimalFormat.format(calculateAverage(zAccelerationValues)));

                gait.setSteps(steps);

                forceReadLH = false;
                xAccelerationReadLH = false;
                yAccelerationReadLH = false;
                zAccelerationReadLH = false;
            } else if (forceReadLF == true && xAccelerationReadLF == true && yAccelerationReadLF == true
                    && zAccelerationReadLF == true)
            {
                System.out.println("-->ALL READINGS HAVE BEEN READ LF<--");

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

                step.setForce(forceReadingLF);
                step.setAccelerationX(xAccelerationReadingLF);
                step.setAccelerationY(yAccelerationReadingLF);
                step.setAccelerationZ(zAccelerationReadingLF);
                step.setHoof("LF");

                List<Step> steps = gait.getSteps();
                steps.add(step);

                ArrayList<Double> forceValues = new ArrayList<>();
                ArrayList<Double> xAccelerationValues = new ArrayList<>();
                ArrayList<Double> yAccelerationValues = new ArrayList<>();
                ArrayList<Double> zAccelerationValues = new ArrayList<>();

                for (Step step : steps)
                {
                    forceValues.add(Double.parseDouble(String.valueOf(step.getForce().getForce())));
                    xAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationX().getAccelerationX())));
                    yAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationY().getAccelerationY())));
                    zAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationZ().getAccelerationZ())));
                }

                forceLF.setText(decimalFormat.format(calculateAverage(forceValues)));
                xAxisAccelerationLF.setText(decimalFormat.format(calculateAverage(xAccelerationValues)));
                yAxisAccelerationLF.setText(decimalFormat.format(calculateAverage(yAccelerationValues)));
                zAxisAccelerationLF.setText(decimalFormat.format(calculateAverage(zAccelerationValues)));

                gait.setSteps(steps);

                forceReadLF = false;
                xAccelerationReadLF = false;
                yAccelerationReadLF = false;
                zAccelerationReadLF = false;
            } else if (forceReadRH == true && xAccelerationReadRH == true && yAccelerationReadRH == true
                    && zAccelerationReadRH == true)
            {
                System.out.println("-->ALL READINGS HAVE BEEN READ RH<--");

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

                step.setForce(forceReadingRH);
                step.setAccelerationX(xAccelerationReadingRH);
                step.setAccelerationY(yAccelerationReadingRH);
                step.setAccelerationZ(zAccelerationReadingRH);
                step.setHoof("RH");

                List<Step> steps = gait.getSteps();
                steps.add(step);

                ArrayList<Double> forceValues = new ArrayList<>();
                ArrayList<Double> xAccelerationValues = new ArrayList<>();
                ArrayList<Double> yAccelerationValues = new ArrayList<>();
                ArrayList<Double> zAccelerationValues = new ArrayList<>();

                for (Step step : steps)
                {
                    forceValues.add(Double.parseDouble(String.valueOf(step.getForce().getForce())));
                    xAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationX().getAccelerationX())));
                    yAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationY().getAccelerationY())));
                    zAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationZ().getAccelerationZ())));
                }

                forceRH.setText(decimalFormat.format(calculateAverage(forceValues)));
                xAxisAccelerationRH.setText(decimalFormat.format(calculateAverage(xAccelerationValues)));
                yAxisAccelerationRH.setText(decimalFormat.format(calculateAverage(yAccelerationValues)));
                zAxisAccelerationRH.setText(decimalFormat.format(calculateAverage(zAccelerationValues)));

                gait.setSteps(steps);

                forceReadRH = false;
                xAccelerationReadRH = false;
                yAccelerationReadRH = false;
                zAccelerationReadRH = false;
            } else if (forceReadRF == true && xAccelerationReadRF == true && yAccelerationReadRF == true
                    && zAccelerationReadRF == true)
            {
                System.out.println("-->ALL READINGS HAVE BEEN READ RF<--");

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

                step.setForce(forceReadingRF);
                step.setAccelerationX(xAccelerationReadingRF);
                step.setAccelerationY(yAccelerationReadingRF);
                step.setAccelerationZ(zAccelerationReadingRF);
                step.setHoof("RF");

                List<Step> steps = gait.getSteps();
                steps.add(step);

                ArrayList<Double> forceValues = new ArrayList<>();
                ArrayList<Double> xAccelerationValues = new ArrayList<>();
                ArrayList<Double> yAccelerationValues = new ArrayList<>();
                ArrayList<Double> zAccelerationValues = new ArrayList<>();

                for (Step step : steps)
                {
                    forceValues.add(Double.parseDouble(String.valueOf(step.getForce().getForce())));
                    xAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationX().getAccelerationX())));
                    yAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationY().getAccelerationY())));
                    zAccelerationValues.add(Double.parseDouble(String.valueOf(step.getAccelerationZ().getAccelerationZ())));
                }

                forceRF.setText(decimalFormat.format(calculateAverage(forceValues)));
                xAxisAccelerationRF.setText(decimalFormat.format(calculateAverage(xAccelerationValues)));
                yAxisAccelerationRF.setText(decimalFormat.format(calculateAverage(yAccelerationValues)));
                zAxisAccelerationRF.setText(decimalFormat.format(calculateAverage(zAccelerationValues)));

                gait.setSteps(steps);

                forceReadRF = false;
                xAccelerationReadRF = false;
                yAccelerationReadRF = false;
                zAccelerationReadRF = false;
            }

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    private Double calculateAverage(ArrayList<Double> values)
    {
        Double average = 0.0;

        for (int i = 0; i < values.size(); i++)
        {
            average += values.get(i);
        }

        return average / values.size();
    }

    @Override
    public void onError(BeanError error)
    {

    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(String title);
    }

    private void startBeanDiscovery()
    {
        BeanManager.getInstance().startDiscovery(this);
        this.dialog = new SpotsDialog(getActivity(), R.style.CustomProgressDialog);
        dialog.show();
    }
}
