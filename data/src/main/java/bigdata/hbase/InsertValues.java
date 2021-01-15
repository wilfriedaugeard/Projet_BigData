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

import bigdata.entities.IBigDataObject;
import bigdata.util.Config;

import java.io.IOException;

public class InsertValues {

    public static final <T, U> void insert(Configuration config, List<Tuple2<T, U>> values, String tableName, String[] families, String[] columns) throws IOException {

        try (Connection connection = ConnectionFactory.createConnection(config);) {
            Table table = connection.getTable(TableName.valueOf(Config.TABLE_PREFIX + tableName));
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
                        Bytes.toBytes(columns[1)],
                        Bytes.toBytes(tuple._2.toString())
                );

                list.add(put);
                if (row == Config.MAX_LIST_SIZE) {
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
