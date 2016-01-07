package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class AccelerationY implements Database.StoredObject
{
    String id;
    String axis;
    Long timeCreated;
    Long value;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        accelerationY(AccelerationY.class);

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

    public AccelerationY(String id, String axis, Long value)
    {
        this.id = id;
        this.axis = axis;
        setTimeCreated();
        this.value = value;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.accelerationY;
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

    public String getAxis()
    {
        return axis;
    }

    public void setAxis(String axis)
    {
        this.axis = axis;
    }

    public Long getValue()
    {
        return value;
    }

    public void setValue(Long value)
    {
        this.value = value;
    }

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("axis", axis));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
