package MediaProcessing.Tracking;

import MediaProcessing.Converters.Converter;
import MediaProcessing.Converters.DistanceMask;
import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Convolution.GaussianBlurFilter;

import MediaProcessing.Utils.Colors.HSV;
import MediaProcessing.Utils.Colors.MaskValue;

import MediaProcessing.Utils.Vectors.Point;


import java.util.ArrayList;


/**
 * Detect cards in image such as playing cards
 * Has trouble with close together cards
 */
public class CardDetector {
    /**
     * Detect cards in image such as playing cards
     * @param threshold How close the color needs to be to count as card material. For HSV ~0.5. Varies by image.
     * @param card_main_color The background color of the card itself. Usually white for playing cards.
     */
    public CardDetector(double threshold, HSV card_main_color) {
        this.threshold = threshold;
        this.card_main_color = card_main_color;
    }

    private final double threshold;
    final private HSV card_main_color;

    /**
     * Detect cards in image
     */
    public ArrayList<BoundingPolygon> detect(Image<HSV> image){
        image = image.makeCopy();//Make copy of image to apply operations to
        new GaussianBlurFilter<HSV>(3,1).apply(image); //Blur to help with detection. (Removing this might help with some cases)
        Converter<HSV, MaskValue> mask_filter = new DistanceMask<>(card_main_color, threshold); //Do masking
        Image<MaskValue> mask =mask_filter.convert(image);
        
        new GaussianBlurFilter<MaskValue>(3,3).apply(mask); //Smooth mask
        
        ArrayList<Shape> shapes = ShapeDetector.detectShapes(mask); //Detect outlines

        ArrayList<BoundingPolygon> out = new ArrayList<>();
        for (Shape shape: shapes) {
            if (shape.size() < 100) { //Not significant size
                continue;
            }
            //Approximate polygons
            ArrayList<Point> points = shape.detectCorners(shape.size() / 20); //Epsilon is arbitrary, increase if not enough points detected
            if (points.size() == 4) { //Is quadrilateral
                out.add(new BoundingPolygon(points.get(0), points.get(1), points.get(2), points.get(3)));
            }
        }
        return out;
    }


}
