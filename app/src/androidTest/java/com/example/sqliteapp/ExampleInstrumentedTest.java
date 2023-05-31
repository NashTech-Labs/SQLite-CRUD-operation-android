package com.example.sqliteapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.sqliteapp", appContext.getPackageName());
    }


    @Before
    public void setUp() {
        // Initialize the DatabaseHelper and get the writable database
        databaseHelper = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        database = databaseHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        // Close the database
        databaseHelper.close();
    }

    @Test
    public void testInsertData() {
        // Prepare test data
        String name = "Salil";
        String surname = "Kumar";
        String marks = "80";

        // Insert data into the database
        boolean result = databaseHelper.insertData(name, surname, marks);

        // Verify that the data was inserted successfully
        assertTrue(result);

        // Query the database for the inserted data
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);

        // Verify that the inserted data exists in the database
        assertTrue(cursor.moveToFirst());
        assertEquals(name, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2)));
        assertEquals(surname, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_3)));
        assertEquals(marks, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_4)));
        // Clean up: delete the inserted data
        database.delete(DatabaseHelper.TABLE_NAME, "ID = ?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_1)))
        });
    }
    @Test
    public void testUpdateData() {
        // Prepare test data
        String name = "Harsh";
        String surname = "Vardan";
        String marks = "80";

        // Insert data into the database
        boolean insertResult = databaseHelper.insertData(name, surname, marks);
        assertTrue(insertResult);

        // Query the database for the inserted data
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        assertTrue(cursor.moveToFirst());

        // Update the inserted data
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_1));
        String updatedName = "Harsh";
        String updatedSurname = "Vardan";
        String updatedMarks = "90";
        boolean updateResult = databaseHelper.updateData(String.valueOf(id), updatedName, updatedSurname, updatedMarks);
        assertTrue(updateResult);

        // Query the database for the updated data
        Cursor updatedCursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE ID = " + id, null);
        assertTrue(updatedCursor.moveToFirst());

        // Verify that the data was updated correctly
        assertEquals(updatedName, updatedCursor.getString(updatedCursor.getColumnIndex(DatabaseHelper.COL_2)));
        assertEquals(updatedSurname, updatedCursor.getString(updatedCursor.getColumnIndex(DatabaseHelper.COL_3)));
        assertEquals(updatedMarks, updatedCursor.getString(updatedCursor.getColumnIndex(DatabaseHelper.COL_4)));

        // Clean up: delete the updated data
        database.delete(DatabaseHelper.TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
    }

    @Test
    public void testDeleteData() {
        // Prepare test data
        String name = "Harsh";
        String surname = "Vardan";
        String marks = "80";

        // Insert data into the database
        boolean insertResult = databaseHelper.insertData(name, surname, marks);
        assertTrue(insertResult);

        // Query the database for the inserted data
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        assertTrue(cursor.moveToFirst());

        // Delete the inserted data
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_1));
        int deleteResult = databaseHelper.deleteData(String.valueOf(id));
        assertEquals(1, deleteResult);

        // Query the database for the deleted data
        Cursor deletedCursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE ID = " + id, null);

        // Verify that the data was deleted
        assertFalse(deletedCursor.moveToFirst());
    }

    @Test
    public void testGetAllData() {
        // Prepare test data
        String name1 = "Harsh";
        String surname1 = "Vardan";
        String marks1 = "80";
        boolean insertResult1 = databaseHelper.insertData(name1, surname1, marks1);
        assertTrue(insertResult1);

        String name2 = "Adam";
        String surname2 = "Smith";
        String marks2 = "90";
        boolean insertResult2 = databaseHelper.insertData(name2, surname2, marks2);
        assertTrue(insertResult2);

        // Retrieve all data from the database
        Cursor cursor = databaseHelper.getAllData();

        // Verify that the cursor is not null and contains the correct number of rows
        assertNotNull(cursor);
        assertEquals(2, cursor.getCount());

        // Verify that the data retrieved matches the inserted data
        assertTrue(cursor.moveToFirst());

        // First row
        assertEquals(name1, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2)));
        assertEquals(surname1, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_3)));
        assertEquals(marks1, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_4)));

        // Second row
        assertTrue(cursor.moveToNext());
        assertEquals(name2, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2)));
        assertEquals(surname2, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_3)));
        assertEquals(marks2, cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_4)));

        // Clean up: delete the inserted data
        database.delete(DatabaseHelper.TABLE_NAME, null, null);
    }

}