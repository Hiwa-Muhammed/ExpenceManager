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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailsAdmin extends AppCompatActivity {
    ArrayList<String> expensePrice,expenseDate,expenseNote,idEmployee,employeeName;
    DBHelper mydb;
    EmployeeExpenseAdminAdapter EmployeeExpenseAdminAdapter;
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_details_admin_layout);
        mydb=new DBHelper(this);

        savedInstanceState=getIntent().getExtras();
        int id=savedInstanceState.getInt("id");
        String categoryName=savedInstanceState.getString("categoryName");
        String amount=savedInstanceState.getString("amount");

        TextView categoryNameTextView=(TextView) findViewById(R.id.category_details_name_admin);
        categoryNameTextView.setText(categoryName);


        if(!amount.equals("all")) {
            TextView expensesTextView = (TextView) findViewById(R.id.total_amount_details_admin);
            expensesTextView.setText(amount + " IQD");

            expensePrice = new ArrayList<>();
            expenseDate = new ArrayList<>();
            expenseNote = new ArrayList<>();
            idEmployee = new ArrayList<>();
            employeeName=new ArrayList<>();
            Cursor res = mydb.getAllExpensesDataAdminDetails();
            if (res.moveToFirst()) {
                do {
                    expensePrice.add(res.getString(0) + " IQD");
                    expenseDate.add(res.getString(1));
                    expenseNote.add(res.getString(2));
                    idEmployee.add(res.getString(3));
                } while (res.moveToNext());
            }

            for (int i=0;i<idEmployee.size();i++){
                Cursor nam=mydb.getName(Integer.parseInt(idEmployee.get(i)));
                if(nam.moveToFirst()){
                    do{
                        employeeName.add(nam.getString(0));
                        System.out.println(employeeName.get(i));
                    }while (nam.moveToNext());
                }
            }


            ListView lv = (ListView) findViewById(R.id.expenses_list_admin);
            ArrayList<EmployeeExpenseAdmin> employeeExpenseAdmin = new ArrayList<>();
            for (int i = 0; i < expensePrice.size(); i++) {
                employeeExpenseAdmin.add(new EmployeeExpenseAdmin(expensePrice.get(i), expenseDate.get(i), expenseNote.get(i),employeeName.get(i)));
            }
            EmployeeExpenseAdminAdapter = new EmployeeExpenseAdminAdapter(this, employeeExpenseAdmin);
            lv.setAdapter(EmployeeExpenseAdminAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(TransactionDetailsAdmin.this, ExpenseDetails.class);
                    intent.putExtra("id",id);
                    intent.putExtra("categoryName",categoryName);
                    intent.putExtra("amount",expensePrice.get(i));
                    intent.putExtra("date",expenseDate.get(i));
                    intent.putExtra("note",expenseNote.get(i));
                    startActivity(intent);
                }
            });
        }else{

            Cursor res = mydb.getAllExpenses();
            if (res.moveToFirst()) {
                do {
                    amount=res.getString(0) + " IQD";
                } while (res.moveToNext());
            }
            TextView expensesTextView = (TextView) findViewById(R.id.total_amount_details_admin);
            expensesTextView.setText(amount);

            expensePrice = new ArrayList<>();
            expenseDate = new ArrayList<>();
            expenseNote = new ArrayList<>();
            idEmployee = new ArrayList<>();
            employeeName=new ArrayList<>();
            Cursor data = mydb.getAllExpensesDataAdminDetails();
            if (data.moveToFirst()) {
                do {
                    expensePrice.add(data.getString(0) + " IQD");
                    expenseDate.add(data.getString(1));
                    expenseNote.add(data.getString(2));
                    idEmployee.add(data.getString(3));
                } while (data.moveToNext());
            }

            for (int i=0;i<idEmployee.size();i++){
                Cursor nam=mydb.getName(Integer.parseInt(idEmployee.get(i)));
                if(nam.moveToFirst()){
                    do{
                        employeeName.add(nam.getString(0));
                        System.out.println(employeeName.get(i));
                    }while (nam.moveToNext());
                }
            }

            ListView lv = (ListView) findViewById(R.id.expenses_list_admin);
            ArrayList<EmployeeExpenseAdmin> employeeExpenseAdmin = new ArrayList<>();
            for (int i = 0; i < expensePrice.size(); i++) {
                employeeExpenseAdmin.add(new EmployeeExpenseAdmin(expensePrice.get(i), expenseDate.get(i), expenseNote.get(i),employeeName.get(i)));
            }
            EmployeeExpenseAdminAdapter = new EmployeeExpenseAdminAdapter(this, employeeExpenseAdmin);
            lv.setAdapter(EmployeeExpenseAdminAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(TransactionDetailsAdmin.this, ExpenseDetails.class);
                    intent.putExtra("id",id);
                    intent.putExtra("categoryName",categoryName);
                    intent.putExtra("amount",expensePrice.get(i));
                    intent.putExtra("date",expenseDate.get(i));
                    intent.putExtra("note",expenseNote.get(i));
                    startActivity(intent);
                }
            });


        }
        ImageButton btnBack= (ImageButton) findViewById(R.id.back_img_transaction_admin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionDetailsAdmin.this, adminHome.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }
}
class EmployeeExpenseAdmin {

    private String expensePrice;
    private String expenseDate;
    private String expenseNote;
    private String expenseEmployee;
    // Constructor that is used to create an instance of the Movie object
    public EmployeeExpenseAdmin(String expensePrice, String expenseDate,String expenseNote,String expenseEmployee) {
        this.expensePrice = expensePrice;
        this.expenseDate = expenseDate;
        this.expenseNote = expenseNote;
        this.expenseEmployee = expenseEmployee;
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
    public String getExpenseEmployee() {
        return expenseEmployee;
    }
    public void setExpenseEmployee(String expenseEmployee) {
        this.expenseEmployee = expenseEmployee;
    }
}
class EmployeeExpenseAdminAdapter extends ArrayAdapter<EmployeeExpenseAdmin> {

    private Context mContext;
    private List<EmployeeExpenseAdmin> expenceList = new ArrayList<>();

    public EmployeeExpenseAdminAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<EmployeeExpenseAdmin> list) {
        super(context, 0 , list);
        mContext = context;
        expenceList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.tranaction_admin_list,parent,false);

        EmployeeExpenseAdmin currentEmployee = expenceList.get(position);


        TextView price = (TextView) listItem.findViewById(R.id.expense_price_admin);
        price.setText(currentEmployee.getExpensePrice());

        TextView date = (TextView) listItem.findViewById(R.id.expense_date_admin);
        date.setText(currentEmployee.getExpenseDate());

        TextView note = (TextView) listItem.findViewById(R.id.expense_note_admin);
        note.setText(currentEmployee.getExpenseNote());

        TextView name = (TextView) listItem.findViewById(R.id.expense_admin_name);
        name.setText(currentEmployee.getExpenseEmployee());


        return listItem;
    }
}