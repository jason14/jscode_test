package pr.jason.myuipratice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class MainViewPagerAdpater extends FragmentPagerAdapter{
    private int pageCount;
    public MainViewPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    public MainViewPagerAdpater(FragmentManager fm, int pageCount){
        super(fm);
        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
