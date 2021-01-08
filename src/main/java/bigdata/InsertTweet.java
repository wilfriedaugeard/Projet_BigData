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
import org.apache.hadoop.util.Tool;

import java.io.IOException;

public class InsertTweet extends Configured implements Tool{
    private static final byte[] TABLE_NAME = Bytes.toBytes("Big-data-project");
    private static final byte[][] FAMILIES = {
        Bytes.toBytes("create at"),
        Bytes.toBytes("id"),
        Bytes.toBytes("text"),
        Bytes.toBytes("in reply to statys id"),
        Bytes.toBytes("in replu to user id"),
        Bytes.toBytes("in reply de screen name")
    };

    public static class SimpleMapper extends Mapper<Object, Text, Text, NullWritable>{
        public void map(Object key, org.w3c.dom.Text value, Context context) throws IOException, InterruptedException{
            context.write(value, NullWritable.get());
        }
    }

    public static class SimpleReducer extends TableReducer<Text, NullWritable,ImmutableBytesWritable>{
        Table table;
        int row;

        public void old_setup(Context context) throws IOException {
            Connection connection = ConnectionFactory.createConnection(context.getConfiguration());
            table = connection.getTable(TableName.valueOf(TABLE_NAME));
            row = 0;
        }

        public Put insertTweet(String line, String row){
            String tweet = value.toString();
            String[] splittedTweet = tweet.split(",");
            
            put.add(Bytes.toBytes(""));
            put.add(Bytes.toBytes("create at"));
            put.add(Bytes.toBytes("id"));
            put.add(Bytes.toBytes("text"));
            put.add(Bytes.toBytes("in reply to statys id"));
            put.add(Bytes.toBytes("in reply to user id"));
            put.add(Bytes.toBytes("in reply de screen name"));
            put.add(Bytes.toBytes("user id"));
            put.add(Bytes.toBytes("user name"));
            put.add(Bytes.toBytes("user screen name"));
            put.add(Bytes.toBytes("user location"));
            put.add(Bytes.toBytes("user verified"));
            put.add(Bytes.toBytes("user created at"));
            put.add(Bytes.toBytes("user followers count"));
            put.add(Bytes.toBytes("user friends count"));
            put.add(Bytes.toBytes("user reply count"));
            put.add(Bytes.toBytes("user retweet count"));
            put.add(Bytes.toBytes("user favorite count"));
            put.add(Bytes.toBytes("favorited"));
            put.add(Bytes.toBytes("retweeted"));
            put.add(Bytes.toBytes("lang"));
            put.add(Bytes.toBytes("hashtags"));
            put.add(Bytes.toBytes("mentions"));

            return put;
        }

        public void reduce(org.w3c.dom.Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException{
            context.write(null, insertTweet(key.toString(),String.valueOf(row)));
            this.row+=1;
        }

        public int run(String filename) throws Exception{
            Job job = Job.getInstance(getConf(), "InsertTweet");

            job.setNumReduceTasks(1);
            job.setJarByClass(InsertTweet.class);
            
            job.setMapperClass(SimpleMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);

            
            job.setInputFormatClass(TextInputFormat.class);
            FileInputFormat.addInputPath(job, new Path(filename));

            TableMapReduceUtil.initTableReducerJob(
                    "Big-data-project",
                    SimpleReducer.class,
                    job
            );

            return job.waitForCompletion(true)? 0 : 1;
        }
    }

}