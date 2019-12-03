package com.perezjquim.epost_it.data.model;

import com.orm.SugarRecord;

public class ePostItHasTags extends SugarRecord
{
    private Long id;

    private ePostIt epost_it;
    private Tag tag;

    public ePostItHasTags()
    {
    }

    public ePostItHasTags(ePostIt epost_it, Tag tag)
    {
        this.epost_it = epost_it;
        this.tag = tag;
    }
}
