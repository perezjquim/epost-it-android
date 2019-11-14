package com.perezjquim.epost_it.data.model;
import com.orm.*;

import java.sql.*;

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
}