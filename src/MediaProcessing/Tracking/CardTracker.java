package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Tracking.DrawBounding;
import MediaProcessing.Filters.Tracking.DrawMarker;
import MediaProcessing.Utils.Colors.HSV;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Point;

import java.awt.*;
import java.util.ArrayList;

/**
 * Track cards in video
 */
public class CardTracker {

    /**
     * Data being tracked
     */
    private  class Card {
        /**
         * Create  a card with a face and a positions
         */
        public Card(Image<HSV> card_face, BoundingPolygon last_position) {
            this.card_face = card_face;
            this.last_position = last_position;
            num_untracked = 0;
            display_color = new HSV(Math.random(),Math.random(), Math.random()); //Unique display color
        }

        /**
         * Update card values
         * @param card_face
         * @param last_position
         */
        public  void update(Image<HSV> card_face, BoundingPolygon last_position) {
            this.card_face = card_face;
            this.last_position = last_position;
            num_untracked = 0;
        }

        /**
         * Update card if not found
         */
        public void update(){
            num_untracked++;
        }

        public  Image<HSV> card_face;
        public BoundingPolygon last_position;
        public int num_untracked ; //# of sequential frames where card was not found
        public HSV display_color;
    }

    private int frame_count = 0;
    private final ArrayList<Card> cards = new ArrayList<>();
    private final int max_untracked_frames;
    private final CardDetector detector;
    private final double max_movement_distance;

    /**
     * Get number of frames tracked so far
     * Useful for percent completed
     */
    public int getFrameCount(){
        return frame_count;
    }

    /**
     * Tack cards in video
     * @param max_untracked_frames Max frames a card can go untracked before it is forgotten
     * @param detector Detector to detect cards
     * @param max_movement_distance Max distances that a card could move from frame to frame without losing tracking in pixels
     */
    public CardTracker(int max_untracked_frames, CardDetector detector, double max_movement_distance) {
        this.max_untracked_frames = max_untracked_frames;
        this.detector = detector;
        this.max_movement_distance = max_movement_distance;
    }

    /**
     * Track the next frame form video or video stream
     */
    public void trackFrame(Image<HSV> frame){
        frame_count++;
        ArrayList<BoundingPolygon> polygons = detector.detect(frame); //Get polygons
        //Match to current cards
        for(Card card : cards){
            //Get closest polygon todo card similarity
            Point card_center = card.last_position.getCenter();
            BoundingPolygon closest = null;
            double closest_dist = max_movement_distance;
            for (BoundingPolygon polygon: polygons) {
                Point center = polygon.getCenter(); //todo compute once per polygon
                double distance = card_center.distance(center);
                if (distance < closest_dist){
                    closest_dist = distance;
                    closest = polygon;
                }
            }
            if(closest == null){
                card.update();
            }else {
                card.update(null,closest);
                //todo find polygons that do not correspond to any card(remaining polygons)
               // polygons.remove(closest);
            }
        }
        for (BoundingPolygon remaining_polygon: polygons) {
            if(frame_count == 1) {     //todo look for new cards every frame, not just first
                cards.add(new Card(null,remaining_polygon));
            }
        }
       // cards.removeIf(card -> card.num_untracked > max_untracked_frames);
    }

    /**
     * Overlay the card bounding
     * @param frame
     */
    public void drawCardPositions(Image<HSV> frame){
        for (Card card : cards) {
            new DrawBounding<HSV>(card.last_position,null, new DrawMarker<>(null,card.display_color,7,3 ),10).apply(frame);
        }
    }




}
