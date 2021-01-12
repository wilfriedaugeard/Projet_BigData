package bigdata.util;

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

import bigdata.entities.IBigDataObject;

import java.io.IOException;

public class InsertValues {
    private static final int MAX_LIST_SIZE = 100;
    private static final String tablePrefix = "augeard-tarmil-";

    public static final <U> List<Tuple2<Integer, U>> createFromJavaRDD(JavaRDD<U> rdd) {
        List<Tuple2<Integer, U>> list = new ArrayList<>();
        rdd.foreach(item -> list.add(new Tuple2<Integer, U>(new Integer(item.hashCode()), item)));
        return list;
    }

    public static final <T, U> List<Tuple2<T, U>> createFromJavaPairRDD(JavaPairRDD<T, U> rdd) {
        List<Tuple2<T, U>> list = new ArrayList<Tuple2<T, U>>();
        rdd.foreach(item -> list.add(new Tuple2<T, U>(item._1, item._2)));
        return list;
    }

    public static final <T, U> void insert(Configuration config, List<Tuple2<T, U>> values, String tableName, String column1, String column2) throws IOException {

        try (Connection connection = ConnectionFactory.createConnection(config);) {
            Table table = connection.getTable(TableName.valueOf(tablePrefix + tableName));

            ArrayList<Put> list = new ArrayList<Put>();
            for (int row = 0; row < values.size(); row++) {

                Tuple2<T, U> tuple = values.get(row);

                Put put = new Put(Bytes.toBytes(Integer.toString(row)));

                put.add(
                        Bytes.toBytes(tuple._1.getClass().getSimpleName()),
                        Bytes.toBytes(column1),
                        Bytes.toBytes(tuple._1.toString())
                );

                put.add(
                        Bytes.toBytes(tuple._2.getClass().getSimpleName()),
                        Bytes.toBytes(column2),
                        Bytes.toBytes(tuple._1.toString())
                );

                list.add(put);

                if (row == MAX_LIST_SIZE) {
                    table.put(list);
                    list.clear();
                }
            }
            table.put(list);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
    }


}
