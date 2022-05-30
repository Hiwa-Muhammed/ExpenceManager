package com.example.expencemanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ExpenseManagement.db";

    public static final String EMPLOYEES_TABLE_NAME ="employees";
    public static final String EMPLOYEES_COLUMN_ID ="id";
    public static final String EMPLOYEES_COLUMN_NAME ="name";
    public static final String EMPLOYEES_COLUMN_EMAIL ="email";
    public static final String EMPLOYEES_COLUMN_PHONE ="phone";
    public static final String EMPLOYEES_COLUMN_PASSWORD ="password";

    public static final String EXPENSES_TABLE_NAME ="expenses";
    public static final String EXPENSES_COLUMN_ID ="id";
    public static final String EXPENSES_COLUMN_PRICE ="price";
    public static final String EXPENSES_COLUMN_CATEGORY ="category";
    public static final String EXPENSES_COLUMN_NOTE ="note";
    public static final String EXPENSES_COLUMN_DATE ="date";

    public static final String INCOME_TABLE_NAME ="income";
    public static final String INCOME_COLUMN_ID ="id";
    public static final String INCOME_COLUMN_AMOUNT ="amount";

    public static final String CATEGORIES_TABLE_NAME ="categories";
    public static final String CATEGORIES_COLUMN_NAME ="name";



    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table employees " +
                        "(id INTEGER primary key AUTOINCREMENT, name text,email text unique,phone text, password text)"
        );
        db.execSQL(
                "create table expenses " +
                        "(id INTEGER , price INTEGER,category text,note text, date date,FOREIGN KEY(id) REFERENCES employees(id),FOREIGN KEY(category) REFERENCES categories(name))"
        );db.execSQL(
                "create table income " +
                        "(id INTEGER , amount INTEGER,FOREIGN KEY(id) REFERENCES employees(id))"
        );db.execSQL(
                "create table categories " +
                        "(name text primary key)"
        );
        db.execSQL(
                "INSERT into categories(name) values('Food');"
        );
        db.execSQL(
                "INSERT into categories(name) values('Fuel');"
        );
        db.execSQL(
                "INSERT into categories(name) values('Shopping');"
        );
        db.execSQL(
                "INSERT into categories(name) values('Recharge');"
        );
        db.execSQL(
                "INSERT into categories(name) values('Other');"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employees");
        db.execSQL("DROP TABLE IF EXISTS expenses");
        db.execSQL("DROP TABLE IF EXISTS income");
        onCreate(db);
    }
    public boolean insertEmployee (String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        db.insert("employees", null, contentValues);
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor id =  db2.rawQuery( "select id from employees where email='"+email+"'", null );
        int theId = 0;
        if(id.moveToFirst()){
            do{
                theId=Integer.parseInt(id.getString(0));
            }while (id.moveToNext());
        }
        ContentValues contentValues1=new ContentValues();
        contentValues1.put("id",theId);
        contentValues1.put("amount",0);
        db.insert("income",null,contentValues1);
        return true;
    }
    public boolean insertExpenses(int id, int price, String category, String note, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("price", price);
        contentValues.put("category", category);
        contentValues.put("note", note);
        contentValues.put("date", date);
        db.insert("expenses", null, contentValues);
        return true;
    }
    public boolean setIncome(int id, int income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", income);
        db.update(INCOME_TABLE_NAME,contentValues,"id="+id,null);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from employees where id="+id+"", null );
        return res;
    }
    public Cursor getId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select id from employees where email='"+email+"'", null );
        return res;
    }
    public Cursor getName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select name from employees where id="+id+"", null );
        return res;
    }
    public Cursor getIncome(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select amount from income where id="+id+"", null );
        return res;
    }
    public Cursor getAllIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select SUM(amount) from income" , null );
        return res;
    }
    public Cursor getAllIncomeData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from income" , null );
        return res;
    }
    public Cursor getExpenses(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select SUM(price) from expenses where id="+id+"", null );
        return res;
    }
    public Cursor getAllExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select SUM(price) from expenses", null );
        return res;
    }
    public Cursor getAllExpensesEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select SUM(price) from expenses where id="+id+"", null );
        return res;
    }
    public Cursor getAllExpensesDataEmployeePerCategory(int id,String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select price,date,note from expenses where id="+id+" and category='"+categoryName+"'", null );
        return res;
    }
    public Cursor getAllExpensesDataAdmin(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select price,date,note,id from expenses where category='"+categoryName+"'", null );
        return res;
    }
    public Cursor getAllExpensesDataEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select price,date,note from expenses where id="+id+"", null );
        return res;
    }
    public Cursor getAllExpensesDataAdminDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select price,date,note,id from expenses", null );
        return res;
    }
    public Cursor getCategoryNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select name from categories", null );
        return res;
    }
    public Cursor getCategoryAmount(String categoryName ,int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select SUM(price) from expenses where id="+id+" and category='"+categoryName+"'", null );
        return res;
    }
    public Cursor getCategoryAllAmount(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select SUM(price) from expenses where category='"+categoryName+"'", null );
        return res;
    }
    public Cursor checkEmployees(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select password from employees where email='"+email+"'", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EMPLOYEES_TABLE_NAME);
        return numRows;
    }
    public boolean updateEmployee (Integer id, String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        db.update("employees", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteEmployee (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("employees",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    @SuppressLint("Range")
    public ArrayList<String> getAllEmployees() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from employees", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(EMPLOYEES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }




}
