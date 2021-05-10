package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class UpdateContact extends AppCompatActivity {

    EditText fName, lName, job, email, phone;
    Contact oldContact;
    RoomDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        db = RoomDB.getInstance(this);

        oldContact = (Contact) getIntent().getSerializableExtra("contact");
        //  Debug
        setTitle("Update contact");

        Fragment form = new ContactInfoFragment();
        Bundle contactBundle = new Bundle();
        contactBundle.putSerializable("contact", oldContact);

        form.setArguments(contactBundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fName = findViewById(R.id.edit_fname);
        lName = findViewById(R.id.edit_lname);
        job = findViewById(R.id.edit_job);
        email = findViewById(R.id.edit_email);
        phone = findViewById(R.id.edit_phone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    public void updateContact(MenuItem item) {
        Contact updatedContact = new Contact(
                fName.getText().toString(),
                lName.getText().toString(),
                phone.getText().toString(),
                email.getText().toString(),
                job.getText().toString()
        );

        updatedContact.ID = oldContact.ID;

        System.out.println("new contact to insert : " + updatedContact);

        db.contactDAO().update(updatedContact);

        Toast.makeText(this, "Contact updated!", Toast.LENGTH_SHORT).show();
        goToHome(null);
    }

    public void goToHome(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}