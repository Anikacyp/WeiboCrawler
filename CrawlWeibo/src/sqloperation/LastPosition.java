package sqloperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LastPosition {
	SqlConnection sql = new connection().conn();
	public int lastLevel = 1;

	public ArrayList<Object> getArray() {
		ArrayList<Object> CurArray = new ArrayList<Object>();
		ArrayList<Object> lastArray = new ArrayList<Object>();
		String query = "select max(level) from bilateralRelation;";
		lastLevel = sql.getCount(query);
		if (lastLevel == 0) {
			lastArray.add("2495406312");
			return lastArray;
		} else {
			query = "select distinct id from bilateralRelation where level="
					+ lastLevel;
			ResultSet rs = sql.executeQuery(query);
			try {
				while (rs.next()) {
					CurArray.add(rs.getString("id"));
				}
				query = "select distinct bilateralID from bilateralRelation where level="
						+ (lastLevel - 1);
				rs = sql.executeQuery(query);
				while (rs.next()) {
					lastArray.add(rs.getString("bilateralID"));
				}
				if (lastArray.size() == CurArray.size()) {
					query = "select distinct bilateralID from bilateralRelation where level="
							+ lastLevel;
					rs = sql.executeQuery(query);
					while (rs.next())
						CurArray.add(rs.getString("bilateralID"));
					return CurArray;
				} else {
					for (Object obj : CurArray) {
						if (lastArray.contains(obj))
							lastArray.remove(obj);
					}
				}
				return lastArray;
			} catch (SQLException e) {
				lastLevel = 0;
				lastArray.add("2495406312");
				e.printStackTrace();
				return lastArray;
			}
		}
	}
	/*
	 * public static void main(String args[]) { System.out.println(new
	 * LastPosition().getArray().size()); }
	 */
}
