package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Rotate image by angle
 */
public class Rotate implements Filter<RGBA> {
    private final double angle; //angle radians
    private final double center_x;
    private final double center_y;

    /**
     * Rotate image over origin
     *
     * @param angle Angle to rotate(degrees)
     * @param center_x,center_Y The point to rotate around
     */
    public Rotate(double angle, double center_x, double center_y) {
        this.angle = Math.toRadians(angle);
        this.center_x = center_x;
        this.center_y = center_y;
    }
    //todo change center of rotation

    @Override
    public void apply(Image<RGBA> image) {
        Image<RGBA> new_image =new Image<>(image.getWidth(), image.getHeight());
        for (int x = 0; x < new_image.getWidth(); x++) { //Iterate over new image
            for (int y = 0; y < new_image.getHeight(); y++) {
                new_image.setPixel(x,y, new RGBA()); //Set background color

                //Global coordinates to object(original image) coordinates

                //Subtract center to make relative to origin
                double o_x = x - center_x;
                double o_y = y - center_y;
                //rotate point
                double new_x =(Math.cos(angle) * o_x - Math.sin(angle) * o_y);
                double new_y =(Math.sin(angle)  * o_x + Math.cos(angle) * o_y);

                //add center again
                 new_x +=center_x;
                 new_y += center_y;

                if(image.inBounds((int)new_x,(int)new_y)){ //If in bounds of original
                    RGBA color = image.getPixel((int)new_x, (int)new_y); //original image color
                    new_image.setPixel(x, y, color);
                }


            }
        }

        image.setData(new_image);
    }

}