package com.example.cureeasy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbMedicine {
    String medicine;
    String time;
    public DbMedicine()
    {

    }
    public DbMedicine(String m,String t)
    {
        medicine=m;
        time=t;
    }
public String getTime()
{
    return this.time;
}

public String getMedicine()
{
    return this.medicine;
}}

