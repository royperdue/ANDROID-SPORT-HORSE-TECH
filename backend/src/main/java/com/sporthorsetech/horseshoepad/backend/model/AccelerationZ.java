package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class AccelerationZ
{
    @Id
    @Index
    long id;
    @Unindex
    Long accelerationZ;

    public AccelerationZ()
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

    public Long getAccelerationZ()
    {
        return accelerationZ;
    }

    public void setAccelerationZ(Long accelerationZ)
    {
        this.accelerationZ = accelerationZ;
    }
}
