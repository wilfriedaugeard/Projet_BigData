package bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import bigdata.entities.*;
import bigdata.builder.BuilderTable;
import bigdata.util.Config;

import scala.Tuple2;

import java.util.List;
import java.util.ArrayList;

public class Save {

    public static final <T, U> void apply(Configuration hConf, String tableName, String[] families, String[] columns, JavaPairRDD<T, U> rdd) throws Exception {
        try {
            BuilderTable.createTable(
                    hConf,
                    tableName,
                    families[0],
                    families[1]
            );

            List<Tuple2<T, U>> values;
            if (rdd.count() > Config.TOP_K) {
                values = new ArrayList<>();
                int index = 0;
                rdd.foreach(item -> {
                    values.add(index, item);
                    index++;
                });

            } else {
                values = rdd.collect();
            }

            InsertValues.insert(
                    hConf,
                    values,
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
