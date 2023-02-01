package MediaProcessing.Data;


import MediaProcessing.Utils.Colors.Color;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Image sequence data
 * @param <DataType> Data to store for each pixel
 */
public class  Video<DataType extends Color>  {

    private ArrayList<Image<DataType>> frames; //data

    //metadata
    private int frame_rate;
    private int width, height;

    /**
     * Create a new empty video
     * @param width, height Dimensions of video(pixels)
     * @param frame_rate Frames per second
     */
    public Video(int width, int height, int frame_rate){
        this.width = width;
        this.height = height;
        this.frame_rate = frame_rate;
        frames = new ArrayList<>();
    }

    /**
     * Add a frame to the end of the video
     * @param frame Image that is same dimensions and color type as video
     */
    public void addFrame(Image<DataType> frame){
        if(frame.getWidth() != getWidth() || frame.getHeight() != getHeight()){
            throw new IllegalArgumentException("Frame dimensions must equal video dimensions");
        }
        frames.add(frame);
    }

    /**
     * Get a frame in the video
     * @param idx Frame #. Should be less than frame count.
     * Editing image will edit video
     */
    public Image<DataType> getFrame(int idx){
        return frames.get(idx);
    }

    /**
     * Get width in pixels
     */
    public int getWidth(){
        return width;
    }

    /**
     * Get height in pixels
     */
    public int getHeight(){
        return height;
    }

    /**
     * Get the total # of frames
     */
    public int getFrameCount(){
        return frames.size();
    }

    /**
     * Get the frame rate
     */
    public int getFrameRate(){
        return frame_rate;
    }

    /**
     * Apply an operation on each frame
     * @param consumer Operation
     */
    public void forEach(Consumer<Image<DataType>> consumer){
        for (Image<DataType> frame: frames) {
            consumer.accept(frame);
        }
    }

    /**
     * Get a parallel stream of all the frames
     * Use to speed up operations applied on all frames using multithreading
     */
    public Stream<Image<DataType>> parallelFrames(){
        return frames.parallelStream();
    }



}
