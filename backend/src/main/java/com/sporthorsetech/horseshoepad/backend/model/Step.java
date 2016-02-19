package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class Step
{
    @Id
    @Index
    long id;
    @Unindex
    String hoof;
    @Unindex
    Long timeCreated;
    @Unindex
    private AccelerationX accelerationX;
    @Unindex
    private AccelerationY accelerationY;
    @Unindex
    private AccelerationZ accelerationZ;
    @Unindex
    private Force force;

    public Step()
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

    public String getHoof()
    {
        return hoof;
    }

    public void setHoof(String hoof)
    {
        this.hoof = hoof;
    }

    public Long getTimeCreated()
    {
        return timeCreated;
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
}
