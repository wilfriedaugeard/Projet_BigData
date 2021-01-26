package bigdata.hbase;

import scala.Tuple2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.HBaseConfiguration;
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
    public static final <T, U> void insert( JavaPairRDD<T, U> rdd, String tableName, String[] families, String[] columns) throws IOException {

        rdd.foreachPartition (iterator -> {
                try {
		   Configuration hconf =  HBaseConfiguration.create();
		   Connection connection = ConnectionFactory.createConnection(hconf);
                    BufferedMutator table = connection.getBufferedMutator(TableName.valueOf(Config.tablePrefix + tableName));
while(iterator.hasNext()){                    
Tuple2<T, U> tuple = iterator.next();
                    Put put = new Put(Bytes.toBytes(tuple._1.toString()));
                    put.addColumn(
                            Bytes.toBytes(families[1]),
                            Bytes.toBytes(columns[1]),
                            Bytes.toBytes(tuple._2.toString())
                    );
                    table.mutate(put);
}
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    System.exit(-1);
                }
            });
        }


    }
