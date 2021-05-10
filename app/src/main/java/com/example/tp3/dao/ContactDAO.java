package com.example.tp3.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tp3.Contact;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);

    @Query("SELECT * FROM contacts")
    List<Contact> getAll();
}
