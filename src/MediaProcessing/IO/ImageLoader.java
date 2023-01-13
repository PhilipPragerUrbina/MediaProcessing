package MediaProcessing.IO;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Color;
import MediaProcessing.Utils.Colors.RGBA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Load an image
 * @param <ColorType> Color to use
 */
public class ImageLoader <ColorType extends Color> {
    private BufferedImage raw_image; //java image

    /**
     * Read an image
     * @param filename Location of image
     * @throws IOException Error reading image
     */
    public ImageLoader(String filename) throws IOException {
        raw_image = ImageIO.read(new File(filename));
    }

    /**
     * Get the 2d array image
     * @param type Color class to use
     */
    public Image<ColorType> getImage(Class<ColorType> type){
        Image<ColorType> image = new Image<>(raw_image.getWidth(), raw_image.getHeight()); //Create image
        for (int x = 0; x < raw_image.getWidth(); x++) {
            for (int y = 0; y < raw_image.getHeight(); y++) {
                ColorType color = null;
                try {
                    color = type.getDeclaredConstructor().newInstance(); //Create color
                    java.awt.Color read_color = new  java.awt.Color(raw_image.getRGB(x,y)); //read color
                    color.setRGBA(new RGBA(read_color.getRed(),read_color.getGreen(),read_color.getBlue(), read_color.getAlpha())); //set color
                    image.setPixel(x,y, color);//copy over
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }


}
