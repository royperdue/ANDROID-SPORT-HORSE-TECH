package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 2/19/16.
 */
public class BatteryReading implements Database.StoredObject
{
    String id;
    Long timeCreated;
    String padIdOne = "-";
    String padOneBatteryVoltage = "-";
    String padIdTwo = "-";
    String padTwoBatteryVoltage = "-";
    String padIdThree = "-";
    String padThreeBatteryVoltage = "-";
    String padIdFour = "-";
    String padFourBatteryVoltage = "-";

    public enum TYPE implements Database.StoredObject.TYPE
    {
        batteryReading(BatteryReading.class);

        private Class cls;

        TYPE(Class cls)
        {
            this.cls = cls;
        }

        @Override
        public Class getTypeClass()
        {
            return cls;
        }

        @Override
        public String getTypeName()
        {
            return name();
        }
    }

    public BatteryReading(String id)
    {
        setTimeCreated();
        this.id = id;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.batteryReading;
    }

    @Override
    public String getStoredObjectId()
    {
        return id;
    }

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated()
    {
        this.timeCreated = System.currentTimeMillis();
    }

    public String getId()
    {
        return id;
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

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("id", id));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
