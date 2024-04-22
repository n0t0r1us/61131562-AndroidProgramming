package ntu.dinhvu61131562.bottomnavigationviewwithfragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button openPdfButton = rootView.findViewById(R.id.myResumeButton);
        openPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdfFile();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    private void openPdfFile(){
        try{
            String pdfFilePath = "file:///android_asset/CV.pdf";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(pdfFilePath), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if(intent.resolveActivity(requireActivity().getPackageManager()) != null){
                startActivity(intent);
            } else {

            }
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }
}