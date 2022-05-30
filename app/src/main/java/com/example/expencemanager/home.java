package com.example.expencemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    ListView lv;
    ArrayList<String> categoryNames;
    ArrayList<String> categoryAmounts;
    CateoriesAdapter cateoriesAdapter;

    DBHelper mydb;
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        savedInstanceState=getIntent().getExtras();
        int id=savedInstanceState.getInt("id");

        //setting username from database
        mydb =new DBHelper(this);
        Cursor nam=mydb.getName(id);
        String username = null;
        if(nam.moveToFirst()){
            do{
                username=nam.getString(0);
            }while (nam.moveToNext());
        }
        TextView usernameTextView=(TextView) findViewById(R.id.user_name);
        usernameTextView.setText(username);


        //getting income from database
        Cursor inc=mydb.getIncome(id);
        int income = 0;
        if(inc.moveToFirst()){
            do{
                income=Integer.parseInt(inc.getString(0));
            }while (inc.moveToNext());
        }
        TextView incomeTextView=(TextView) findViewById(R.id.income_ammount);
        incomeTextView.setText(income+" IQD");


        //getting expenses from database
        Cursor exp=mydb.getExpenses(id);
        int expenses = 0;
        if(exp.moveToFirst()){
            do{
                if(exp.getString(0)!=null)
                expenses=Integer.parseInt(exp.getString(0));
            }while (exp.moveToNext());
        }
        TextView expensesTextView=(TextView) findViewById(R.id.expense_ammount);
        expensesTextView.setText(expenses+" IQD");

        //setting total balance from income and expenses
        int totalBalance=income-expenses;
        TextView totalBalanceTextView=(TextView) findViewById(R.id.total_balance);
        totalBalanceTextView.setText(totalBalance+" IQD");



        //setting transaction data
        categoryNames=new ArrayList<>();
        Cursor cat=mydb.getCategoryNames();
        if(cat.moveToFirst()){
            do{
                categoryNames.add(cat.getString(0));
            }while (cat.moveToNext());
        }

        categoryAmounts=new ArrayList<>();
        for (int i=0;i<categoryNames.size();i++){
            Cursor amm=mydb.getCategoryAmount(categoryNames.get(i),id);
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
                Intent intent = new Intent(home.this, TransactionDetailsEmployee.class);
                intent.putExtra("id",id);
                intent.putExtra("categoryName",categoryNames.get(i));
                intent.putExtra("amount",categoryAmounts.get(i));
                startActivity(intent);
            }
        });
        TextView viewAll = (TextView) findViewById(R.id.employee_view_all);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, TransactionDetailsEmployee.class);
                intent.putExtra("id",id);
                intent.putExtra("categoryName","All Transactions");
                intent.putExtra("amount","all");
                startActivity(intent);
            }
        });







        //add expenses floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, AddExpences.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }

}
class Categories {

    private String cName;
    private String cAmount;

    public Categories( String cName, String cAmount) {
        this.cName = cName;
        this.cAmount = cAmount;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAmount() {
        return cAmount;
    }

    public void setcAmount(String cAmount) {
        this.cAmount = cAmount;
    }
}
 class CateoriesAdapter extends ArrayAdapter<Categories> {

    private Context cContext;
    private List<Categories> categoryList = new ArrayList<>();

    public CateoriesAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Categories> list) {
        super(context, 0 , list);
        cContext = context;
        categoryList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(cContext).inflate(R.layout.categories_list,parent,false);

        Categories currentCategory = categoryList.get(position);
        ImageView image = (ImageView)listItem.findViewById(R.id.category_img);
        if(currentCategory.getcName().equals("Fuel")){
            image.setImageResource(R.drawable.gas);
        }
        else if(currentCategory.getcName().equals("Food")){
            image.setImageResource(R.drawable.menu);
        }
        else if(currentCategory.getcName().equals("Shopping")){
            image.setImageResource(R.drawable.shopping);
        }
        else if(currentCategory.getcName().equals("Recharge")){
            image.setImageResource(R.drawable.smartphone);
        }
        else{
            image.setImageResource(R.drawable.more);
        }
            TextView name = (TextView) listItem.findViewById(R.id.category_name);
        name.setText(currentCategory.getcName());

        TextView amount = (TextView) listItem.findViewById(R.id.field_ammount);
        if(currentCategory.getcAmount()!=null)
        amount.setText(currentCategory.getcAmount()+" IQD");
        else
            amount.setText("0 IQD");

        return listItem;
    }
}
