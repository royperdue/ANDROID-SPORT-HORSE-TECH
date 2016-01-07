package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class Force implements Database.StoredObject
{
    String id;
    Long timeCreated;
    Long value;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        force(Force.class);

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

    public Force(String id, Long value)
    {
        this.id = id;
        setTimeCreated();
        this.value = value;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.force;
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
        retval.add(new SearchableTagValuePair("value", String.valueOf(value)));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
