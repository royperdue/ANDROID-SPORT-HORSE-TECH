package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

@Entity
public class Horse
{
    @Id
    private long id;
    private String horseName;
    private Date regDate;

    public Horse()
    {
    }

    public Horse(Long id, String horseName)
    {
        this.id = id;
        this.horseName = horseName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getHorseName()
    {
        return horseName;
    }

    public void setHorseName(String horseName)
    {
        this.horseName = horseName;
    }

    public Date getRegDate()
    {
        return regDate;
    }

    public void setRegDate(Date regDate)
    {
        this.regDate = regDate;
    }
}
