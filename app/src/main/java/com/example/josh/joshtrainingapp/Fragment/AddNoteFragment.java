package com.example.josh.joshtrainingapp.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.josh.joshtrainingapp.Database.DBManager;
import com.example.josh.joshtrainingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNoteFragment extends Fragment {


    public AddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.floatingActionButton2);
        final Spinner spinner = getView().findViewById(R.id.spinnerAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager dbManager = new DBManager(getContext());
                dbManager.open();
                EditText editTextAdd = getView().findViewById(R.id.editTextAdd);
                EditText editTextTitle = getView().findViewById(R.id.editTextTitle);
                dbManager.insert(editTextAdd.getText().toString(), editTextTitle.getText().toString(), spinner.getSelectedItem().toString());
                getFragmentManager().popBackStackImmediate();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

}
