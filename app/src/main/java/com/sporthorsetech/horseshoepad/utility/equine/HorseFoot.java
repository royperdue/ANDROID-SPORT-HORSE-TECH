package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class HorseFoot implements Database.StoredObject
{
    String id;
    String foot;
    Long timeCreated;
    String currentHorseShoePad;
    List<String> previousHorseshoePads;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        horseFoot(HorseFoot.class);

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

    public HorseFoot(String id, String foot)
    {
        this.id = id;
        this.foot = foot;
        setTimeCreated();
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.horseFoot;
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

    public String getFoot()
    {
        return foot;
    }

    public void setFoot(String foot)
    {
        this.foot = foot;
    }

    public String getCurrentHorseShoePad()
    {
        return currentHorseShoePad;
    }

    public void setCurrentHorseShoePad(String currentHorseShoePad)
    {
        if(this.currentHorseShoePad != null)
            previousHorseshoePads.add(currentHorseShoePad);

        this.currentHorseShoePad = currentHorseShoePad;
    }

    public List<String> getPreviousHorseshoePads()
    {
        return previousHorseshoePads;
    }

    public void setPreviousHorseshoePads(List<String> previousHorseshoePads)
    {
        this.previousHorseshoePads = previousHorseshoePads;
    }

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("foot", foot));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated()
    {
        this.timeCreated = System.currentTimeMillis();
    }
}
