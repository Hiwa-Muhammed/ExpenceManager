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

public class IncomesManager extends AppCompatActivity {
    ArrayList<Integer> id;
    ArrayList<String> amount;
    ArrayList<String> names;
    EmployeeAdapter employeeAdapter;
    DBHelper mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomes_manager_layout);
        id=new ArrayList<Integer>();
        amount=new ArrayList<String>();
        mydb=new DBHelper(this);
        names=new ArrayList<String>();

        Cursor res=mydb.getAllIncomeData();
        if(res.moveToFirst()){
            do{
                id.add(Integer.parseInt(res.getString(0)));
                amount.add(res.getString(1)+" IQD");
            }while (res.moveToNext());
        }

        for (int i=0;i<id.size();i++){
            Cursor nam=mydb.getName(id.get(i));
            if(nam.moveToFirst()){
                do{
                    names.add(nam.getString(0));
                    System.out.println(names.get(i));
                }while (nam.moveToNext());
            }
        }


        ListView lv=(ListView) findViewById(R.id.employees_list);
        ArrayList<EmployeeIncome> employeeIncome=new ArrayList<>();
        for (int i=0;i<names.size();i++){
            employeeIncome.add(new EmployeeIncome(names.get(i),amount.get(i)));
        }
        employeeAdapter= new EmployeeAdapter(this,employeeIncome);
        lv.setAdapter(employeeAdapter);
//        ArrayAdapter<String> arrayAdapter =
//                new ArrayAdapter<String>(this,R.layout.employee_list,R.id.employee_name, names);
//        ArrayAdapter<String> arrayAdapter2 =
//                new ArrayAdapter<String>(this,R.layout.employee_list,R.id.income_amount, amount);
//        ListView listView = (ListView) findViewById(R.id.employees_list);
//        listView.setAdapter(arrayAdapter);
//        listView.setAdapter(arrayAdapter2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(IncomesManager.this, EditIncome.class);
                intent.putExtra("id",id.get(i));
                intent.putExtra("amount",amount.get(i));
                startActivity(intent);
            }
        });
        ImageButton btnBack= (ImageButton) findViewById(R.id.back_img_incomes_manager);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IncomesManager.this, adminHome.class);
                startActivity(intent);
            }
        });
    }
}
class EmployeeIncome {

    // Store the name of the movie
    private String employeeName;
    // Store the release date of the movie
    private String incomeAmount;
    // Constructor that is used to create an instance of the Movie object
    public EmployeeIncome(String employeeName, String incomeAmount) {
        this.employeeName = employeeName;
        this.incomeAmount = incomeAmount;
    }

    public String getmName() {
        return employeeName;
    }

    public void setmName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIncomeAmmount() {
        return incomeAmount;
    }
    public void setIncomeAmmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
}
class EmployeeAdapter extends ArrayAdapter<EmployeeIncome> {

    private Context mContext;
    private List<EmployeeIncome> employeeList = new ArrayList<>();

    public EmployeeAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<EmployeeIncome> list) {
        super(context, 0 , list);
        mContext = context;
        employeeList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.employee_list,parent,false);

        EmployeeIncome currentEmployee = employeeList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.employee_name);
        name.setText(currentEmployee.getmName());

        TextView release = (TextView) listItem.findViewById(R.id.income_amount);
        release.setText(currentEmployee.getIncomeAmmount());


        return listItem;
    }
}