package bigdata.util;

import bigdata.entities.*;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import java.util.Set;

public class DisplayResult {

    /**
     * Function that permit to determined the type of function to call to display the RDD values depending of the type of RDD
     *
     * @param o : the RDD
     * @param k : the number of values to display
     */
    public static void displayResult(Object o, int k) {
        switch (o.getClass().getSimpleName()) {
            case "JavaRDD":
                displayResultJavaRDD((JavaRDD<IBigDataObject>) o, k);
                break;
            case "JavaPairRDD":
                try {
                    JavaPairRDD<IBigDataObject, Long> rdd = (JavaPairRDD<IBigDataObject, Long>) o;
                    displayResultJavaPairRDDInt(rdd, k);
                } catch (Exception e) {
                    try {
                        JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd = (JavaPairRDD<IBigDataObject, Set<IBigDataObject>>) o;
                        displayResultJavaPairRDDSet(rdd, k);
                    } catch (Exception e2) {
                    }
                } finally {
                    break;
                }
            default:
                break;
        }
    }

    /**
     * Display the k first values of a JavaRDD
     *
     * @param rdd :  the RDD
     * @param k   : the number of values to display
     */
    private static void displayResultJavaRDD(JavaRDD<IBigDataObject> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

    /**
     * Display the k first values of a JavaPairRDD with a Int as a second value of the Tuple
     *
     * @param rdd : the RDD
     * @param k   : the number of values to display
     */
    private static void displayResultJavaPairRDDInt(JavaPairRDD<IBigDataObject, Long> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

    /**
     * Display the k first values of a JavaPairRDD with a Set as the second value of the Tuple
     *
     * @param rdd : the RDD
     * @param k   : the number of values to display
     */
    private static void displayResultJavaPairRDDSet(JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }
}