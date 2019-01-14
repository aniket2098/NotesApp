package com.example.josh.joshtrainingapp.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.josh.joshtrainingapp.Database.DBManager;
import com.example.josh.joshtrainingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewNoteFragment extends Fragment {


    public ViewNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        DBManager dbManager = new DBManager(getContext());
        dbManager.open();
        Bundle bundle = getArguments();
        final int position = bundle.getInt("position");
        Cursor cursor = dbManager.fetchRow(position);
        TextView textView = getView().findViewById(R.id.textViewAdd);
        textView.setText(cursor.getString(cursor.getColumnIndex("note")));
        textView = getView().findViewById(R.id.textViewTitle);
        textView.setText(cursor.getString(cursor.getColumnIndex("title")));

        FloatingActionButton fab = getView().findViewById(R.id.fabUpdate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Fragment fragment = new UpdateNoteFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_left_exit,
                        R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_right_exit);
                fragmentTransaction.replace(R.id.layoutContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_view_note, container, false);
    }

}
