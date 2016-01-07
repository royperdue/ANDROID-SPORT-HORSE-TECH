package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.ArrayList;
import java.util.List;

public class ActivatePadsFragment extends Fragment implements ISimpleDialogCancelListener
{
    private OnFragmentInteractionListener mListener;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ImageView imageView;
    private ImageButton rightHind;
    private ImageButton leftHind;
    private ImageButton leftFront;
    private ImageButton rightFront;
    private final List<Bean> beans = new ArrayList<>();

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
                RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.radioButtonGroupEquine);

                System.out.println("Total beans discovered: " + beans.size());

                for (int i = 0; i < radioGroup.getChildCount(); i++)
                {
                    ((RadioButton) radioGroup.getChildAt(i)).setText(beans.get(i).getDevice().getName());
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_activate_pads, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonGroupEquine);
        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.horse_trotting_top);

        rightFront = (ImageButton) view.findViewById(R.id.imageButtonRF);
        rightFront.setImageResource(R.drawable.rf);
        rightFront.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Toast.makeText(getActivity(), ((RadioButton) view.findViewById(selectedId)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        rightHind = (ImageButton) view.findViewById(R.id.imageButtonRH);
        rightHind.setImageResource(R.drawable.rh);
        rightHind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Toast.makeText(getActivity(), ((RadioButton) view.findViewById(selectedId)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        leftFront = (ImageButton) view.findViewById(R.id.imageButtonLF);
        leftFront.setImageResource(R.drawable.lf);
        leftFront.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Toast.makeText(getActivity(), ((RadioButton) view.findViewById(selectedId)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        leftHind = (ImageButton) view.findViewById(R.id.imageButtonLH);
        leftHind.setImageResource(R.drawable.lh);
        leftHind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Toast.makeText(getActivity(), ((RadioButton) view.findViewById(selectedId)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
    public void onCancelled(int requestCode)
    {

    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
