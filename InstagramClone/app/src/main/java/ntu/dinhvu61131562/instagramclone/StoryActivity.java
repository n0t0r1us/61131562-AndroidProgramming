package ntu.dinhvu61131562.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import ntu.dinhvu61131562.instagramclone.Model.Story;
import ntu.dinhvu61131562.instagramclone.Model.User;

public class StoryActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    int counter = 0;
    long pressTime = 0L;
    long limit = 500L;

    StoriesProgressView storiesProgressView;
    ImageView image, story_photo;
    TextView story_taiKhoan;

    LinearLayout r_daXem;
    TextView soNguoiDaXem;
    ImageView story_xoa;

    List<String> images;
    List<String> storyIds;
    String userId;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        r_daXem = findViewById(R.id.r_daXem);
        soNguoiDaXem = findViewById(R.id.soNguoiDaXem);
        story_xoa = findViewById(R.id.story_xoa);

        storiesProgressView = findViewById(R.id.stories);
        image = findViewById(R.id.image);
        story_photo = findViewById(R.id.story_photo);
        story_taiKhoan = findViewById(R.id.story_taiKhoan);

        r_daXem.setVisibility(View.GONE);
        story_xoa.setVisibility(View.GONE);

        userId = getIntent().getStringExtra("userId");

        if (userId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            r_daXem.setVisibility(View.VISIBLE);
            story_xoa.setVisibility(View.VISIBLE);
        }

        getStories(userId);
        userInfo(userId);

        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);

        r_daXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoryActivity.this, NguoiTheoDoiActivity.class);
                intent.putExtra("id", userId);
                intent.putExtra("storyId", storyIds.get(counter));
                intent.putExtra("Tiêu Đề", "Lượt Xem");
                startActivity(intent);
            }
        });

        story_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story")
                        .child(userId).child(storyIds.get(counter));
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(StoryActivity.this, "Đã Xóa!", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onNext() {
        Glide.with(getApplicationContext()).load(images.get(++counter)).into(image);

        themLuotXem(storyIds.get(counter));
        soNguoiDaXem(storyIds.get(counter));

    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        Glide.with(getApplicationContext()).load(images.get(--counter)).into(image);

        soNguoiDaXem(storyIds.get(counter));

    }

    @Override
    public void onComplete() {
        finish();

    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        storiesProgressView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        storiesProgressView.resume();
        super.onResume();
    }

    private void getStories(String userId){
        images = new ArrayList<>();
        storyIds = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story")
                .child(userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                images.clear();
                storyIds.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Story story = dataSnapshot.getValue(Story.class);
                    long timeCurrent = System.currentTimeMillis();
                    if (timeCurrent > story.getTimeStart() && timeCurrent < story.getTimeEnd()){
                        images.add(story.getImageUrl());
                        storyIds.add(story.getStoryId());
                    }
                }
                storiesProgressView.setStoriesCount(images.size());
                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(StoryActivity.this);
                storiesProgressView.startStories(counter);

                Glide.with(getApplicationContext()).load(images.get(counter))
                        .into(image);

                themLuotXem(storyIds.get(counter));
                soNguoiDaXem(storyIds.get(counter));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void userInfo(String userId){
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Tài Khoản")
                .child(userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageUrl()).into(story_photo);
                story_taiKhoan.setText(user.getTaiKhoan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void themLuotXem(String storyId){
        FirebaseDatabase.getInstance().getReference("Story").child(userId)
                .child(storyId).child("Lượt Xem")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(true);
    }

    private void soNguoiDaXem(String storyId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story")
                .child(userId).child(storyId).child("Lượt Xem");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soNguoiDaXem.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}