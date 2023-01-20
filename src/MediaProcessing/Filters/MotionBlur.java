package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Add motion blur to image
 */
public class MotionBlur implements Filter<RGBA> {
    private final int blur_size;

    /**
     * Add blur to image in X direction
     * @param blur_size Size of blur
     */
    public MotionBlur(int blur_size){
     this.blur_size = blur_size;
    }

    @Override
    public void apply(Image<RGBA> image) {
        Image<RGBA> new_image = new Image<>(image.getWidth(),image.getHeight());
        for (int x = 0; x < image.getWidth(); x++) { //Iterate over image
            for (int y = 0; y < image.getHeight(); y++) {
                new_image.setPixel( x,y,blurPixel(image, x , y ));
            }
        }
        image.setData(new_image);
    }

    /**
     * Get average in X direction
     * @param image Image to search in
     * @param c_x,c_y start
     * @return Average blur color
     */
    private RGBA blurPixel(Image<RGBA> image, int c_x, int c_y) {
        double r = 0,g = 0,b = 0,a = 0; //Sum colors
        double count = blur_size; //Color sum count

        for (int x = c_x; x < Math.min(c_x + blur_size, image.getWidth()); x++) {
            RGBA color = image.getPixel(x,c_y); //Get pixel
            r += color.getR();
            g += color.getG();
            b += color.getB();
            a += color.getA(); //todo add() color method
        }
        //get average color
        return new RGBA((short) (r / count), (short)(g / count), (short)(b/count),(short)(a/count));
    }
}
