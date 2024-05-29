package ntu.dinhvu61131562.instagramclone.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;

import ntu.dinhvu61131562.instagramclone.BinhLuanActivity;
import ntu.dinhvu61131562.instagramclone.Fragment.PostDetailFragment;
import ntu.dinhvu61131562.instagramclone.Fragment.ProfileFragment;
import ntu.dinhvu61131562.instagramclone.Model.Post;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.NguoiTheoDoiActivity;
import ntu.dinhvu61131562.instagramclone.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post> mPost;
    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.ViewHolder holder, final int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post = mPost.get(position);

        if (post == null) {
            holder.post_image.setImageResource(R.drawable.placeholder);
            holder.moTa.setVisibility(View.GONE);
            return;
        }

        Glide.with(mContext).load(post.getPostImage())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(holder.post_image);

        if (post.getMoTa().equals("")){
            holder.moTa.setVisibility(View.GONE);
        } else {
            holder.moTa.setVisibility(View.VISIBLE);
            holder.moTa.setText(post.getMoTa());
        }

        nguoiDangInfo(holder.image_profile, holder.taiKhoan, holder.nguoiDang, post.getNguoiDang());
        daThich(post.getPostId(), holder.like);
        nrLikes(holder.likes, post.getPostId());
        getCmts(post.getPostId(), holder.cmts);
        daLuu(post.getPostId(), holder.save);

        holder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                                Context.MODE_PRIVATE).edit();
                editor.putString("profileId", post.getNguoiDang());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_contain, new ProfileFragment()).commit();
            }
        });

        holder.taiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                        Context.MODE_PRIVATE).edit();
                editor.putString("profileId", post.getNguoiDang());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_contain, new ProfileFragment()).commit();
            }
        });

        holder.nguoiDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                        Context.MODE_PRIVATE).edit();
                editor.putString("profileId", post.getNguoiDang());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_contain, new ProfileFragment()).commit();
            }
        });

        holder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                        Context.MODE_PRIVATE).edit();
                editor.putString("postId", post.getPostId());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_contain, new PostDetailFragment()).commit();
            }
        });

        // Thiết lập sự kiện lưu/huỷ lưu bài đăng
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.save.getTag().equals("Lưu")){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Lưu")
                            .child(firebaseUser.getUid())
                            .child(post.getPostId()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Lưu")
                            .child(firebaseUser.getUid())
                            .child(post.getPostId()).removeValue();
                }
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.like.getTag().equals("Thích")){
                    FirebaseDatabase.getInstance().getReference().child("Likes")
                            .child(post.getPostId())
                            .child(firebaseUser.getUid()).setValue(true);
                    themThongBao(post.getNguoiDang(), post.getPostId());
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes")
                            .child(post.getPostId())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BinhLuanActivity.class);
                intent.putExtra("postId", post.getPostId());
                intent.putExtra("idNguoiDang", post.getNguoiDang());
                mContext.startActivity(intent);
            }
        });

        holder.cmts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BinhLuanActivity.class);
                intent.putExtra("postId", post.getPostId());
                intent.putExtra("idNguoiDang", post.getNguoiDang());
                mContext.startActivity(intent);
            }
        });

        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NguoiTheoDoiActivity.class);
                intent.putExtra("id", post.getPostId());
                intent.putExtra("Tiêu Đề", "Lượt Thích");
                mContext.startActivity(intent);
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.sua:
                                editPost(post.getPostId());
                                return true;
                            case R.id.xoa:
                                final String id = post.getPostId();
                                FirebaseDatabase.getInstance().getReference("Posts")
                                        .child(post.getPostId()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    StorageReference xoaAnh = FirebaseStorage.getInstance()
                                                                    .getReferenceFromUrl(post.getPostImage());
                                                    xoaAnh.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                xoaThongBao(id, firebaseUser.getUid());
                                                                mPost.remove(position);
                                                                notifyItemRemoved(position);
                                                                notifyItemRangeChanged(position, mPost.size());
                                                                Toast.makeText(mContext, "Đã Xóa!", Toast.LENGTH_SHORT)
                                                                        .show();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                return true;
                            case R.id.baoCao:
                                Toast.makeText(mContext, "Đã Báo Cáo!", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.inflate(R.menu.post_menu);
                if (!post.getNguoiDang().equals(firebaseUser.getUid())){
                    popupMenu.getMenu().findItem(R.id.sua).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.xoa).setVisible(false);
                }
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_profile, post_image, like, cmt, save, more;
        public TextView taiKhoan, likes, nguoiDang, moTa, cmts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            cmt = itemView.findViewById(R.id.cmt);
            save = itemView.findViewById(R.id.save);
            taiKhoan = itemView.findViewById(R.id.taiKhoan);
            likes = itemView.findViewById(R.id.likes);
            nguoiDang = itemView.findViewById(R.id.nguoiDang);
            moTa = itemView.findViewById(R.id.moTa);
            cmts = itemView.findViewById(R.id.cmts);
            more = itemView.findViewById(R.id.more);

        }
    }

    private void getCmts(String postId, final TextView Cmts){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Cmts")
                .child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cmts.setText("Xem tất cả " +snapshot.getChildrenCount() + " bình luận");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void xoaThongBao(final String postId, String userId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Thông Báo")
                .child(userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("postId").getValue().equals(postId)){
                        dataSnapshot.getRef().removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(mContext, "Đã Xóa!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void daThich(String postId, ImageView imageView){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("Đã Thích");
                } else {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("Thích");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void themThongBao(String userId, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Thông Báo")
                .child(userId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", firebaseUser.getUid());
        hashMap.put("text", "đã thích bài của bạn");
        hashMap.put("postId", postId);
        hashMap.put("daDang", true);

        reference.push().setValue(hashMap);
    }

    private void nrLikes(final TextView likes, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount()+" lượt thích");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void nguoiDangInfo(final ImageView image_profile, final TextView taiKhoan
            , final TextView nguoiDang, final String userId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tài Khoản")
                .child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageUrl()).into(image_profile);
                taiKhoan.setText(user.getTaiKhoan());
                nguoiDang.setText(user.getTaiKhoan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // Kiểm tra trạng thái lưu bài đăng
    private void daLuu(String postId, ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Lưu")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postId).exists()){
                    imageView.setImageResource(R.drawable.ic_save_black);
                    imageView.setTag("Đã Lưu");
                } else {
                    imageView.setImageResource(R.drawable.ic_savee_black);
                    imageView.setTag("Lưu");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void editPost(String postId){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Sửa Bài");

        EditText editText = new EditText(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);

        getText(postId, editText);

        alertDialog.setPositiveButton("Sửa",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("moTa", editText.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Posts")
                                .child(postId).updateChildren(hashMap);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private void getText(String postId, EditText editText){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts")
                .child(postId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                editText.setText(snapshot.getValue(Post.class).getMoTa());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

