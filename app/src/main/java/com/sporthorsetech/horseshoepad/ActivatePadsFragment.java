package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.HorseHoof;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class ActivatePadsFragment extends Fragment implements BeanDiscoveryListener, BeanListener
{
    private OnFragmentInteractionListener mListener;
    private MaterialDialog materialDialog;
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
        final View view = inflater.inflate(R.layout.fragment_activate_pads, container, false);
        setHasOptionsMenu(true);

        List<Horse> horseList = Database.with(getActivity().getApplicationContext()).load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();
        Horse[] horseArray = horseList.toArray(new Horse[horseList.size()]);

        materialDialog = new MaterialDialog(getActivity());
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
        radioButton1.setVisibility(View.INVISIBLE);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton2.setVisibility(View.INVISIBLE);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioButton3.setVisibility(View.INVISIBLE);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4);
        radioButton4.setVisibility(View.INVISIBLE);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonGroupEquine);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (radioButton1.isChecked())
                {
                    horseshoePadSelected = true;
                } else if (radioButton2.isChecked())
                {
                    horseshoePadSelected = true;
                } else if (radioButton3.isChecked())
                {
                    horseshoePadSelected = true;
                } else if (radioButton4.isChecked())
                {
                    horseshoePadSelected = true;
                }
            }
        });

        textView1 = (EditText) view.findViewById(R.id.textView1);
        textView2 = (EditText) view.findViewById(R.id.textView2);
        textView3 = (EditText) view.findViewById(R.id.textView3);
        textView4 = (EditText) view.findViewById(R.id.textView4);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.horse_trotting_top);
        imageView.getLayoutParams().width = metrics.widthPixels;
        imageView.requestLayout();

        rightFront = (ImageButton) view.findViewById(R.id.imageButtonRF);
        rightFront.getLayoutParams().width = metrics.widthPixels / 4;
        rightFront.requestLayout();
        rightFront.setImageResource(R.drawable.rf);
        rightFront.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
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
                } else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = true;
                    rightHindSelected = false;
                    leftFrontSelected = false;
                    leftHindSelected = false;

                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.confirm_pad_assignment)).setMessage("Set horseshoe pad " + ((RadioButton)
                                view.findViewById(selectedId)).getText() + " to right front foot?")
                                //materialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton("OK", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        assignHorseshoePad();
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

                    horseshoePadAssignment = "Right Front: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    rightFront.setEnabled(false);
                } else
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horseshoe_pad))
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
            }
        });

        rightHind = (ImageButton) view.findViewById(R.id.imageButtonRH);
        rightHind.getLayoutParams().width = metrics.widthPixels / 4;
        rightHind.requestLayout();
        rightHind.setImageResource(R.drawable.rh);
        rightHind.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
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
                } else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = false;
                    rightHindSelected = true;
                    leftFrontSelected = false;
                    leftHindSelected = false;

                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.confirm_pad_assignment)).setMessage("Set horseshoe pad " + ((RadioButton)
                                view.findViewById(selectedId)).getText() + " to right hind foot?")
                                //materialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton("OK", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        assignHorseshoePad();
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


                    horseshoePadAssignment = "Right Hind: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    rightHind.setEnabled(false);
                } else
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horseshoe_pad))
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
            }
        });

        leftFront = (ImageButton) view.findViewById(R.id.imageButtonLF);
        leftFront.getLayoutParams().width = metrics.widthPixels / 4;
        leftFront.requestLayout();
        leftFront.setImageResource(R.drawable.lf);
        leftFront.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
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
                } else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = false;
                    rightHindSelected = false;
                    leftFrontSelected = true;
                    leftHindSelected = false;

                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.confirm_pad_assignment)).setMessage("Set horseshoe pad " + ((RadioButton)
                                view.findViewById(selectedId)).getText() + " to left front foot?")
                                //materialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton("OK", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        assignHorseshoePad();
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


                    horseshoePadAssignment = "Left Front: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    leftFront.setEnabled(false);
                } else
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horseshoe_pad))
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
            }
        });

        leftHind = (ImageButton) view.findViewById(R.id.imageButtonLH);
        leftHind.getLayoutParams().width = metrics.widthPixels / 4;
        leftHind.requestLayout();
        leftHind.setImageResource(R.drawable.lh);
        leftHind.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                if (horseSelected == false)
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horse))
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
                } else if (horseshoePadSelected == true)
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    horseShoePadId = ((RadioButton) view.findViewById(selectedId)).getText().toString();
                    ((RadioButton) view.findViewById(selectedId)).setEnabled(false);
                    rightFrontSelected = false;
                    rightHindSelected = false;
                    leftFrontSelected = false;
                    leftHindSelected = true;

                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.confirm_pad_assignment)).setMessage("Set horseshoe pad " + ((RadioButton)
                                view.findViewById(selectedId)).getText() + " to left hind foot?")
                                //materialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton("OK", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        assignHorseshoePad();
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


                    horseshoePadAssignment = "Left Hind: " + ((RadioButton)
                            view.findViewById(selectedId)).getText();
                    horseshoePadSelected = false;
                    leftHind.setEnabled(false);
                } else
                {
                    if (materialDialog != null)
                    {
                        materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.must_select_horseshoe_pad))
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
            }
        });

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
                    if (materialDialog != null)
                    {
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
            }
        });

        BeanManager.getInstance().

                startDiscovery(this);

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

    private void assignHorseshoePad()
    {
        ArrayList<HorseHoof> horseFeet;

        if (rightFrontSelected == true)
        {
            HorseHoof horseHoof = new HorseHoof("1", "RF");
            horseHoof.setCurrentHorseShoePad(horseShoePadId);

            if (horse.getHorseHooves() != null)
                horseFeet = (ArrayList<HorseHoof>) horse.getHorseHooves();
            else
                horseFeet = new ArrayList<>();

            horseFeet.add(horseHoof);
            horse.setHorseHooves(horseFeet);
        } else if (rightHindSelected == true)
        {
            HorseHoof horseHoof = new HorseHoof("2", "RH");
            horseHoof.setCurrentHorseShoePad(horseShoePadId);

            if (horse.getHorseHooves() != null)
                horseFeet = (ArrayList<HorseHoof>) horse.getHorseHooves();
            else
                horseFeet = new ArrayList<>();

            horseFeet.add(horseHoof);
            horse.setHorseHooves(horseFeet);
        } else if (leftFrontSelected == true)
        {
            HorseHoof horseHoof = new HorseHoof("3", "LF");
            horseHoof.setCurrentHorseShoePad(horseShoePadId);

            if (horse.getHorseHooves() != null)
                horseFeet = (ArrayList<HorseHoof>) horse.getHorseHooves();
            else
                horseFeet = new ArrayList<>();

            horseFeet.add(horseHoof);
            horse.setHorseHooves(horseFeet);
        } else if (leftHindSelected == true)
        {
            HorseHoof horseHoof = new HorseHoof("4", "LH");
            horseHoof.setCurrentHorseShoePad(horseShoePadId);

            if (horse.getHorseHooves() != null)
                horseFeet = (ArrayList<HorseHoof>) horse.getHorseHooves();
            else
                horseFeet = new ArrayList<>();

            horseFeet.add(horseHoof);
            horse.setHorseHooves(horseFeet);
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

    @Override
    public void onBeanDiscovered(Bean bean, int rssi)
    {
        beans.add(bean);
        bean.connect(getActivity(), this);
    }

    @Override
    public void onDiscoveryComplete()
    {
        System.out.println("Total beans discovered: " + beans.size());
        Toast.makeText(getActivity().getApplicationContext(), "Total beans discovered: " + beans.size(), Toast.LENGTH_SHORT).show();

        if (beans.size() == 0)
        {
            if (materialDialog != null)
            {
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
        } else if (beans.size() == 1)
        {
            radioButton1.setVisibility(View.VISIBLE);
        } else if (beans.size() == 2)
        {
            radioButton1.setVisibility(View.VISIBLE);
            radioButton2.setVisibility(View.VISIBLE);
        } else if (beans.size() == 3)
        {
            radioButton1.setVisibility(View.VISIBLE);
            radioButton2.setVisibility(View.VISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
        } else if (beans.size() == 4)
        {
            radioButton1.setVisibility(View.VISIBLE);
            radioButton2.setVisibility(View.VISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < beans.size(); i++)
        {
            ((RadioButton) radioGroup.getChildAt(i)).setText(beans.get(i).getDevice().getName());
        }

        if (beans.size() % 2 != 0)
        {
            if (materialDialog != null)
            {
                materialDialog.setTitle(getString(R.string.notice)).setMessage(getString(R.string.short_one_horseshoe_pad))
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

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
