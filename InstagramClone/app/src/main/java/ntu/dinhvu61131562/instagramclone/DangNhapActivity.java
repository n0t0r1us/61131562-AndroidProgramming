package ntu.dinhvu61131562.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhapActivity extends AppCompatActivity {
    EditText email, matKhau;
    Button dangNhap;
    TextView txt_Dk;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ánh xạ các thành phần giao diện
        setContentView(R.layout.activity_dangnhap);
        email = findViewById(R.id.email);
        dangNhap = findViewById(R.id.dangNhap);
        matKhau = findViewById(R.id.matKhau);
        txt_Dk = findViewById(R.id.txt_Dk);
        // Khởi tạo FirebaseAuth
        auth = FirebaseAuth.getInstance();
        // Thiết lập sự kiện click cho TextView chuyển đến màn hình đăng ký
        txt_Dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            }
        });
        // Thiết lập sự kiện click cho nút đăng nhập
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(DangNhapActivity.this);
                pd.setMessage("Vui lòng đợi...");
                pd.show();
                // Lấy giá trị từ các trường nhập liệu
                String str_email = email.getText().toString();
                String str_matKhau = matKhau.getText().toString();
                // Kiểm tra các trường nhập liệu
                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_matKhau)){
                    Toast.makeText(DangNhapActivity.this, "Không được bỏ trống!",
                            Toast.LENGTH_SHORT).show();

                }else {
                    // Đăng nhập với FirebaseAuth
                    auth.signInWithEmailAndPassword(str_email, str_matKhau)
                            .addOnCompleteListener(DangNhapActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                .getReference()
                                                .child("Tài Khoản")
                                                .child(auth.getCurrentUser().getUid());
                                        // Lắng nghe thay đổi trên DatabaseReference
                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                pd.dismiss();
                                                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                pd.dismiss();

                                            }
                                        });
                                    }else {
                                        pd.dismiss();
                                        Toast.makeText(DangNhapActivity.this,"Xác thực đã thất bại!",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }
            }
        });

    }
}