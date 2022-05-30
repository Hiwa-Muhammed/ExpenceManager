package com.example.expencemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_details_layout);
        savedInstanceState=getIntent().getExtras();
        int id=savedInstanceState.getInt("id");
        String categoryName=savedInstanceState.getString("categoryName");
        String amount=savedInstanceState.getString("amount");
        String date=savedInstanceState.getString("date");
        String note=savedInstanceState.getString("note");

        TextView categoryNameTextView=(TextView) findViewById(R.id.category_type);
        categoryNameTextView.setText(categoryName);

        TextView categoryAmountTextView=(TextView) findViewById(R.id.expense_price);
        categoryAmountTextView.setText(amount);

        TextView categoryDateTextView=(TextView) findViewById(R.id.expense_date_details);
        categoryDateTextView.setText(date);

        TextView categoryNoteTextView=(TextView) findViewById(R.id.expense_note_details);
        categoryNoteTextView.setText(note);


    }
}
