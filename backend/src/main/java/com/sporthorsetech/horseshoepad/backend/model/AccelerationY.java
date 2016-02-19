package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class AccelerationY
{
    @Id
    @Index
    long id;
    @Unindex
    Long accelerationY;

    public AccelerationY()
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

    public Long getAccelerationY()
    {
        return accelerationY;
    }

    public void setAccelerationY(Long accelerationY)
    {
        this.accelerationY = accelerationY;
    }
}
