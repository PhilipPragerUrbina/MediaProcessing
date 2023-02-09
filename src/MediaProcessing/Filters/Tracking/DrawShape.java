package MediaProcessing.Filters.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Tracking.BoundingPolygon;
import MediaProcessing.Tracking.Shape;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Vectors.Point;

/**
 * Draw a Shape on an image
 */
public class DrawShape<ColorType extends Color> implements Filter<ColorType> {
    private Shape shape;
    final private ColorType color;

    /**
     * Draw a shape
     * @param shape Shape to draw
     * @param color Color of shape
     */
    public DrawShape(Shape shape, ColorType color) {
        this.shape = shape;
        this.color = color;
    }

    /**
     * Set the shape to draw
     */
    public void setShape(Shape shape){
        this.shape = shape;
    }



    @Override
    public void apply(Image<ColorType> image) {
       shape.forEach(point -> {
           image.setPixel(point.getX(), point.getY(), color); //draw shape
       });
    }
}
