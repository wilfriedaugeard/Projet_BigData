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

public class BuilderTable {

    private static final String tablePrefix = "augeard-tarmil-";

    public static final void createTable(Configuration config, String tableName, String familyName1, String familyName2) throws IOException {
        Connection connection = ConnectionFactory.createConnection(config);
        try (Admin admin = connection.getAdmin()) {

            tableName = this.tablePrefix + tableName;
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(Bytes.toBytes(tableName)));

            tableDescriptor.addFamily(new HColumnDescriptor(Bytes.toBytes(familyName1)));
            tableDescriptor.addFamily(new HColumnDescriptor(Bytes.toBytes(familyName2)));

            if (admin.tableExists(tableDescriptor.getTableName())) {
                admin.disableTable(tableDescriptor.getTableName());
                admin.deleteTable(tableDescriptor.getTableName());
            }

            admin.createTable(tableDescriptor);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


}
