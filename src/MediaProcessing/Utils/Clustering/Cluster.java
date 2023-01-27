package MediaProcessing.Utils.Clustering;

import MediaProcessing.Utils.Vectors.Vector;

import java.util.Random;

/**
 * A cluster for K-means clustering
 * @param <PositionType> Type to use for the center of the cluster in space
 */
public class Cluster <PositionType extends Vector<PositionType>>{
    private PositionType center; //Center of cluster
    private PositionType average_center; //Center for calculations
    private int num_points = 0; //Points currently assinged to cluster
    private boolean stable = false; //If it has stopped changing

    /**
     * Create a cluster at a center
     */
    public Cluster(PositionType center){
        this.center = center;
        this.average_center = center;
    }

    /**
     * Create a cluster with a randomly initialized center between two other positions
     * @param min Minimum random center components(Inclusive)
     * @param max Maximum random center components(Exclusive)
     * @param random Random to use
     */
    public Cluster(PositionType min, PositionType max, Random random){
        this(max.subtract(min).randomRange(random).add(min)); // equivalent to random*(max-min) + min
    }

    /**
     *  Tell the cluster that no points think they want to be part of it any more
     *  Reset cluster center and point count
     */
    public void clear(){
        num_points = 0;
        average_center = center.subtract(center);//Set average center to 0 in a general way
    }

    /**
     * Get current center. Is not affected by added points until apply()
     */
    public PositionType getCenter(){
        return center;
    }

    /**
     *  Set the center to the average of the registered points
     */
    public void update(){
        stable = center.equals(average_center);  //Is stable if the center has not changed
        center = average_center;
    }

    /**
     * True if center has not changed in last update.
     */
    public boolean isStable(){
        return stable;
    }



    /**
     * Register a new point
     * @param new_point Point that belongs to cluster
     *  Is called by point when cluster is registered to it
     */
    public void registerPoint(PositionType new_point){
        num_points++; //Update num points
        average_center = (average_center.multiply(num_points).add(new_point)).divide(num_points+1); //take a continuous average average. (size*average + value)/(size+1)
    }
}
