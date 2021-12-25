 package com.jnu.course_item.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jnu.course_item.BookListMainActivity;
import com.jnu.course_item.R;

 public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);

        EditText editTextName=findViewById(R.id.edit_text_name);
        String name=intent.getStringExtra("name");
        if(null!=name){
            editTextName.setText(name);
        }

        Button buttonOk=this.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();

                intent.putExtra("position",position);
                intent.putExtra("name",editTextName.getText().toString());
                setResult(BookListMainActivity.RESULT_CODE_ADD_DATA,intent);
                InputActivity.this.finish();
            }
        });
    }
}