package com.example.expencemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditIncome extends AppCompatActivity {
    DBHelper mydb;
    EditText amount;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_income_layout);
        savedInstanceState=getIntent().getExtras();
        id=savedInstanceState.getInt("id");
        String LastAmount=savedInstanceState.getString("amount");

        TextView txt=(TextView)findViewById(R.id.last_amount);
        txt.setText("Last Income amount: "+LastAmount);

        Button btnAddExpense=(Button)findViewById(R.id.btn_set_income);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount=(EditText) findViewById(R.id.new_income_amount);
                mydb=new DBHelper(EditIncome.this);
                if(!(amount.getText().toString().isEmpty())){
                    if(mydb.setIncome(id,Integer.parseInt(amount.getText().toString()))){
                        Toast.makeText(EditIncome.this, "Updated Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditIncome.this, IncomesManager.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                }
            }
        });
        ImageButton btnBack= (ImageButton) findViewById(R.id.back_img_edit_income);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditIncome.this, IncomesManager.class);
                startActivity(intent);
            }
        });
    }
}
