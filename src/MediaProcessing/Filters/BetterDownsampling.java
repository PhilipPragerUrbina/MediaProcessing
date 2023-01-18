package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Average down sample image to resize it
 */
public class BetterDownsampling implements Filter<RGBA> {
    private int factor;

    /**
     * Down sample an image
     * @param factor How much to divide image size. Should be positive and not 0.
     */
    public BetterDownsampling(int factor){
     this.factor = factor;
    }

    @Override
    public void apply(Image<RGBA> image) {
        Image<RGBA> temporary_image = new Image<>(image.getWidth() /factor,image.getHeight() /factor); //Create resized image

        for (int x = 0; x < temporary_image.getWidth(); x++) { //Iterate over image
            for (int y = 0; y < temporary_image.getHeight(); y++) {
                temporary_image.setPixel( x,y,getAverageGrid(image, x * factor, y * factor));
            }
        }
        image.setData(temporary_image); //Set original image to new image
    }

    /**
     * Get average in region size of factor*factor
     * @param image Image to search in
     * @param c_x,c_y top left corner
     * @return Average color
     */
    private RGBA getAverageGrid(Image<RGBA> image, int c_x, int c_y) {
        double r = 0,g = 0,b = 0,a = 0; //Sum colors
        for (int x = c_x; x < c_x + factor; x++) { //Iterate over region
            for (int y = c_y; y < c_y+factor; y++) {
                RGBA color = image.getPixel(x,y); //Get pixel
                r += color.getR();
                g += color.getG();
                b += color.getB();
                a += color.getA();
            }
        }
        double count = factor*factor;
        //get average color
        return new RGBA((short) (r / count), (short)(g / count), (short)(b/count),(short)(a/count));
    }
}
