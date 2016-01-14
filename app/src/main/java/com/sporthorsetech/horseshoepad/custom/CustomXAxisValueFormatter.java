package com.sporthorsetech.horseshoepad.custom;

import com.github.mikephil.charting.utils.ViewPortHandler;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;

public class CustomXAxisValueFormatter implements XAxisValueFormatter
{

    public CustomXAxisValueFormatter()
    {
    }

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler)
    {
            return original;
    }
}