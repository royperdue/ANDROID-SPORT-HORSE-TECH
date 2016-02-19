package com.sporthorsetech.horseshoepad.backend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royperdue on 2/19/16.
 */
public class Horses
{
    List<Horse> horseList = new ArrayList<>();

    public Horses()
    {
    }

    public List<Horse> getHorseList()
    {
        return horseList;
    }

    public void setHorseList(List<Horse> horseList)
    {
        this.horseList = horseList;
    }
}
