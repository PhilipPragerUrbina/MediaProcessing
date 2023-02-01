package MediaProcessing.IO;

import MediaProcessing.Data.Image;
import MediaProcessing.Data.Video;
import org.jcodec.api.awt.AWTSequenceEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Write a video
 */
public class VideoWriter {
    private final File file;

    /**
     * Create a video writer
     * @param filename Location
     */
    public VideoWriter(String filename){
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
    //todo not repeat with image writer

    /**
     * Write the video to the location
     * @param video video data to write
     * @exception IOException Error writing video
     */
    public void writeVideo(Video video) throws IOException {
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(file, video.getFrameRate()); //Create encoder

        for (int i = 0; i < video.getFrameCount(); i++) { //Loop through frames
            Image frame = video.getFrame(i); //Get frame

            //convert to buffered image
            BufferedImage image = new BufferedImage(frame.getWidth(),frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
            setBufferedImage(image,frame);

            encoder.encodeImage(image);
        }
        encoder.finish();


    }


}
