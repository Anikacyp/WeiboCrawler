package sqloperation;

public class connection {
	public SqlConnection conn() {
		SqlConnection sql = new SqlConnection();
		try {
			sql.connectMySQL("166.111.68.40", "sqlsugg", "sqlsugg", "SinaData");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
}
