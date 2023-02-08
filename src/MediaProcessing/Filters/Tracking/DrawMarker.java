package MediaProcessing.Filters.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Point;

/**
 * Mark a location on an image
 */
public class DrawMarker<ColorType extends Color> implements Filter<ColorType> {
    private Point position;
    final private ColorType color;
    final private int radius;
    final private int width;

    /**
     * Draw a hollow circle on an image
     * @param position Coordinate of circle
     * @param color Color of circle
     * @param radius Radius of circle in pixels
     * @param width How thick to make circle line
     */
    public DrawMarker(Point position, ColorType color, int radius, int width) {
        this.position = position;
        this.color = color;
        this.radius = radius;
        this.width = width;
    }

    /**
     * Set the position to draw the marker at
     */
    public void setPosition(Point position){
        this.position = position;
    }



    @Override
    public void apply(Image<ColorType> image) {
        //For each angle
        for (double angle = 0; angle < 360; angle++) {
            //For each ring to make circle line
            for (double new_radius = radius-width; new_radius < radius; new_radius++) {
                //Convert angle to radians, get position on unit circle, scale, and finally translate
                int x = position.getX() + (int)(new_radius * Math.cos(angle * Math.PI / 180.0));
                int y = position.getY() + (int)(new_radius * Math.sin(angle * Math.PI / 180.0));
                if(image.inBounds(x,y)){ //Check if inside image
                    image.setPixel(x,y,color);
                }
            }
        }
    }
}
