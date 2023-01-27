package MediaProcessing.Utils.Clustering;

import MediaProcessing.Utils.Vectors.Vector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Perform K means clustering
 * @param <PositionType> Type of vector to use for position. Example: 3d vector for rgb color
 *  @param <ValueType> Value that the point contains. Example: 2d coordinate(pixel) for image processing
 */
public class KMeans <PositionType extends Vector<PositionType>, ValueType > {
    private ArrayList<Point<PositionType,ValueType>> points;
    private ArrayList<Cluster<PositionType>> clusters;
    boolean has_run = false;

    /**
     *  Init k means clustering algorithm
     * @param k How many clusters
     * @param min minimum position value(Inclusive)
     * @param max maximum position value(Exclusive)
     */
    public KMeans(int k, PositionType min, PositionType max){
        //init arrays
        points = new ArrayList<>();
        clusters = new ArrayList<>(k);
        //Init clusters
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            clusters.add(new Cluster<>(min, max,random));
        }
    }

    /**
     * Add data point
     * @param position Position in space
     * @param value Value it contains
     */
    public void addData(PositionType position, ValueType value){
        points.add(new Point<>(position,value));
    }

    /**
     * Run the algorithm
     * Can only be run once
     */
    public void run(){
        if(has_run){ //Check to make sure everything has been cleared
            throw new UnsupportedOperationException("Each K-Means instance only run once.");
        }
        has_run = true;

        //todo multithreading or other optimization
        while (true) { //While the clusters are still moving
            for (Cluster<PositionType> cluster: clusters) { //For each cluster
                cluster.clear(); //Clear clusters
            }
            for (Point<PositionType,ValueType> point: points) { //For each point
                point.setCluster(getClosestCluster(point)); //Assign to cluster
            }
            int stable_count = 0; //Number of stable clusters
            for (Cluster<PositionType> cluster: clusters) { //For each cluster.
                //todo see if this can be combined with first loop to make code cleaner
                cluster.update(); //Update clusters
                stable_count += cluster.isStable() ? 1 : 0; //Add to stable count if stable
            }
            if(stable_count == clusters.size()){ //All clusters are stable
                break; //Stop algorithm
            }
        }
    }

    /**
     * Get the cluster with the least distance
     * @param point Point to compare to
     * @return Closest cluster
     */
    private Cluster<PositionType> getClosestCluster(Point<PositionType,ValueType> point) {
        double closest_distance = Double.MAX_VALUE; //todo start with first
        Cluster<PositionType> closest = null;

        for (Cluster<PositionType> cluster: clusters) { //For each cluster
            double distance = point.distance(cluster);
            if(distance < closest_distance){
                closest_distance = distance;
                closest = cluster;
            }
        }
        return closest;
    }

    /**
     * Get the clustered(cluster center) position of a point
     * @param i index
     *   Only call after run or explode
     */
    public PositionType getClusteredPosition(int i){
        return points.get(i).getCluster().getCenter();
    }

    /**
     * Get the orginal position of a point
     * @param i index
     */
    public PositionType getOriginalPosition(int i){
        return points.get(i).getPosition();
    }

    /**
     * Get the number of points or the maximum index for accessing point data
     */
    public int getNumPoints(){
        return points.size();
    }


    /**
     * Get the value of a point
     * @param i index
     */
    public ValueType getValue(int i){
        return points.get(i).getValue();
    }

}
