package com.jnu.course_item.ui.book;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.course_item.R;

public class inputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent1=getIntent();
        int position= intent1.getIntExtra("position",0);
        EditText editName = findViewById(R.id.input_name);
        EditText editMessage = findViewById(R.id.input_message);
        EditText editPicture = findViewById(R.id.input_picture);
        String name= intent1.getStringExtra("name");
        Double message=intent1.getDoubleExtra("message",0);
        int picture=intent1.getIntExtra("picture",R.drawable.a5);
        if(null!=name){
            editName.setText(name);
            editMessage.setText(message.toString());
        }


        Button buttonOk=this.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("position",position);
                if(!TextUtils.isEmpty(editName.getText().toString())) {
                    intent.putExtra("name", editName.getText().toString());
                }
                if(!TextUtils.isEmpty(editMessage.getText().toString())){
                    intent.putExtra("message", Double.parseDouble(editMessage.getText().toString()));
                }
                if(!TextUtils.isEmpty(editPicture.getText().toString())) {
                    int p = Integer.parseInt(editPicture.getText().toString());
                    if (p > 0 && p < 5) {
                        intent.putExtra("picture", R.drawable.a1 - 1 + p);
                    }
                    else {
                        intent.putExtra("picture", picture);
                    }
                }
                if(TextUtils.isEmpty(editPicture.getText().toString())) {
                    intent.putExtra("picture", picture);
                }
                setResult(BookItem.result_Code_add,intent);
                inputActivity.this.finish();
            }
        });
        Button buttonCancel=this.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                Toast.makeText(inputActivity.this, "操作已取消", Toast.LENGTH_LONG).show();
                inputActivity.this.finish();
            }
        });
    }
}