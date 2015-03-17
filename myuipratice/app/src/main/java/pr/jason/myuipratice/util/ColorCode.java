package pr.jason.myuipratice.util;

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
        int rgbValue = 0;
        switch (color) {
            case RED:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfde0dc; break;

                    case 100:rgbValue = 0xf9bdbb; break;

                    case 200:rgbValue = 0xf69988; break;

                    case 300:rgbValue = 0xf36c60; break;

                    case 400:rgbValue = 0xe84e40; break;

                    case 500:rgbValue = 0xe51c23; break;

                    case 600:rgbValue = 0xdd191d; break;

                    case 700:rgbValue = 0xd01716; break;

                    case 800:rgbValue = 0xc41411; break;

                    case 900:rgbValue = 0xb0120a; break;

                    case 1100:rgbValue = 0xff7997; break;

                    case 1200:rgbValue = 0xff5177; break;

                    case 1400:rgbValue = 0xff2d6f;break;

                    case 1700:rgbValue = 0xe00032;break;

                    default:rgbValue =0xffffff; break;
                }
                break;


            case PINK:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfce4ec; break;

                    case 100:rgbValue = 0xf8bbd0; break;

                    case 200:rgbValue = 0xf48fb1; break;

                    case 300:rgbValue = 0xf06292; break;

                    case 400:rgbValue = 0xec407a; break;

                    case 500:rgbValue = 0xe91e63; break;

                    case 600:rgbValue = 0xd81b60; break;

                    case 700:rgbValue = 0xc2185b; break;

                    case 800:rgbValue = 0xad1457; break;

                    case 900:rgbValue = 0x880e4f; break;

                    case 1100:rgbValue = 0xff80ab; break;

                    case 1200:rgbValue = 0xff4081; break;

                    case 1400:rgbValue = 0xf50057;break;

                    case 1700:rgbValue = 0xc51162;break;

                    default:rgbValue =0xffffff; break;
                }
                break;







            case PURPLE:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xf3e5f5; break;

                    case 100:rgbValue = 0xe1bee7; break;

                    case 200:rgbValue = 0xce93d8; break;

                    case 300:rgbValue = 0xba68c8; break;

                    case 400:rgbValue = 0xab47bc; break;

                    case 500:rgbValue = 0x9c27b0; break;

                    case 600:rgbValue = 0x8e24aa; break;

                    case 700:rgbValue = 0x7b1fa2; break;

                    case 800:rgbValue = 0x6a1b9a; break;

                    case 900:rgbValue = 0x4a148c; break;

                    case 1100:rgbValue = 0xea80fc; break;

                    case 1200:rgbValue = 0xe040fb; break;

                    case 1400:rgbValue = 0xd500f9;break;

                    case 1700:rgbValue = 0xaa00ff;break;

                    default:rgbValue =0xffffff; break;
                }
                break;







            case DEEP_PURPLE:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xede7f6; break;

                    case 100:rgbValue = 0xd1c4e9; break;

                    case 200:rgbValue = 0xb39ddb; break;

                    case 300:rgbValue = 0x9575cd; break;

                    case 400:rgbValue = 0x7e57c2; break;

                    case 500:rgbValue = 0x673ab7; break;

                    case 600:rgbValue = 0x5e35b1; break;

                    case 700:rgbValue = 0x512da8; break;

                    case 800:rgbValue = 0x4527a0; break;

                    case 900:rgbValue = 0x311b92; break;

                    case 1100:rgbValue = 0xb388ff; break;

                    case 1200:rgbValue = 0x7c4dff; break;

                    case 1400:rgbValue = 0x651fff;break;

                    case 1700:rgbValue = 0x6200ea;break;

                    default:rgbValue =0xffffff; break;
                }
                break;







            case INDIGO:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xe8eaf6; break;

                    case 100:rgbValue = 0xc5cae9; break;

                    case 200:rgbValue = 0x9fa8da; break;

                    case 300:rgbValue = 0x7986cb; break;

                    case 400:rgbValue = 0x5c6bc0; break;

                    case 500:rgbValue = 0x3f51b5; break;

                    case 600:rgbValue = 0x3949ab; break;

                    case 700:rgbValue = 0x303f9f; break;

                    case 800:rgbValue = 0x283593; break;

                    case 900:rgbValue = 0x1a237e; break;

                    case 1100:rgbValue = 0x8c9eff; break;

                    case 1200:rgbValue = 0x536dfe; break;

                    case 1400:rgbValue = 0x3d5afe;break;

                    case 1700:rgbValue = 0x304ffe;break;

                    default:rgbValue =0xffffff; break;
                }
                break;







            case BLUE:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xe3f2fd; break;

                    case 100:rgbValue = 0xbbdefb; break;

                    case 200:rgbValue = 0x90caf9; break;

                    case 300:rgbValue = 0x64b5f6; break;

                    case 400:rgbValue = 0x42a5f5; break;

                    case 500:rgbValue = 0x2196f3; break;

                    case 600:rgbValue = 0x1e88e5; break;

                    case 700:rgbValue = 0x1976d2; break;

                    case 800:rgbValue = 0x1565c0; break;

                    case 900:rgbValue = 0x0d47a1; break;

                    case 1100:rgbValue = 0x82b1ff; break;

                    case 1200:rgbValue = 0x448aff; break;

                    case 1400:rgbValue = 0x2979ff;break;

                    case 1700:rgbValue = 0x2962ff;break;

                    default:rgbValue =0xffffff; break;
                }
                break;




            case LIGHT_BLUE:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xe1f5fe; break;

                    case 100:rgbValue = 0xb3e5fc; break;

                    case 200:rgbValue = 0x81d4f4; break;

                    case 300:rgbValue = 0x4fc3f7; break;

                    case 400:rgbValue = 0x29b6f6; break;

                    case 500:rgbValue = 0x03a9f4; break;

                    case 600:rgbValue = 0x039be5; break;

                    case 700:rgbValue = 0x0288d1; break;

                    case 800:rgbValue = 0x0277bd; break;

                    case 900:rgbValue = 0x01579b; break;

                    case 1100:rgbValue = 0x80d8ff; break;

                    case 1200:rgbValue = 0x40c4ff; break;

                    case 1400:rgbValue = 0x00b0ff;break;

                    case 1700:rgbValue = 0x0091ea;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case CYAN:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xe0f7fa; break;

                    case 100:rgbValue = 0xb2ebf2; break;

                    case 200:rgbValue = 0x80deea; break;

                    case 300:rgbValue = 0x4dd0e1; break;

                    case 400:rgbValue = 0x26c6da; break;

                    case 500:rgbValue = 0x00bcd4; break;

                    case 600:rgbValue = 0x00acc1; break;

                    case 700:rgbValue = 0x0097a7; break;

                    case 800:rgbValue = 0x00838f; break;

                    case 900:rgbValue = 0x006064; break;

                    case 1100:rgbValue = 0x84ffff; break;

                    case 1200:rgbValue = 0x18ffff; break;

                    case 1400:rgbValue = 0x00e5ff;break;

                    case 1700:rgbValue = 0x00b8d4;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case TEAL:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xe0f2f1; break;

                    case 100:rgbValue = 0xb2dfdb; break;

                    case 200:rgbValue = 0x80cbc4; break;

                    case 300:rgbValue = 0x4db6ac; break;

                    case 400:rgbValue = 0x26a69a; break;

                    case 500:rgbValue = 0x009688; break;

                    case 600:rgbValue = 0x00897b; break;

                    case 700:rgbValue = 0x00796b; break;

                    case 800:rgbValue = 0x00695c; break;

                    case 900:rgbValue = 0x004d40; break;

                    case 1100:rgbValue = 0xa7ffeb; break;

                    case 1200:rgbValue = 0x64ffda; break;

                    case 1400:rgbValue = 0x1de9b6;break;

                    case 1700:rgbValue = 0x00bfa5;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case GREEN:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xe8f5e9; break;

                    case 100:rgbValue = 0xc8e6c9; break;

                    case 200:rgbValue = 0xa5d6a7; break;

                    case 300:rgbValue = 0x81c784; break;

                    case 400:rgbValue = 0x66bb6a; break;

                    case 500:rgbValue = 0x4caf50; break;

                    case 600:rgbValue = 0x43a047; break;

                    case 700:rgbValue = 0x388e3c; break;

                    case 800:rgbValue = 0x2e7d32; break;

                    case 900:rgbValue = 0x1b5e20; break;

                    case 1100:rgbValue = 0xb9f6ca; break;

                    case 1200:rgbValue = 0x69f0ae; break;

                    case 1400:rgbValue = 0x00e676;break;

                    case 1700:rgbValue = 0x00c853;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case LIGHT_GREEN:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xf1f8e9; break;

                    case 100:rgbValue = 0xdcedc8; break;

                    case 200:rgbValue = 0xc5e1a5; break;

                    case 300:rgbValue = 0xaed581; break;

                    case 400:rgbValue = 0x9ccc65; break;

                    case 500:rgbValue = 0x8bc34a; break;

                    case 600:rgbValue = 0x7cb342; break;

                    case 700:rgbValue = 0x689f38; break;

                    case 800:rgbValue = 0x558b2f; break;

                    case 900:rgbValue = 0x33691e; break;

                    case 1100:rgbValue = 0xccff90; break;

                    case 1200:rgbValue = 0xb2ff59; break;

                    case 1400:rgbValue = 0x76ff03;break;

                    case 1700:rgbValue = 0x64dd17;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case LIME:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xf9fbe7; break;

                    case 100:rgbValue = 0xf0f4c3; break;

                    case 200:rgbValue = 0xe6ee9c; break;

                    case 300:rgbValue = 0xdce775; break;

                    case 400:rgbValue = 0xd4e157; break;

                    case 500:rgbValue = 0xcddc39; break;

                    case 600:rgbValue = 0xc0ca33; break;

                    case 700:rgbValue = 0xafb42b; break;

                    case 800:rgbValue = 0x9e9d24; break;

                    case 900:rgbValue = 0x827717; break;

                    case 1100:rgbValue = 0xf4ff81; break;

                    case 1200:rgbValue = 0xeeff41; break;

                    case 1400:rgbValue = 0xc6ff00;break;

                    case 1700:rgbValue = 0xaeea00;break;

                    default:rgbValue =0xffffff; break;
                }
                break;






            case YELLOW:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfffde7; break;

                    case 100:rgbValue = 0xfff9c4; break;

                    case 200:rgbValue = 0xfff59d; break;

                    case 300:rgbValue = 0xfff176; break;

                    case 400:rgbValue = 0xffee58; break;

                    case 500:rgbValue = 0xffeb3b; break;

                    case 600:rgbValue = 0xfdd835; break;

                    case 700:rgbValue = 0xfbc02d; break;

                    case 800:rgbValue = 0xf9a825; break;

                    case 900:rgbValue = 0xf57f17; break;

                    case 1100:rgbValue = 0xffff8d; break;

                    case 1200:rgbValue = 0xffff00; break;

                    case 1400:rgbValue = 0xffea00;break;

                    case 1700:rgbValue = 0xffd600;break;

                    default:rgbValue =0xffffff; break;
                }
                break;







            case AMBER:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfff8e1; break;

                    case 100:rgbValue = 0xffecb3; break;

                    case 200:rgbValue = 0xffe082; break;

                    case 300:rgbValue = 0xffd54f; break;

                    case 400:rgbValue = 0xffca28; break;

                    case 500:rgbValue = 0xffc107; break;

                    case 600:rgbValue = 0xffb300; break;

                    case 700:rgbValue = 0xffa000; break;

                    case 800:rgbValue = 0xff8f00; break;

                    case 900:rgbValue = 0xff6f00; break;

                    case 1100:rgbValue = 0xffe57f; break;

                    case 1200:rgbValue = 0xffd740; break;

                    case 1400:rgbValue = 0xffc400;break;

                    case 1700:rgbValue = 0xffab00;break;

                    default:rgbValue =0xffffff; break;
                }
                break;







            case ORANGE:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfff3e0; break;

                    case 100:rgbValue = 0xffe0b2; break;

                    case 200:rgbValue = 0xffcc80; break;

                    case 300:rgbValue = 0xffb74d; break;

                    case 400:rgbValue = 0xffa726; break;

                    case 500:rgbValue = 0xff9800; break;

                    case 600:rgbValue = 0xfb8c00; break;

                    case 700:rgbValue = 0xf57c00; break;

                    case 800:rgbValue = 0xef6c00; break;

                    case 900:rgbValue = 0xe65100; break;

                    case 1100:rgbValue = 0xffd180; break;

                    case 1200:rgbValue = 0xffab40; break;

                    case 1400:rgbValue = 0xff9100;break;

                    case 1700:rgbValue = 0xff6d00;break;

                    default:rgbValue =0xffffff; break;
                }
                break;




            case DEEP_ORANGE:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfbe9e7; break;

                    case 100:rgbValue = 0xffccbc; break;

                    case 200:rgbValue = 0xffab91; break;

                    case 300:rgbValue = 0xff8a65; break;

                    case 400:rgbValue = 0xff7043; break;

                    case 500:rgbValue = 0xff5722; break;

                    case 600:rgbValue = 0xf4511e; break;

                    case 700:rgbValue = 0xe64a19; break;

                    case 800:rgbValue = 0xd84315; break;

                    case 900:rgbValue = 0xbf360c; break;

                    case 1100:rgbValue = 0xff9e80; break;

                    case 1200:rgbValue = 0xff6e40; break;

                    case 1400:rgbValue = 0xff3d00;break;

                    case 1700:rgbValue = 0xdd2c00;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case BROWN:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xefebe9; break;

                    case 100:rgbValue = 0xd7ccc8; break;

                    case 200:rgbValue = 0xbcaaa4; break;

                    case 300:rgbValue = 0xa1887f; break;

                    case 400:rgbValue = 0x8d6e63; break;

                    case 500:rgbValue = 0x795548; break;

                    case 600:rgbValue = 0x6d4c41; break;

                    case 700:rgbValue = 0x5d4037; break;

                    case 800:rgbValue = 0x4e342e; break;

                    case 900:rgbValue = 0x3e2723; break;

                    case 1100:rgbValue = 0xbda299; break;

                    case 1200:rgbValue = 0x8c6d62; break;

                    case 1400:rgbValue = 0x6b443b;break;

                    case 1700:rgbValue = 0x52322d;break;

                    default:rgbValue =0xffffff; break;
                }
                break;



            case GREY:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xfafafa; break;

                    case 100:rgbValue = 0xf5f5f5; break;

                    case 200:rgbValue = 0xeeeeee; break;

                    case 300:rgbValue = 0xe0e0e0; break;

                    case 400:rgbValue = 0xbdbdbd; break;

                    case 500:rgbValue = 0x9e9e9e; break;

                    case 600:rgbValue = 0x757575; break;

                    case 700:rgbValue = 0x616161; break;

                    case 800:rgbValue = 0x424242; break;

                    case 900:rgbValue = 0x212121; break;

                    case 1100:rgbValue = 0xf0f0f0; break;

                    case 1200:rgbValue = 0xc0c0c0; break;

                    case 1400:rgbValue = 0x808080;break;

                    case 1700:rgbValue = 0x505050;break;

                    default:rgbValue =0xffffff; break;
                }
                break;

            case BLUE_GREY:
                switch (code) {
                    case 0:rgbValue = 0x000000; break;

                    case 50:rgbValue = 0xeceff1; break;

                    case 100:rgbValue = 0xcfd8dc; break;

                    case 200:rgbValue = 0xb0bec5; break;

                    case 300:rgbValue = 0x90a4ae; break;

                    case 400:rgbValue = 0x78909c; break;

                    case 500:rgbValue = 0x607d8b; break;

                    case 600:rgbValue = 0x546e7a; break;

                    case 700:rgbValue = 0x455a64; break;

                    case 800:rgbValue = 0x37474f; break;

                    case 900:rgbValue = 0x263238; break;

                    case 1100:rgbValue = 0xb0bed9; break;

                    case 1200:rgbValue = 0x7890c0; break;

                    case 1400:rgbValue = 0x546e89;break;

                    case 1700:rgbValue = 0x374760;break;

                    default:rgbValue =0xffffff; break;
                }
                break;

        }

        return rgbValue;
    }
}
