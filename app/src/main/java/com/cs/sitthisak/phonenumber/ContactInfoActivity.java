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

public class ContactInfoActivity extends AppCompatActivity {
    private TextInputLayout tiplName, tiplMobile;
    private Button btnUpdate, btnDelete;
    private PhoneNumberDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        matchView();
        db = new PhoneNumberDBHelper(ContactInfoActivity.this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String mobile = intent.getStringExtra("mobile");

        tiplName.getEditText().setText(name);
        tiplMobile.getEditText().setText(mobile);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tiplName.getEditText().getText().toString();
                String mobile = tiplMobile.getEditText().getText().toString();

                if(name.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(
                            ContactInfoActivity.this,
                            "กรุณากรอกข้อมูลให้ครบก่อน!",
                            Toast.LENGTH_SHORT
                    ).show();
                }else {
                    Contact contact = new Contact(id, name, mobile);
                    boolean result = db.update(contact);
                    if(result){
                        Toast.makeText(
                                ContactInfoActivity.this,
                                "แก้ไขข้อมูลเพื่อนสำเร็จ!",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = new Intent(ContactInfoActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = db.delete(id);
                if(result){
                    Toast.makeText(
                            ContactInfoActivity.this,
                            "ลบเพื่อนสำเร็จ!",
                            Toast.LENGTH_SHORT
                    ).show();
                    Intent intent = new Intent(ContactInfoActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void matchView() {
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        tiplName = findViewById(R.id.tipl_info_name);
        tiplMobile = findViewById(R.id.tipl_info_mobile);
    }
}