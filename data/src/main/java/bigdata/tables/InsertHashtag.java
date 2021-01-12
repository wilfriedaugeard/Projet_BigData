package bigdata;


import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

public class InsertHashtag{
    public void apply(HBaseConfiguration config, javaRDD, String tablename) {
        Job newAPIJobConfiguration1 = Job.getInstance(config);
        newAPIJobConfiguration1.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, tablename);
        newAPIJobConfiguration1.setOutputFormatClass(org.apache.hadoop.hbase.mapreduce.TableOutputFormat.class);
        

        JavaPairRDD<ImmutableBytesWritable, Put> hbasePuts = javaRDD.mapToPair(
            new PairFunction<Row, ImmutableBytesWritable, Put>() {
                @Override
                public Tuple2<ImmutableBytesWritable, Put> call(Row row) throws Exception {
        
                    Put put = new Put(Bytes.toBytes(row.getString(0)));
                    put.add(Bytes.toBytes("hashtag"), Bytes.toBytes("hashtag-value"), Bytes.toBytes(row.getString(1)));
                    put.add(Bytes.toBytes("hashtag"), Bytes.toBytes("count"), Bytes.toBytes(row.getString(2)));
                    
                return new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(), put);    
                }
            }
        );

        hbasePuts.saveAsNewAPIHadoopDataset(newAPIJobConfiguration1.getConfiguration());
    }
}


