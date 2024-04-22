package ntu.dinhvu61131562.viewpager2withfragmenttablayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class QuocGiaFragment extends Fragment {
    QuocGia quocGia;
    public QuocGiaFragment(QuocGia _QuocGia) {
        quocGia = _QuocGia;
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFrag = inflater.inflate(R.layout.fragment_quoc_gia, container, false);
        // Inflate the layout for this fragment
        TextView textViewTenQG = viewFrag.findViewById(R.id.textViewCountryName);
        TextView textViewDanSo = viewFrag.findViewById(R.id.textViewPopulation);
        ImageView imageView = viewFrag.findViewById(R.id.imageViewFlag);
        textViewTenQG.setText(quocGia.getCountryName());
        textViewDanSo.setText("Popuplation: "+String.valueOf(quocGia.getPopulation()));
        int resId = viewFrag.getResources().getIdentifier(quocGia.getCountryFlag(),
                "mipmap",viewFrag.getContext().getPackageName());
        imageView.setImageResource(resId);
        return viewFrag;
    }
}