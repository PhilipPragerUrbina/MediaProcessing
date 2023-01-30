package MediaProcessing.IO;

import MediaProcessing.Data.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Write an image
 */
public class ImageWriter {
    private final File file;

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
     * Set a bufferimage's pixels to the pixels from an image
     * @param out_image Set pixel values of this image
     * @param in_image The input pixel values
     */
    private static void setBufferedImage(BufferedImage out_image, Image in_image){
        for (int x = 0; x < out_image.getWidth(); x++) {
            for (int y = 0; y < out_image.getHeight(); y++) {
                out_image.setRGB(x,y,in_image.getPixel(x,y).getJavaColor().getRGB());
            }
        }
    }

    /**
     * Write the image to the location as png
     * @param image Image data to write
     */
    public void writePNG(Image image){
        BufferedImage out = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_ARGB);
       setBufferedImage(out,image);
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
        setBufferedImage(out,image);
        try {
            ImageIO.write(out,"jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
