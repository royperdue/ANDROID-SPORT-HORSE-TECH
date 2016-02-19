package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class HorseHoof
{
    @Id
    @Index
    long id;
    @Unindex
    String foot;
    @Unindex
    Long timeCreated;
    @Unindex
    String currentHorseShoePad;

    List<String> previousHorseshoePads = new ArrayList<>();

    public HorseHoof()
    {
    }

    public HorseHoof(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFoot()
    {
        return foot;
    }

    public void setFoot(String foot)
    {
        this.foot = foot;
    }

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public String getCurrentHorseShoePad()
    {
        return currentHorseShoePad;
    }

    public void setCurrentHorseShoePad(String currentHorseShoePad)
    {
        this.currentHorseShoePad = currentHorseShoePad;
    }

    public List<String> getPreviousHorseshoePads()
    {
        return previousHorseshoePads;
    }

    public void setPreviousHorseshoePads(List<String> previousHorseshoePads)
    {
        this.previousHorseshoePads = previousHorseshoePads;
    }
}
