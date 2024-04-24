package ntu.dinhvu61131562.muzicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chibde.visualizer.BarVisualizer;
import com.chibde.visualizer.SquareBarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button btnPlay, btnNext, btnPrev, btnFf, btnFr;
    SeekBar seekmusic;
    ImageView imageView;
    SquareBarVisualizer visualizer;
    TextView txtSname, txtSstart, txtSstop;
    String sName;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;
    Thread updateSeekBar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (visualizer !=null){
            visualizer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));
        btnPlay = findViewById(R.id.btnPlay);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnFf = findViewById(R.id.btnFF);
        btnFr = findViewById(R.id.btnFR);
        txtSname = findViewById(R.id.txtSN);
        txtSstart = findViewById(R.id.txtStarts);
        txtSstop = findViewById(R.id.txtStops);
        seekmusic = findViewById(R.id.seekBar);
        visualizer = findViewById(R.id.visualizer);
        imageView = findViewById(R.id.imageView);
        if (mediaPlayer !=null ){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("pos",0);
        txtSname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sName = mySongs.get(position).getName();
        txtSname.setText(sName);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekmusic.setProgress(currentPosition);
                    }catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seekmusic.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.MULTIPLY);
        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.SRC_IN);
        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        txtSstop.setText(endTime);
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtSstart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    btnPlay.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }else {
                    btnPlay.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }

            }
        });
        //next listener
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnNext.performClick();
            }
        });
        int audiosessionId = mediaPlayer.getAudioSessionId();
        if (audiosessionId != -1) {
            visualizer.setPlayer(mediaPlayer.getAudioSessionId());
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position+1)%mySongs.size();
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sName = mySongs.get(position).getName();
                txtSname.setText(sName);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);
                startAnimation(imageView);

                int audiosessionId = mediaPlayer.getAudioSessionId();
                if (audiosessionId != -1) {
                    visualizer.setPlayer(mediaPlayer.getAudioSessionId());
                }

            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(mySongs.size()-1):(position-1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sName = mySongs.get(position).getName();
                txtSname.setText(sName);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);
                startAnimation(imageView);
                int audiosessionId = mediaPlayer.getAudioSessionId();
                if (audiosessionId != -1) {
                    visualizer.setPlayer(mediaPlayer.getAudioSessionId());
                }
            }
        });
        btnFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                }
            }
        });
        btnFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                }
            }
        });
    }
    public void startAnimation(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f,360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }
    public String createTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;
        time+=min+":";
        if (sec<10){
            time+="0";
        }
        time+=sec;
        return time;
    }
}