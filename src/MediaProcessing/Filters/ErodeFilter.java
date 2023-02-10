package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.MaskValue;

/**
 * Remove edge pixels of objects
 */
public class ErodeFilter implements Filter<MaskValue> {

    final int n;

    /**
     * Remove edges of mask objects
     * @param n Number of times to apply
     */
    public ErodeFilter(int n) {
        this.n = n;
    }

    @Override
    public void apply(Image<MaskValue> image) {
        for (int i = 0; i < n; i++) {
            Image<MaskValue> mask = image.makeCopy(); //Use copy so that erode does not erode itself

            image.forEach(pixel -> {
                if(isEdgePixel(mask, pixel.getX(), pixel.getY())){
                    pixel.color = new MaskValue(false); //Remove
                    image.setPixel(pixel);
                }
            });
        }
    }

    /**
     * Return if pixel lies on edge of object(inside)
     * Checks for adjacent black pixels
     */
    private static boolean isEdgePixel(final Image<MaskValue> mask, final int pixel_x, final int pixel_y){
        //Check if inside object(must be)
        if(!mask.getPixel(pixel_x,pixel_y).getValue()){return false;} //Outside of object
        //loop through adjacent pixels.
        for (int x = pixel_x-1; x < pixel_x+2; x++) {

            for (int y = pixel_y-1; y < pixel_y+2; y++) {
                if(!mask.inBounds(x,pixel_y)){
                    continue;
                }
                if(!mask.getPixel(x,pixel_y).getValue()){ //Check if black
                    return true; //Has adjacent black pixel
                }
            }
        }

        return false; //is surrounded by white pixels. Inside of object.
    }
}
