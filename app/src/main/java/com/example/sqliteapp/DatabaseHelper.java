package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for managing the SQLite database operations.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    /**
     * Constructs a new instance of the DatabaseHelper.
     *
     * @param context The context for accessing resources and managing the database.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);    // for creating DB
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database instance.
     * @param oldVersion The old version of the database.
     * @param newVersion The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserts data into the database.
     *
     * @param name    The name value to be inserted.
     * @param surname The surname value to be inserted.
     * @param marks   The marks value to be inserted.
     * @return True if the data is inserted successfully, false otherwise.
     */
    public boolean insertData(String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Retrieves all data from the database.
     *
     * @return A cursor containing the retrieved data.
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    /**
     * Updates data in the database based on the specified ID.
     *
     * @param id      The ID of the data to be updated.
     * @param name    The new name value.
     * @param surname The new surname value.
     * @param marks   The new marks value.
     * @return True if the data is updated successfully, false otherwise.
     */
    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    /**
     * Deletes data from the database based on the specified ID.
     *
     * @param id The ID of the data to be deleted.
     * @return The number of rows deleted.
     */
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }


}

