package pr.jason.myuipratice.preferences;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import pr.jason.myuipratice.R;
import pr.jason.myuipratice.util.ColorCode;

/**
 * Created by Jaesin on 2015-03-16.
 */

public class MainColorSelector extends ActionBarActivity{

    //Tone-down 각 ColorCode 배경색 500 글자색 900 Pastel CorlorCode 배경색 500 글자색 200

    private int bgColors[] = {ColorCode.BLUE_GREY, ColorCode.GREY ,ColorCode.BROWN, ColorCode.DEEP_ORANGE,
            ColorCode.ORANGE, ColorCode.AMBER ,ColorCode.YELLOW, ColorCode.LIME,
            ColorCode.LIGHT_GREEN, ColorCode.GREEN ,ColorCode.TEAL, ColorCode.CYAN,
            ColorCode.LIGHT_BLUE, ColorCode.BLUE ,ColorCode.INDIGO, ColorCode.DEEP_PURPLE,
            ColorCode.PURPLE, ColorCode.PINK , ColorCode.RED };

    private int bgColorsName[] = {R.string.blue_grey,R.string.grey,R.string.brwon,R.string.deep_orange,
            R.string.orange,R.string.amber,R.string.yellow,R.string.lime,
            R.string.light_green,R.string.green,R.string.teal,R.string.cyan,
            R.string.light_blue,R.string.blue,R.string.indigo,R.string.deep_purple,
            R.string.purple,R.string.pink,R.string.red };

    private int colorType = 0;
    private final int BASIC = 0;
    private final int TONE_DOWN = 1;
    private final int PASTEL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.main_color_selector_activity);
    }

    private class selectorAdapter extends BaseAdapter{
        private LayoutInflater inflater;

        private ViewHolder viewHolder;

        private class ViewHolder{
            TextView nameTV;
            LinearLayout bgLayoutView;
        }

        public selectorAdapter(){
            inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return bgColorsName.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                viewHolder = new ViewHolder();
                v = inflater.inflate(R.layout.main_color_seletor_row, parent, false);
                viewHolder.bgLayoutView = (LinearLayout)v.findViewById(R.id.color_list_row_layout);
                viewHolder.nameTV =(TextView)v.findViewById(R.id.color_name_tv);
                v.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)v.getTag();
            }
            viewHolder.nameTV.setText(bgColorsName[position]);
            viewHolder.bgLayoutView.setBackgroundColor();
            return v;
        }

        private int setColor(int viewType, int color, int colorType){
            if(colorType){

            }
            return color;
        }
    }


}
