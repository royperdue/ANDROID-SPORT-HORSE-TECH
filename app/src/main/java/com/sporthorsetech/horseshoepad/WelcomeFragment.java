package com.sporthorsetech.horseshoepad;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Gait;
import com.sporthorsetech.horseshoepad.utility.equine.GaitActivity;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.equine.HorseHoof;
import com.sporthorsetech.horseshoepad.utility.equine.Step;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.List;

public class WelcomeFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;
    private TextView testTextView;
    private Button testButton;

    public WelcomeFragment()
    {
    }

    public static Fragment newInstance()
    {
        WelcomeFragment fragment = new WelcomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        testTextView = (TextView) view.findViewById(R.id.test_textview);

        testButton = (Button) view.findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StringBuilder stringBuilder = new StringBuilder();
                List<Horse> horseList = Database.with(getActivity().getApplicationContext()).load(Horse.TYPE.horse)
                        .orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();

                for (Horse horse : horseList)
                {
                    stringBuilder.append("HORSES NAME: " + horse.getName() + " ");

                    if(horse.getHorseHooves() != null)
                    {
                        for (HorseHoof horseHoof : horse.getHorseHooves())
                        {
                            stringBuilder.append("HOOF: " + horseHoof.getFoot() + " ");
                        }
                    }

                    if(horse.getGaitActivities() != null)
                    {
                        for (GaitActivity gaitActivity : horse.getGaitActivities())
                        {
                            stringBuilder.append("GAIT ACTIVITY: " + gaitActivity.getStoredObjectId() + " ");

                            for (Gait gait : gaitActivity.getGaits())
                            {
                                stringBuilder.append("GAIT: " + gait.getName() + " ");

                                for (Step step : gait.getSteps())
                                {
                                    stringBuilder.append("STEP: " + step.getStoredObjectId() + " " + "STEP HOOF: " + step.getHoof());
                                }
                            }
                        }
                    }
                }

                testTextView.setText(stringBuilder.toString());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(getString(R.string.welcome));
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

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(String title);
    }
}
