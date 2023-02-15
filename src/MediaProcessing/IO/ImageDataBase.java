package MediaProcessing.IO;

import MediaProcessing.Converters.Converter;
import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Tracking.Similarity;
import MediaProcessing.Utils.Colors.Color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//todo xml file reading for image metadata
//import javax.xml.parsers.*; Xml parsing library

/**
 * Collection of images
 */
public class ImageDataBase<ColorType extends Color> {
    private ArrayList<Image<ColorType>> images = new ArrayList<>();
    final private Class<ColorType> color_class;

    /**
     * Create a collection of images
     * @param color_class Class of color type for file loading
     */
    public ImageDataBase(Class<ColorType> color_class) {
        this.color_class = color_class;
    }


    /**
     * Add image to collection
     * @param image Image to add(Does NOT make copy)
     * @return image id in collection
     */
    public int addImage(Image<ColorType> image){
        images.add(image);
        return images.size()-1;
    }

    /**
     * Load all images directly in folder
     * Assumes all contents of folder is same file extension
     */
    public void readFolder(File folder){
        if(folder.isDirectory()){
            for (final File file : folder.listFiles()) {
                readFile(file);
            }
        }
    }

    /**
     * read image from filename
     */
    public void readFile(File filename){
        try {
           addImage( new ImageLoader<ColorType>(filename.getPath()).getImage(color_class));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Write images as jpg to a folder with names corresponding to index
     * Folder must exist and be directory
     */
    public void writeFolderJpg(File folder){
        if(folder.exists() && folder.isDirectory()){
            for (int i = 0; i < images.size(); i++) {
                new ImageWriter(folder.getPath() + "/"+ i + ".jpg").writeJPG(images.get(i));
            }
        }
    }

    /**
     * Write images as png to a folder with names corresponding to index
     * Folder must exist and be directory
     */
    public void writeFolderPng(File folder){
        if(folder.exists() && folder.isDirectory()){
            for (int i = 0; i < images.size(); i++) {
                new ImageWriter(folder.getPath() + "/"+ i + ".png").writePNG(images.get(i));
            }
        }
    }

    /**
     * Get image based on id in collection
     */
    public Image<ColorType> getImage(int id){
        return images.get(id);
    }

    /**
     * Get the most similar image in the collection to the reference
     * @param reference Image to compare to
     * @param threshold Minimum similarity value. Smaller means more similarity required not to return null.
     * @return id of most similar image, or null if similarity is above threshold.
     */
    public Integer getMostSimilar(Image<ColorType> reference, double threshold){
        Similarity<ColorType> similarity = new Similarity<>(reference);
        Integer most_similar_idx = null;
        double smallest_similarity = threshold;

        for (int i = 0; i < images.size(); i++) {
            double similarity_error  = similarity.getCumalativeDifferenceOrientations(images.get(i));
            if(similarity_error < smallest_similarity){
                smallest_similarity = similarity_error;
                most_similar_idx = i;
            }
        }
        return most_similar_idx;
    }


    /**
     * Apply filter to all images in parallel
     * @param filter Filter to apply
     */
    public void applyFilter(Filter<ColorType> filter){
        images.parallelStream().forEach(filter::apply);
    }

    /**
     * Get a new collection of converted images
     * @param converter Converter to use
     * @param <NewType> Type to convert to
     * @param new_color_class Class of new color type for file loading
     * @return New collection
     */
     public <NewType extends Color> ImageDataBase<NewType> convert(Converter<ColorType, NewType> converter, Class<NewType> new_color_class){
            ImageDataBase<NewType> new_database = new ImageDataBase<NewType>(new_color_class);
         for (Image<ColorType> image: images) {
             new_database.addImage(converter.convert(image)); //Convert and add
         }
         return new_database;
    }







}
