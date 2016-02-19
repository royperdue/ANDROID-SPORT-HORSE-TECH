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
public class GaitActivity
{
    @Id
    @Index
    long id;
    @Unindex
    Long timeCreated;
    @Unindex
    String footing;

    List<Gait> gaits = new ArrayList<>();
    List<BatteryReading> batteryReadings = new ArrayList<>();

    public GaitActivity()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public String getFooting()
    {
        return footing;
    }

    public void setFooting(String footing)
    {
        this.footing = footing;
    }

    public List<Gait> getGaits()
    {
        return gaits;
    }

    public void setGaits(List<Gait> gaits)
    {
        this.gaits = gaits;
    }

    public List<BatteryReading> getBatteryReadings()
    {
        return batteryReadings;
    }

    public void setBatteryReadings(List<BatteryReading> batteryReadings)
    {
        this.batteryReadings = batteryReadings;
    }
}
