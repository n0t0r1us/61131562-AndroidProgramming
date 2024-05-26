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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class DangKyActivity extends AppCompatActivity {
    EditText taiKhoan, hoTen, email, matKhau;
    Button dangKy;
    TextView txtDn;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        //Ánh xạ các thành phần giao diện
        taiKhoan = findViewById(R.id.taiKhoan);
        hoTen = findViewById(R.id.hoTen);
        email = findViewById(R.id.email);
        matKhau = findViewById(R.id.matKhau);
        dangKy = findViewById(R.id.dangKy);
        txtDn = findViewById(R.id.txt_Dn);
        // Khởi tạo FirebaseAuth
        auth = FirebaseAuth.getInstance();
        // Thiết lập sự kiện click cho TextView chuyển đến màn hình đăng nhập
        txtDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
            }
        });
        // Thiết lập sự kiện click cho nút đăng ký
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị ProgressDialog
                pd = new ProgressDialog(DangKyActivity.this);
                pd.setMessage("Vui lòng đợi...");
                pd.show();
                // Lấy giá trị từ các trường nhập liệu
                String str_taiKhoan = taiKhoan.getText().toString();
                String str_hoTen = hoTen.getText().toString();
                String str_email = email.getText().toString();
                String str_matKhau = matKhau.getText().toString();
                // Kiểm tra các trường nhập liệu
                if (TextUtils.isEmpty(str_taiKhoan) || TextUtils.isEmpty(str_hoTen)
                        || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_matKhau)){
                    Toast.makeText(DangKyActivity.this, "Không được bỏ trống!",
                            Toast.LENGTH_SHORT).show();
                }else if (str_matKhau.length() <6){
                    Toast.makeText(DangKyActivity.this, "Mật khẩu phải hơn 6 ký tự!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    dangKy(str_taiKhoan, str_hoTen, str_email, str_matKhau);

                }
            }
        });

    }
    private void dangKy(String taiKhoan, String hoTen, String email, String matKhau){
        auth.createUserWithEmailAndPassword(email, matKhau)
                .addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String taiKhoanId = firebaseUser.getUid();
                            // Tham chiếu tới Firebase Realtime Database
                            reference = FirebaseDatabase.getInstance().getReference()
                                    .child("Tài Khoản").child(taiKhoanId);
                            // Tạo một HashMap để lưu thông tin người dùng
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", taiKhoanId);
                            hashMap.put("taiKhoan", taiKhoan.toLowerCase());
                            hashMap.put("hoTen", hoTen);
                            hashMap.put("bio", "");
                            hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/instagramclone-69696.appspot.com/o/placeholder.png?alt=media&token=b7d200f6-6f24-4a91-87a8-cd10fee1a194");
                            // Lưu thông tin người dùng vào Firebase Realtime Database
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(DangKyActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(DangKyActivity.this, "Bạn không thể đăng ký bằng email hoặc mật khẩu này!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}