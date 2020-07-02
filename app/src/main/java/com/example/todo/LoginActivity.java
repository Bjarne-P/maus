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
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    boolean emailFlag;
    boolean passwordFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText editEmail = findViewById(R.id.editEmail);
        final EditText editPassword = findViewById(R.id.editPassword);
        final Button btnLogin = findViewById(R.id.BtnLogin);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editEmail.getText().toString().equals("") && !editPassword.getText().toString().equals(""))
                    btnLogin.setEnabled(true);
                else
                    btnLogin.setEnabled(false);
            }
        };

        editEmail.addTextChangedListener(textWatcher);
        editPassword.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()) {
                    editEmail.setError("Please Enter a valid email address");
                } else if (editPassword.length() < 6) {
                    editPassword.setError("Your Password must be 6 digits long!");

                } else startActivity(new Intent(LoginActivity.this, TodoListActivity.class));
            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editEmail != null)
                    emailFlag = true;
                else emailFlag = false;

                if (passwordFlag && emailFlag)
                    l.setEnabled(true);
                else l.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editPassword != null)
                    passwordFlag = true;
                else passwordFlag = false;

                if (passwordFlag && emailFlag)
                    l.setEnabled(true);
                else l.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}