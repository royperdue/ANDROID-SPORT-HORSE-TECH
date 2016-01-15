package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/14/16.
 */
public class AccelerationZ implements Database.StoredObject
{
    String id;
    Long accelerationZ;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        accelerationZ(AccelerationZ.class);

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

    public AccelerationZ(String id, Long accelerationZ)
    {
        this.id = id;
        this.accelerationZ = accelerationZ;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.accelerationZ;
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

    public Long getAccelerationZ()
    {
        return accelerationZ;
    }

    public void setAccelerationZ(Long accelerationZ)
    {
        this.accelerationZ = accelerationZ;
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
