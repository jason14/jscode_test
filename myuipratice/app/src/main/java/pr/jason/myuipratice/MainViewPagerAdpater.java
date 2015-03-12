package pr.jason.myuipratice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class MainViewPagerAdpater extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{
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
                    return StarredFragment.newInstance(0, "");

                case 1:
                    return RecentFragment.newInstance(1, "");

                case 2:
                    return ContactsFragment.newInstance(2, "");
                case 3:
                    return SettingFragment.newInstance(3, "");
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
            case 3:
                return "설정";

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public int getPageIconResId(int position) {
        if(position == 0){
            return R.drawable.ic_star_outline_grey600_48dp;
        }else if(position == 1){
            return R.drawable.ic_access_time_grey600_48dp;
        }else if(position == 2){
            return R.drawable.ic_person_outline_grey600_48dp;
        }else{
            return R.drawable.ic_settings_grey600_48dp;
        }


    }
}
