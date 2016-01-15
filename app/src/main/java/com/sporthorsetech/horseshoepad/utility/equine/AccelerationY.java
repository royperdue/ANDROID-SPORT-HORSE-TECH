package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/14/16.
 */
public class AccelerationY implements Database.StoredObject
{
    String id;
    Long accelerationY;

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

    public AccelerationY(String id, Long accelerationY)
    {
        this.id = id;
        this.accelerationY = accelerationY;
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

    public void setId(String id)
    {
        this.id = id;
    }

    public Long getAccelerationY()
    {
        return accelerationY;
    }

    public void setAccelerationY(Long accelerationY)
    {
        this.accelerationY = accelerationY;
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
