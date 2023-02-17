package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.MaskValue;

/**
 * Subtract one mask from another
 */
public class MaskSubtract implements Filter<MaskValue> {

    private Image<MaskValue> subtractor;

    /**
     * Subtract one mask from another. Basically remove all white pixels that are present in both images
     * @param subtractor Mask to subtract from other masks
     *                   Must be same size
     */
    public MaskSubtract(Image<MaskValue> subtractor) {
        this.subtractor = subtractor;
    }

    @Override
    public void apply(Image<MaskValue> image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if(image.getPixel(x,y).getValue()&&subtractor.getPixel(x,y).getValue()){
                    image.setPixel(x,y, new MaskValue(false));
                }
            }
        }
    }
}
