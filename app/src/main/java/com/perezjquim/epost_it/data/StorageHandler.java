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
        return Select
                .from(ePostIt.class)
                .where(Condition.prop("ISACTIVE").eq("1"))
                .list();
    }

    public static boolean doesPostItExist(String bt_addr)
    {
        return Select
                .from(ePostIt.class)
                .where(Condition.prop("ISACTIVE").eq("1"))
                .and(Condition.prop("BTADDR").eq(bt_addr))
                .list()
                .size() > 0;
    }

    public static void insertEPostIt(String bt_addr, String name)
    {
        List<ePostIt> epost_it_list = Select
                .from(ePostIt.class)
                .where(Condition.prop("BTADDR").eq(bt_addr))
                .list();
        boolean doesExist = epost_it_list.size() > 0;

        ePostIt e;

        if (doesExist)
        {
            e = epost_it_list.get(0);
            e.setActive(true);

            System.out.println("EPOSTIT - INSERT - RE-ENABLE");
        } else
        {
            e = new ePostIt(bt_addr, true, name);
            e.save();

            System.out.println("EPOSTIT - INSERT - NEW ENTRY");
        }
    }

    public static void deleteEPostIt(String bt_addr)
    {
        List<ePostIt> epost_it_list = Select
                .from(ePostIt.class)
                .where(Condition.prop("BTADDR").eq(bt_addr))
                .list();
        boolean doesExist = epost_it_list.size() > 0;

        if (doesExist)
        {
            ePostIt e = epost_it_list.get(0);
            e.setActive(false);

            System.out.println("EPOSTIT - DELETE/DISABLE");
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
        List<Tag> tag_list = Select
                .from(Tag.class)
                .where(Condition.prop("DESC").eq(desc))
                .list();
        boolean doesExist = tag_list.size() > 0;

        if (!doesExist)
        {
            Tag t = new Tag(desc);
            t.save();

            System.out.println("TAG - INSERT - NEW ENTRY");
        } else
        {
            System.out.println("TAG - INSERT - ALREADY EXISTS");
        }
    }
    // <<< TAG
    // <<< TAG
    // <<< TAG


    // >>> ALERT
    // >>> ALERT
    // >>> ALERT
    public static void insertAlert(ePostIt e, Date begin_date, Date end_date, Time alert_time, boolean is_mon, boolean is_tue, boolean is_wed, boolean is_thu, boolean is_fri, boolean is_sat, boolean is_sun)
    {
        Alert a = new Alert(e, begin_date, end_date, alert_time, is_mon, is_tue, is_wed, is_thu, is_fri, is_sat, is_sun, true);
        a.save();

        System.out.println("ALERT - INSERT - NEW ENTRY");
    }

    public static void deleteAlert(Alert a)
    {
        // no need
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
        List<ePostIt> epost_it_list = Select
                .from(ePostIt.class)
                .where(Condition.prop("BTADDR").eq(bt_addr))
                .list();
        boolean epost_it_exists = epost_it_list.size() > 0;

        List<Tag> tag_list = Select
                .from(Tag.class)
                .where(Condition.prop("DESC").eq(tag_desc))
                .list();
        boolean tag_exists = tag_list.size() > 0;

        if (epost_it_exists && tag_exists)
        {
            ePostIt e = epost_it_list.get(0);
            Tag t = tag_list.get(0);

            List<ePostItHasTags> link_list = Select
                    .from(ePostItHasTags.class)
                    .where(Condition.prop("EPOSTIT").eq(e)).and(Condition.prop("TAG").eq(t))
                    .list();
            boolean link_exists = link_list.size() > 0;

            if (!link_exists)
            {
                ePostItHasTags link = new ePostItHasTags(e, t);
                link.save();

                System.out.println("EPOSTIT_HAS_TAGS - INSERT - NEW ENTRY");
            } else
            {
                System.out.println("EPOSTIT_HAS_TAGS - INSERT - ALREADY EXISTS");
            }
        } else
        {
            System.out.println("EPOSTIT_HAS_TAGS - INVALID EPOSTIT OR TAG");
        }
    }

    public static void unlinkTagToEPostIt(String bt_addr, String tag_desc)
    {
        List<ePostIt> epost_it_list = Select
                .from(ePostIt.class)
                .where(Condition.prop("BTADDR").eq(bt_addr))
                .list();
        boolean epost_it_exists = epost_it_list.size() > 0;

        List<Tag> tag_list = Select
                .from(Tag.class)
                .where(Condition.prop("DESC").eq(tag_desc))
                .list();
        boolean tag_exists = tag_list.size() > 0;

        if (epost_it_exists && tag_exists)
        {
            ePostIt e = epost_it_list.get(0);
            Tag t = tag_list.get(0);

            List<ePostItHasTags> link_list = Select
                    .from(ePostItHasTags.class)
                    .where(Condition.prop("EPOSTIT").eq(e)).and(Condition.prop("TAG").eq(t))
                    .list();
            boolean link_exists = link_list.size() > 0;

            if (link_exists)
            {
                ePostItHasTags link = link_list.get(0);
                link.delete();

                System.out.println("EPOSTIT_HAS_TAGS - DELETE - LINK REMOVED");
            } else
            {
                System.out.println("EPOSTIT_HAS_TAGS - DELETE - ENTRY NOT FOUND");
            }
        } else
        {
            System.out.println("EPOSTIT_HAS_TAGS - INVALID EPOSTIT OR TAG");
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

        System.out.println("-- DB CLEARED --");
    }
    // <<< GENERAL
    // <<< GENERAL
    // <<< GENERAL

    public static void test()
    {
        clearData();

        Tag a = new Tag("ola");
        a.save();
        ePostIt e1 = new ePostIt("TT:TT:TT:TT", true, "Name 1");
        e1.save();
        ePostIt e2 = new ePostIt("AA:AA:AA:AA", true, "Name 2");
        e2.save();
        ePostIt e3 = new ePostIt("CC:CC:CC:CC", true, "Name 3");
        e3.save();

        List<Tag> c = Select.from(Tag.class).list();
        System.out.println(c.size());
    }
}
