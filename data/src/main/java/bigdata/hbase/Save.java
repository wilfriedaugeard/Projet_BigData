package bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.api.java.JavaPairRDD;

import bigdata.entities.*;
import bigdata.builder.BuilderTable;
import bigdata.util.Config;

import scala.Tuple2;

import java.util.List;
import java.util.ArrayList;

public class Save {

    /**
     * Call the function needed to create a specific table and insert values into the table
     *
     * @param hConf     : the Hbase configuration
     * @param tableName : the name of the table to create and fill
     * @param families  : array containing the class of the two values
     * @param columns   : array containing the "type" of the two values
     * @param values    :  the list of values to insert
     * @param <T>       : the class of the first value of the tuple
     * @param <U>       : the class of the second value of the tuple
     * @throws Exception
     */
    public static final <T, U> void apply(Configuration hConf, String tableName, String[] families, String[] columns, JavaPairRDD<T, U> rdd,) throws Exception {
        try {
            BuilderTable.createTable(
                    hConf,
                    tableName,
                    families[0],
                    families[1]
            );

            InsertValues.insert(
                    hConf,
                    rdd,
                    tableName,
                    families,
                    columns
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
