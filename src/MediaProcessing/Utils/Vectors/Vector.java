package MediaProcessing.Utils.Vectors;

import java.util.Random;

/**
 * N dimensional vector interface
 * Immutable
 * @param <Type> Actual underlying vector type
 */
public interface Vector<Type> {

    /**
     * Distance between this vector and the other
     */
    double distance(Type other);

    /**
     * Get magnitude of vector
     */
    double length();

    /**
     * Get a unit vector representation
     */
    Type normalized();

    /**
     * Get the component wise sum of two vectors
     */
    Type add(Type other);

    /**
     * Get the component wise subtraction of two vectors
     */
    Type subtract(Type other);

    /**
     * Get the component wise product of two vectors
     */
    Type multiply(Type other);

    /**
     * Get the component wise division of two vectors
     */
    Type divide(Type other);

    /**
     * Get the dot product
     */
    double dot(Type other);

    /**
     * Get a new vector that has random components in a range defined by this vector
     * @param random Random to use
     * @return Random vector in range of (0)-(Corresponding component of this vector exclusive)
     */
     Type randomRange(Random random);

    /**
     * multiply by scalar
     */
     Type multiply(double scalar);

    /**
     * add by scalar
     */
    Type add(double scalar);

    /**
     * divide by scalar
     */
    Type divide(double scalar);

    /**
     * subtract by scalar
     */
    Type subtract(double scalar);



}
