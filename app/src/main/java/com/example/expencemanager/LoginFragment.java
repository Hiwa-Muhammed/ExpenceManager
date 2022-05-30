package com.example.expencemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    DBHelper mydb;
    EditText email;
    EditText password;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        Button btnFragment=(Button) view.findViewById(R.id.btn_login_to_signup);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.viewPager,new RegisterFragment()).commit();
            }
        });
        Button btnLogin=(Button) view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=(EditText) getView().findViewById(R.id.et_email);
                password=(EditText) getView().findViewById(R.id.et_password);
                mydb =new DBHelper(getActivity());
                if(!(email.getText().toString().isEmpty())&&!(password.getText().toString().isEmpty())){
                    if((email.getText().toString().equals("admin"))&&(password.getText().toString().equals("admin"))){
                        Intent intent = new Intent(getContext(), adminHome.class);
                        startActivity(intent);
                    }
                    else{
                        Cursor pass=mydb.checkEmployees(email.getText().toString());
                        String thePass = null;
                        if(pass.moveToFirst()){
                            do{
                                thePass=pass.getString(0);
                            }while (pass.moveToNext());
                        }
                        System.out.println(thePass);
                        if(thePass == null){
                            Toast.makeText(getActivity(), "You don't have account",Toast.LENGTH_SHORT).show();
                        }
                        else if(thePass.equals(password.getText().toString())){
                            Toast.makeText(getActivity(), "You are logged in successfully",Toast.LENGTH_SHORT).show();
                            Cursor id=mydb.getId(email.getText().toString());
                            int theId = 0;
                            if(id.moveToFirst()){
                                do{
                                    theId=Integer.parseInt(id.getString(0));
                                }while (id.moveToNext());
                            }
                            System.out.println(theId);
                            Intent intent = new Intent(getContext(), home.class);
                            intent.putExtra("id",theId);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getActivity(), "wrong password",Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });


        return view;
    }


}