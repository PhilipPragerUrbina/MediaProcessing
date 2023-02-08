package MediaProcessing.Filters.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Tracking.BoundingPolygon;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Vectors.Point;

/**
 * Draw a bounding polygon on an image
 */
public class DrawBounding<ColorType extends Color> implements Filter<ColorType> {
    private BoundingPolygon polygon;
    final private ColorType color;
    final private int width;
    final DrawMarker<ColorType> corner_marker;

    /**
     * Draw a bounding quadrilateral
     * @param polygon quadrilateral
     * @param color Color of lines
     * @param width How thick to make lines
     * @param corner_marker Marker to use for corners. Set to null if no corner markers.
     */
    public DrawBounding(BoundingPolygon polygon, ColorType color, DrawMarker<ColorType> corner_marker, int width) {
        this.polygon = polygon;
        this.color = color;
        this.width = width;
        this.corner_marker = corner_marker;
    }

    /**
     * Set the bounding to draw
     */
    public void setBounding(BoundingPolygon polygon){
        this.polygon = polygon;
    }



    @Override
    public void apply(Image<ColorType> image) {
        if(corner_marker != null){ //if specified
            //Draw corners
            for (Point corner : polygon.getCorners()) {
                corner_marker.setPosition(corner);
                corner_marker.apply(image);
            }
        }
        //todo draw lines
    }
}
