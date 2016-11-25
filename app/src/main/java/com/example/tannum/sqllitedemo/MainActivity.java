package com.example.tannum.sqllitedemo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextMarks;
    EditText editTextId;
    Button  buttonAddData;
    Button buttonViewAll;
    Button buttonUpdate;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);


        editTextName = (EditText)findViewById(R.id.editText_name);
        editTextSurname = (EditText)findViewById(R.id.editText_surname);
        editTextMarks = (EditText)findViewById(R.id.editText_masks);
        editTextId = (EditText)findViewById(R.id.editText_id);
        buttonAddData = (Button)findViewById(R.id.button_apply);
        buttonViewAll = (Button)findViewById(R.id.button_viewall);
        buttonUpdate = (Button)findViewById(R.id.button_update);
        buttonDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        updateData();
        deleteData();


    }

    public void AddData(){
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTextName.getText().toString(),editTextSurname.getText().toString(),editTextMarks.getText().toString());
                if(isInserted == true){
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data on Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","No data found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id : "+res.getString(0)+"\n");
                    buffer.append("Name : "+res.getString(1)+"\n");
                    buffer.append("Surname : "+res.getString(2)+"\n");
                    buffer.append("Marks : "+res.getString(3)+"\n\n");
                }
                // Show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                        editTextName.getText().toString(),
                        editTextSurname.getText().toString(),
                        editTextMarks.getText().toString());
                if(isUpdate == true){
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage(String title , String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
