package MediaProcessing.Filters.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Data.Video;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Filters.Video.VideoFilter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector3;

import java.util.ArrayList;

/**
 * Apply a marker animation to a video
 */
public class VideoMarker<ColorType extends Color> implements VideoFilter<ColorType> {

    private final DrawMarker<ColorType> marker;
    private final ArrayList<Point> positions;

    /**
     * Draw a series of markers positions on a video.
     * @param marker How the marker should look like
     * @param positions Position of point at each frame
     */
    public VideoMarker(DrawMarker<ColorType> marker, ArrayList<Point> positions) {
        this.marker = marker;
        this.positions = positions;
    }


    @Override
    public void apply(Video<ColorType> video) {

        //Iterate over frames
        for (int i = 0; i < Math.min(video.getFrameCount(), positions.size()); i++) { //Apply as many positions as possible
            Image<ColorType> frame = video.getFrame(i); //Get the frame
            marker.setPosition(positions.get(i)); //Get the next position
            marker.apply(frame); //Draw the marker

        }

    }




}
