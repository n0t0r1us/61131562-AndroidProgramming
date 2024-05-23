package ntu.dinhvu61131562.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ntu.dinhvu61131562.instagramclone.Model.User;

public class BinhLuanActivity extends AppCompatActivity {
    EditText themBinhLuan;
    ImageView image_profile;
    TextView post;

    String postId;
    String idNguoiDang;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binhluan);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bình Luận");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        themBinhLuan = findViewById(R.id.addCmt);
        image_profile = findViewById(R.id.image_profile);
        post = findViewById(R.id.post);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        idNguoiDang = intent.getStringExtra("idNguoiDang");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (themBinhLuan.getText().toString().equals("")){
                    Toast.makeText(BinhLuanActivity.this, "Bạn không thể gửi bình luận trống!", Toast.LENGTH_SHORT).show();
                } else {
                    themBinhLuan();
                }
            }
        });

        getImage();
    }

    private void themBinhLuan(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cmts")
                .child(postId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Cmt", themBinhLuan.getText().toString());
        hashMap.put("nguoiDang", firebaseUser.getUid());

        reference.push().setValue(hashMap);
        themBinhLuan.setText("");
    }

    private void getImage(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tài Khoản")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageUrl()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}