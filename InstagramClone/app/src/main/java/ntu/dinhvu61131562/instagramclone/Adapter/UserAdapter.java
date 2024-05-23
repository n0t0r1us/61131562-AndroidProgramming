package ntu.dinhvu61131562.instagramclone.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;
import ntu.dinhvu61131562.instagramclone.Fragment.ProfileFragment;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context mContext;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = mUsers.get(position);
        holder.btn_TheoDoi.setVisibility(View.VISIBLE);
        holder.taiKhoan.setText(user.getTaiKhoan());
        holder.hoTen.setText(user.getHoTen());
        Glide.with(mContext).load(user.getImageUrl()).into(holder.image_profile);
        dangTheoDoi(user.getId(),holder.btn_TheoDoi);
        if(user.getId().equals(firebaseUser.getUid())){
            holder.btn_TheoDoi.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",
                        Context.MODE_PRIVATE).edit();
                editor.putString("profileId", user.getId());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_contain,
                        new ProfileFragment()).commit();
            }
        });

        holder.btn_TheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btn_TheoDoi.getText().toString().equals("Theo Dõi")){
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(firebaseUser.getUid()).child("Đang Theo Dõi")
                            .child(user.getId()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(user.getId()).child("Người Theo Dõi")
                            .child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(firebaseUser.getUid()).child("Đang Theo Dõi")
                            .child(user.getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Theo Dõi")
                            .child(user.getId()).child("Người Theo Dõi")
                            .child(firebaseUser.getUid()).removeValue();

                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView taiKhoan;
        public TextView hoTen;
        public CircleImageView image_profile;
        public Button btn_TheoDoi;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            taiKhoan = itemView.findViewById(R.id.taiKhoan);
            hoTen = itemView.findViewById(R.id.hoTen);
            image_profile = itemView.findViewById(R.id.image_profile);
            btn_TheoDoi = itemView.findViewById(R.id.btn_TheoDoi);
        }
    }

    private void dangTheoDoi(String userId, Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Theo Dõi").child(firebaseUser.getUid()).child("Đang Theo Dõi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userId).exists()){
                    button.setText("Đang Theo Dõi");
                } else {
                    button.setText("Theo Dõi");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
