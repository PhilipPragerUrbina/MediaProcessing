package MediaProcessing.IO;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Write an image
 */
public class ImageWriter {
    private File file;

    /**
     * Create an image writer
     * @param filename Location
     */
    public ImageWriter(String filename){
        file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write the image to the location as png
     * @param image Image data to write
     */
    public void writePNG(Image image){
        BufferedImage out = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < out.getWidth(); x++) {
            for (int y = 0; y < out.getHeight(); y++) {
                out.setRGB(x,y,image.getPixel(x,y).getJavaColor().getRGB());
            }
        }
        try {
            ImageIO.write(out,"png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the image to the location as jpg
     * @param image Image data to write
     */
    public void writeJPG(Image image){
        BufferedImage out = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < out.getWidth(); x++) { //todo not repeat for different formats
            for (int y = 0; y < out.getHeight(); y++) {
                out.setRGB(x,y,image.getPixel(x,y).getJavaColor().getRGB());
            }
        }
        try {
            ImageIO.write(out,"jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
