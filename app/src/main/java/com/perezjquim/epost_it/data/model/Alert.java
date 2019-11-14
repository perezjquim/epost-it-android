package com.perezjquim.epost_it.data.model;
import java.sql.*;
import com.orm.*;

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

    public Alert() {}

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
}