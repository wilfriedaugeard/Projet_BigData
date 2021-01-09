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

public class InsertTweet extends Configured implements Tool{
    private static final byte[] TABLE_NAME = Bytes.toBytes("augeard-tarmil-Ntweet");
    private static final byte[][] FAMILIES = {
        Bytes.toBytes("create at"),
     /*   Bytes.toBytes("id"),
        Bytes.toBytes("text"),
        Bytes.toBytes("in reply to statys id"),
        Bytes.toBytes("in replu to user id"),
        Bytes.toBytes("in reply de screen name")
    */};

    public static class SimpleMapper extends Mapper<Object, Text, Text, NullWritable>{
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
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
	System.out.println("\n\n\n\n line : "+line+" \n\n\n");
            String[] splittedTweet = line.split("-");
            for(int i=0; i < splittedTweet.length;i++){
                splittedTweet[i] = splittedTweet[i].substring(splittedTweet[i].indexOf(":")+1);
            }
            Put put = new Put(Bytes.toBytes(row));

            put.add(Bytes.toBytes("date"),Bytes.toBytes("create-at"), Bytes.toBytes(splittedTweet[1]));
           
            return put;
        }

        public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException{
            context.write(null, insertTweet(key.toString(),String.valueOf(row)));
            this.row+=1;
        }
    }

    @Override
    public int run(String[] args) throws Exception{
        Job job = Job.getInstance(getConf(), "InsertTweet");
        job.setNumReduceTasks(1);
        job.setJarByClass(InsertTweet.class);
        
        job.setMapperClass(SimpleMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path("tweet.txt"));
        TableMapReduceUtil.initTableReducerJob(
                "augeard-tarmil-Ntweet",
                SimpleReducer.class,
                job
        );
        return job.waitForCompletion(true)? 0 : 1;
    }
}


