package com.perezjquim.epost_it.data.model;

import com.orm.SugarRecord;

public class ePostIt extends SugarRecord
{
    private Long id;

    private String bt_addr;
    private boolean is_active;

    public ePostIt() {}

    public ePostIt(String bt_addr, boolean is_active)
    {
        this.bt_addr = bt_addr;
        this.is_active = is_active;
    }

    public void setActive(boolean is_active)
    {
        this.is_active = is_active;
        save();
    }

    public String getBTAddress()
    {
        return bt_addr;
    }
}