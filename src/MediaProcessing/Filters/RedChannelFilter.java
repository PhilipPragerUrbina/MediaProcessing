package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Simple test filter to convert RGBA to R
 */
public class RedChannelFilter implements Filter<RGBA> {



    @Override
    public void apply(Image<RGBA> image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                    image.setPixel(x,y,new RGBA(image.getPixel(x,y).getR(),0,0,255)); //Use just red
            }
        }
    }
}
