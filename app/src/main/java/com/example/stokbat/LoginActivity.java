package com.example.stokbat;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.loginButton);

        TextView registerTextView = findViewById(R.id.register);
        TextView tcTextView = findViewById(R.id.tc);

        SpannableString span1 = new SpannableString(registerTextView.getText());
        SpannableString span2 = new SpannableString(tcTextView.getText());

        ForegroundColorSpan yellowColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.buttonn));
        UnderlineSpan underlineSpan = new UnderlineSpan();

        span1.setSpan(yellowColorSpan, 18, span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(yellowColorSpan, 45, 65, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        span1.setSpan(underlineSpan, 18, span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(underlineSpan, 45, 65, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        registerTextView.setText(span1);
        tcTextView.setText(span2);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.equals("bonaventura@gmail.com") && password.equals("215150401111051")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
