package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewHorseFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    private OnFragmentInteractionListener mListener;

    public NewHorseFragment()
    {
    }

    public static NewHorseFragment newInstance()
    {
        NewHorseFragment fragment = new NewHorseFragment();
        //Bundle args = new Bundle();
        //args.putString(Constant.ARG_PARAM1, param1);
        //fragment.setArguments(args);

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
        View view = inflater.inflate(R.layout.fragment_new_horse, container, false);

        Spinner equestrianSportSpinner = (Spinner) view.findViewById(R.id.horse_sport_spinner);
        ArrayAdapter<CharSequence> adapterSport = ArrayAdapter.createFromResource(getActivity(),
                R.array.equestrian_discipline_array, android.R.layout.simple_spinner_item);
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equestrianSportSpinner.setAdapter(adapterSport);

        Spinner horseSexSpinner = (Spinner) view.findViewById(R.id.horse_sex_spinner);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(getActivity(),
                R.array.horse_sex_array, android.R.layout.simple_spinner_item);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equestrianSportSpinner.setAdapter(adapterSex);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(getString(R.string.new_horse_profile));
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
            mListener.onFragmentInteraction(getString(R.string.new_horse_profile));
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
