package com.example.josh.joshtrainingapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.josh.joshtrainingapp.Database.DBManager;
import com.example.josh.joshtrainingapp.POJO.NoteDisplayObject;
import com.example.josh.joshtrainingapp.R;
import com.example.josh.joshtrainingapp.Fragment.ViewNoteFragment;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<NoteDisplayObject> notes;
    FragmentManager fragmentManager;
    Context context;
    String style;


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notes;
        TextView textViewDate;
        TextView textViewDesc;
        TextView textViewTag;
        TextView textViewSymbol;
        CardView noteContatiner;
        MyViewHolder(View view) {
            super(view);
            notes = view.findViewById(R.id.note);
            textViewDate = view.findViewById(R.id.dates);
            textViewDesc = view.findViewById(R.id.description);
            textViewTag = view.findViewById(R.id.textViewTags);
            noteContatiner = view.findViewById(R.id.noteContainer);
            textViewTag = view.findViewById(R.id.textViewTags);
            textViewSymbol = view.findViewById(R.id.textViewSymbol);
        }
    }

    public NotesAdapter(List<NoteDisplayObject> notes, FragmentManager fragmentManager, Context context, String style) {

            this.notes = notes;
            this.fragmentManager = fragmentManager;
            this.context = context;
            this.style = style;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notes_grid, viewGroup, false);

            return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,int i) {

        final int j =i;
        String tag = notes.get(i).getTag();
        myViewHolder.notes.setText(notes.get(i).getNote());
        myViewHolder.textViewDate.setText(notes.get(i).getDate());
        myViewHolder.textViewDesc.setText(notes.get(i).getDesc());
        myViewHolder.textViewTag.setText(tag);
        if(style.equals("Dark")) {
            myViewHolder.textViewSymbol.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_dark));
        }
        else {
            myViewHolder.textViewSymbol.setBackground(ContextCompat.getDrawable(context, R.drawable.circle));
        }
        switch (tag) {

            case "Personal":
                myViewHolder.textViewSymbol.setText("P");
                break;
            case "Professional":
                myViewHolder.textViewSymbol.setText("Pr");
                break;
            case "Idea":
                myViewHolder.textViewSymbol.setText("I");
                break;
            case "Important":
                myViewHolder.textViewSymbol.setText("Im");
                break;
        }
        if(!myViewHolder.notes.getText().toString().equals(notes.get(i).getNote())) {
            myViewHolder.notes.append("...");
        }
        myViewHolder.noteContatiner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.i("LOGGG", Integer.toString(j));
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if(style.equals("Light")) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    }
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                new DBManager(context).delete(notes.get(j).getPosition());
                                notes.remove(j);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });

        myViewHolder.noteContatiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("position", notes.get(j).getPosition());
                Fragment fragment = new ViewNoteFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
    public int getItemCount() {

        return notes.size();
    }
}
