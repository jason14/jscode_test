package pr.jason.myuipratice.util;

import android.graphics.Color;

/**
 * Created by Jaesin on 2015-03-12.
 */
public class ColorCode {
    public static final int RED = 0;
    public static final int PINK = 1;
    public static final int PURPLE = 2;
    public static final int DEEP_PURPLE =3;
    public static final int INDIGO = 4;
    public static final int BLUE = 5;
    public static final int LIGHT_BLUE =6;
    public static final int CYAN =7;
    public static final int TEAL=8;
    public static final int GREEN=9;
    public static final int LIGHT_GREEN=10;
    public static final int LIME=11;
    public static final int YELLOW=12;
    public static final int AMBER=13;
    public static final int ORANGE=14;
    public static final int DEEP_ORANGE=15;
    public static final int BROWN=16;
    public static final int GREY=17;
    public static final int BLUE_GREY=18;

    public static int getColorValue(int color, int code){
        String rgbStringValue = "";
        switch (color) {
            case RED:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fde0dc"; break;

                    case 100:rgbStringValue = "#f9bdbb"; break;

                    case 200:rgbStringValue = "#f69988"; break;

                    case 300:rgbStringValue = "#f36c60"; break;

                    case 400:rgbStringValue = "#e84e40"; break;

                    case 500:rgbStringValue = "#e51c23"; break;

                    case 600:rgbStringValue = "#dd191d"; break;

                    case 700:rgbStringValue = "#d01716"; break;

                    case 800:rgbStringValue = "#c41411"; break;

                    case 900:rgbStringValue = "#b0120a"; break;

                    case 1100:rgbStringValue = "#ff7997"; break;

                    case 1200:rgbStringValue = "#ff5177"; break;

                    case 1400:rgbStringValue = "#ff2d6f"; break;

                    case 1700:rgbStringValue = "#e00032"; break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;


            case PINK:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fce4ec"; break;

                    case 100:rgbStringValue = "#f8bbd0"; break;

                    case 200:rgbStringValue = "#f48fb1"; break;

                    case 300:rgbStringValue = "#f06292"; break;

                    case 400:rgbStringValue = "#ec407a"; break;

                    case 500:rgbStringValue = "#e91e63"; break;

                    case 600:rgbStringValue = "#d81b60"; break;

                    case 700:rgbStringValue = "#c2185b"; break;

                    case 800:rgbStringValue = "#ad1457"; break;

                    case 900:rgbStringValue = "#880e4f"; break;

                    case 1100:rgbStringValue = "#ff80ab"; break;

                    case 1200:rgbStringValue = "#ff4081"; break;

                    case 1400:rgbStringValue = "#f50057";break;

                    case 1700:rgbStringValue = "#c51162";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;







            case PURPLE:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#f3e5f5"; break;

                    case 100:rgbStringValue = "#e1bee7"; break;

                    case 200:rgbStringValue = "#ce93d8"; break;

                    case 300:rgbStringValue = "#ba68c8"; break;

                    case 400:rgbStringValue = "#ab47bc"; break;

                    case 500:rgbStringValue = "#9c27b0"; break;

                    case 600:rgbStringValue = "#8e24aa"; break;

                    case 700:rgbStringValue = "#7b1fa2"; break;

                    case 800:rgbStringValue = "#6a1b9a"; break;

                    case 900:rgbStringValue = "#4a148c"; break;

                    case 1100:rgbStringValue = "#ea80fc"; break;

                    case 1200:rgbStringValue = "#e040fb"; break;

                    case 1400:rgbStringValue = "#d500f9";break;

                    case 1700:rgbStringValue = "#aa00ff";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;







            case DEEP_PURPLE:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#ede7f6"; break;

                    case 100:rgbStringValue = "#d1c4e9"; break;

                    case 200:rgbStringValue = "#b39ddb"; break;

                    case 300:rgbStringValue = "#9575cd"; break;

                    case 400:rgbStringValue = "#7e57c2"; break;

                    case 500:rgbStringValue = "#673ab7"; break;

                    case 600:rgbStringValue = "#5e35b1"; break;

                    case 700:rgbStringValue = "#512da8"; break;

                    case 800:rgbStringValue = "#4527a0"; break;

                    case 900:rgbStringValue = "#311b92"; break;

                    case 1100:rgbStringValue = "#b388ff"; break;

                    case 1200:rgbStringValue = "#7c4dff"; break;

                    case 1400:rgbStringValue = "#651fff";break;

                    case 1700:rgbStringValue = "#6200ea";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;







            case INDIGO:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#e8eaf6"; break;

                    case 100:rgbStringValue = "#c5cae9"; break;

                    case 200:rgbStringValue = "#9fa8da"; break;

                    case 300:rgbStringValue = "#7986cb"; break;

                    case 400:rgbStringValue = "#5c6bc0"; break;

                    case 500:rgbStringValue = "#3f51b5"; break;

                    case 600:rgbStringValue = "#3949ab"; break;

                    case 700:rgbStringValue = "#303f9f"; break;

                    case 800:rgbStringValue = "#283593"; break;

                    case 900:rgbStringValue = "#1a237e"; break;

                    case 1100:rgbStringValue = "#8c9eff"; break;

                    case 1200:rgbStringValue = "#536dfe"; break;

                    case 1400:rgbStringValue = "#3d5afe";break;

                    case 1700:rgbStringValue = "#304ffe";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;







            case BLUE:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#e3f2fd"; break;

                    case 100:rgbStringValue = "#bbdefb"; break;

                    case 200:rgbStringValue = "#90caf9"; break;

                    case 300:rgbStringValue = "#64b5f6"; break;

                    case 400:rgbStringValue = "#42a5f5"; break;

                    case 500:rgbStringValue = "#2196f3"; break;

                    case 600:rgbStringValue = "#1e88e5"; break;

                    case 700:rgbStringValue = "#1976d2"; break;

                    case 800:rgbStringValue = "#1565c0"; break;

                    case 900:rgbStringValue = "#0d47a1"; break;

                    case 1100:rgbStringValue = "#82b1ff"; break;

                    case 1200:rgbStringValue = "#448aff"; break;

                    case 1400:rgbStringValue = "#2979ff";break;

                    case 1700:rgbStringValue = "#2962ff";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;




            case LIGHT_BLUE:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#e1f5fe"; break;

                    case 100:rgbStringValue = "#b3e5fc"; break;

                    case 200:rgbStringValue = "#81d4f4"; break;

                    case 300:rgbStringValue = "#4fc3f7"; break;

                    case 400:rgbStringValue = "#29b6f6"; break;

                    case 500:rgbStringValue = "#03a9f4"; break;

                    case 600:rgbStringValue = "#039be5"; break;

                    case 700:rgbStringValue = "#0288d1"; break;

                    case 800:rgbStringValue = "#0277bd"; break;

                    case 900:rgbStringValue = "#01579b"; break;

                    case 1100:rgbStringValue = "#80d8ff"; break;

                    case 1200:rgbStringValue = "#40c4ff"; break;

                    case 1400:rgbStringValue = "#00b0ff";break;

                    case 1700:rgbStringValue = "#0091ea";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case CYAN:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#e0f7fa"; break;

                    case 100:rgbStringValue = "#b2ebf2"; break;

                    case 200:rgbStringValue = "#80deea"; break;

                    case 300:rgbStringValue = "#4dd0e1"; break;

                    case 400:rgbStringValue = "#26c6da"; break;

                    case 500:rgbStringValue = "#00bcd4"; break;

                    case 600:rgbStringValue = "#00acc1"; break;

                    case 700:rgbStringValue = "#0097a7"; break;

                    case 800:rgbStringValue = "#00838f"; break;

                    case 900:rgbStringValue = "#006064"; break;

                    case 1100:rgbStringValue = "#84ffff"; break;

                    case 1200:rgbStringValue = "#18ffff"; break;

                    case 1400:rgbStringValue = "#00e5ff";break;

                    case 1700:rgbStringValue = "#00b8d4";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case TEAL:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#e0f2f1"; break;

                    case 100:rgbStringValue = "#b2dfdb"; break;

                    case 200:rgbStringValue = "#80cbc4"; break;

                    case 300:rgbStringValue = "#4db6ac"; break;

                    case 400:rgbStringValue = "#26a69a"; break;

                    case 500:rgbStringValue = "#009688"; break;

                    case 600:rgbStringValue = "#00897b"; break;

                    case 700:rgbStringValue = "#00796b"; break;

                    case 800:rgbStringValue = "#00695c"; break;

                    case 900:rgbStringValue = "#004d40"; break;

                    case 1100:rgbStringValue = "#a7ffeb"; break;

                    case 1200:rgbStringValue = "#64ffda"; break;

                    case 1400:rgbStringValue = "#1de9b6";break;

                    case 1700:rgbStringValue = "#00bfa5";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case GREEN:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#e8f5e9"; break;

                    case 100:rgbStringValue = "#c8e6c9"; break;

                    case 200:rgbStringValue = "#a5d6a7"; break;

                    case 300:rgbStringValue = "#81c784"; break;

                    case 400:rgbStringValue = "#66bb6a"; break;

                    case 500:rgbStringValue = "#4caf50"; break;

                    case 600:rgbStringValue = "#43a047"; break;

                    case 700:rgbStringValue = "#388e3c"; break;

                    case 800:rgbStringValue = "#2e7d32"; break;

                    case 900:rgbStringValue = "#1b5e20"; break;

                    case 1100:rgbStringValue = "#b9f6ca"; break;

                    case 1200:rgbStringValue = "#69f0ae"; break;

                    case 1400:rgbStringValue = "#00e676";break;

                    case 1700:rgbStringValue = "#00c853";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case LIGHT_GREEN:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#f1f8e9"; break;

                    case 100:rgbStringValue = "#dcedc8"; break;

                    case 200:rgbStringValue = "#c5e1a5"; break;

                    case 300:rgbStringValue = "#aed581"; break;

                    case 400:rgbStringValue = "#9ccc65"; break;

                    case 500:rgbStringValue = "#8bc34a"; break;

                    case 600:rgbStringValue = "#7cb342"; break;

                    case 700:rgbStringValue = "#689f38"; break;

                    case 800:rgbStringValue = "#558b2f"; break;

                    case 900:rgbStringValue = "#33691e"; break;

                    case 1100:rgbStringValue = "#ccff90"; break;

                    case 1200:rgbStringValue = "#b2ff59"; break;

                    case 1400:rgbStringValue = "#76ff03";break;

                    case 1700:rgbStringValue = "#64dd17";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case LIME:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#f9fbe7"; break;

                    case 100:rgbStringValue = "#f0f4c3"; break;

                    case 200:rgbStringValue = "#e6ee9c"; break;

                    case 300:rgbStringValue = "#dce775"; break;

                    case 400:rgbStringValue = "#d4e157"; break;

                    case 500:rgbStringValue = "#cddc39"; break;

                    case 600:rgbStringValue = "#c0ca33"; break;

                    case 700:rgbStringValue = "#afb42b"; break;

                    case 800:rgbStringValue = "#9e9d24"; break;

                    case 900:rgbStringValue = "#827717"; break;

                    case 1100:rgbStringValue = "#f4ff81"; break;

                    case 1200:rgbStringValue = "#eeff41"; break;

                    case 1400:rgbStringValue = "#c6ff00";break;

                    case 1700:rgbStringValue = "#aeea00";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;






            case YELLOW:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fffde7"; break;

                    case 100:rgbStringValue = "#fff9c4"; break;

                    case 200:rgbStringValue = "#fff59d"; break;

                    case 300:rgbStringValue = "#fff176"; break;

                    case 400:rgbStringValue = "#ffee58"; break;

                    case 500:rgbStringValue = "#ffeb3b"; break;

                    case 600:rgbStringValue = "#fdd835"; break;

                    case 700:rgbStringValue = "#fbc02d"; break;

                    case 800:rgbStringValue = "#f9a825"; break;

                    case 900:rgbStringValue = "#f57f17"; break;

                    case 1100:rgbStringValue = "#ffff8d"; break;

                    case 1200:rgbStringValue = "#ffff00"; break;

                    case 1400:rgbStringValue = "#ffea00";break;

                    case 1700:rgbStringValue = "#ffd600";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;







            case AMBER:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fff8e1"; break;

                    case 100:rgbStringValue = "#ffecb3"; break;

                    case 200:rgbStringValue = "#ffe082"; break;

                    case 300:rgbStringValue = "#ffd54f"; break;

                    case 400:rgbStringValue = "#ffca28"; break;

                    case 500:rgbStringValue = "#ffc107"; break;

                    case 600:rgbStringValue = "#ffb300"; break;

                    case 700:rgbStringValue = "#ffa000"; break;

                    case 800:rgbStringValue = "#ff8f00"; break;

                    case 900:rgbStringValue = "#ff6f00"; break;

                    case 1100:rgbStringValue = "#ffe57f"; break;

                    case 1200:rgbStringValue = "#ffd740"; break;

                    case 1400:rgbStringValue = "#ffc400";break;

                    case 1700:rgbStringValue = "#ffab00";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;







            case ORANGE:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fff3e0"; break;

                    case 100:rgbStringValue = "#ffe0b2"; break;

                    case 200:rgbStringValue = "#ffcc80"; break;

                    case 300:rgbStringValue = "#ffb74d"; break;

                    case 400:rgbStringValue = "#ffa726"; break;

                    case 500:rgbStringValue = "#ff9800"; break;

                    case 600:rgbStringValue = "#fb8c00"; break;

                    case 700:rgbStringValue = "#f57c00"; break;

                    case 800:rgbStringValue = "#ef6c00"; break;

                    case 900:rgbStringValue = "#e65100"; break;

                    case 1100:rgbStringValue = "#ffd180"; break;

                    case 1200:rgbStringValue = "#ffab40"; break;

                    case 1400:rgbStringValue = "#ff9100";break;

                    case 1700:rgbStringValue = "#ff6d00";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;




            case DEEP_ORANGE:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fbe9e7"; break;

                    case 100:rgbStringValue = "#ffccbc"; break;

                    case 200:rgbStringValue = "#ffab91"; break;

                    case 300:rgbStringValue = "#ff8a65"; break;

                    case 400:rgbStringValue = "#ff7043"; break;

                    case 500:rgbStringValue = "#ff5722"; break;

                    case 600:rgbStringValue = "#f4511e"; break;

                    case 700:rgbStringValue = "#e64a19"; break;

                    case 800:rgbStringValue = "#d84315"; break;

                    case 900:rgbStringValue = "#bf360c"; break;

                    case 1100:rgbStringValue = "#ff9e80"; break;

                    case 1200:rgbStringValue = "#ff6e40"; break;

                    case 1400:rgbStringValue = "#ff3d00";break;

                    case 1700:rgbStringValue = "#dd2c00";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case BROWN:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#efebe9"; break;

                    case 100:rgbStringValue = "#d7ccc8"; break;

                    case 200:rgbStringValue = "#bcaaa4"; break;

                    case 300:rgbStringValue = "#a1887f"; break;

                    case 400:rgbStringValue = "#8d6e63"; break;

                    case 500:rgbStringValue = "#795548"; break;

                    case 600:rgbStringValue = "#6d4c41"; break;

                    case 700:rgbStringValue = "#5d4037"; break;

                    case 800:rgbStringValue = "#4e342e"; break;

                    case 900:rgbStringValue = "#3e2723"; break;

                    case 1100:rgbStringValue = "#bda299"; break;

                    case 1200:rgbStringValue = "#8c6d62"; break;

                    case 1400:rgbStringValue = "#6b443b";break;

                    case 1700:rgbStringValue = "#52322d";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;



            case GREY:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#fafafa"; break;

                    case 100:rgbStringValue = "#f5f5f5"; break;

                    case 200:rgbStringValue = "#eeeeee"; break;

                    case 300:rgbStringValue = "#e0e0e0"; break;

                    case 400:rgbStringValue = "#bdbdbd"; break;

                    case 500:rgbStringValue = "#9e9e9e"; break;

                    case 600:rgbStringValue = "#757575"; break;

                    case 700:rgbStringValue = "#616161"; break;

                    case 800:rgbStringValue = "#424242"; break;

                    case 900:rgbStringValue = "#212121"; break;

                    case 1100:rgbStringValue = "#f0f0f0"; break;

                    case 1200:rgbStringValue = "#c0c0c0"; break;

                    case 1400:rgbStringValue = "#808080";break;

                    case 1700:rgbStringValue = "#505050";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;

            case BLUE_GREY:
                switch (code) {
                    case 0:rgbStringValue = "#000000"; break;

                    case 50:rgbStringValue = "#eceff1"; break;

                    case 100:rgbStringValue = "#cfd8dc"; break;

                    case 200:rgbStringValue = "#b0bec5"; break;

                    case 300:rgbStringValue = "#90a4ae"; break;

                    case 400:rgbStringValue = "#78909c"; break;

                    case 500:rgbStringValue = "#607d8b"; break;

                    case 600:rgbStringValue = "#546e7a"; break;

                    case 700:rgbStringValue = "#455a64"; break;

                    case 800:rgbStringValue = "#37474f"; break;

                    case 900:rgbStringValue = "#263238"; break;

                    case 1100:rgbStringValue = "#b0bed9"; break;

                    case 1200:rgbStringValue = "#7890c0"; break;

                    case 1400:rgbStringValue = "#546e89";break;

                    case 1700:rgbStringValue = "#374760";break;

                    default:rgbStringValue ="#ffffff"; break;
                }
                break;

        }

        return Color.parseColor(rgbStringValue);
    }
}
