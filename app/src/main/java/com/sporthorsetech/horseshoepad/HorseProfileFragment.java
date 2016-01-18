package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.List;

public class HorseProfileFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;

    public HorseProfileFragment()
    {
    }

    public static HorseProfileFragment newInstance()
    {
        return new HorseProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_horse_profile, container, false);

        List<Horse> horseList = Database.with(getActivity().getApplicationContext())
                .load(Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();

        for (Horse horse : horseList)
        {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setBackground(getResources().getDrawable(R.drawable.border));
            linearLayout.setPadding(10, 10, 10, 10);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 15, 10, 15);
            linearLayout.setLayoutParams(layoutParams);

            TextView textView1 = new TextView(getActivity());
            textView1.setTextSize(this.getTextSize());
            textView1.setTextColor(Color.BLACK);
            textView1.setText(getResources().getString(R.string.horse_name));

            TextView textView2 = new TextView(getActivity());
            textView2.setTextSize(this.getTextSize());
            textView2.setTextColor(Color.BLACK);
            textView2.setText(horse.getName());

            TextView textView3 = new TextView(getActivity());
            textView3.setTextSize(this.getTextSize());
            textView3.setTextColor(Color.BLACK);
            textView3.setText(getResources().getString(R.string.horse_height));

            TextView textView4 = new TextView(getActivity());
            textView4.setTextSize(this.getTextSize());
            textView4.setTextColor(Color.BLACK);
            textView4.setText(horse.getHeight().toString());

            TextView textView5 = new TextView(getActivity());
            textView5.setTextSize(this.getTextSize());
            textView5.setTextColor(Color.BLACK);
            textView5.setText(getResources().getString(R.string.horse_breed));

            TextView textView6 = new TextView(getActivity());
            textView6.setTextSize(this.getTextSize());
            textView6.setTextColor(Color.BLACK);
            textView6.setText(horse.getBreed());

            TextView textView7 = new TextView(getActivity());
            textView7.setTextSize(this.getTextSize());
            textView7.setTextColor(Color.BLACK);
            textView7.setText(getResources().getString(R.string.horse_sex));

            TextView textView8 = new TextView(getActivity());
            textView8.setTextSize(this.getTextSize());
            textView8.setTextColor(Color.BLACK);
            textView8.setText(horse.getSex());

            TextView textView9 = new TextView(getActivity());
            textView9.setTextSize(this.getTextSize());
            textView9.setTextColor(Color.BLACK);
            textView9.setText(getResources().getString(R.string.horse_age));

            TextView textView10 = new TextView(getActivity());
            textView10.setTextSize(this.getTextSize());
            textView10.setTextColor(Color.BLACK);
            textView10.setText(horse.getAge().toString());

            TextView textView11 = new TextView(getActivity());
            textView11.setTextSize(this.getTextSize());
            textView11.setTextColor(Color.BLACK);
            textView11.setText(getResources().getString(R.string.horse_sport));

            TextView textView12 = new TextView(getActivity());
            textView12.setTextSize(this.getTextSize());
            textView12.setTextColor(Color.BLACK);
            textView12.setText(horse.getDiscipline());

            linearLayout.addView(textView1);
            linearLayout.addView(textView2);
            linearLayout.addView(textView3);
            linearLayout.addView(textView4);
            linearLayout.addView(textView5);
            linearLayout.addView(textView6);
            linearLayout.addView(textView7);
            linearLayout.addView(textView8);
            linearLayout.addView(textView9);
            linearLayout.addView(textView10);
            linearLayout.addView(textView11);
            linearLayout.addView(textView12);

            LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
            mainLayout.addView(linearLayout);
        }

        return view;
    }

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction("title");
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

    private float getTextSize()
    {
        switch (getActivity().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK)
        {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return 34f;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return 24f;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return 20f;
            default:
                return 24f;
        }
    }

}
