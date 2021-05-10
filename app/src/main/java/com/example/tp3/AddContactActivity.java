package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    EditText fName, lName, job, email, phone;
    RoomDB db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        setTitle("New contact");

        fName = findViewById(R.id.edit_fname);
        lName = findViewById(R.id.edit_lname);
        job = findViewById(R.id.edit_job);
        email = findViewById(R.id.edit_email);
        phone = findViewById(R.id.edit_phone);

        db = RoomDB.getInstance(this);

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
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    public void saveContact(MenuItem item) {
        // Create contact
        Contact contact = new Contact(
                fName.getText().toString(),
                lName.getText().toString(),
                phone.getText().toString(),
                email.getText().toString(),
                job.getText().toString()
        );
        // Save in db
        System.out.println("Adding " + contact);
        db.contactDAO().insert(contact);
        Toast.makeText(this, "Contact added!", Toast.LENGTH_SHORT).show();
        goToHome(null);
    }

    public void clearFields(MenuItem item) {
        fName.setText("");
        lName.setText("");
        job.setText("");
        email.setText("");
        phone.setText("");
    }

    public void goToHome(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
