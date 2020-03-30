package com.sikiedu.jdbc01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo01 {
	public static void main(String[] args) {
		
		//selectAll();
		
		//登陆校验
		//sql注入
//			System.out.println(selectByUsernamePassword("maikou", "123"));
//			System.out.println(selectByUsernamePassword("asd", "asdas' or '1'='1"));//1=1恒成立条件
//			System.out.println("解决sql注入");
//			System.out.println(selectByUP2("asd", "asdas' or '1'='1"));
//			System.out.println(selectByUP2("maikou", "123"));		
//			selectUserByPage(3, 4);
//			insert("ads", "wudonglong");
			//delete(73);
			//update(72,"wu");
			transferAccounts("吴东龙", "哈哈哈", 500);
	}
	
	//列出所有的用户
	public static void selectAll() { 
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		//注册驱动
		try {
			con = JDBCUtils.getConnection();
			
			//向数据库发起查询请求
			stmt = con.createStatement();//返回值是值是result
			
			/*结果集*/rs = stmt.executeQuery("select * from user");
			//得到数据
			while(rs.next()) {
				System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
				System.out.println(rs.getInt("id")+","+rs.getString("username")+","+rs.getString("password"));
				
//				rs.getInt(1);//第一列，得到ID；
//				rs.getString(2);//第二列，得到username;
			}
			
			
			//先打开后关闭
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			//最好分开try catch   不然有一个出问题，其他的也关闭不了
				JDBCUtils.close(rs, stmt, con);
		}
		
		
	}


	//登陆校验(sql注入)
	public static boolean selectByUsernamePassword(String username,String password) {
		Connection con = null;
		Statement stmt =null;
		ResultSet rs = null;
		
		try {
			con = JDBCUtils.getConnection();
			
			stmt = con.createStatement();
			String sql="select * from user where username = '"+username+"' and password = '"+password+"'";//在数据库string类型需要加单引号
			System.out.println();//先创建statement再执行sql语句
			rs = stmt.executeQuery(sql);
			
			
			//只有一条记录
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			//最好分开try catch   不然有一个出问题，其他的也关闭不了
			JDBCUtils.close(rs, stmt, con);
				
		}
		
			return false;//由于是try catch的存在，如果前面有代码出错了，就无法返回一个东西，故此需要返回，保证函数能返回东西
		
		
	}
	
	
	
	//登陆校验(解决sql注入)
	public static boolean selectByUP2(String username,String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//注册驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
		//做链接
			String url = "jdbc:mysql://localhost:3306/web01?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
			con = DriverManager.getConnection(url, "root", "wudonglong");
			
			//先提供sql语句在设置参数
			String sql = "select * from user where username = ? and password = ?";
			pstmt = con.prepareStatement(sql);
			
			
			//在这里他帮我们组拼字符
			pstmt.setString(1, username);//从 1 开始
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//最好分开try catch   不然有一个出问题，其他的也关闭不了
		
				try {
					if(rs!=null)
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				try {
					if(pstmt!=null)
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				try {
					if(con!=null)
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
			return false;//由于是try catch的存在，如果前面有代码出错了，就无法返回一个东西，故此需要返回，保证函数能返回东西
	}
	
	
	//分页查询
	//pageNumber是页数，第几页       pageCount是每页显示多少条数据
	public static void selectUserByPage(int pageNumber,int pageCount) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//注册驱动
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");//使用什么驱动连接数据库
			//String url = "jdbc:mysql://localhost:3306/web01";//有时候会出现乱码，制定连接数据库的编码
			String url = "jdbc:mysql://localhost:3306/web01?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
			String user = "root";
			String password = "wudonglong";
			con = DriverManager.getConnection(url, user, password);//建立连接，使用驱动
			
			//向数据库发起查询请求
			
			stmt = con.prepareStatement("select * from user limit ?,?");
			stmt.setInt(1,(pageNumber-1)*pageCount);
			stmt.setInt(2, pageCount);
			/*结果集*/rs = stmt.executeQuery();
			//得到数据
			while(rs.next()) {
				//System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
				System.out.println(rs.getInt("id")+","+rs.getString("username")+","+rs.getString("password"));
				
//				rs.getInt(1);//第一列，得到ID；
//				rs.getString(2);//第二列，得到username;
			}
			
			
			//先打开后关闭
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//最好分开try catch   不然有一个出问题，其他的也关闭不了
			
				try {
					if(rs!=null)
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				try {
					if(stmt!=null)
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				try {
					if(con!=null)
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	//插入数据
	public static void insert(String username,String password) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			String sql = "insert into user(username,password) values(?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			int result = stmt.executeUpdate();//返回值代表受到影响的行数
			System.out.println("插入成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(rs, stmt, con);
		}
	}
	//删除数据
	public static void delete(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			String sql = "delete from user where id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			int result = stmt.executeUpdate();//返回值代表受到影响的行数
			if(result>0) {
				System.out.println("删除成功");
			}else {
				System.out.println("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(rs, stmt, con);
		}
	}
	//修改数据
	public static void update(int id,String newPassword) {
		Connection con = null;
		PreparedStatement stmt1 = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			String sql = "update user set password = ? where id = ?";
			stmt1 = con.prepareStatement(sql);
			stmt1.setString(1, newPassword);
			stmt1.setInt(2, id);
			int result = stmt1.executeUpdate();//返回值代表受到影响的行数
			if(result>0) {
				System.out.println("修改成功");
			}else {
				System.out.println("修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(rs, stmt1, con);
		}
	}

	public static void transferAccounts(String username1,String username2,int money) {
		Connection con = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try {
			con = JDBCUtils.getConnection();
			
			con.setAutoCommit(false);//开启事务
			
			String sql = "update user set balance = balance - ? where username = ?";
			stmt1 = con.prepareStatement(sql);
			stmt1.setInt(1, money);
			stmt1.setString(2, username1);
			stmt1.executeUpdate();
			
//			String s = null;//隐式异常
//			s.charAt(2);
			
			sql = "update user set balance = balance + ? where username = ?";
			stmt2 = con.prepareStatement(sql);
			stmt2.setInt(1, money);
			stmt2.setString(2, username2);
			stmt2.executeLargeUpdate();
			
			con.commit();//提交事务
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(stmt2, stmt1, con);
		}
	}
}	
