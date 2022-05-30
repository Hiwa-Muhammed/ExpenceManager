package com.example.expencemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    DBHelper mydb;
    EditText name;
    EditText email;
    EditText phone;
    EditText password;
    EditText rePassword;

    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        Button btnFragment=(Button) view.findViewById(R.id.signup_to_login);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.viewPager,new LoginFragment()).commit();
            }
        });
        Button btnRegister=(Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=(EditText) getView().findViewById(R.id.et_name);
                email=(EditText) getView().findViewById(R.id.et_email);
                phone=(EditText) getView().findViewById(R.id.et_phone);
                password=(EditText) getView().findViewById(R.id.et_password);
                rePassword=(EditText) getView().findViewById(R.id.et_repassword);
                mydb =new DBHelper(getActivity());
                if(!(name.getText().toString().isEmpty())&&!(email.getText().toString().isEmpty())&&!(phone.getText().toString().isEmpty())&&!(password.getText().toString().isEmpty())&&!(rePassword.getText().toString().isEmpty())){
                    System.out.println("pass1   "+password.getText());
                    System.out.println("pass2   "+rePassword.getText());
                    if(password.getText().toString().equals(rePassword.getText().toString())){
                        if(mydb.insertEmployee(name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString())){
                            FragmentTransaction fr= getFragmentManager().beginTransaction();
                            fr.replace(R.id.viewPager,new LoginFragment()).commit();
                            Toast.makeText(getActivity(), "Signed up successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getActivity(), "error in insert",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getActivity(), "your passwords not the same",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), "empty",Toast.LENGTH_SHORT).show();
            }
        });

        return view;    }

}