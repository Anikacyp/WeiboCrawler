package crawl.sina;

import java.sql.ResultSet;
import java.sql.SQLException;

import sqloperation.SqlConnection;
import sqloperation.connection;

public class InsertUid {
	SqlConnection sql = new connection().conn();

	public void InsertId() {
		for (int i = 1; i < 2; i++) {
			ResultSet rs=null;
			for (int j = 9; j < 10; j++) {
				System.out.println(i+""+j);
				String query = "select distinct bilateralID from bilateralRelation where bilateralID like \""
						+ i+""+j + "%\";";
				rs = sql.executeQuery(query);
				try {
					while (rs.next()) {
						String currentId = rs.getString("bilateralID");
						String query1="select count(*) from userID where id=\""+currentId+"\";";
						if(!(sql.getCount(query1)==1))
						sql.execute("insert into userID values(\"" + currentId
								+ "\");");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					rs=null;
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) {
		new InsertUid().InsertId();
	}
}
