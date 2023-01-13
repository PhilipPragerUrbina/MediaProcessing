package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Add a border to image
 */
public class BorderFilter implements Filter<RGBA> {
    private RGBA border_color;

    /**
     * Add a "picture frame" like border
     * @param border_color Color of border
     */
    public BorderFilter(RGBA border_color){
        this.border_color = border_color;
    }

    @Override
    public void apply(Image<RGBA> image) {
        
    }
}
