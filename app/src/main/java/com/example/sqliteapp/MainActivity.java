package com.example.sqliteapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The main activity of the SQLite app.
 */
public class MainActivity extends AppCompatActivity {


        DatabaseHelper myDb;
        EditText editName,editSurname,editMarks ,editTextId;
        Button btnAddData;
        Button btnviewAll;
        Button btnDelete;
        Button btnviewUpdate;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Otherwise, it is null.
     */
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            myDb = new DatabaseHelper(this);

            editName = (EditText)findViewById(R.id.editText_name);
            editSurname = (EditText)findViewById(R.id.editText_surname);
            editMarks = (EditText)findViewById(R.id.editText_Marks);
            editTextId = (EditText)findViewById(R.id.editText_id);
            btnAddData = (Button)findViewById(R.id.button_add);
            btnviewAll = (Button)findViewById(R.id.button_viewAll);
            btnviewUpdate= (Button)findViewById(R.id.button_update);
            btnDelete= (Button)findViewById(R.id.button_delete);
            AddData();
            viewAll();
            UpdateData();
            DeleteData();
        }

    /**
     * Deletes data from the database based on the entered ID.
     */
    public void DeleteData() {
            btnDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                            if(deletedRows > 0)
                                Toast.makeText(MainActivity.this.getBaseContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this.getBaseContext(),"Data not Deleted",Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    /**
     * Updates data in the database based on the entered ID and new values.
     */
    public void UpdateData() {
            btnviewUpdate.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                    editName.getText().toString(),
                                    editSurname.getText().toString(),editMarks.getText().toString());
                            if(isUpdate)
                                Toast.makeText(MainActivity.this.getBaseContext(),"Data Update",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this.getBaseContext(),"Data not Updated",Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    /**
     * Adds data to the database based on the entered values.
     */
    public  void AddData() {
            btnAddData.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isInserted = myDb.insertData(editName.getText().toString(),
                                    editSurname.getText().toString(),
                                    editMarks.getText().toString() );
                            if(isInserted)
                                Toast.makeText(MainActivity.this.getBaseContext(),"Data Inserted",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this.getBaseContext(),"Data not Inserted",Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }

    /**
     * Retrieves all data from the database and displays it in an alert dialog.
     */
    public void viewAll() {
            btnviewAll.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cursor res = myDb.getAllData();
                            if(res.getCount() == 0) {
                                // show message
                                showMessage("Error","Nothing found");
                                return;
                            }

                            StringBuilder buffer = new StringBuilder();
                            while (res.moveToNext()) {
                                buffer.append("Id :").append(res.getString(0)).append("\n");
                                buffer.append("Name :").append(res.getString(1)).append("\n");
                                buffer.append("Surname :").append(res.getString(2)).append("\n");
                                buffer.append("Marks :").append(res.getString(3)).append("\n\n");
                            }

                            // Show all data
                            showMessage("Data",buffer.toString());
                        }
                    }
            );
        }
    /**
     * Retrieves all data from the database and displays it in an alert dialog.
     */
    public void showMessage(String title,String Message){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(Message);
            builder.show();
        }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.class.getModifiers(), menu);
            return true;
        }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
