package com.sikiedu.jdbc01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
	private static final String connectionUrl = "jdbc:mysql://localhost:3306/web01?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "wudonglong";
	
	private static BasicDataSource ds;
	
	static {
		//初始化dbcp数据源
		ds = new BasicDataSource();
		ds.setDriverClassName("com.mql.jdbc.Driver");
		ds.setUrl(connectionUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		
		ds.setInitialSize(5);//初始化连接的个数，即连接池里面的个数
		ds.setMaxTotal(20);//连接的最大数，防止无限制创建，保证机器性能
		ds.setMinIdle(3);//最小的空闲连接，相当于备用着
	}
	
		//获得连接
	public Connection getConnection() {
		try {
			return ds.getConnection();//通过dbcp得到的链接，不需要归还，直接close就可以
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//关闭
	public static void close(ResultSet rs,Statement stmt,Connection con) {
		closeResultSet(rs);
		closeStatement(stmt);
		closeConnection(con);
	}
	
	public static void close(Statement stmt1,Statement stmt2,Connection con) {
		closeStatement(stmt1);
		closeStatement(stmt2);
		closeConnection(con);
	}
	
	private static void closeResultSet(ResultSet rs) {
		try {
			if(rs!=null)
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void closeStatement(Statement stmt) {
		try {
			if(stmt!=null)
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void closeConnection(Connection con) {
		try {
			if(con!=null)
			con.close();//不会断开与数据库的连接，而是把链接放回到连接池里面
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
}
