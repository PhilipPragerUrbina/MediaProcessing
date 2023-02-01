package MediaProcessing.Data;

import MediaProcessing.Utils.Colors.Color;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Image data
 * @param <DataType> Data to store for each pixel
 */
public class  Image<DataType extends Color>  {

    private DataType[][] data;
    private int width;
    private int height;

    /**
     * Create empty image with dimensions
     * @param width,height x and y dim
     */
    public Image(int width, int height){
        //todo invalid dim exception
        this.width = width;
        this.height = height;
        data = (DataType[][])new Color[width][height];
    }

    /**
     * Make a separate copy of the image
     * Has new array, so can be modified independently
     * @return New Image
     */
    public Image<DataType> makeCopy(){
        Image<DataType> copy = new Image<DataType>(width,height); //Create new image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                copy.data[x][y] = data[x][y]; //copy data(immutable so just assign)
            }
        }
        return copy;
    }


    /**
     * Create image from 1d buffer and dimensions
     * @param buffer 1d buffer of pixel data stored row after row
     * @param width,height x and y dim
     * Data will not be modified if image is modified
     *
     */
    public Image(DataType[] buffer, int width, int height){
        this(width,height); //Create array
        //todo null data and invalid dim ratio exception
        for (int i = 0; i < buffer.length; i++) {
            data[i/width][i%height] = buffer[i]; //Convert 1d to 2d coordinates
        }

    }

    /**
     * Create image from 2d array
     * @param data 2d array of image data.
     *  Data will be modified if image is modified
     */
    public Image(DataType[][] data){
        //todo null data exception
        this.data = data;
        this.width = data.length;
        this.height = data[0].length;
    }

    /**
     * Set the image data array to the one of the other image
     * @param other Other image
     *  Data will be modified if other image is modified
     *  Changes the width and height(can be used to resize image)
     */
    public void setData(Image<DataType> other){
        this.data = other.data;
        this.width = other.getWidth();
        this.height =other.getHeight();
    }




    /**
     * Set a pixel value
     * @param x,y coordinates
     * @param value Value to set
     */
    public void setPixel(int x, int y, DataType value){
        //todo bounds exception
        data[x][y] = value;
    }

    /**
     * Get a pixel value
     * @param x,y coordinates
     * @return Pixel Value
     */
    public DataType getPixel(int x,  int y){
        //todo bounds exception
        return data[x][y];
    }

    /**
     * Check if coordinates in bounds of image
     * @param x,y coordinates
     * @return True if in bounds
     */
    public boolean inBounds(int x, int y){
        return x > -1 && y > -1 && x < width && y < height;
    }

    /**
     * Get image width
     */
    public int getWidth(){
        return width;
    }

    /**
     * Get image height
     */
    public int getHeight(){
        return height;
    }

    /**
     * Mutable Pixel data for processing with streams
     * Since color is immutable, this mutable wrapper allows modification of image in stream
     */
    public static class Pixel<DataType>{
        private final int x;
        private final int y; //Read only coordinates

        Pixel(int x, int y, DataType color){
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public DataType color; //Color of pixel.
    }

    /**
     * Build a stream from the image
     * Useful for parallel operations using parallel().forEach or other built in stream shenanigans
     * @warning Modifications to pixel color does not edit source image, make sure to use applyStream() or setPixel() to apply the changes
     * @return A stream of pixels that contain colors
     */
    public Stream<Pixel<DataType>> getStream(){
        Stream.Builder<Pixel<DataType>> builder = Stream.builder(); //get builder
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                builder.accept(new Pixel(x, y, data[x][y])); //add pixels
            }
        }
        return builder.build();
    }

    /**
     * Apply pixel data to an image
     * @param pixel Coordinates and color to change in image
     *  Does not reference pixel in any way
     *   Make sure in bounds
     */
    public void setPixel(Pixel<DataType> pixel){
        setPixel(pixel.getX(),pixel.getY(), pixel.color);
    }

    /**
     * Apply color changes from stream pixels to source image
     * Simply calls setPixel on the color value of each pixel. Can also be done manually.
     * @param stream Stream from getStream()
     */
    public void applyStream(Stream<Pixel<DataType>> stream){
        stream.forEach(this::setPixel);
    }


    /**
     * Apply a simple operation on each pixel
     * Loops through all coordinates and gives pixel data
     * Similar to getStream but simpler
     * Make sure to apply changes
     * @param consumer Operation
     */
    public void forEach(Consumer<Pixel<DataType>> consumer){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                consumer.accept(new Pixel<>(x, y, data[x][y]));
            }
        }
    }
}
