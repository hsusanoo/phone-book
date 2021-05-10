package com.example.tp3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int ID;
    @ColumnInfo(name = "fName")
    public String fName;
    @ColumnInfo(name = "lName")
    public String lName;
    @ColumnInfo(name = "job")
    public String job;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "email")
    public String email;

    public Contact(String fName, String lName, String phone, String email, String job) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.job = job;
    }

    /**
     * Get contact data concatenated in a string
     *
     * @return contact data
     */
    public String getDetailsString() {
        return fName.toLowerCase() + " " +
                lName.toLowerCase() + " " +
                job.toLowerCase() + " " +
                phone + " " +
                email.toLowerCase();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "ID=" + ID +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", job='" + job + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
