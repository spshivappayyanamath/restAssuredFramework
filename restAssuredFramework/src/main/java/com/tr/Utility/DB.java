package com.tr.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DB {
	
	public static void main(String a[]) throws ClassNotFoundException, SQLException {
		
		Map<String,String> dbMap=DB.readDB("select * from wkf_Task where task_id in (1072368,1072389)");
		
		Set<String> keys = dbMap.keySet();
		for (String key : keys) {
			System.out.println( key +" :: " + dbMap.get(key));
		}
		
		
	}
	
	public static Map<String,String>  readDB(String Query) throws SQLException, ClassNotFoundException {
		
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//sdcdrdbcscan.int.thomsonreuters.com:1521/dealsdb","ran","away");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		PreparedStatement stmt1=conn.prepareStatement(Query);
		ResultSet rs1= stmt1.executeQuery();
		ResultSetMetaData col=rs1.getMetaData();
		
		Map<String,String> dbMap=new HashMap<String, String>();
		ArrayList<String> dbValue= new ArrayList<String>();
		
		String key =null;
		for(int i=1; i<=col.getColumnCount(); i++){
			 key=col.getColumnName(i);
		}
		
		while(rs1.next()){
			int i=1;
//			for(int i=1; i<=col.getColumnCount(); i++){
			
//				System.out.println(col.getColumnName(i));
//				String key=col.getColumnName(i);
				String value=rs1.getString(i);
				dbValue.add(value);
			
//			}
		
		}
		dbMap.put(key, dbValue.toString());

		return dbMap;
		
			
	}

}
