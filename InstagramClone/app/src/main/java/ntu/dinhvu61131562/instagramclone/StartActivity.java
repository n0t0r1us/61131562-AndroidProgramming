package ntu.dinhvu61131562.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button dangNhap, dangKy;
    FirebaseUser firebaseUser;  

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //chuyển hướng nếu user không null
        if(firebaseUser != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Ánh xạ các thành phần giao diện
        dangNhap = findViewById(R.id.dangNhap);
        dangKy = findViewById(R.id.dangKy);
        // Thiết lập sự kiện click cho nút đăng nhập
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, DangNhapActivity.class));
            }
        });
        // Thiết lập sự kiện click cho nút đăng ký
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, DangKyActivity.class));
            }
        });
    }
}