package com.evlishelper.lwizapp.activities.notes;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evlishelper.lwizapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes;

    int getPosition() {
        return position;
    }

    private int position;

    NoteAdapter(List<Note> recipes) {
        this.notes = recipes;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, final int position) {
        holder.title.setText(notes.get(position).title);
        String text = notes.get(position).text;
        if (text.contains("\n"))
            holder.text.setText(notes.get(position).text.substring(0, text.indexOf("\n")));
        else
            holder.text.setText(notes.get(position).text);
        SimpleDateFormat formatter;
        Calendar dateNote = Calendar.getInstance();
        dateNote.setTime(new Date(notes.get(position).date));
        Calendar dateCurrent = Calendar.getInstance();
        dateCurrent.setTime(new Date(System.currentTimeMillis()));
        dateCurrent.get(Calendar.DAY_OF_YEAR);
        if (dateNote.get(Calendar.DAY_OF_YEAR) == dateCurrent.get(Calendar.DAY_OF_YEAR)
                && dateNote.get(Calendar.YEAR) == dateCurrent.get(Calendar.YEAR)) {
            formatter = new SimpleDateFormat("hh:mm", Locale.getDefault());
        } else if (dateNote.get(Calendar.YEAR) == dateCurrent.get(Calendar.YEAR)) {
            formatter = new SimpleDateFormat("dd MMM hh:mm", Locale.getDefault());
        } else {
            formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
        }
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF);
        border.setStroke(1, Color.parseColor("#B1BCBE"));
        border.setCornerRadius(10);
        holder.colorHolder.setBackground(border);
        holder.letter.getBackground().setColorFilter(notes.get(position).color, PorterDuff.Mode.SRC);
        if (notes.get(position).title.length() != 0) {
            holder.letter.setText(String.valueOf(Character.toUpperCase(notes.get(position).title.charAt(0))));
        } else {
            holder.letter.setText("");
        }

        holder.date.setText(formatter.format(notes.get(position).date));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            private NoteHolder anonHolder;

            @Override
            public boolean onLongClick(View v) {
                NoteAdapter.this.position = anonHolder.getAdapterPosition();
                return false;
            }

            private View.OnLongClickListener init(NoteHolder var) {
                anonHolder = var;
                return this;
            }
        }.init(holder));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView title;
        TextView text;
        TextView date;
        TextView letter;
        RelativeLayout colorHolder;

        NoteHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.noteTitle);
            text = (TextView) itemView.findViewById(R.id.noteText);
            date = (TextView) itemView.findViewById(R.id.noteDate);
            letter = (TextView) itemView.findViewById(R.id.letter);
            colorHolder = (RelativeLayout) itemView.findViewById(R.id.noteHolder);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, 0, 0, "Edit");
            menu.add(Menu.NONE, 1, 1, "Delete");
        }
    }
}