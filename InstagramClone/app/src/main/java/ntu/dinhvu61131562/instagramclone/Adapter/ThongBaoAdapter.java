package ntu.dinhvu61131562.instagramclone.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ntu.dinhvu61131562.instagramclone.Fragment.PostDetailFragment;
import ntu.dinhvu61131562.instagramclone.Fragment.ProfileFragment;
import ntu.dinhvu61131562.instagramclone.Model.Post;
import ntu.dinhvu61131562.instagramclone.Model.ThongBao;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.R;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {

    private Context mContext;
    private List<ThongBao> mThongBao;

    public ThongBaoAdapter(Context mContext, List<ThongBao> mThongBao) {
        this.mContext = mContext;
        this.mThongBao = mThongBao;
    }

    @NonNull
    @Override
    public ThongBaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.thongbao_item , parent,
                false);
        return new ThongBaoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThongBaoAdapter.ViewHolder holder, final int position) {
        final ThongBao thongBao = mThongBao.get(position);

        holder.text.setText(thongBao.getText());

        getUserInfo(holder.image_profile, holder.taiKhoan, thongBao.getUserId());
        if (thongBao.isDaDang()){
            holder.post_image.setVisibility(View.VISIBLE);
            getPostImage(holder.post_image, thongBao.getPostId());
        } else {
            holder.post_image.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thongBao.isDaDang()){
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                            Context.MODE_PRIVATE).edit();
                    editor.putString("postId", thongBao.getPostId());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_contain, new PostDetailFragment()).commit();
                } else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                            Context.MODE_PRIVATE).edit();
                    editor.putString("profileId", thongBao.getUserId());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_contain, new ProfileFragment()).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mThongBao.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, post_image;
        public TextView taiKhoan, text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            taiKhoan = itemView.findViewById(R.id.taiKhoan);
            text = itemView.findViewById(R.id.cmt);
        }
    }
    private void getUserInfo(final ImageView imageView, final TextView taiKhoan, String idNguoiDang){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tài Khoản")
                .child(idNguoiDang);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageUrl()).into(imageView);
                taiKhoan.setText(user.getTaiKhoan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getPostImage(ImageView imageView, final String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts")
                .child(postId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                Glide.with(mContext).load(post.getPostImage()).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
