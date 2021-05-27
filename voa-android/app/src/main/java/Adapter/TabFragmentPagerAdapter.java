package Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter{

    private FragmentManager fragmentManager;
    private List<Fragment> mList;
    public TabFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList=list;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
