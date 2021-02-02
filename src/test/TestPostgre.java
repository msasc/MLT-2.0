package test;

import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.DefaultRow;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.data.StyleImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.mongodb.mongo2.MongoDbDataContext;
import org.apache.metamodel.query.FilterItem;
import org.apache.metamodel.schema.ColumnType;
import org.apache.metamodel.schema.MutableColumn;
import org.bson.Document;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.util.Iterator;
import java.util.List;

public class TestPostgre {
	public static void main(String[] args) {

		Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
		source.setDataSourceName("PostgreSQL Local");
		source.setServerName("localhost");
		source.setPortNumber(5432);
		source.setDatabaseName("postgres");
		source.setUser("postgres");
		source.setPassword("postgres");
		source.setMaxConnections(100);

		JdbcDataContext dc = new JdbcDataContext(source);

		List<String> schemaNames = dc.getSchemaNames();
		for (String name : schemaNames) {
			System.out.println(name);
		}

		DataSet ds = dc.query()
			.from("qtfx.instruments")
			.select("instr_id", "instr_desc")
			.where("instr_id").like("%EUR%")
			.and("instr_id").like("SA%")
			.execute();
		Iterator<Row> rows = ds.iterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			System.out.print(row);
		}
		ds.close();

		MongoDbDataContext mg;
		FilterItem fi;
		ColumnType ct;
		Document doc;
	}
}
