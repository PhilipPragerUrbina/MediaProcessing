package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.Color;

/**
 * Run multiple image filters in sequence as a single filter
 */
public class CompoundFilter<ColorType extends Color> implements Filter<ColorType>{
    private final Filter<ColorType>[] filters;

    /**
     * Create a filter that applies the specified image filters in order
     * @param filters Filters to apply(in order)
     */
    public CompoundFilter(Filter<ColorType>... filters){
        this.filters = filters;
    }

    @Override
    public void apply(Image<ColorType> image) {
        for (Filter<ColorType> filter : filters) {
            filter.apply(image);
        }
    }
}
