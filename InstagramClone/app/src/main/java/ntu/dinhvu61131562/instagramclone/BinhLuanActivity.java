package ntu.dinhvu61131562.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ntu.dinhvu61131562.instagramclone.Adapter.CmtAdapter;
import ntu.dinhvu61131562.instagramclone.Model.Cmt;
import ntu.dinhvu61131562.instagramclone.Model.User;

public class BinhLuanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CmtAdapter cmtAdapter;
    private List<Cmt> cmtList;
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
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        idNguoiDang = intent.getStringExtra("idNguoiDang");

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        cmtList = new ArrayList<>();
        cmtAdapter = new CmtAdapter(this, cmtList, postId);
        recyclerView.setAdapter(cmtAdapter);

        themBinhLuan = findViewById(R.id.addCmt);
        image_profile = findViewById(R.id.image_profile);
        post = findViewById(R.id.post);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



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
        docBinhLuan();
    }

    private void themBinhLuan(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cmts")
                .child(postId);

        String cmtId = reference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Cmt", themBinhLuan.getText().toString());
        hashMap.put("nguoiDang", firebaseUser.getUid());
        hashMap.put("cmtId", cmtId);

        reference.child(cmtId).setValue(hashMap);
        themThongBao();
        themBinhLuan.setText("");
    }
    private void themThongBao(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Thông Báo")
                .child(idNguoiDang);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", firebaseUser.getUid());
        hashMap.put("text", "đã bình luận: "+themBinhLuan.getText().toString());
        hashMap.put("postId", postId);
        hashMap.put("daDang", true);

        reference.push().setValue(hashMap);
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
    private void docBinhLuan(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cmts")
                .child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cmtList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Cmt cmt = dataSnapshot.getValue(Cmt.class);
                    cmtList.add(cmt);
                }
                cmtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}