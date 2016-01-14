package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sporthorsetech.horseshoepad.utility.equine.Horse;

/**
 * Created by royperdue on 7/20/15.
 */
public class SpinnerAdapter extends ArrayAdapter<Horse>
{
    private Context context;
    private Horse[] horses;

    public SpinnerAdapter(Context context, int textViewResourceId, Horse[] horses)
    {
        super(context, textViewResourceId, horses);

        this.context = context;
        this.horses = horses;
    }

    public int getCount()
    {
        return horses.length;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public Horse getItem(int position)
    {
        return horses[position];
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView textView = new TextView(context);
        textView.setTextSize(getTextSize());
        textView.setTextColor(Color.BLACK);
        textView.setPadding(15, 10, 15, 10);
        textView.setGravity(Gravity.CENTER);

        textView.setText(horses[position].getName());

        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent)
    {
        TextView textView = new TextView(context);
        textView.setTextSize(getTextSize());
        textView.setTextColor(Color.BLACK);
        textView.setPadding(15, 10, 15, 10);
        textView.setText(horses[position].getName());

        return textView;
    }

    private float getTextSize()
    {
        switch (context.getResources().getConfiguration().screenLayout &
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


    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        //stringBuilder.append(horseList.get(0).getSpinner().getSelectedItem());

        return stringBuilder.toString();
    }
}