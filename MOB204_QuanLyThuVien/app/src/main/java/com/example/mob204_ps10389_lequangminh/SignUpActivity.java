package com.example.mob204_ps10389_lequangminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText edtEmail,edtMatKhauDK;
    Button btnDangKy;
    //firebase
    FirebaseAuth mAuthencation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhxa();
        mAuthencation = FirebaseAuth.getInstance();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKy();
            }
        });
    }
    private  void  DangKy(){

        final String email = edtEmail.getText().toString().trim();
        final String pass = edtMatKhauDK.getText().toString().trim();
        mAuthencation.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                            //dua ve man hinh dang nhap
                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this,"Đăng ký không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void anhxa(){
        edtEmail = findViewById(R.id.edtTaiKhoanDangKy);
        edtMatKhauDK = findViewById(R.id.edtNhapMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
    }
}
