package ntu.dinhvu61131562.bottomnavigationviewwithfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
public class ImageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.cv);
        //ScrollView scrollView = rootView.findViewById(R.id.scrollView);
        //scrollView.setSmoothScrollingEnabled(true);
        return rootView;
    }
}