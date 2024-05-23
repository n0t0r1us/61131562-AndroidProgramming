package ntu.dinhvu61131562.instagramclone.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ntu.dinhvu61131562.instagramclone.Model.Post;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.R;
public class ProfileFragment extends Fragment {
    ImageView image_profile, options;
    TextView posts, nguoiTheoDoi, dangTheoDoi, hoTen, bio, taiKhoan;
    Button edit_profile;
    FirebaseUser firebaseUser;
    String profileId;
    ImageButton myFotos, savedFotos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileId = prefs.getString("profileId",FirebaseAuth.getInstance().getCurrentUser().getUid());

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

        userInfo();
        getFollowers();
        getNrPosts();

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
                } else if (btn.equals("Theo Dõi")){
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(firebaseUser.getUid()).child("Đang Theo Dõi")
                            .child(profileId).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(profileId).child("Người Theo Dõi")
                            .child(firebaseUser.getUid()).setValue(true);
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

        return view;
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
}