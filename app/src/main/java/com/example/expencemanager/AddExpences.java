package com.example.expencemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddExpences extends AppCompatActivity {
    DBHelper mydb;
    EditText price;
    EditText note;
    DatePicker date;
    Spinner category;
    ArrayList <String> categoryNames;
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expences_layout);
        savedInstanceState=getIntent().getExtras();
        int id=savedInstanceState.getInt("id");
        mydb=new DBHelper(this);
        categoryNames=new ArrayList<>();
        Cursor cat=mydb.getCategoryNames();
        if(cat.moveToFirst()){
            do{
                categoryNames.add(cat.getString(0));
            }while (cat.moveToNext());
        }


        Spinner spinnerCatigories=findViewById(R.id.spinner_catigories);
        ArrayAdapter<String> categoriesAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoryNames);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCatigories.setAdapter(categoriesAdapter);


        Button btnAddExpense=(Button)findViewById(R.id.btn_add_expense);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price=(EditText)findViewById(R.id.et_price);
                note=(EditText)findViewById(R.id.et_note);
                category=(Spinner) findViewById(R.id.spinner_catigories);
                date=(DatePicker) findViewById(R.id.date_picker);
                mydb =new DBHelper(AddExpences.this);

                String day = date.getDayOfMonth()+"";
                String month =(date.getMonth()+1)+"";
                String year = date.getYear()+"";
                String date=day+"/"+month+"/"+year;
                System.out.println(day+"/n"+month+"/n"+year);
                if(!(price.getText().toString().isEmpty())){
                    if(mydb.insertExpenses(id,Integer.parseInt(price.getText().toString()),category.getSelectedItem().toString(),note.getText().toString(),date)){
                        Toast.makeText(AddExpences.this, "Added Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddExpences.this, home.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                }
            }
        });
        ImageButton btnBack= (ImageButton) findViewById(R.id.back_img_add_income);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExpences.this, home.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}
