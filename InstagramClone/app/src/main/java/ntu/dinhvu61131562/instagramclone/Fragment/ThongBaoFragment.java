package ntu.dinhvu61131562.instagramclone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ntu.dinhvu61131562.instagramclone.Adapter.ThongBaoAdapter;
import ntu.dinhvu61131562.instagramclone.Model.ThongBao;
import ntu.dinhvu61131562.instagramclone.R;
public class ThongBaoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ThongBaoAdapter thongBaoAdapter;
    private List<ThongBao> thongBaoList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        thongBaoList = new ArrayList<>();
        thongBaoAdapter = new ThongBaoAdapter(getContext(), thongBaoList);
        recyclerView.setAdapter(thongBaoAdapter);

        docThongBao();
        return view;
    }

    private void docThongBao(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Thông Báo")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thongBaoList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ThongBao thongBao = dataSnapshot.getValue(ThongBao.class);
                    thongBaoList.add(thongBao);
                }
                Collections.reverse(thongBaoList);
                thongBaoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}