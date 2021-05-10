package com.example.tp3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> implements Filterable {

    List<Contact> contactList, contactListAll;
    Activity context;
    RoomDB db;

    public ContactsAdapter(List<Contact> contactList, Activity context) {
        this.contactList = contactList;
        contactListAll = new ArrayList<>(contactList);
        this.context = context;
        db = RoomDB.getInstance(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.contact_list_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = contactList.get(position);

        db = RoomDB.getInstance(context);

        holder.name.setText(contact.fName + " " + contact.lName);
        holder.job.setText(contact.job);
        holder.email.setText(contact.email);
        holder.phone.setText(contact.phone);

        holder.callButton.setOnClickListener(v -> {
            String phoneNumber = contact.phone;
            if (phoneNumber.isEmpty()) {
                Toast.makeText(context, "No phone number specified!", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            //  Dial
            Intent dialIntent = new Intent(Intent.ACTION_DIAL)
                    .setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(dialIntent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            System.out.println("Long Press trigger");
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(v.getContext());
            dialogBuilder.setTitle("Delete contact")
                    .setMessage("You're about to delete a contact, proceed?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        System.out.println("Deleting contact...");
                        db.contactDAO().delete(contact);
                        updateList();
                        System.out.println("contact deleted...");
                    })
                    .setNegativeButton("Cancel", ((dialog, which) -> {
                        System.out.println("Canceled.");
                    }));

            dialogBuilder.show();
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateContact.class);
            intent.putExtra("contact", contact);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Contact> filteredContacts = new ArrayList<>();
                String searchString = constraint.toString().toLowerCase();

                if (searchString.isEmpty()) {
                    filteredContacts.addAll(contactListAll);
                } else {
                    for (Contact contact :
                            contactListAll) {
                        if (contact.getDetailsString().contains(searchString)) {
                            filteredContacts.add(contact);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredContacts;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactList.clear();
                contactList.addAll((Collection<? extends Contact>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public void updateList() {
        contactList.clear();
        contactList.addAll(db.contactDAO().getAll());
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageButton callButton;
        TextView name, job, email, phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            callButton = itemView.findViewById(R.id.callButton);
            name = itemView.findViewById(R.id.txt_name);
            job = itemView.findViewById(R.id.txt_job);
            email = itemView.findViewById(R.id.txt_email);
            phone = itemView.findViewById(R.id.txt_number);
        }

        @Override
        public void onClick(View v) {}

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }

}
