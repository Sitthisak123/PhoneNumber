package com.cs.sitthisak.phonenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import com.cs.sitthisak.phonenumber.dao.PhoneNumberDBHelper;
import com.cs.sitthisak.phonenumber.model.Contact;

public class AddContactActivity extends AppCompatActivity {
    private TextInputLayout tiplName, tiplMobile;
    private Button btnAdd;
    private PhoneNumberDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        matchView();
        db = new PhoneNumberDBHelper(AddContactActivity.this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tiplName.getEditText().getText().toString();
                String mobile = tiplMobile.getEditText().getText().toString();

                if(name.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(
                            AddContactActivity.this,
                            "กรุณากรอกข้อมูลให้ครบก่อน!",
                            Toast.LENGTH_SHORT
                    ).show();
                }else {
                    Contact contact = new Contact(name, mobile);
                    boolean result = db.insert(contact);
                    if(result){
                        Toast.makeText(
                                AddContactActivity.this,
                                "เพิ่มข้อมูลเพื่อนสำเร็จ!",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = new Intent(AddContactActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void matchView() {
        tiplName = findViewById(R.id.tipl_add_name);
        tiplMobile = findViewById(R.id.tipl_add_mobile);
        btnAdd = findViewById(R.id.btn_add);
    }
}