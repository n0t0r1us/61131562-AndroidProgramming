package ntu.dinhvu61131562.instagramclone.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ntu.dinhvu61131562.instagramclone.Model.Story;
import ntu.dinhvu61131562.instagramclone.Model.User;
import ntu.dinhvu61131562.instagramclone.R;
import ntu.dinhvu61131562.instagramclone.StoryActivity;
import ntu.dinhvu61131562.instagramclone.ThemStoryActivity;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private Context mContext;
    private List<Story> mStory;

    public StoryAdapter(Context mContext, List<Story> mStory) {
        this.mContext = mContext;
        this.mStory = mStory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(mContext).inflate(R.layout.them_story_item, parent,
                    false);
            return new StoryAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.story_item, parent,
                    false);
            return new StoryAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Story story = mStory.get(position);
        userInfo(holder, story.getUserId(), position);

        if (holder.getAdapterPosition() != 0) {
            storyDaXem(holder, story.getUserId());
        }

        if (holder.getAdapterPosition() == 0) {
            myStory(holder.themStoryText, holder.story_them, false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == 0){
                    myStory(holder.themStoryText, holder.story_them, true);
                } else {
                    Intent intent  = new Intent(mContext, StoryActivity.class);
                    intent.putExtra("userId", story.getUserId());
                    mContext.startActivity(intent);
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        return mStory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView story_photo, story_them, story_photo_daXem;
        public TextView story_taiKhoan, themStoryText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            story_photo = itemView.findViewById(R.id.story_photo);
            story_them = itemView.findViewById(R.id.story_them);
            story_photo_daXem = itemView.findViewById(R.id.story_photo_daXem);
            story_taiKhoan = itemView.findViewById(R.id.story_taiKhoan);
            themStoryText = itemView.findViewById(R.id.themStoryText);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }
        return 1;
    }

    private void userInfo(final ViewHolder viewHolder, String userId, int pos){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tài Khoản")
                .child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageUrl()).into(viewHolder.story_photo);
                if (pos != 0){
                    Glide.with(mContext).load(user.getImageUrl()).into(viewHolder.story_photo_daXem);
                    viewHolder.story_taiKhoan.setText(user.getTaiKhoan());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void myStory(TextView textView, ImageView imageView, final boolean click){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                long timeCurrent = System.currentTimeMillis();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Story story = dataSnapshot.getValue(Story.class);
                    if (timeCurrent > story.getTimeStart() && timeCurrent < story.getTimeEnd()){
                        count++;
                    }
                }

                if (click){
                    if (count > 0){
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Xem Story",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent  = new Intent(mContext, StoryActivity.class);
                                        intent.putExtra("userId", FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getUid());
                                        mContext.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Thêm Story",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(mContext, ThemStoryActivity.class);
                                        mContext.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        Intent intent = new Intent(mContext, ThemStoryActivity.class);
                        mContext.startActivity(intent);

                    }
                } else {
                    if (count > 0){
                        textView.setText("Story của tôi");
                        imageView.setVisibility(View.GONE);
                    } else {
                        textView.setText("Thêm Story");
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void storyDaXem(final ViewHolder viewHolder, String userId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story")
                .child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (!dataSnapshot.child("Lượt xem")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .exists() && System.currentTimeMillis() < dataSnapshot.getValue(Story.class)
                            .getTimeEnd()){
                        i++;
                    }
                }
                if (i > 0){
                    viewHolder.story_photo.setVisibility(View.VISIBLE);
                    viewHolder.story_photo_daXem.setVisibility(View.GONE);
                } else {
                    viewHolder.story_photo.setVisibility(View.GONE);
                    viewHolder.story_photo_daXem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
