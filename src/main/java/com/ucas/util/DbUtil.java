package com.ucas.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;


/**
 * 数据库工具类
 * @author 涛、想、杰
 *
 */
public class DbUtil {

	private String dbUrl= new String(Base64.getDecoder().decode("amRiYzpteXNxbDovLzExOS4zLjE5MC40OTozMzA2L2RiX2Jvb2s=".getBytes())); // 数据库连接地址
	private String dbUserName=new String(Base64.getDecoder().decode("dWNhc19ib29r".getBytes())); // 用户名
	private String dbPassword= new String(Base64.getDecoder().decode("dWNhc19ib29rQGRiX2Jvb2s=".getBytes())); // 密码
	private String jdbcName="com.mysql.jdbc.Driver"; // 驱动名称

	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getCon()throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}

	/**
	 * 关闭数据库连接
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();
		}
	}

	public static void main(String[] args) {
		DbUtil dbUtil=new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}

}
