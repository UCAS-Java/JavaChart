package com.ucas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ucas.model.BookType;
import com.ucas.util.StringUtil;

/**
 * ͼ��Dao��
 * 
 * @author �Ρ��ܡ���
 *
 */
public class BookDao {

	/**
	 * ��ѯͼ����𼯺�
	 * 
	 * @param con
	 * @param bookType
	 * @return
	 * @throws Exception
	 */
	public ResultSet list(Connection con, BookType bookType) throws Exception {
		StringBuffer sb = new StringBuffer("select * from t_booktype");
		if (StringUtil.isNotEmpty(bookType.getBookTypeName())) {
			sb.append(" and bookTypeName like '%" + bookType.getBookTypeName() + "%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}

	/**
	 * �鿴ͼ���������ֱ�����ж��ٱ���
	 * 
	 * @param con
	 * @param bookType
	 * @return
	 * @throws Exception
	 */
	public ResultSet showTypeCount(Connection con) throws Exception {
		String sql = "select bookTypeName AS name,count(bookTypeId) AS num from v_typecount group by bookTypeID order by bookTypeID";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}

	/**
	 * ��ѯͼ����𼯺ϣ�������ͼ
	 * 
	 * @param con
	 * @param bookType
	 * @return
	 * @throws Exception
	 */
	public ResultSet listView(Connection con, BookType bookType) throws Exception {
		StringBuffer sb = new StringBuffer("select * from v_typecount");
		if (StringUtil.isNotEmpty(bookType.getBookTypeName())) {
			sb.append(" and bookTypeName like '%" + bookType.getBookTypeName() + "%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}

	/**
	 * ��ѯͼ�鼯�ϣ�������ͼ
	 * 
	 * @param con
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public ResultSet showBookCount(Connection con) throws Exception {
		String sql = "select bookName AS name,count(bookName) AS num from v_typecount group by bookName order by bookName";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}
}
