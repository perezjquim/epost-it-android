package com.perezjquim.epost_it.data;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.perezjquim.epost_it.data.model.Alert;
import com.perezjquim.epost_it.data.model.Tag;
import com.perezjquim.epost_it.data.model.ePostIt;
import com.perezjquim.epost_it.data.model.ePostItHasTags;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public abstract class StorageHandler
{
    // >>> EPOST-IT
    // >>> EPOST-IT
    // >>> EPOST-IT
    public static List<ePostIt> getAllEPostIts()
    {
        List<ePostIt> list = Select.from(ePostIt.class).where(Condition.prop("is_active").eq(true)).list();
        return list;
    }
    public static void insertEPostIt(String bt_addr)
    {
        List<ePostIt> epost_it_list = Select.from(ePostIt.class).where(Condition.prop("bt_addr").eq(bt_addr)).list();
        boolean doesExist = epost_it_list.size() > 0;

        ePostIt e;

        if(doesExist)
        {
            e = epost_it_list.get(0);
            e.setActive(true);
        }
        else
        {
            e = new ePostIt(bt_addr, true);
            e.save();
        }
    }

    public static void deleteEPostIt(String bt_addr)
    {
        List<ePostIt> epost_it_list = Select.from(ePostIt.class).where(Condition.prop("bt_addr").eq(bt_addr)).list();
        boolean doesExist = epost_it_list.size() > 0;

        if(doesExist)
        {
            ePostIt e = epost_it_list.get(0);
            e.setActive(false);
        }
    }
    // <<< EPOST-IT
    // <<< EPOST-IT
    // <<< EPOST-IT


    // >>> TAG
    // >>> TAG
    // >>> TAG
    public static List<Tag> getAllTags()
    {
        return Tag.listAll(Tag.class);
    }
    public static void insertTag(String desc)
    {
        List<Tag> tag_list = Select.from(Tag.class).where(Condition.prop("desc").eq(desc)).list();
        boolean doesExist = tag_list.size() > 0;

        if(!doesExist)
        {
            Tag t = new Tag(desc);
            t.save();
        }
    }
    // <<< TAG
    // <<< TAG
    // <<< TAG


    // >>> ALERT
    // >>> ALERT
    // >>> ALERT
    public static void insertAlert()
    {

    }
    public static void deleteAlert()
    {

    }
    public static List<Alert> getAllAlerts()
    {
        return Alert.listAll(Alert.class);
    }
    // <<< ALERT
    // <<< ALERT
    // <<< ALERT

    // >>> TAG - EPOST-IT
    // >>> TAG - EPOST-IT
    // >>> TAG - EPOST-IT
    public static void linkTagToEPostIt(String bt_addr, String tag_desc)
    {
        List<ePostIt> epost_it_list = Select.from(ePostIt.class).where(Condition.prop("bt_addr").eq(bt_addr)).list();
        boolean epost_it_exists = epost_it_list.size() > 0;

        List<Tag> tag_list = Select.from(Tag.class).where(Condition.prop("desc").eq(tag_desc)).list();
        boolean tag_exists = tag_list.size() > 0;

        if(epost_it_exists && tag_exists)
        {
            ePostIt e = epost_it_list.get(0);
            Tag t = tag_list.get(0);

            List<ePostItHasTags> link_list = Select.from(ePostItHasTags.class).where(Condition.prop("epost_it").eq(e)).and(Condition.prop("tag").eq(t)).list();
            boolean link_exists = link_list.size() > 0;

            if(!link_exists)
            {
               ePostItHasTags link = new ePostItHasTags(e,t);
               link.save();
            }
        }
    }

    public static void unlinkTagToEPostIt(String bt_addr, String tag_desc)
    {
        List<ePostIt> epost_it_list = Select.from(ePostIt.class).where(Condition.prop("bt_addr").eq(bt_addr)).list();
        boolean epost_it_exists = epost_it_list.size() > 0;

        List<Tag> tag_list = Select.from(Tag.class).where(Condition.prop("desc").eq(tag_desc)).list();
        boolean tag_exists = tag_list.size() > 0;

        if(epost_it_exists && tag_exists)
        {
            ePostIt e = epost_it_list.get(0);
            Tag t = tag_list.get(0);

            List<ePostItHasTags> link_list = Select.from(ePostItHasTags.class).where(Condition.prop("epost_it").eq(e)).and(Condition.prop("tag").eq(t)).list();
            boolean link_exists = link_list.size() > 0;

            if(link_exists)
            {
                ePostItHasTags link = link_list.get(0);
                link.delete();
            }
        }
    }
    // <<< TAG - EPOST-IT
    // <<< TAG - EPOST-IT
    // <<< TAG - EPOST-IT

    // >>> GENERAL
    // >>> GENERAL
    // >>> GENERAL
    public static void clearData()
    {
        Alert.deleteAll(Alert.class);
        ePostIt.deleteAll(ePostIt.class);
        ePostItHasTags.deleteAll(ePostItHasTags.class);
        Tag.deleteAll(Tag.class);
    }
    // <<< GENERAL
    // <<< GENERAL
    // <<< GENERAL

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
