package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class Force
{
    @Id
    @Index
    long id;
    @Unindex
    Long force;

    public Force()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Long getForce()
    {
        return force;
    }

    public void setForce(Long force)
    {
        this.force = force;
    }
}
