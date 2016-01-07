package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class Gait implements Database.StoredObject
{
    String id;
    String name;
    Long timeCreated;
    ArrayList<AccelerationX> accelerationsX;
    ArrayList<AccelerationY> accelerationsY;
    ArrayList<AccelerationZ> accelerationsZ;
    ArrayList<Force> forces;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        gait(Gait.class);

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

    public Gait(String id, String name)
    {
        setTimeCreated();
        this.id = id;
        this.name = name;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.gait;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<AccelerationX> getAccelerationsX()
    {
        return accelerationsX;
    }

    public void setAccelerationsX(ArrayList<AccelerationX> accelerationsX)
    {
        this.accelerationsX = accelerationsX;
    }

    public ArrayList<AccelerationY> getAccelerationsY()
    {
        return accelerationsY;
    }

    public void setAccelerationsY(ArrayList<AccelerationY> accelerationsY)
    {
        this.accelerationsY = accelerationsY;
    }

    public ArrayList<AccelerationZ> getAccelerationsZ()
    {
        return accelerationsZ;
    }

    public void setAccelerationsZ(ArrayList<AccelerationZ> accelerationsZ)
    {
        this.accelerationsZ = accelerationsZ;
    }

    public ArrayList<Force> getForces()
    {
        return forces;
    }

    public void setForces(ArrayList<Force> forces)
    {
        this.forces = forces;
    }

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("name", name));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
