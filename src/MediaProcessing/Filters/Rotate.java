package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Rotate image by angle
 */
public class Rotate<ColorType extends Color> implements Filter<ColorType> {
    private final double angle; //angle radians
    private final double center_x;
    private final double center_y;
    private final ColorType bg_color;

    /**
     * Rotate image over origin
     * @param bg_color Color of background behind image
     * @param angle Angle to rotate(degrees)
     * @param center_x,center_Y The point to rotate around
     */
    public Rotate(double angle, double center_x, double center_y, ColorType bg_color) {
        this.angle = Math.toRadians(angle);
        this.center_x = center_x;
        this.center_y = center_y;
        this.bg_color = bg_color;
    }
    //todo change center of rotation

    @Override
    public void apply(Image<ColorType> image) {
        Image<ColorType> new_image =new Image<>(image.getWidth(), image.getHeight());
        for (int x = 0; x < new_image.getWidth(); x++) { //Iterate over new image
            for (int y = 0; y < new_image.getHeight(); y++) {
                new_image.setPixel(x,y, bg_color); //Set background color

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
                    ColorType color = image.getPixel((int)new_x, (int)new_y); //original image color
                    new_image.setPixel(x, y, color);
                }


            }
        }

        image.setData(new_image);
    }

}