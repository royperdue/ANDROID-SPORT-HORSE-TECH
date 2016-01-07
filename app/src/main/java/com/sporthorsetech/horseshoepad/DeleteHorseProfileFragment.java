package com.sporthorsetech.horseshoepad;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteHorseProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteHorseProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteHorseProfileFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;
    private Button deleteAllHorseProfilesButton;
    private List<Horse> horses;

    public DeleteHorseProfileFragment()
    {
    }

    public static DeleteHorseProfileFragment newInstance()
    {
        DeleteHorseProfileFragment fragment = new DeleteHorseProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_delete_horse_profile, container, false);

        deleteAllHorseProfilesButton = (Button) view.findViewById(R.id.delete_all_horse_profiles);
        deleteAllHorseProfilesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteAllHorseProfiles();
            }
        });

        return view;
    }

    private void deleteAllHorseProfiles()
    {
        horses = Database.with(getActivity().getApplicationContext()).load(
                Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.DESC).limit(Constant.MAX_HORSES).execute();

        for(Horse horse : horses)
            Database.with(getActivity().getApplicationContext()).deleteObject(horse);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
}
