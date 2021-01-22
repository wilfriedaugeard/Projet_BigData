
import bigdata.entities.*;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

public class DisplayResult {

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


    private static void displayResultJavaRDD(JavaRDD<IBigDataObject> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

    private static void displayResultJavaPairRDDInt(JavaPairRDD<IBigDataObject, Long> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

    private static void displayResultJavaPairRDDSet(JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }
}