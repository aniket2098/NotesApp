package com.example.josh.joshtrainingapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.josh.joshtrainingapp.Adapter.NotesAdapter;
import com.example.josh.joshtrainingapp.POJO.NoteDisplayObject;
import com.example.josh.joshtrainingapp.R;


public class NotesFragment extends Fragment {

    String style;
    NotesAdapter notesAdapter;

    public NotesFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        style = bundle.getString("Style");

        notesAdapter = new NotesAdapter(new NoteDisplayObject().getNotes(getContext()), getFragmentManager(), getContext(), style);

        RecyclerView recyclerView = getView().findViewById(R.id.notesRecycler);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("NotePref", Context.MODE_PRIVATE);
        if(preferences.getString("View","").equals("List")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new AddNoteFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_notes, container, false);
        }

    public NotesAdapter getNotesAdapter() {
        return notesAdapter;
    }
}
