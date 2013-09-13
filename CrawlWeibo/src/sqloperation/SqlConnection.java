package sqloperation;

import java.sql.*;
public class SqlConnection {
	Connection conn;
	String dbName;
	
	public SqlConnection() {
        conn = null;
	}
	 
	public void connectMySQL (String server, String userName, String password, String dbName) 
		throws InstantiationException, SQLException, 
		ClassNotFoundException, IllegalAccessException{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + 
				server + "/" + dbName + "?useUnicode=true&characterEncoding=gbk&jdbcCompliantTruncation=false", 
				userName, password);
		if(!conn.isClosed())
			System.out.println("Succeeded connecting to the database!");
		this.dbName = dbName;
		this.useDB(dbName);
	}
	
	public void disconnectMySQL (){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = null;
	}
	
	public void useDB (String dbName) throws SQLException {
		String stat = "USE " + dbName;
		this.execute(stat);
	}
	
	public void execute (String statement){
		Statement stat;
		try {
			stat = this.conn.createStatement();
			stat.execute(statement);

		} catch (SQLException e) {
			/*System.out.println("ERROR!");
			System.out.println(statement);*/
		}
	}
	
	public int executeUpdate (String statement) throws SQLException {
		Statement stat = this.conn.createStatement();
		return stat.executeUpdate(statement);
	}
	
	public ResultSet executeQuery (String statement){
		ResultSet rs = null;
		try {
		Statement stat = this.conn.createStatement();	
		rs = stat.executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public int getCount(String statment){
		ResultSet rs=null;
		Statement stat;
		try {
			stat = this.conn.createStatement();
			rs=stat.executeQuery(statment);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
}
