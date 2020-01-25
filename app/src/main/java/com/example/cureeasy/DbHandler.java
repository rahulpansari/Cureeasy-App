package com.example.cureeasy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Reminder";
    private static final String TABLE_ALARM = "alarm";
    private static final String KEY_MEDICINE = "medicine";
    private static final String KEY_TIME = "time";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ALARM + "("
                + KEY_TIME + " TEXT PRIMARY KEY," + KEY_MEDICINE + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);

        // Create tables again
        onCreate(db);
    }

    void addMedicine(DbMedicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, medicine.getTime()); // Contact Name
        values.put(KEY_MEDICINE,medicine.getMedicine()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_ALARM, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    DbMedicine getMedicine(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARM, new String[] {KEY_TIME,
                        KEY_MEDICINE }, KEY_TIME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DbMedicine medicine = new DbMedicine(cursor.getString(1),
                cursor.getString(0));
        // return contact
        return medicine;
    }
    public int updateMedicine(DbMedicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, medicine.getTime());
        values.put(KEY_MEDICINE,medicine.getMedicine());

        // updating row
        return db.update(TABLE_ALARM, values, KEY_TIME + " = ?",
                new String[] { String.valueOf(medicine.getTime()) });
    }
    public int getMedicineCount(String col) {
        String countQuery = "SELECT  * FROM "+TABLE_ALARM+" WHERE "+KEY_TIME+" LIKE '"+col+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
        return cursor.getCount();
    }
}

