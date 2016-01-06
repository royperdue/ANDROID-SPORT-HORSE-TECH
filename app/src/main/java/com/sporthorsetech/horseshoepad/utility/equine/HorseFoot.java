package com.sporthorsetech.horseshoepad.utility.equine;

/**
 * Created by royperdue on 1/5/16.
 */
public class HorseFoot
{
    private String horseName;
    private String foot;
    private String padId;

    public HorseFoot(String horseName, String foot)
    {
        this.horseName = horseName;
        this.foot = foot;
    }

    public String getHorseName()
    {
        return horseName;
    }

    public void setHorseName(String horseName)
    {
        this.horseName = horseName;
    }

    public String getFoot()
    {
        return foot;
    }

    public void setFoot(String foot)
    {
        this.foot = foot;
    }

    public String getPadId()
    {
        return padId;
    }

    public void setPadId(String padId)
    {
        this.padId = padId;
    }
}
