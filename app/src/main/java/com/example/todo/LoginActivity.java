package com.example.todo;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Button l = findViewById(R.id.BtnLogin);
        final EditText editPassword = findViewById(R.id.editPassword);
        final EditText editEmail = findViewById(R.id.editEmail);


        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPassword.length() < 8){
                    editPassword.setError("Your Password must be 8 digits long!");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()) {
                    editEmail.setError("Please Enter a valid email address");
                } else startActivity(new Intent(LoginActivity.this, TodoListActivity.class));
            }
        });
    }

}
