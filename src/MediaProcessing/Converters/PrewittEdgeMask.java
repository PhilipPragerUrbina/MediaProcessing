package MediaProcessing.Converters;


import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Convolution.PrewittEdgeDetection;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.MaskValue;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Vector;

/**
 * Masks image based on prewitt
 */
public class PrewittEdgeMask<ColorType extends Color> implements Converter<ColorType, MaskValue> {


    public PrewittEdgeMask() {

    }
    @Override
    public Image<MaskValue> convert(Image<ColorType> input) {
        Image<ColorType> copy = input.makeCopy();
       new PrewittEdgeDetection<ColorType>().apply(copy);
       Image<MaskValue> mask = new Image<>(input.getWidth(), input.getHeight());
        input.forEach(pixel -> {
            java.awt.Color col = copy.getPixel(pixel.getX(),pixel.getY()).getJavaColor();
           mask.setPixel(pixel.getX(),pixel.getY(), new MaskValue(col));
        });
        return mask;
    }
}
