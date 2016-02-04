package com.sporthorsetech.horseshoepad.helper;

import android.app.Activity;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.DateTime;
import com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse;

import java.util.Calendar;

public class HorseHelper
{
    private static final String EMPTY = "";
    private static final int FIRST_POSITION = 0;

    private Activity context;

    public HorseHelper(Activity context)
    {
        this.context = context;
    }

    public void init()
    {

    }

    public Horse createHorseRequestDTO()
    {
        Horse horse = new Horse();
        horse.setHorseName("HORSE_NAME");
        horse.setRegDate(new DateTime(Calendar.getInstance().getTime()));
        return horse;
    }

    public boolean validate(GoogleAccountCredential credential)
    {
        if (credential == null)
        {
            Toast.makeText(context, "You must be authenticated!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
