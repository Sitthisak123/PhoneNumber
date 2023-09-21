package com.cs.sitthisak.phonenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import com.cs.sitthisak.phonenumber.adapter.ContactAdapter;
import com.cs.sitthisak.phonenumber.dao.PhoneNumberDBHelper;
import com.cs.sitthisak.phonenumber.model.Contact;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvContact;
    private List<Contact> contacts;
    private ContactAdapter adapter;
    private PhoneNumberDBHelper db;
    private FloatingActionButton fabAdd;
    private ImageView imgvNoData;
    private TextView tvNoData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new PhoneNumberDBHelper(MainActivity.this);
        matchView();

        contacts = db.select();
        if(contacts.size()>0){
            adapter = new ContactAdapter(MainActivity.this, contacts);
            rcvContact.setAdapter(adapter);
            rcvContact.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            imgvNoData.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
        }else {
            imgvNoData.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        }


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,
                        AddContactActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void matchView() {
        rcvContact = findViewById(R.id.rcv_contact);
        fabAdd = findViewById(R.id.fab_add);
        tvNoData = findViewById(R.id.tv_no_data);
        imgvNoData = findViewById(R.id.imgv_no_data);
    }
}