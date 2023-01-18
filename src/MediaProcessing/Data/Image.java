package MediaProcessing.Data;

import MediaProcessing.Utils.Color;

import java.lang.reflect.Array;
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
                copy.data[x][y] = (DataType) data[x][y].clone(); //copy data
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
        this.width = data.length;
        this.height = data[0].length;
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
     * Build a stream from the image
     * Useful for parallel operations using parallel().forEach or other built in stream shenanigans
     * @warning Make sure to set values, not create new object if you want to modify the source image
     * @return A stream of colors
     */
    public Stream<DataType> getStream(){
        Stream.Builder<DataType> builder = Stream.builder(); //get builder
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                builder.accept(data[x][y]);
            }
        }
        return builder.build();
    }




}
