package com.perezjquim.epost_it.data;

import com.perezjquim.epost_it.data.model.*;
import android.content.*;

public abstract class DBHandler
{
    public static void test()
    {
        Tag a = new Tag(1,"ola");
        a.save();
        ePostIt e  = new ePostIt(1,"TT:TT:TT:TT",true);
        e.save();
    }
}
