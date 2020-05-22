package com.ucas.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;


/**
 * ���ݿ⹤����
 * @author �Ρ��롢��
 *
 */
public class DbUtil {

	private String dbUrl= new String(Base64.getDecoder().decode("amRiYzpteXNxbDovLzExOS4zLjE5MC40OTozMzA2L2RiX2Jvb2s=".getBytes())); // ���ݿ����ӵ�ַ
	private String dbUserName=new String(Base64.getDecoder().decode("dWNhc19ib29r".getBytes())); // �û���
	private String dbPassword= new String(Base64.getDecoder().decode("dWNhc19ib29rQGRiX2Jvb2s=".getBytes())); // ����
	private String jdbcName="com.mysql.jdbc.Driver"; // ��������

	/**
	 * ��ȡ���ݿ�����
	 * @return
	 * @throws Exception
	 */
	public Connection getCon()throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}

	/**
	 * �ر����ݿ�����
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
			System.out.println("���ݿ����ӳɹ���");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿ�����ʧ��");
		}
	}

}
