package ntu.dinhvu61131562.instagramclone.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ntu.dinhvu61131562.instagramclone.Adapter.MyFotoAdapter;
import ntu.dinhvu61131562.instagramclone.EditProfileActivity;
import ntu.dinhvu61131562.instagramclone.Model.Post;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.NguoiTheoDoiActivity;
import ntu.dinhvu61131562.instagramclone.OptionsActivity;
import ntu.dinhvu61131562.instagramclone.R;
public class ProfileFragment extends Fragment {
    ImageView image_profile, options;
    TextView posts, nguoiTheoDoi, dangTheoDoi, hoTen, bio, taiKhoan;
    Button edit_profile;

    private List<String> mySaves;

    RecyclerView recyclerView_saves;
    MyFotoAdapter myFotoAdapter_saves;
    List<Post> postList_saves;

    RecyclerView recyclerView;
    MyFotoAdapter myFotoAdapter;
    List<Post> postList;
    FirebaseUser firebaseUser;
    String profileId;
    ImageButton myFotos, savedFotos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileId = prefs.getString("profileId","none");

        image_profile = view.findViewById(R.id.image_profile);
        options = view.findViewById(R.id.options);
        posts = view.findViewById(R.id.posts);
        nguoiTheoDoi = view.findViewById(R.id.nguoiTheoDoi);
        dangTheoDoi = view.findViewById(R.id.dangTheoDoi);
        hoTen = view.findViewById(R.id.hoTen);
        bio = view.findViewById(R.id.bio);
        taiKhoan = view.findViewById(R.id.taiKhoan);
        edit_profile = view.findViewById(R.id.edit_profile);
        myFotos = view.findViewById(R.id.myFotos);
        savedFotos = view.findViewById(R.id.savedFotos);
        // Thiết lập RecyclerView cho bài đăng đã lưu
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getContext(), postList);
        recyclerView.setAdapter(myFotoAdapter);

        recyclerView_saves = view.findViewById(R.id.recycle_view_save);
        recyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_saves = new GridLayoutManager(getContext(), 3);
        recyclerView_saves.setLayoutManager(linearLayoutManager_saves);
        postList_saves = new ArrayList<>();
        myFotoAdapter_saves = new MyFotoAdapter(getContext(), postList_saves);
        recyclerView_saves.setAdapter(myFotoAdapter_saves);
        // Hiển thị RecyclerView của bài đăng cá nhân
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView_saves.setVisibility(View.GONE);

        userInfo();
        getFollowers();
        getNrPosts();
        myFotos();
        mySaves();

        if (profileId.equals(firebaseUser.getUid())){
            edit_profile.setText("Sửa Hồ Sơ");
        } else {
            checkFollow();
            savedFotos.setVisibility(View.GONE);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = edit_profile.getText().toString();

                if (btn.equals("Sửa Hồ Sơ")){
                    // đi đến EditProfile
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                } else if (btn.equals("Theo Dõi")){
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(firebaseUser.getUid()).child("Đang Theo Dõi")
                            .child(profileId).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(profileId).child("Người Theo Dõi")
                            .child(firebaseUser.getUid()).setValue(true);
                    themThongBao();
                } else if (btn.equals("Đang Theo Dõi")) {
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(firebaseUser.getUid()).child("Đang Theo Dõi")
                            .child(profileId).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(profileId).child("Người Theo Dõi")
                            .child(firebaseUser.getUid()).removeValue();

                }
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OptionsActivity.class);
                startActivity(intent);
            }
        });
        // Chuyển đổi giữa hiển thị bài đăng cá nhân và bài đăng đã lưu
        myFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_saves.setVisibility(View.GONE);
            }
        });

        savedFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_saves.setVisibility(View.VISIBLE);
            }
        });

        nguoiTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NguoiTheoDoiActivity.class);
                intent.putExtra("id", profileId);
                intent.putExtra("Tiêu Đề", "Người Theo Dõi");
                startActivity(intent);
            }
        });

        dangTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NguoiTheoDoiActivity.class);
                intent.putExtra("id", profileId);
                intent.putExtra("Tiêu Đề", "Đang Theo Dõi");
                startActivity(intent);
            }
        });

        return view;
    }

    private void themThongBao(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Thông Báo")
                .child(profileId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", firebaseUser.getUid());
        hashMap.put("text", "đã theo dõi bạn");
        hashMap.put("postId", "");
        hashMap.put("daDang", false);

        reference.push().setValue(hashMap);
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tài Khoản")
                .child(profileId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null ){
                    return;
                }
                User user = snapshot.getValue(User.class);

                    Glide.with(getContext()).load(user.getImageUrl()).into(image_profile);
                    taiKhoan.setText(user.getTaiKhoan());
                    hoTen.setText(user.getHoTen());
                    bio.setText(user.getBio());
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkFollow(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Theo Dõi").child(firebaseUser.getUid())
                .child("Đang Theo Dõi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(profileId).exists()){
                    edit_profile.setText("Đang Theo Dõi");
                } else {
                    edit_profile.setText("Theo Dõi");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getFollowers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Theo Dõi")
                .child(profileId)
                .child("Người Theo Dõi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nguoiTheoDoi.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
                .child("Theo Dõi")
                .child(profileId)
                .child("Đang Theo Dõi");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dangTheoDoi.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getNguoiDang().equals(profileId)){
                        i++;
                    }
                }
                posts.setText(""+i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void myFotos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getNguoiDang().equals(profileId)){
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // Lấy danh sách bài đăng đã lưu
    private void mySaves(){
        mySaves = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lưu")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    mySaves.add(dataSnapshot.getKey());
                }

                readSaves();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // Đọc bài đăng đã lưu từ Firebase
    private void readSaves(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList_saves.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);

                    for (String id : mySaves){
                        if (post.getPostId().equals(id)){
                            postList_saves.add(post);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}