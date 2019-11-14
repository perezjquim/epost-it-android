package com.perezjquim.epost_it.data.model;
import com.orm.*;
import java.sql.*;

public class Tag extends SugarRecord
{
    private Long id;

    private String desc;

    public Tag() {}

    public Tag(String desc)
    {
        this.id = id;
        this.desc = desc;
    }
}
