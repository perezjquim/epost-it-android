package com.perezjquim.epost_it.data.model;

import com.orm.SugarRecord;

import java.sql.Date;
import java.sql.Time;

public class Alert extends SugarRecord
{
    private Long id;

    private ePostIt epost_it;

    private Date begin_date;
    private Date end_date;
    private Time alert_time;

    private boolean is_mon;
    private boolean is_tue;
    private boolean is_wed;
    private boolean is_thu;
    private boolean is_fri;
    private boolean is_sat;
    private boolean is_sun;

    private boolean is_active;

    public Alert()
    {
    }

    public Alert(ePostIt epost_it, Date begin_date, Date end_date, Time alert_time, boolean is_mon, boolean is_tue, boolean is_wed, boolean is_thu, boolean is_fri, boolean is_sat, boolean is_sun, boolean is_active)
    {
        this.epost_it = epost_it;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.alert_time = alert_time;
        this.is_mon = is_mon;
        this.is_tue = is_tue;
        this.is_wed = is_wed;
        this.is_thu = is_thu;
        this.is_fri = is_fri;
        this.is_sat = is_sat;
        this.is_sun = is_sun;
        this.is_active = is_active;
    }

    public void setActive(boolean is_active)
    {
        this.is_active = is_active;
        save();
    }

    public void setBeginDate(Date begin_date)
    {
        // TODO - validar se a data está no passado
        this.begin_date = begin_date;
        save();
    }

    public void setEndDate(Date end_date)
    {
        // TODO - validar se a data está no passado
        this.end_date = end_date;
        save();
    }

    public void setAlertTime(Time alert_time)
    {
        // TODO - validar se o tempo introduzido está no passado
        this.alert_time = alert_time;
        save();
    }

    public void setMonday(boolean is_mon)
    {
        this.is_mon = is_mon;
        save();
    }

    public void setTuesday(boolean is_tue)
    {
        this.is_tue = is_tue;
        save();
    }

    public void setWednesday(boolean is_wed)
    {
        this.is_wed = is_wed;
        save();
    }

    public void setThursday(boolean is_thu)
    {
        this.is_thu = is_thu;
        save();
    }

    public void setFriday(boolean is_fri)
    {
        this.is_fri = is_fri;
        save();
    }

    public void setSaturday(boolean is_sat)
    {
        this.is_sat = is_sat;
        save();
    }

    public void setSunday(boolean is_sun)
    {
        this.is_sun = is_sun;
        save();
    }

    public ePostIt getEPostIt()
    {
        return epost_it;
    }

    public Date getBeginDate()
    {
        return begin_date;
    }

    public Date getEndDate()
    {
        return end_date;
    }

    public Time getAlertTime()
    {
        return alert_time;
    }

    public boolean isMonday()
    {
        return is_mon;
    }

    public boolean isTuesday()
    {
        return is_tue;
    }

    public boolean isWednesday()
    {
        return is_wed;
    }

    public boolean isThursday()
    {
        return is_thu;
    }

    public boolean isFriday()
    {
        return is_fri;
    }

    public boolean isSaturday()
    {
        return is_sat;
    }

    public boolean isSunday()
    {
        return is_sun;
    }
}