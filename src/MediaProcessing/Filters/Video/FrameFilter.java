package MediaProcessing.Filters.Video;

import MediaProcessing.Data.Video;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;

/**
 * Use image filter as video filter
 */
public class FrameFilter<ColorType extends Color> implements VideoFilter<ColorType>{

    private final Filter<ColorType> filter;

    /**
     * Create a video filter that applies an image filter to each frame
     * @param filter Filter to use
     */
    public FrameFilter(Filter<ColorType> filter) {
        this.filter = filter;
    }

    @Override
    public void apply(Video<ColorType> video) {
        //Use multithreaded stream for maximum cpu utilization
        video.parallelFrames().forEach(filter::apply); //Quickly apply the filter to each frame in parallel
    }




}
