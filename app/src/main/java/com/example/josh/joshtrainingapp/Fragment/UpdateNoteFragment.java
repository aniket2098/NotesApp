package com.example.josh.joshtrainingapp.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.josh.joshtrainingapp.Database.DBManager;
import com.example.josh.joshtrainingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateNoteFragment extends Fragment {


    public UpdateNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        final int position = bundle.getInt("position");
        Cursor cursor = (new DBManager(getContext())).fetchRow(position);
        final EditText editTextNote = getView().findViewById(R.id.editTextNoteUpdate);
        editTextNote.setText(cursor.getString(cursor.getColumnIndex("note")));
        final EditText editTextTitle = getView().findViewById(R.id.editTextTitleUpdate);
        editTextTitle.setText(cursor.getString(cursor.getColumnIndex("title")));

        FloatingActionButton fab = getView().findViewById(R.id.fabDone);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                (new DBManager(getContext())).update(position, editTextNote.getText().toString(), editTextTitle.getText().toString());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_note, container, false);
    }

}
