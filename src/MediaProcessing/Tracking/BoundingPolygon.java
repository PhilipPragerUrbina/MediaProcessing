package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector2;

/**
 * Represent a 4 corner bounding quadrilateral.
 * immutable
 */
public class BoundingPolygon {
    private Point[] corners = new Point[4];

    /**
     * Create a bounding polygon from 4 corners
     * a->b->c->d->a
     * a is bottom left
     * b is top left
     */
    public BoundingPolygon(Point a,Point b,Point c,Point d){
        corners[0] = a;
        corners[1] = b;
        corners[2] = c;
        corners[3] = d;
    }

    /**
     * Get copy of corners
     */
    public Point[] getCorners(){
        return corners.clone();
    }

    //todo out of bounds color rather than error

    /**
     * Get a new image that is perspective corrected using this polygon
     * It is skewed to undo the polygon and give rectangle image
     * @param original Original image
     * @return Skewed image
     * Corners are points on the original image. Must be in bounds or out of bounds error will occur.
     * @param width,height dimensions of output image
     */
    public <ColorType extends Color> Image<ColorType> skewCorrectedImage(Image<ColorType> original, int width, int height){
        Image<ColorType> corrected = new Image<>(width,height); //Make output image

        //We transform the destination point, to a coordinate on the source(original) image, so there are no holes
        corrected.forEach(pixel -> { //Iterate over image
            Vector2 destination_point = new Vector2(pixel.getX(),pixel.getY()); //get vector representation of destination point
            //Homography seems complicated so lets use Lines! instead,(my own algorithm)
            //X axis first. Line going along top of image.
            double t_x = destination_point.getX(); //The t value of the parametric lines defining the top and bottom of the image. How far the destination point is along the line.
            // For the destination image this is just how far it is horizontally, since the corrected line is parallel to the x-axis.
            //Top horizontal edge of polygon is line between two top corners. make sure to divide t_x by the width to make sure t matches with the final coordinate system.
            Vector2 top_point = new Vector2(corners[1]).add(new Vector2(corners[2].subtract(corners[1])).multiply(t_x/width)); //Get a point that is along the top horizontal edge of the polygon.
            Vector2 bottom_point = new Vector2(corners[0]).add(new Vector2(corners[3].subtract(corners[0])).multiply(t_x/width)); //Get a point that is along the bottom horizontal edge of the polygon.
            //Now that we have a point for the top and bottom of the image using horizontal lines, representing the x-axis. Now all we need to do is connect the two points with a vertical line representing the y-axis.
            double t_y = destination_point.getY(); //The t value of the parametric line defining the line between the two horizontal points. How far the destination point is along the horizontal line.
            Vector2 source_point =  top_point.add(bottom_point.subtract(top_point).multiply(t_y/height)); //Get the final source point. line goes top to bottom, since origin is at the top
            //Now copy over the color from the source to the destination
            pixel.color = original.getPixel((int)source_point.getX(),(int)source_point.getY());
            corrected.setPixel(pixel);//Save changes
        });
        return corrected;
    }

    /**
     * Same as normal skew correction, but it does a basic estimation of width and height.
     * It will be flat, and contain an appropriate pixel density, but is not guaranteed to have the same aspect ratio as the actual object. Will probably be too wide or too tall.
     */
    public <ColorType extends Color> Image<ColorType> skewCorrectedImage(Image<ColorType> original){
        //Get final image width and height
        int height = (int)Math.ceil(new Vector2(corners[0]).distance(new Vector2(corners[1]))); //Side edge length
        int width = (int)Math.ceil(new Vector2(corners[1]).distance(new Vector2(corners[2]))); //Top edge length
        return skewCorrectedImage(original,width,height);
    }

    //todo automatic estimation of perspective correct width and height

    //todo image skew transform

}
