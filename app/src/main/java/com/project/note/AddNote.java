package com.project.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    EditText etTitle, etDesc;
    NumberPicker numberPicker;

    public static final String Ex_Id = "id";
    public static final String Ex_Title = "title";
    public static final String Ex_Desc = "desc";
    public static final String Ex_Pri = "pri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        numberPicker = findViewById(R.id.number_picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(Ex_Id)){
            setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(Ex_Title));
            etDesc.setText(intent.getStringExtra(Ex_Desc));
            numberPicker.setValue(intent.getIntExtra(Ex_Pri,1));
        }else {
            setTitle("Add Note");
        }

    }

    public void savedNote(){

        String Title = etTitle.getText().toString();
        String Desc = etDesc.getText().toString();
        int Priority = numberPicker.getValue();

        if(Title.trim().isEmpty() || Desc.trim().isEmpty()){
            Toast.makeText(AddNote.this, "Title & Desc cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Ex_Title,Title);
        data.putExtra(Ex_Desc,Desc);
        data.putExtra(Ex_Pri,Priority);
        int id = getIntent().getIntExtra(Ex_Id,-1);
        if(id != -1){
            data.putExtra(Ex_Id,id);
        }
        setResult(RESULT_OK,data);
        finish();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                savedNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
