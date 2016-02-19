package com.sporthorsetech.horseshoepad.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by royperdue on 2/19/16.
 */
@Entity
public class Owner
{
    @Id
    @Index
    String id;
    @Unindex
    String ownerName;
    @Unindex
    String ownerEmail;

    Map<String, Horse> horses = new HashMap<>();

    public Owner()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail()
    {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail)
    {
        this.ownerEmail = ownerEmail;
    }

    public Map<String, Horse> getHorses()
    {
        return horses;
    }

    public void setHorses(Map<String, Horse> horses)
    {
        this.horses = horses;
    }
}
