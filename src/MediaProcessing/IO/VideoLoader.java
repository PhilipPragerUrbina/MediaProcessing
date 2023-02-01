package MediaProcessing.IO;

import MediaProcessing.Data.Image;
import MediaProcessing.Data.Video;
import MediaProcessing.Utils.Colors.Color;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.common.io.IOUtils;
import org.jcodec.scale.AWTUtil;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Load a video
 * @param <ColorType> Color to use
 */
public class VideoLoader<ColorType extends Color> {
    private ArrayList<BufferedImage> frames; //java image

    /**
     * Read a video
     * @param filename Location of video
     * @throws JCodecException,IOException Error reading video
     */
    public VideoLoader(String filename) throws JCodecException, IOException {
        File file = new File(filename); //Get file
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file)); //Grab frames
        frames = new ArrayList<>();
        Picture picture;
        while (null != (picture = grab.getNativeFrame())) { //loop through frames
            frames.add(AWTUtil.toBufferedImage(picture));//Add frames to array
        }

    }

    /**
     * Get a video from video loader
     * @param type Color class to use
     */
    public Video<ColorType> getVideo(Class<ColorType> type){
        //todo read frame rate
        //todo check for empty
        Video<ColorType> video = new Video<>(frames.get(0).getWidth(), frames.get(0).getHeight(),24);

        for (BufferedImage original_frame: frames ) {
            //todo not reapat with image loader
            Image<ColorType> image = new Image<>(original_frame.getWidth(), original_frame.getHeight()); //Create image
            for (int x = 0; x < original_frame.getWidth(); x++) {
                for (int y = 0; y < original_frame.getHeight(); y++) {
                    ColorType color = null;
                    try {
                        java.awt.Color read_color = new  java.awt.Color(original_frame.getRGB(x,y)); //read color
                        color = type.getDeclaredConstructor(java.awt.Color.class).newInstance(read_color); //Create color using reflection(requires constructor with java color parameter)
                        image.setPixel(x,y, color);//copy over
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace(); //Template Color is invalid
                    }
                }
            }
            //todo this fills up memory. Maybe save as buffered image, and convert to our image format dynamically. borrowFrame(). returnFrame()
            video.addFrame(image);
        }
        return video;
    }



}
