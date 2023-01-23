package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Denoising filter
 */
public class MedianFilter implements Filter<RGBA> {
    final private int radius;

    /**
     * Denoise an image using the median of a region of pixels around each pixel
     * @param radius How large the window should be to take the median of
     */
    public MedianFilter(int radius) {
        this.radius = radius;
    }

    @Override
    public void apply(Image<RGBA> image) {
        Image<RGBA> new_image = image.makeCopy(); //Make copy of image
        new_image.getStream().parallel().forEach(pixel ->{ //Iterate over new image in PARALLEL(it's a very heavy filter, and otherwise takes forever)
            pixel.color = getMedianColor(image, pixel.getX(),pixel.getY());  //Get the median around the pixel
            new_image.setPixel(pixel); //Set new image pixel, not modifying original
        });
        image.setData(new_image); //Save changes
    }

    /**
     * Get the median color of an image in a window of width ((2*radius)+1) centered on a pixel
     * @param input Image to query
     * @param center_x, center_y Pixel to center window around
     * @return Median color
     */
    private RGBA getMedianColor(Image<RGBA> input, int center_x, int center_y){

        ArrayList<RGBA> color_array = new ArrayList<>((2*radius + 1) * (2*radius + 1)); //pre allocate array size. Width^2
        //iterate over square window centered on point
        for (int x = center_x  - radius; x < center_x + radius + 1; x++) {
            for (int y = center_y  - radius; y < center_y + radius + 1; y++) {
                if (input.inBounds(x, y)) {   //is valid pixel
                    //add to median calculation
                    color_array.add(input.getPixel(x,y));
                }
            }
        }

        //get median index. The center of array.
        int index = color_array.size()/2;
        // It is not exact for even sizes where you would usually take an average of the two middle elements
        // But its good enough, and it's not worth the extra complexity to add a check for even sizes.

        //Get medians for each channel, by sorting it multiple times, once for each component.
        color_array.sort(Comparator.comparing(RGBA::getR));
        short r = color_array.get(index).getR();
        color_array.sort(Comparator.comparing(RGBA::getG));
        short g = color_array.get(index).getG();
        color_array.sort(Comparator.comparing(RGBA::getB));
        short b = color_array.get(index).getB();
        color_array.sort(Comparator.comparing(RGBA::getA));
        short a = color_array.get(index).getA();

        return new RGBA(r,g,b,a); //Return median color
    }
}
