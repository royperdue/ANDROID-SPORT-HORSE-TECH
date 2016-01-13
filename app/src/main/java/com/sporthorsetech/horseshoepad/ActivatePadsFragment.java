package com.sporthorsetech.horseshoepad;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private Horse horse;
    private final List<Bean> beans = new ArrayList<>();
    private boolean horseSelected = false;
    private boolean horseshoePadSelected = false;

    public ActivatePadsFragment()
    {
    }

    public static Fragment newInstance()
    {
        ActivatePadsFragment fragment = new ActivatePadsFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_activate_pads, container, false);
        setHasOptionsMenu(true);

        this.dialog = new SpotsDialog(getActivity(), R.style.CustomProgressDialog);
        dialog.show();

        List<Horse> horseList = Database.with(getActivity().getApplicationContext()).load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();
        this.horseArray = horseList.toArray(new Horse[horseList.size()]);
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
        beans.add(bean);
        bean.connect(getActivity(), this);
    }

    @Override
    public void onDiscoveryComplete()
    {
        ArrayList<String> activatedPadIds;
        ArrayList<String> padIds = new ArrayList<>();

        System.out.println("Total beans discovered: " + beans.size());
        Toast.makeText(getActivity().getApplicationContext(), "Total beans discovered: " + beans.size(), Toast.LENGTH_SHORT).show();

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

        textView1 = (EditText) view.findViewById(R.id.textView1);
        textView2 = (EditText) view.findViewById(R.id.textView2);
        textView3 = (EditText) view.findViewById(R.id.textView3);
        textView4 = (EditText) view.findViewById(R.id.textView4);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);


        activatePadsButton = (Button) view.findViewById(R.id.buttonActivatePads);
        activatePadsButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                // CHANGED FROM 2 & 4 TO 1 & 4 FOR TESTING.
                if (horse.getHorseHooves().size() == 1 || horse.getHorseHooves().size() == 4)
                    Database.with(getActivity().getApplicationContext()).saveObject(horse);
                else
                {
                    final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                    materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_all_horseshoe_pads))
                            //materialDialog.setBackgroundResource(R.drawable.background);
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

        LinearLayout padIdLayout = (LinearLayout) view.findViewById(R.id.pad_id_Layout);

        if (beans.size() == 0)
        {
            final MaterialDialog materialDialog = new MaterialDialog(getActivity());
            materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_all_horseshoe_pads))
                    //materialDialog.setBackgroundResource(R.drawable.background);
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
            activatedPadIds = LittleDB.getInstance(getActivity().getApplicationContext()).getListString(Constant.ACTIVATED_PAD_IDS);

            for (int i = 0; i < beans.size(); i++)
            {
                CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setId(i);
                checkBox.setText(beans.get(i).getDevice().getName());
                checkBox.setTextSize(getResources().getDimension(R.dimen.text_20sp));
                checkBox.setTextColor(getResources().getColor(R.color.black));
                checkBox.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                checkBox.setOnClickListener(this);

                LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                checkParams.setMargins(15, 15, 0, 0);

                checkBox.setLayoutParams(checkParams);
                padIdLayout.addView(checkBox);
                dialog.dismiss();
            }
        }
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
        if (((CheckBox) v).isChecked())
        {
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
