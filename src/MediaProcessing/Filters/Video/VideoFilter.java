package MediaProcessing.Filters.Video;

import MediaProcessing.Data.Image;
import MediaProcessing.Data.Video;
import MediaProcessing.Utils.Colors.Color;

/**
 * Filter to apply to videos
 * @param <DataType> Video type required
 */
public interface VideoFilter <DataType extends Color> {
    /**
     * Apply filter to video
     * @param image Video to apply to
     */
    void apply(Video<DataType> image);
}