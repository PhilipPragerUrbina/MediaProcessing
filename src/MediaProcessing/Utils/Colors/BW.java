//package MediaProcessing.Utils.Colors;
//
//import MediaProcessing.Utils.Colors.Color;
//
///**
// * Binary black and white color
// */
//public class BW implements Color {
//    private boolean is_white;
//
//    public BW(){}
//
//    /**
//     *  Create a black and white color
//     * @param is_white Is the color white(true) or black(false)
//     */
//    public BW(boolean is_white){
//        this.is_white = is_white;
//    }
//
//    @Override
//    public Color clone() {
//        return new BW(is_white);
//    }
//
//    @Override
//    public RGBA getRGBA() {
//        if(is_white){
//            return new RGBA(255,255,255,255);
//        }
//            return new RGBA(0,0,0,0);
//    }
//
//    @Override
//    public void setRGBA(RGBA values) {
//        //todo implement this
//    }
//
//    /**
//     * Set the color to black
//     */
//    public void setBlack(){
//        is_white = false;
//    }
//
//    /**
//     * Set the color to white
//     */
//    public void setWhite(){
//        is_white = true;
//    }
//
//    /**
//     * True if white
//     */
//    public boolean isWhite(){
//        return is_white;
//    }
//
//    /**
//     * True if black
//     */
//    public boolean isBlack(){
//        return !is_white;
//    }
//
//
//
//}
