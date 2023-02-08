package MediaProcessing.Tracking;


import MediaProcessing.Converters.Converter;
import MediaProcessing.Converters.DistanceMask;
import MediaProcessing.Data.Image;

import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.MaskValue;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Point;

/**
 * Simple average pixel mask tracker tracker
 */
public class SimpleColorTracker<ColorType extends Color> {
    final Converter<ColorType, MaskValue> mask_filter;// color mask

    /**
     * Create a really simple tracker that uses a mask to get the average position of the white pixels
     * @param mask_filter Mask to use. Such as distance mask.
     */
    public SimpleColorTracker( Converter<ColorType, MaskValue> mask_filter){
        this.mask_filter = mask_filter;
    }

    /**
     * Get the tracked point from an image
     */
    Point track(Image<ColorType> image){
        //Baseline algorithm.
        Image<MaskValue> mask = mask_filter.convert(image); //get mask
        Point average = new Point(0,0);
        int count = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if(mask.getPixel(x,y).getValue()){ //Is white
                    average = average.add(new Point(x,y));
                    count++;
                }
            }
        }
        return average.divide(count);
    }

}
