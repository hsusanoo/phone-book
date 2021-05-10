package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactInfoFragment extends Fragment {

    private static final String CONTACT = "contact";

    private Contact contact;

    public ContactInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param contact Contact.
     * @return A new instance of fragment ContactInfoFragment.
     */
    public static ContactInfoFragment newInstance(@Nullable Contact contact) {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            contact = (Contact) getArguments().getSerializable(CONTACT);
        } else {
            //  Get bundle from parent activity
            Bundle args = getActivity().getIntent().getExtras();
            if (args != null) {
                contact = (Contact) args.getSerializable(CONTACT);
                setArguments(args);
                System.out.println(contact);
            } else {
                System.out.println("NO ARGUMENTS PASSED!");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View form = inflater.inflate(R.layout.fragment_contact_info, container, false);
        if (contact != null) {
            ((TextView) form.findViewById(R.id.edit_fname)).setText(contact.fName);
            ((TextView) form.findViewById(R.id.edit_lname)).setText(contact.lName);
            ((TextView) form.findViewById(R.id.edit_job)).setText(contact.job);
            ((TextView) form.findViewById(R.id.edit_email)).setText(contact.email);
            ((TextView) form.findViewById(R.id.edit_phone)).setText(contact.phone);
        }
        return form;
    }

    public void goToHome(View view) {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}