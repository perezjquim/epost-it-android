package com.perezjquim.epost_it.data;

import com.orm.query.*;
import com.perezjquim.epost_it.data.model.*;
import android.content.*;
import java.util.*;

public abstract class DBHandler
{
    public static void clearData()
    {
        Alert.deleteAll(Alert.class);
        ePostIt.deleteAll(ePostIt.class);
        ePostItHasTags.deleteAll(ePostItHasTags.class);
        Tag.deleteAll(Tag.class);
    }

    public static void test()
    {
        clearData();

        Tag a = new Tag("ola");
        a.save();
        ePostIt e  = new ePostIt("TT:TT:TT:TT",true);
        e.save();

        List<Tag> c = Select.from(Tag.class).list();
        System.out.println(c.size());
    }
}
