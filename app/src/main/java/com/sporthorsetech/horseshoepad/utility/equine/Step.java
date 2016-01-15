package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

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
    private AccelerationX accelerationX;
    private AccelerationY accelerationY;
    private AccelerationZ accelerationZ;
    private Force force;

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

    public Step(String id)
    {
        this.id = id;
        this.timeCreated = System.currentTimeMillis();
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

    public AccelerationX getAccelerationX()
    {
        return accelerationX;
    }

    public void setAccelerationX(AccelerationX accelerationX)
    {
        this.accelerationX = accelerationX;
    }

    public AccelerationY getAccelerationY()
    {
        return accelerationY;
    }

    public void setAccelerationY(AccelerationY accelerationY)
    {
        this.accelerationY = accelerationY;
    }

    public AccelerationZ getAccelerationZ()
    {
        return accelerationZ;
    }

    public void setAccelerationZ(AccelerationZ accelerationZ)
    {
        this.accelerationZ = accelerationZ;
    }

    public Force getForce()
    {
        return force;
    }

    public void setForce(Force force)
    {
        this.force = force;
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
