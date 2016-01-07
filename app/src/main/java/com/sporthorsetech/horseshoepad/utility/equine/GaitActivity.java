package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class GaitActivity implements Database.StoredObject
{
    String id;
    Long timeCreated;
    List<Gait> gaits;
    String footing;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        gaitActivity(GaitActivity.class);

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

    public GaitActivity(String footing, String id, Long timeCreated, List<Gait> gaits)
    {
        this.footing = footing;
        this.id = id;
        setTimeCreated();
        this.gaits = gaits;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.gaitActivity;
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

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated()
    {
        this.timeCreated = System.currentTimeMillis();
    }

    public List<Gait> getGaits()
    {
        return gaits;
    }

    public void setGaits(List<Gait> gaits)
    {
        this.gaits = gaits;
    }

    public String getFooting()
    {
        return footing;
    }

    public void setFooting(String footing)
    {
        this.footing = footing;
    }

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("footing", footing));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
