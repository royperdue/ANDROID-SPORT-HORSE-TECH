package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Horse
{
    @Id@Index
    long id;
    @Unindex
    String name;
    @Unindex
    Long timeCreated;
    @Unindex
    String sex;
    @Unindex
    Long age;
    @Unindex
    String breed;
    @Unindex
    Long height;
    @Unindex
    String discipline;

    List<HorseHoof> horseHooves = new ArrayList<>();
    List<GaitActivity> gaitActivities = new ArrayList<>();

    public Horse()
    {
    }

    public Horse(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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

    public Long getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public Long getAge()
    {
        return age;
    }

    public void setAge(Long age)
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

    public List<HorseHoof> getHorseHooves()
    {
        return horseHooves;
    }

    public void setHorseHooves(List<HorseHoof> horseHooves)
    {
        this.horseHooves = horseHooves;
    }

    public List<GaitActivity> getGaitActivities()
    {
        return gaitActivities;
    }

    public void setGaitActivities(List<GaitActivity> gaitActivities)
    {
        this.gaitActivities = gaitActivities;
    }
}
