package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;
import com.sporthorsetech.horseshoepad.utility.persist.Database;

import java.util.ArrayList;

public class NewHorseFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;
    private Spinner horseSexSpinner;
    private Spinner horseBreedSpinner;
    private Spinner equestrianSportSpinner;
    private Button createHorseProfileButton;
    private Button activateHorseshoePadsButton;
    private EditText horseNameEditText;
    private EditText horseAgeEditText;
    private EditText horseHeightEditText;
    private String horseSex;
    private String horseBreed;
    private String horseDiscipline;

    public NewHorseFragment()
    {
    }

    public static Fragment newInstance()
    {
        return new NewHorseFragment();
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

        horseSexSpinner = (Spinner) view.findViewById(R.id.horse_sex_spinner);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(getActivity(),
                R.array.horse_sex_array, android.R.layout.simple_spinner_item);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horseSexSpinner.setAdapter(adapterSex);
        horseSexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                horseSex = horseSexSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        horseBreedSpinner = (Spinner) view.findViewById(R.id.horse_breed_spinner);
        ArrayAdapter<CharSequence> adapterBreed = ArrayAdapter.createFromResource(getActivity(),
                R.array.horse_breed_array, android.R.layout.simple_spinner_item);
        adapterBreed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horseBreedSpinner.setAdapter(adapterBreed);
        horseBreedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                horseBreed = horseBreedSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        equestrianSportSpinner = (Spinner) view.findViewById(R.id.horse_sport_spinner);
        ArrayAdapter<CharSequence> adapterSport = ArrayAdapter.createFromResource(getActivity(),
                R.array.equestrian_discipline_array, android.R.layout.simple_spinner_item);
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equestrianSportSpinner.setAdapter(adapterSport);
        equestrianSportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                horseDiscipline = equestrianSportSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        horseNameEditText = (EditText) view.findViewById(R.id.horse_name_editText);
        horseAgeEditText = (EditText) view.findViewById(R.id.horse_age_editText);
        horseHeightEditText = (EditText) view.findViewById(R.id.horse_height_editText);

        createHorseProfileButton = (Button) view.findViewById(R.id.create_horse_profile);
        createHorseProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createHorseProfile();
                createHorseProfileButton.setVisibility(View.GONE);
                activateHorseshoePadsButton.setVisibility(View.VISIBLE);
            }
        });

        activateHorseshoePadsButton = (Button) view.findViewById(R.id.activate_horseshoe_pads_button);
        activateHorseshoePadsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                String title = getString(R.string.activate_horseshoe_pads);
                Fragment fragment = ActivatePadsFragment.newInstance();

                transaction.addToBackStack(title);
                transaction.replace(R.id.container, fragment, title);
                transaction.commit();
            }
        });

        return view;
    }

    private void createHorseProfile()
    {
        String id = "-1";
        String name = "name";
        Long age = -1L;
        String sex = "sex";
        String breed = "breed";
        String discipline = "discipline";
        Long height = -1L;
        ArrayList<String> horseIds = LittleDB.getInstance(getActivity().getApplicationContext()).getListString(Constant.HORSE_IDS);

        if (horseIds == null || horseIds.size() == 0)
        {
            id = "1";
            horseIds.add("1");
            LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.HORSE_IDS, horseIds);
        }
        else if (horseIds != null && horseIds.size() > 0)
        {
            String lastId = horseIds.get(horseIds.size() - 1);
            id = String.valueOf(Integer.parseInt(lastId) + 1);
            horseIds.add(lastId);
            horseIds.add(id);
            LittleDB.getInstance(getActivity().getApplicationContext()).putListString(Constant.HORSE_IDS, horseIds);
        }

        if (!TextUtils.isEmpty(this.horseNameEditText.getText().toString()))
        {
            name = this.horseNameEditText.getText().toString();
        }

        if (!TextUtils.isEmpty(this.horseAgeEditText.getText().toString()))
        {
            age = Long.parseLong(String.valueOf((long) Double.parseDouble(this.horseAgeEditText.getText().toString())));
        }

        if (!TextUtils.isEmpty(this.horseHeightEditText.getText().toString()))
        {
            height = Long.parseLong(String.valueOf((long) Double.parseDouble(this.horseHeightEditText.getText().toString())));
        }

        if (this.horseSex != null)
            sex = this.horseSex;

        if (this.horseBreed != null)
            breed = this.horseBreed;

        if (this.horseDiscipline != null)
            discipline = this.horseDiscipline;

        Database.with(getActivity().getApplicationContext()).saveObject(new Horse(id, name, sex, age, breed, height, discipline));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            //mListener.onFragmentInteraction(getString(R.string.new_horse_profile));
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
