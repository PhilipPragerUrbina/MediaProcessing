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
     * todo clear or check that it only runs once
     */
    public void run(){
        for (int i = 0; i < 10; i++) { //todo switch to detecting no movemnt
            for (Cluster<PositionType> cluster: clusters) { //For each cluster
                cluster.clear();;
            }
            for (Point<PositionType,ValueType> point: points) { //For each point
                point.setCluster(getClosestCluster(point));
            }
            for (Cluster<PositionType> cluster: clusters) { //For each cluster
                cluster.update();;
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
     * Get the clustered position of a point
     * @param i index
     *          Will be error if algorithm not run yet
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
     * Get the value of a point
     * @param i index
     */
    public ValueType getValue(int i){
        return points.get(i).getValue();
    }

}
