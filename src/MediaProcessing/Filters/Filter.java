package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Color;

/**
 * Filter to apply to images
 * @param <DataType> Image type required
 */
public interface Filter <DataType extends Color> {
    /**
     * Apply filter to image
     * @param image Image to apply to
     */
    void apply(Image<DataType> image);
}
