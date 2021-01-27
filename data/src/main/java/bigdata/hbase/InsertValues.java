package bigdata.hbase;

import scala.Tuple2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Iterator;

import bigdata.entities.IBigDataObject;
import bigdata.util.Config;

import java.io.IOException;


public class InsertValues {


    /**
     * Create the List of values of a Pair RDD
     *
     * @param rdd : the RDD to convert into list
     * @param <T> : the class of the first value of the tuple
     * @param <U> : the class of the second value of the tuple
     * @return : the new list of values
     * @throws Exception
     */
    public static final <T, U> List<Tuple2<T, U>> convert(JavaPairRDD<T, U> rdd) throws Exception {
        List<Tuple2<T, U>> values = new ArrayList<>();
        rdd.cache();
        long count = rdd.count();

        try {
            if (count > Config.MAX_RDD_VALUES) {
                values.addAll(rdd.take(Config.MAX_RDD_VALUES));
            } else if (rdd.count() > Config.TOP_K) {
                rdd.foreach(item -> {
                    values.add(item);
                });
            } else {
                values.addAll(rdd.collect());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n\n\n count : " + values.size() + "\n\n");
        return values;
    }


    /**
     * Insert the values of a list (representing the RDD) into a table in Hbase
     *
     * @param config
     * @param values    : the list of values
     * @param tableName : the name of the table in which we need to insert the values
     * @param families  : array containing the class of the two values
     * @param columns   : array containing the "type" of the two values
     * @param <T>       : the class of the first value of the tuple
     * @param <U>       : the class of the second value of the tuple
     * @throws IOException
     */
    public static final <T, U> void insert(Configuration config, List<Tuple2<T, U>> values, String tableName, String[] families, String[] columns) throws IOException {

        try (Connection connection = ConnectionFactory.createConnection(config);) {
            Table table = connection.getTable(TableName.valueOf(Config.tablePrefix + tableName));
            ArrayList<Put> list = new ArrayList<Put>();

            for (int row = 0; row < values.size(); row++) {
                Tuple2<T, U> tuple = values.get(row);
                Put put = new Put(Bytes.toBytes(Integer.toString(row)));

                put.add(
                        Bytes.toBytes(families[0]),
                        Bytes.toBytes(columns[0]),
                        Bytes.toBytes(tuple._1.toString())
                );

                put.add(
                        Bytes.toBytes(families[1]),
                        Bytes.toBytes(columns[1]),
                        Bytes.toBytes(tuple._2.toString())
                );

                list.add(put);
                if (row % Config.MAX_LIST_SIZE == 0) {
                    table.put(list);
                    list.clear();
                }
                if (row == 500000) {
                    break;
                }
            }

            table.put(list);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
    }


}
