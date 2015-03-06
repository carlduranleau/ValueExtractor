package com.flexio.parser.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private String host = null;
	private String schema = null;
	private String table = null;
	private List<String> columns = null;
	
	public Database (final String pHost, final String pUsername, final String pPassword, final String pSchema, final String pTable) throws ClassNotFoundException, SQLException {
		
		this.host = pHost;
		this.schema = pSchema;
		this.table = pTable;
		
		Class.forName("com.mysql.jdbc.Driver");
		
		String connectString = "jdbc:mysql://" + pHost + "/" + pSchema + "?user=" + pUsername + "&password=" + pPassword;
		
		this.connect = DriverManager.getConnection(connectString);
		initDataTable();
	}
	
	public void writeDataSet (Map<String, String> data) throws SQLException {
		
		this.columns = getAvailableColumns();
		
		for (String key : data.keySet()) {
			if (!this.columns.contains(key)) {
				addNewColumn(key);
			}
		}
		
		String columnNames = null;
		String columnValues = null;
		
		for (String key : data.keySet()) {
			if (columnNames == null) {
				columnNames = key;
			} else {
				columnNames += "," + key;
			}
//			if (columnValues == null) {
//			columnValues = "'" + data.get(key) + "'";
//			} else {
//				columnValues += ",'" + data.get(key) + "'";
//			}
			if (columnValues == null) {
				columnValues = "?";
			} else {
				columnValues += ",?";
			}
		}
				
		columnNames = "(" + columnNames + ")";
		columnValues = "(" + columnValues + ")";
		
		preparedStatement = connect.prepareStatement("insert into " + this.table + columnNames + " values " + columnValues + ";");

		int index = 1;
		for (String key : data.keySet()) {
			preparedStatement.setString(index++, data.get(key));
		}
		preparedStatement.executeUpdate();
	}
	
	private void initDataTable () throws SQLException {
		DatabaseMetaData meta = this.connect.getMetaData();
		
		ResultSet res = meta.getTables(null, null, this.table, new String[] {"TABLE"});
		
		if (!res.next()) {
			String createStatement = "CREATE TABLE " + this.table + " (id INT(64) NOT NULL AUTO_INCREMENT, PRIMARY KEY(id));";
			statement = this.connect.createStatement();
			statement.executeUpdate(createStatement);
		} 
	}
	
	private List<String> getAvailableColumns () throws SQLException {
		
		String query = "SELECT column_name FROM INFORMATION_SCHEMA.columns where table_schema='" + this.schema +"' and table_name='" + this.table + "'";
		List<String> columns = new ArrayList<String>();
		
		statement = this.connect.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next()) {
			columns.add(resultSet.getString("column_name"));
		}
		
		return columns;
	}
	
	private void addNewColumn(final String column) throws SQLException {
		
		String alterQuery = "ALTER TABLE " + this.table + " ADD " + column + " VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;";
		statement = this.connect.createStatement();
		statement.executeUpdate(alterQuery);
	}
}
