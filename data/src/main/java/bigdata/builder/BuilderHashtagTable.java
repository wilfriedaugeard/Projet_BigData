package bigdata.builder;

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

public class BuilderHashtagTable extends Configured implements Tool {
    private static byte[] tableName ;
    private static final byte[][] FAMILIES = {
        Bytes.toBytes("hashtag"),
        Bytes.toBytes("user")
    };

    public static final void createTable(Connection connection, String tableName) throws IOException {
        try( Admin admin = connection.getAdmin()){
            this.tableName = Bytes.toBytes(tableName);
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(this.tableName));
        
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

    public int run(String[] args) throws Exception{
        Connection connection = ConnectionFactory.createConnection(getConf());
        switch(args[0]){
            case "Top":
                createTable(connection, "augeard-tarmil-top-hashtag");
                break;
            
            case "byUser":
                createTable(connection, "augeard-tarmil-hashtag-byUser");
                break;

            case "triplet":
                createTable(connection, "augeard-tarmil-top-triplet-hashtag");
                break;

            default:
                break;
        }
        return 0;
    }


}
