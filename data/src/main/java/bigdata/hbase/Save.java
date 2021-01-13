package bigdata.util;

import org.apache.hadoop.conf.Configuration;

import bigdata.entities.*;
import bigdata.builder.BuilderTable;

import scala.Tuple2;

import java.util.List;

public static class Save {

    public static final void apply(Configuration hConf, String tableName, String[] families, String[] columns, List<Tuple2<T, U>> values) throws Exception {
        try {
            BuilderTable.createTable(
                    hConf,
                    tableName,
                    families[0],
                    families[1]
            );

            InsertValues.insert(
                    hConf,
                    values,
                    tableName,
                    columns[0],
                    columns[1]
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}