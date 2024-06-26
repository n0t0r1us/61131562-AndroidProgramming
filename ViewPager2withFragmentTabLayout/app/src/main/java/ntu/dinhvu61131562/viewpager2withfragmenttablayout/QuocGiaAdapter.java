package ntu.dinhvu61131562.viewpager2withfragmenttablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class QuocGiaAdapter extends FragmentStateAdapter {
    List<QuocGia> quocGiaList;
    public QuocGiaAdapter(@NonNull FragmentActivity fragmentActivity, List<QuocGia> _dataSource) {
        super(fragmentActivity);
        quocGiaList= _dataSource;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        QuocGia quocGiaX = quocGiaList.get(position);
        QuocGiaFragment quocGiaFragment = new QuocGiaFragment(quocGiaX);
        return quocGiaFragment;
    }

    @Override
    public int getItemCount() {
        return quocGiaList.size();
    }
}
