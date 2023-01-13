package MediaProcessing.Filters.GrayScale;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.G;

/**
 * Apply polychrome filter to quantize grayscale
 */
public class GreyscalePolyFilter implements Filter<G> {
    private int n;

    /**
     * Create a polychrome filter
     * @param n Number of buckets
     */
    public GreyscalePolyFilter(int n){
        this.n = n;
    }

    @Override
    public void apply(Image<G> image) {
        int w = 256/n; //get number of buckets(256 to avoid problems with 255 values going over)
        image.getStream().forEach(color -> {
                    int bucket =color.getValue() / w; //Get bucket number
                    color.setValue((short) ((bucket * w) + (w / 2))); //Get color in center of bucket
                }
        );
    }
}
