package com.sporthorsetech.horseshoepad;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.simplealertdialog.SimpleAlertDialog;
import com.simplealertdialog.SimpleAlertDialogFragment;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.HorseFoot;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.ArrayList;
import java.util.List;

public class ActivatePadsFragment extends Fragment implements SimpleAlertDialog.OnClickListener
{
    private OnFragmentInteractionListener mListener;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private EditText textView1;
    private EditText textView2;
    private EditText textView3;
    private EditText textView4;
    private ImageView imageView;
    private ImageButton rightHind;
    private ImageButton leftHind;
    private ImageButton leftFront;
    private ImageButton rightFront;
    private Spinner selectHorseSpinner;
    private Button activatePadsButton;
    private Horse horse;
    private String horseShoePadId = "horseShoePadId";
    private final List<Bean> beans = new ArrayList<>();
    private boolean leftHindSelected = false;
    private boolean leftFrontSelected = false;
    private boolean rightHindSelected = false;
    private boolean rightFrontSelected = false;
    private boolean horseSelected = false;
    private boolean horseshoePadSelected = false;
    private String horseshoePadAssignment = "";

    public ActivatePadsFragment()
    {
        // Required empty public constructor
    }

    public static ActivatePadsFragment newInstance()
    {
        ActivatePadsFragment fragment = new ActivatePadsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        detectHorseshoePads();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_activate_pads, container, false);
        setHasOptionsMenu(true);

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
                    horseSelected = true;
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

        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonGroupEquine);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(radioButton1.isChecked())
                {
                    horseshoePadSelected = true;
                }
                else if(radioButton2.isChecked())
                {
                    horseshoePadSelected = true;
                }
                else if(radioButton3.isChecked())
                {
                    horseshoePadSelected = true;
                }
                else if(radioButton4.isChecked())
                {
                    horseshoePadSelected = true;
                }
            }
        });

        textView1 = (EditText) view.findViewById(R.id.textView1);
        textView2 = (EditText) view.findViewById(R.id.textView2);
        textView3 = (EditText) view.findViewById(R.id.textView3);
        textView4 = (EditText) view.findViewById(R.id.textView4);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.horse_trotting_top);

        rightFront = (ImageButton) view.findViewById(R.id.imageButtonRF);
        rightFront.setImageResource(R.drawable.rf);
        rightFront.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horse.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
                else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = true;
                    rightHindSelected = false;
                    leftFrontSelected = false;
                    leftHindSelected = false;

                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("Set horseshoe pad " + ((RadioButton)
                                    view.findViewById(selectedId)).getText() + " to right front foot?")
                            .setPositiveButton(android.R.string.ok)
                            .setNegativeButton(android.R.string.cancel)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .setRequestCode(1)
                            .create().show(getActivity().getFragmentManager(), "dialog");

                    horseshoePadAssignment = "Right Front: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    rightFront.setEnabled(false);
                }
                else
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horseshoe pad.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
            }
        });

        rightHind = (ImageButton) view.findViewById(R.id.imageButtonRH);
        rightHind.setImageResource(R.drawable.rh);
        rightHind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horse.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
                else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = false;
                    rightHindSelected = true;
                    leftFrontSelected = false;
                    leftHindSelected = false;

                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("Set horseshoe pad " + ((RadioButton)
                                    view.findViewById(selectedId)).getText() + " to right hind foot?")
                            .setPositiveButton(android.R.string.ok)
                            .setNegativeButton(android.R.string.cancel)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .setRequestCode(1)
                            .create().show(getActivity().getFragmentManager(), "dialog");

                    horseshoePadAssignment = "Right Hind: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    rightHind.setEnabled(false);
                }
                else
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horseshoe pad.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
            }
        });

        leftFront = (ImageButton) view.findViewById(R.id.imageButtonLF);
        leftFront.setImageResource(R.drawable.lf);
        leftFront.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horse.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
                else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = false;
                    rightHindSelected = false;
                    leftFrontSelected = true;
                    leftHindSelected = false;

                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("Set horseshoe pad " + ((RadioButton)
                                    view.findViewById(selectedId)).getText() + " to left front foot?")
                            .setPositiveButton(android.R.string.ok)
                            .setNegativeButton(android.R.string.cancel)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .setRequestCode(1)
                            .create().show(getActivity().getFragmentManager(), "dialog");

                    horseshoePadAssignment = "Left Front: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    leftFront.setEnabled(false);
                }
                else
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horseshoe pad.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
            }
        });

        leftHind = (ImageButton) view.findViewById(R.id.imageButtonLH);
        leftHind.setImageResource(R.drawable.lh);
        leftHind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horse.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
                else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = false;
                    rightHindSelected = false;
                    leftFrontSelected = false;
                    leftHindSelected = true;

                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("Set horseshoe pad " + ((RadioButton)
                                    view.findViewById(selectedId)).getText() + " to left hind foot?")
                            .setPositiveButton(android.R.string.ok)
                            .setNegativeButton(android.R.string.cancel)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .setRequestCode(1)
                            .create().show(getActivity().getFragmentManager(), "dialog");

                    horseshoePadAssignment = "Left Hind: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    leftHind.setEnabled(false);
                }
                else
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must select a horseshoe pad.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
            }
        });

        activatePadsButton = (Button) view.findViewById(R.id.buttonActivatePads);
        activatePadsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horse.getHorseFeet().size() == 2 || horse.getHorseFeet().size() == 4)
                    Database.with(getActivity().getApplicationContext()).saveObject(horse);
                else
                {
                    new SimpleAlertDialogFragment.Builder()
                            .setMessage("You must assign all horseshoe pads before activating.")
                            .setPositiveButton(android.R.string.ok)
                            .setTargetFragment(ActivatePadsFragment.this)
                            .create().show(getActivity().getFragmentManager(), "dialog");
                }
            }
        });

        return view;
    }

    private void detectHorseshoePads()
    {
        final BeanListener beanListener = new BeanListener()
        {
            @Override
            public void onConnected()
            {
            }

            @Override
            public void onError(BeanError berr)
            {
                System.out.println("Bean has errors..");
            }

            @Override
            public void onConnectionFailed()
            {
                System.out.println("Bean connection failed");
            }

            @Override
            public void onDisconnected()
            {
                System.out.println("Bean disconnected");
            }

            @Override
            public void onScratchValueChanged(ScratchBank bank, byte[] value)
            {
            }

            @Override
            public void onSerialMessageReceived(byte[] data)
            {
            }
        };

        BeanDiscoveryListener listener = new BeanDiscoveryListener()
        {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi)
            {
                beans.add(bean);
                bean.connect(getActivity(), beanListener);
            }

            @Override
            public void onDiscoveryComplete()
            {
                System.out.println("Total beans discovered: " + beans.size());
                Toast.makeText(getActivity().getApplicationContext(), "Total beans discovered: " + beans.size(), Toast.LENGTH_SHORT).show();

                for (Bean bean : beans)
                {
                    System.out.println(bean.getDevice().getName());   // "Bean"
                }

                for (int i = 0; i < radioGroup.getChildCount(); i++)
                {
                    ((RadioButton) radioGroup.getChildAt(i)).setText(beans.get(i).getDevice().getName());
                }
            }
        };
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(getString(R.string.activate_horseshoe_pads));
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
            detectHorseshoePads();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDialogPositiveButtonClicked(SimpleAlertDialog dialog, int requestCode, View view)
    {
        ArrayList<HorseFoot> horseFeet;

        if (requestCode == 1)
        {
            if (rightFrontSelected == true)
            {
                HorseFoot horseFoot = new HorseFoot("1", "RF");
                horseFoot.setCurrentHorseShoePad(horseShoePadId);

                if(horse.getHorseFeet() != null)
                    horseFeet = (ArrayList<HorseFoot>) horse.getHorseFeet();
                else
                    horseFeet = new ArrayList<>();

                horseFeet.add(horseFoot);
                horse.setHorseFeet(horseFeet);
            }
            else if (rightHindSelected == true)
            {
                HorseFoot horseFoot = new HorseFoot("2", "RH");
                horseFoot.setCurrentHorseShoePad(horseShoePadId);

                if(horse.getHorseFeet() != null)
                    horseFeet = (ArrayList<HorseFoot>) horse.getHorseFeet();
                else
                    horseFeet = new ArrayList<>();

                horseFeet.add(horseFoot);
                horse.setHorseFeet(horseFeet);
            }
            else if (leftFrontSelected == true)
            {
                HorseFoot horseFoot = new HorseFoot("3", "LF");
                horseFoot.setCurrentHorseShoePad(horseShoePadId);

                if(horse.getHorseFeet() != null)
                    horseFeet = (ArrayList<HorseFoot>) horse.getHorseFeet();
                else
                    horseFeet = new ArrayList<>();

                horseFeet.add(horseFoot);
                horse.setHorseFeet(horseFeet);
            }
            else if (leftHindSelected == true)
            {
                HorseFoot horseFoot = new HorseFoot("4", "LH");
                horseFoot.setCurrentHorseShoePad(horseShoePadId);

                if(horse.getHorseFeet() != null)
                    horseFeet = (ArrayList<HorseFoot>) horse.getHorseFeet();
                else
                    horseFeet = new ArrayList<>();

                horseFeet.add(horseFoot);
                horse.setHorseFeet(horseFeet);
            }

            if (TextUtils.isEmpty(this.textView1.getText().toString()))
            {
                this.textView1.setText(horseshoePadAssignment);
            } else if (TextUtils.isEmpty(this.textView2.getText().toString()))
            {
                this.textView2.setText(horseshoePadAssignment);
            } else if (TextUtils.isEmpty(this.textView3.getText().toString()))
            {
                this.textView3.setText(horseshoePadAssignment);
            } else if (TextUtils.isEmpty(this.textView4.getText().toString()))
            {
                this.textView4.setText(horseshoePadAssignment);
            }
        }
    }

    @Override
    public void onDialogNegativeButtonClicked(SimpleAlertDialog dialog, int requestCode, View view)
    {

    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
