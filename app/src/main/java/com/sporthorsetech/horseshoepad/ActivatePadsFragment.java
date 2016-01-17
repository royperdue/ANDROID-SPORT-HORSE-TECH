package com.sporthorsetech.horseshoepad;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.HorseHoof;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import me.drakeet.materialdialog.MaterialDialog;

public class ActivatePadsFragment extends Fragment implements BeanDiscoveryListener, BeanListener, View.OnClickListener
{
    private OnFragmentInteractionListener mListener;
    private View view;
    private AlertDialog dialog;
    private Horse[] horseArray;
    private EditText textView1;
    private EditText textView2;
    private EditText textView3;
    private EditText textView4;
    private Spinner selectHorseSpinner;
    private Button activatePadsButton;
    private Button monitorGaitActivityButton;
    private Horse horse;
    private ArrayList<String> padIdsToDisplay;
    private ArrayList<Integer> isCheckedList = new ArrayList<>();
    private final List<Bean> beans = new ArrayList<>();
    private LinearLayout padIdLayout;
    private ArrayList<HorseHoof> horseHooves;
    private boolean horseSelected = false;
    private List<Horse> horseList;
    private boolean horseshoePadSelected = false;

    public ActivatePadsFragment()
    {
    }

    public static Fragment newInstance()
    {
        return new ActivatePadsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_activate_pads, container, false);
        setHasOptionsMenu(true);

        horseList = Database.with(getActivity().getApplicationContext()).load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();
        horseArray = horseList.toArray(new Horse[horseList.size()]);

        this.dialog = new SpotsDialog(getActivity(), R.style.CustomProgressDialog);
        dialog.show();

        BeanManager.getInstance().startDiscovery(this);

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

        inflater.inflate(R.menu.menu_activate, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.detect_horseshoe_pads)
        {
            BeanManager.getInstance().cancelDiscovery();
            BeanManager.getInstance().startDiscovery(this);
            this.dialog = new SpotsDialog(getActivity(), R.style.CustomProgressDialog);
            dialog.show();
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
        ArrayList<Bean> beansToActivate = new ArrayList<>();
        this.padIdsToDisplay = new ArrayList<>();

        System.out.println("Total beans discovered: " + beans.size());
        Toast.makeText(getActivity().getApplicationContext(), "Total beans discovered: " + beans.size(), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getActivity().getApplicationContext(), horse.getName(), Toast.LENGTH_SHORT).show();

                    if (horse.getHorseHooves() != null)
                        horseHooves = (ArrayList<HorseHoof>) horse.getHorseHooves();
                    else
                        horseHooves = new ArrayList<>();

                }
                initializing = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                horseSelected = true;

                SpinnerAdapter spinnerAdapter = (SpinnerAdapter) selectHorseSpinner.getAdapter();

                horse = spinnerAdapter.getItem(0);
                Toast.makeText(getActivity().getApplicationContext(), horse.getName(), Toast.LENGTH_SHORT).show();

                if (horse.getHorseHooves() != null)
                    horseHooves = (ArrayList<HorseHoof>) horse.getHorseHooves();
                else
                    horseHooves = new ArrayList<>();
            }
        });

        textView1 = (EditText) view.findViewById(R.id.textView1);
        textView2 = (EditText) view.findViewById(R.id.textView2);
        textView3 = (EditText) view.findViewById(R.id.textView3);
        textView4 = (EditText) view.findViewById(R.id.textView4);

        padIdLayout = (LinearLayout) view.findViewById(R.id.pad_id_Layout);

        activatePadsButton = (Button) view.findViewById(R.id.buttonActivatePads);
        activatePadsButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == true && isCheckedList.size() > 0)
                {
                    LittleDB.getInstance(getActivity().getApplicationContext()).putInt(Constant.NUMBER_OF_HORSESHOE_PADS_ACTIVATED, isCheckedList.size());

                    for (int i = 0; i < padIdLayout.getChildCount(); i++)
                    {
                        View view = padIdLayout.getChildAt(i);

                        if (view instanceof CheckBox)
                        {
                            String padId = ((CheckBox) view).getText().toString();
                            String[] s = padId.split("-");
                            HorseHoof horseHoof = new HorseHoof(Integer.toString(i), s[0]);
                            horseHoof.setCurrentHorseShoePad(padId);
                            horseHooves.add(horseHoof);
                            horse.setHorseHooves(horseHooves);
                            ((CheckBox) view).setEnabled(false);
                        }
                    }
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
                } else if (isCheckedList.size() == 0)
                {
                    final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                    materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horseshoe_pad))
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
                Database.with(getActivity().getApplicationContext()).saveObject(horse);
            }
        });

        monitorGaitActivityButton = (Button) view.findViewById(R.id.monitor_gait_activity_button);
        monitorGaitActivityButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                BeanManager.getInstance().cancelDiscovery();

                for (Bean bean : beans)
                {
                    if (bean.isConnected())
                    {
                        bean.disconnect();
                    }
                }

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                String title = getString(R.string.monitor_gait_activity);
                Fragment fragment = GaitMonitorFragment.newInstance();

                transaction.addToBackStack(title);
                transaction.replace(R.id.container, fragment, title);
                transaction.commit();
            }
        });

        if (beans.size() == 0)
        {
            final MaterialDialog materialDialog = new MaterialDialog(getActivity());
            materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.no_pads_detected))
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
        } else
        {
            for (Bean bean : beans)
            {
                padIdsToDisplay.add(bean.getDevice().getName());
            }

            for (int i = 0; i < beans.size(); i++)
            {
                CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setId(i);
                checkBox.setText(padIdsToDisplay.get(i));
                checkBox.setTextSize(getResources().getDimension(R.dimen.text_20sp));
                checkBox.setTextColor(getResources().getColor(R.color.black));
                checkBox.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                checkBox.setOnClickListener(this);

                LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                checkParams.setMargins(15, 15, 0, 0);

                checkBox.setLayoutParams(checkParams);
                padIdLayout.addView(checkBox);
            }
        }
        dialog.dismiss();
    }

    @Override
    public void onConnected()
    {

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

    }

    @Override
    public void onError(BeanError error)
    {

    }

    @Override
    public void onClick(View v)
    {
        if (isCheckedList.contains(((CheckBox) v).getId()))
        {
            if (!((CheckBox) v).isChecked())
            {
                if (textView1.getText().toString().equals(((CheckBox) v).getText().toString()))
                    textView1.setText("");
                else if (textView2.getText().toString().equals(((CheckBox) v).getText().toString()))
                    textView2.setText("");
                else if (textView3.getText().toString().equals(((CheckBox) v).getText().toString()))
                    textView3.setText("");
                else if (textView4.getText().toString().equals(((CheckBox) v).getText().toString()))
                    textView4.setText("");

                isCheckedList.remove(((CheckBox) v).getId());
            }
        }

        if (((CheckBox) v).isChecked())
        {
            isCheckedList.add(((CheckBox) v).getId());

            if (TextUtils.isEmpty(textView1.getText().toString()))
            {
                textView1.setText(((CheckBox) v).getText().toString());
            } else if (TextUtils.isEmpty(textView2.getText().toString()))
            {
                textView2.setText(((CheckBox) v).getText().toString());
            } else if (TextUtils.isEmpty(textView3.getText().toString()))
            {
                textView3.setText(((CheckBox) v).getText().toString());
            } else if (TextUtils.isEmpty(textView4.getText().toString()))
            {
                textView4.setText(((CheckBox) v).getText().toString());
            }
            Toast.makeText(getActivity(),
                    "Bro, try Android :)", Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
