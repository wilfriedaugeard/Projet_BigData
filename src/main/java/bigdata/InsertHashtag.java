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

public class InsertHashtag extends Configured implements Tool{
    private static final byte[] TABLE_NAME = Bytes.toBytes("augeard-tarmil-top-hashtag");
    private static final byte[][] FAMILIES = {
        Bytes.toBytes("hashtag"),
    };

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
	        String[] splittedTweet = line.split(",");

            Put put = new Put(Bytes.toBytes(row));

            put.add(Bytes.toBytes("hashtag"),Bytes.toBytes("hashtag-value"), Bytes.toBytes(splittedTweet[0]));
            put.add(Bytes.toBytes("hashtag"),Bytes.toBytes("count"), Bytes.toBytes(splittedTweet[1]));

            return put;
        }

        public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException{
            context.write(null, insertTweet(key.toString(),String.valueOf(row)));
            this.row+=1;
        }
    }

    @Override
    public int run(String[] args) throws Exception{
        Job job = Job.getInstance(getConf(), "InsertHashtag");
        job.setNumReduceTasks(1);
        job.setJarByClass(InsertHashtag.class);
        
        job.setMapperClass(SimpleMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path("/users/ctarmil/saveFile.txt"));
        System.out.println("\n\n\n\nPATH : "+FileInputFormat.getInputPaths(job));
        TableMapReduceUtil.initTableReducerJob(
                "augeard-tarmil-top-hashtag",
                SimpleReducer.class,
                job
        );
        return job.waitForCompletion(true)? 0 : 1;
    }
}


