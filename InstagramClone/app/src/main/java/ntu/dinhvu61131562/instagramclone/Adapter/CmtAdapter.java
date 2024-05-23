package ntu.dinhvu61131562.instagramclone.Adapter;

import android.content.Context;
import android.content.Intent;
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

import ntu.dinhvu61131562.instagramclone.MainActivity;
import ntu.dinhvu61131562.instagramclone.Model.Cmt;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.R;

public class CmtAdapter extends RecyclerView.Adapter<CmtAdapter.ViewHolder> {

    private Context mContext;
    private List<Cmt> mCmt;

    private FirebaseUser firebaseUser;

    public CmtAdapter(Context mContext, List<Cmt> mCmt) {
        this.mContext = mContext;
        this.mCmt = mCmt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cmt_item, parent, false);
        return new CmtAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Cmt cmt = mCmt.get(position);

        holder.cmt.setText(cmt.getCmt());
        getUserInfo(holder.image_profile, holder.taiKhoan, cmt.getNguoiDang());

        holder.cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("idNguoiDang", cmt.getNguoiDang());
                mContext.startActivity(intent);
            }
        });

        holder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("idNguoiDang", cmt.getNguoiDang());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCmt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile;
        public TextView taiKhoan, cmt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            taiKhoan = itemView.findViewById(R.id.taiKhoan);
            cmt = itemView.findViewById(R.id.cmt);
        }
    }

    private void getUserInfo(final ImageView imageView, final TextView taiKhoan, String idNguoiDang){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Tài Khoản")
                .child(idNguoiDang);
        reference.addValueEventListener(new ValueEventListener() {
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
}
