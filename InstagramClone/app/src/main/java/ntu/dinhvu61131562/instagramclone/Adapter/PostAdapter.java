package ntu.dinhvu61131562.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ntu.dinhvu61131562.instagramclone.Model.Post;
import ntu.dinhvu61131562.instagramclone.Model.User;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);

        Glide.with(mContext).load(post.getPostImage()).into(holder.post_image);

        if (post.getMoTa().equals("")){
            holder.moTa.setVisibility(View.GONE);
        } else {
            holder.moTa.setVisibility(View.VISIBLE);
            holder.moTa.setText(post.getMoTa());
        }

        nguoiDangInfo(holder.image_profile, holder.taiKhoan, holder.nguoiDang, post.getNguoiDang());

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_profile, post_image, like, cmt, save;
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

        }
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

}

