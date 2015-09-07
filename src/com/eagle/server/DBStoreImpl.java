package com.eagle.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.server.DBStore;

public class DBStoreImpl implements DBStore {

	private String driver = null;
	private String url = null;
	private String username;
	private String password = null;
	private Connection conn = null;
	private PreparedStatement ps = null;

	public void init(Properties p) {
		url = p.getProperty("url");
		driver = p.getProperty("driver");
		username = p.getProperty("username");
		password = p.getProperty("password");

	}

	public void saveToDB(Collection<BIDR> bidrs) throws Exception {
		Class.forName(driver);
		
		conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		String sql = "";
		int i=0;
		int j=0;
		for (BIDR bidr : bidrs) {
			j++;
			Timestamp login_date = bidr.getLogin_date();
			int n = login_date.getDate();
			if (i != n) {
				if (i != 0) {
					ps.executeBatch();
					j=0;
				}
				sql = "insert into t_detail_" + n + " values(?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				i = n;
			}
			
			ps.setString(1, bidr.getAAA_login_name());
			ps.setString(2, bidr.getLogin_ip());
			ps.setTimestamp(3, bidr.getLogin_date());
			ps.setTimestamp(4, bidr.getLogout_date());
			ps.setString(5, bidr.getNAS_ip());
			ps.setInt(6, bidr.getTime_deration());
			ps.addBatch();
			//批处理100条插入一次
			if(j==100) {
				ps.executeBatch();
				j=0;
			}
//			System.out.println(n);
//			System.out.println(login_date);
//			System.out.println(sql);
		}
		ps.executeBatch();
		conn.commit();
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

}
