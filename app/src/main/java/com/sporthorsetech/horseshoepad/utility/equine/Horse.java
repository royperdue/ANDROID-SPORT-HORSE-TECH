package com.sporthorsetech.horseshoepad.utility.equine;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/5/16.
 */
public class Horse implements Database.StoredObject
{
    String id;
    String name;
    Long timeCreated;
    String sex;
    Integer age;
    String breed;
    Long height;
    String discipline;
    List<HorseFoot> horseFeet;
    List<GaitActivity> gaitActivities;

    public enum TYPE implements Database.StoredObject.TYPE
    {
        horse(Horse.class);

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

    public Horse(String id, String name, String sex, Integer age, String breed, Long height, String discipline)
    {
        this.id = id;
        this.name = name;
        setTimeCreated();
        this.sex = sex;
        this.age = age;
        this.breed = breed;
        this.height = height;
        this.discipline = discipline;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.horse;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public String getBreed()
    {
        return breed;
    }

    public void setBreed(String breed)
    {
        this.breed = breed;
    }

    public Long getHeight()
    {
        return height;
    }

    public void setHeight(Long height)
    {
        this.height = height;
    }

    public String getDiscipline()
    {
        return discipline;
    }

    public void setDiscipline(String discipline)
    {
        this.discipline = discipline;
    }

    public List<HorseFoot> getHorseFeet()
    {
        return horseFeet;
    }

    public void setHorseFeet(List<HorseFoot> horseFeet)
    {
        this.horseFeet = horseFeet;
    }

    public List<GaitActivity> getGaitActivities()
    {
        return gaitActivities;
    }

    public void setGaitActivities(List<GaitActivity> gaitActivities)
    {
        this.gaitActivities = gaitActivities;
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

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated()
    {
        this.timeCreated = System.currentTimeMillis();
    }
}
