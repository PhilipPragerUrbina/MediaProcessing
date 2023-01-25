package MediaProcessing.Converters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.Color;

/**
 * Convert between image types
 */
public interface Converter <InType extends Color, OutType extends Color>{

    /**
     * Convert between two color types
     * @param input Input image
     * @return New image with converted data
     */
    Image<OutType> convert(Image<InType> input);
}
