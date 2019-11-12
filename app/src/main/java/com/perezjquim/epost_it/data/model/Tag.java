package com.perezjquim.epost_it.data.model;
import com.orm.*;
import java.sql.*;

public class Tag extends SugarRecord
{
    private int id;
    private String desc;

    public Tag() {}

    public Tag(int id, String desc)
    {
        this.id = id;
        this.desc = desc;
    }
}
