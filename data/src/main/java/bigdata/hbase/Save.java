package bigdata.hbase;

import org.apache.hadoop.conf.Configuration;

import bigdata.entities.*;
import bigdata.builder.BuilderTable;

import scala.Tuple2;

import java.util.List;

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
                    list.add(index, item);
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
