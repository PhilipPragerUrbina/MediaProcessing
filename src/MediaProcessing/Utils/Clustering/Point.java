package MediaProcessing.Utils.Clustering;

import MediaProcessing.Utils.Vectors.Vector;

/**
 * A point for clustering
 * @param <PositionType> Type of vector to use for position. Example: 3d vector for rgb color
 * @param <ValueType> Value that the point contains. Example: 2d coordinate(pixel) for image processing
 */
public class Point <PositionType extends Vector<PositionType>, ValueType > {
    private PositionType position;  //Position(immutable)
    private  ValueType value; //Value
    private Cluster<PositionType> current_cluster;

    /**
     * Create a point
     * @param position Position in space
     * @param value Value it holds
     */
    public Point(PositionType position, ValueType value) {
        this.position = position;
        this.value = value;
    }

    /**
     * Get the position
     */
    public PositionType getPosition() {
        return position;
    }

    /**
     * Get the value
     */
    public ValueType getValue() {
        return value;
    }

    /**
     * Get distance between cluster and a point
     */
    public double distance(Cluster<PositionType> cluster){
        return position.distance(cluster.getCenter());
    }

    /**
     * Set the cluster the point belongs to
     * Point will register itself with the cluster
     */
    public void setCluster(Cluster<PositionType> cluster){
        cluster.registerPoint(this.getPosition());
        current_cluster = cluster;
    }


    /**
     * Get eh current cluster
     * @return May be null
     */
    public Cluster<PositionType> getCluster() {
        return current_cluster;
    }
}
