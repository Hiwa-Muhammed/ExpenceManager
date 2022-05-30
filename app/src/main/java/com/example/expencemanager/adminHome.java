package com.example.expencemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class adminHome extends AppCompatActivity {
    ArrayList<String> mobileArray;
    DBHelper mydb;
    ListView lv;
    ArrayList<String> categoryNames;
    ArrayList<String> categoryAmounts;
    CateoriesAdapter cateoriesAdapter;

    @Override
    public void onBackPressed() {
        }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_layout);
        mydb=new DBHelper(this);



        Button btnFragment=(Button) findViewById(R.id.incomes_btn);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, IncomesManager.class);
                startActivity(intent);
            }
        });

//        getting sum of all incomes from database
        int income = 0;
            Cursor inc = mydb.getAllIncome();
            if (inc.moveToFirst()) {
                do {
                    income = Integer.parseInt(inc.getString(0));
                } while (inc.moveToNext());
            }
            TextView incomeTextView = (TextView) findViewById(R.id.admin_income_ammount);
            incomeTextView.setText(income + " IQD");



        //getting expenses from database
        Cursor exp=mydb.getAllExpenses();
        int expenses = 0;
        if(exp.moveToFirst()){
            do{
                expenses=Integer.parseInt(exp.getString(0));
            }while (exp.moveToNext());
        }
        TextView expensesTextView=(TextView) findViewById(R.id.admin_expense_ammount);
        expensesTextView.setText(expenses+" IQD");

        //setting total balance from income and expenses
        int totalBalance=income-expenses;
        TextView totalBalanceTextView=(TextView) findViewById(R.id.admin_total_balance);
        totalBalanceTextView.setText(totalBalance+" IQD");




        categoryNames=new ArrayList<>();
        Cursor cat=mydb.getCategoryNames();
        if(cat.moveToFirst()){
            do{
                categoryNames.add(cat.getString(0));
            }while (cat.moveToNext());
        }

        categoryAmounts=new ArrayList<>();
        for (int i=0;i<categoryNames.size();i++){
            Cursor amm=mydb.getCategoryAllAmount(categoryNames.get(i));
            if(amm.moveToFirst()){
                do{
                    categoryAmounts.add(amm.getString(0));
                }while (amm.moveToNext());
            }
        }

        ListView lv=(ListView) findViewById(R.id.catigory_list);
        ArrayList<Categories> categoriesArrayList=new ArrayList<>();
        for (int i=0;i<categoryNames.size();i++){
            categoriesArrayList.add(new Categories(categoryNames.get(i),categoryAmounts.get(i)));
        }
        cateoriesAdapter= new CateoriesAdapter(this,categoriesArrayList);
        lv.setAdapter(cateoriesAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(adminHome.this, TransactionDetailsAdmin.class);
                intent.putExtra("id","admin");
                intent.putExtra("categoryName",categoryNames.get(i));
                intent.putExtra("amount",categoryAmounts.get(i));
                startActivity(intent);
            }
        });
        TextView viewAll = (TextView) findViewById(R.id.admin_view_all);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, TransactionDetailsAdmin.class);
                intent.putExtra("id","admin");
                intent.putExtra("categoryName","All Transactions");
                intent.putExtra("amount","all");
                startActivity(intent);
            }
        });

        ImageButton btnBack= (ImageButton) findViewById(R.id.setting);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, IncomesManager.class);
                startActivity(intent);
            }
        });



    }
}
