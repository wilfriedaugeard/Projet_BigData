
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

public class InitTable extends Configured implements Tool {
    private static final byte[] TABLE_NAME = Bytes.toBytes("Big-data-project");
    private static final byte[][] FAMILIES = {
        Bytes.toBytes("create at"),
        Bytes.toBytes("id"),
        Bytes.toBytes("text"),
        Bytes.toBytes("in reply to statys id"),
        Bytes.toBytes("in reply to user id"),
        Bytes.toBytes("in reply de screen name"),
        Bytes.toBytes("user id"),
        Bytes.toBytes("user name"),
        Bytes.toBytes("user screen name"),
        Bytes.toBytes("user location"),
        Bytes.toBytes("user verified"),
        Bytes.toBytes("user created at"),
        Bytes.toBytes("user followers count"),
        Bytes.toBytes("user friends count"),
        Bytes.toBytes("user reply count"),
        Bytes.toBytes("user retweet count"),
        Bytes.toBytes("user favorite count"),
        Bytes.toBytes("favorited"),
        Bytes.toBytes("retweeted"),
        Bytes.toBytes("lang"),
        Bytes.toBytes("hashtags"),
        Bytes.toBytes("mentions")
    };

    public static void createTable(Connection connexion){
        try( Admin admin = connection.getAdmin()){
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
        
            for(byte[] family : FAMILIES){
                tableDescriptor.addFamily(new HColumnDescriptor(family));
            }

            if(admin.tableExists(tableDescriptor.getTableName())){
                admin.disableTable(tableDescriptor.getTableName());
                admin.deleteTable(tableDescriptor.getTableName());
            }

        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public int run(String[] args) throws IOException{
        Connection connection = ConnectionFactory.createConnection(getConf());
        createTable(connexion);
        return 0;
    }


}
