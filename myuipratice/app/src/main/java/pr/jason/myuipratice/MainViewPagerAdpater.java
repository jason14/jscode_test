package pr.jason.myuipratice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class MainViewPagerAdpater extends FragmentPagerAdapter{
    private int pageCount;
   // FloatingActionButton fab;
    public MainViewPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    public MainViewPagerAdpater(FragmentManager fm, int pageCount){
        super(fm);
        this.pageCount = pageCount;
        //this.fab = fab;
    }

    @Override
    public Fragment getItem(int position) {

            switch (position) {
                case 0:
                /*ViewHelper.setTranslationX(fab,MainActivity.fabTransWidth);*/
                    return StarredFragment.newInstance(0, "즐겨찾기");

                case 1:
                    return RecentFragment.newInstance(2, "최근");

                case 2:
                    return ContactsFragment.newInstance(1, "연락처");

                default:
                    return null;
            }


    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "즐겨찾기";

            case 1:
                return "최근";

            case 2:
                return "연락처";

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
