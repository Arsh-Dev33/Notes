package com.project.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    final NoteRecylerViewAdpater noteRecylerViewAdpater = new NoteRecylerViewAdpater();
     FloatingActionButton floatingActionButton;
    public static final int ADD_NOTE =1;
    public static  final int EDIT_NOTE =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_recylerview);
        floatingActionButton = findViewById(R.id.bt_float);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteRecylerViewAdpater);


        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {

                noteRecylerViewAdpater.submitList(notes);
               // Toast.makeText(MainActivity.this, "On Changed", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(MainActivity.this,AddNote.class);
                startActivityForResult(add,ADD_NOTE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notesViewModel.delete(noteRecylerViewAdpater.getNotesAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteRecylerViewAdpater.setOnItemClickListener(new NoteRecylerViewAdpater.OnItemClickListener() {
            @Override
            public void OnItemClick(Notes notes) {
                Intent intent = new Intent(MainActivity.this,AddNote.class);
                intent.putExtra(AddNote.Ex_Title,notes.getTitle());
                intent.putExtra(AddNote.Ex_Desc,notes.getDescription());
                intent.putExtra(AddNote.Ex_Pri,notes.getPriority());
                intent.putExtra(AddNote.Ex_Id,notes.getId());
                startActivityForResult(intent,EDIT_NOTE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE && resultCode == RESULT_OK){
            String Title = data.getStringExtra(AddNote.Ex_Title);
            String Desc = data.getStringExtra(AddNote.Ex_Desc);
            int pri = data.getIntExtra(AddNote.Ex_Pri,1);

            Notes notes = new Notes(Title,Desc,pri);
            notesViewModel.insert(notes);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddNote.Ex_Id,-1);
                    if(id == -1){
                        Toast.makeText(this, "Note cannot be Updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
            String Title = data.getStringExtra(AddNote.Ex_Title);
            String Desc = data.getStringExtra(AddNote.Ex_Desc);
            int pri = data.getIntExtra(AddNote.Ex_Pri,1);

            Notes notes = new Notes(Title,Desc,pri);
            notes.setId(id);
            notesViewModel.update(notes);
            Toast.makeText(this, "Note Edited", Toast.LENGTH_SHORT).show();

        }

        else{
            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_delete_all :
                notesViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
