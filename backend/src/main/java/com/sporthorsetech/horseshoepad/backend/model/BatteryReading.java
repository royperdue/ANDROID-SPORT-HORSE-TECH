package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class BatteryReading
{
    @Id
    @Index
    long id;
    @Unindex
    Long timeCreated;
    @Unindex
    String padIdOne = "-";
    @Unindex
    String padOneBatteryVoltage = "-";
    @Unindex
    String padIdTwo = "-";
    @Unindex
    String padTwoBatteryVoltage = "-";
    @Unindex
    String padIdThree = "-";
    @Unindex
    String padThreeBatteryVoltage = "-";
    @Unindex
    String padIdFour = "-";
    @Unindex
    String padFourBatteryVoltage = "-";

    public BatteryReading()
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

    public String getPadIdOne()
    {
        return padIdOne;
    }

    public void setPadIdOne(String padIdOne)
    {
        this.padIdOne = padIdOne;
    }

    public String getPadOneBatteryVoltage()
    {
        return padOneBatteryVoltage;
    }

    public void setPadOneBatteryVoltage(String padOneBatteryVoltage)
    {
        this.padOneBatteryVoltage = padOneBatteryVoltage;
    }

    public String getPadIdTwo()
    {
        return padIdTwo;
    }

    public void setPadIdTwo(String padIdTwo)
    {
        this.padIdTwo = padIdTwo;
    }

    public String getPadTwoBatteryVoltage()
    {
        return padTwoBatteryVoltage;
    }

    public void setPadTwoBatteryVoltage(String padTwoBatteryVoltage)
    {
        this.padTwoBatteryVoltage = padTwoBatteryVoltage;
    }

    public String getPadIdThree()
    {
        return padIdThree;
    }

    public void setPadIdThree(String padIdThree)
    {
        this.padIdThree = padIdThree;
    }

    public String getPadThreeBatteryVoltage()
    {
        return padThreeBatteryVoltage;
    }

    public void setPadThreeBatteryVoltage(String padThreeBatteryVoltage)
    {
        this.padThreeBatteryVoltage = padThreeBatteryVoltage;
    }

    public String getPadIdFour()
    {
        return padIdFour;
    }

    public void setPadIdFour(String padIdFour)
    {
        this.padIdFour = padIdFour;
    }

    public String getPadFourBatteryVoltage()
    {
        return padFourBatteryVoltage;
    }

    public void setPadFourBatteryVoltage(String padFourBatteryVoltage)
    {
        this.padFourBatteryVoltage = padFourBatteryVoltage;
    }
}
