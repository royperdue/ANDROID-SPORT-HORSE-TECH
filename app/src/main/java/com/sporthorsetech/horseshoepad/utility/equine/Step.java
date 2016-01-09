package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class Step implements Database.StoredObject
{
    String id;
    String hoof;
    Long timeCreated;
    HashMap<Long, Long> accelerationsX;
    HashMap<Long, Long> accelerationsY;
    HashMap<Long, Long> accelerationsZ;
    HashMap<Long, Long> forceReadings;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        step(Step.class);

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

    public Step(String id, String hoof, HashMap<Long, Long> accelerationsX, HashMap<Long, Long> accelerationsY,
                HashMap<Long, Long> accelerationsZ, HashMap<Long, Long> forceReadings)
    {
        this.id = id;
        this.hoof = hoof;
        this.timeCreated = System.currentTimeMillis();
        this.accelerationsX = accelerationsX;
        this.accelerationsY = accelerationsY;
        this.accelerationsZ = accelerationsZ;
        this.forceReadings = forceReadings;
    }

    public Step(String id)
    {
        this.id = id;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.step;
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

    public void setId(String id)
    {
        this.id = id;
    }

    public String getHoof()
    {
        return hoof;
    }

    public void setHoof(String hoof)
    {
        this.hoof = hoof;
    }

    public void setTimeCreated(Long timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public HashMap<Long, Long> getAccelerationsX()
    {
        return accelerationsX;
    }

    public void setAccelerationsX(HashMap<Long, Long> accelerationsX)
    {
        this.accelerationsX = accelerationsX;
    }

    public HashMap<Long, Long> getAccelerationsY()
    {
        return accelerationsY;
    }

    public void setAccelerationsY(HashMap<Long, Long> accelerationsY)
    {
        this.accelerationsY = accelerationsY;
    }

    public HashMap<Long, Long> getAccelerationsZ()
    {
        return accelerationsZ;
    }

    public void setAccelerationsZ(HashMap<Long, Long> accelerationsZ)
    {
        this.accelerationsZ = accelerationsZ;
    }

    public HashMap<Long, Long> getForceReadings()
    {
        return forceReadings;
    }

    public void setForceReadings(HashMap<Long, Long> forceReadings)
    {
        this.forceReadings = forceReadings;
    }

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("hoof", hoof));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
