package bigdata;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

public class InitHashtagTable extends Configured implements Tool {
    private static final byte[] TABLE_NAME = Bytes.toBytes("augeard-tarmil-Ntweet");
    private static final byte[][] FAMILIES = {
        Bytes.toBytes("date"),
      /*  Bytes.toBytes("id"),
        Bytes.toBytes("text"),
        Bytes.toBytes("in reply to"),
        Bytes.toBytes("user"),
        Bytes.toBytes("favorited"),
        Bytes.toBytes("retweeted"),
        Bytes.toBytes("lang"),
        Bytes.toBytes("hashtags"),
        Bytes.toBytes("mentions")*/
    };

    public static void createTable(Connection connection) throws IOException {
        try( Admin admin = connection.getAdmin()){
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
        
            for(byte[] family : FAMILIES){
                tableDescriptor.addFamily(new HColumnDescriptor(family));
            }

            if(admin.tableExists(tableDescriptor.getTableName())){
                admin.disableTable(tableDescriptor.getTableName());
                admin.deleteTable(tableDescriptor.getTableName());
            }
	   admin.createTable(tableDescriptor);

        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public int run(String[] args) throws IOException{
        Connection connection = ConnectionFactory.createConnection(getConf());
        createTable(connection);
        return 0;
    }


}
