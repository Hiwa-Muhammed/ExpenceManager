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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailsEmployee extends AppCompatActivity {
    ArrayList<String> expensePrice,expenseDate,expenseNote;
    DBHelper mydb;
    EmployeeExpenseAdapter EmployeeExpenseAdapter;
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactoin_details_employee_layout);
        mydb=new DBHelper(this);

        savedInstanceState=getIntent().getExtras();
        int id=savedInstanceState.getInt("id");
        String categoryName=savedInstanceState.getString("categoryName");
        String amount=savedInstanceState.getString("amount");

        TextView categoryNameTextView=(TextView) findViewById(R.id.category_details_name);
        categoryNameTextView.setText(categoryName);


        if(!amount.equals("all")) {
            TextView expensesTextView = (TextView) findViewById(R.id.total_amount_details);
            expensesTextView.setText(amount + " IQD");

            expensePrice = new ArrayList<>();
            expenseDate = new ArrayList<>();
            expenseNote = new ArrayList<>();
            Cursor res = mydb.getAllExpensesDataEmployeePerCategory(id, categoryName);
            if (res.moveToFirst()) {
                do {
                    expensePrice.add(res.getString(0) + " IQD");
                    expenseDate.add(res.getString(1));
                    expenseNote.add(res.getString(2));
                } while (res.moveToNext());
            }


            ListView lv = (ListView) findViewById(R.id.expenses_list);
            ArrayList<EmployeeExpense> employeeExpense = new ArrayList<>();
            for (int i = 0; i < expensePrice.size(); i++) {
                employeeExpense.add(new EmployeeExpense(expensePrice.get(i), expenseDate.get(i), expenseNote.get(i)));
            }
            EmployeeExpenseAdapter = new EmployeeExpenseAdapter(this, employeeExpense);
            lv.setAdapter(EmployeeExpenseAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(TransactionDetailsEmployee.this, ExpenseDetails.class);
                    intent.putExtra("id",id);
                    intent.putExtra("categoryName",categoryName);
                    intent.putExtra("amount",expensePrice.get(i));
                    intent.putExtra("date",expenseDate.get(i));
                    intent.putExtra("note",expenseNote.get(i));
                    startActivity(intent);
                }
            });
        }else{


            Cursor res = mydb.getAllExpensesEmployee(id);
            if (res.moveToFirst()) {
                do {
                    amount=res.getString(0) + " IQD";
                } while (res.moveToNext());
            }
            TextView expensesTextView = (TextView) findViewById(R.id.total_amount_details);
            expensesTextView.setText(amount);

            expensePrice = new ArrayList<>();
            expenseDate = new ArrayList<>();
            expenseNote = new ArrayList<>();
            Cursor data = mydb.getAllExpensesDataEmployee(id);
            if (data.moveToFirst()) {
                do {
                    expensePrice.add(data.getString(0) + " IQD");
                    expenseDate.add(data.getString(1));
                    expenseNote.add(data.getString(2));
                } while (data.moveToNext());
            }

            ListView lv = (ListView) findViewById(R.id.expenses_list);
            ArrayList<EmployeeExpense> employeeExpense = new ArrayList<>();
            for (int i = 0; i < expensePrice.size(); i++) {
                employeeExpense.add(new EmployeeExpense(expensePrice.get(i), expenseDate.get(i), expenseNote.get(i)));
            }
            EmployeeExpenseAdapter = new EmployeeExpenseAdapter(this, employeeExpense);
            lv.setAdapter(EmployeeExpenseAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(TransactionDetailsEmployee.this, ExpenseDetails.class);
                    intent.putExtra("id",id);
                    intent.putExtra("categoryName",categoryName);
                    intent.putExtra("amount",expensePrice.get(i));
                    intent.putExtra("date",expenseDate.get(i));
                    intent.putExtra("note",expenseNote.get(i));
                    startActivity(intent);
                }
            });


        }
        ImageButton btnBack= (ImageButton) findViewById(R.id.back_img_transaction_employee);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionDetailsEmployee.this, home.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }
}
class EmployeeExpense {

    private String expensePrice;
    private String expenseDate;
    private String expenseNote;
    // Constructor that is used to create an instance of the Movie object
    public EmployeeExpense(String expensePrice, String expenseDate,String expenseNote) {
        this.expensePrice = expensePrice;
        this.expenseDate = expenseDate;
        this.expenseNote = expenseNote;
    }

    public String getExpensePrice() {
        return expensePrice;
    }

    public void setPrice(String expensePrice) {
        this.expensePrice = expensePrice;
    }

    public String getExpenseDate() {
        return expenseDate;
    }
    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
    public String getExpenseNote() {
        return expenseNote;
    }
    public void setExpenseNote(String expenseNote) {
        this.expenseNote = expenseNote;
    }
}
class EmployeeExpenseAdapter extends ArrayAdapter<EmployeeExpense> {

    private Context mContext;
    private List<EmployeeExpense> expenceList = new ArrayList<>();

    public EmployeeExpenseAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<EmployeeExpense> list) {
        super(context, 0 , list);
        mContext = context;
        expenceList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.transaction_employee_list,parent,false);

        EmployeeExpense currentEmployee = expenceList.get(position);


        TextView price = (TextView) listItem.findViewById(R.id.expense_amount_details);
        price.setText(currentEmployee.getExpensePrice());

        TextView date = (TextView) listItem.findViewById(R.id.expense_date);
        date.setText(currentEmployee.getExpenseDate());

        TextView note = (TextView) listItem.findViewById(R.id.expense_note);
        note.setText(currentEmployee.getExpenseNote());


        return listItem;
    }
}