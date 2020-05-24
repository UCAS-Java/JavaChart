package com.ucas.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.ucas.dao.BookDao;
import com.ucas.model.BookType;
import com.ucas.util.DbUtil;
import sun.plugin.javascript.JSContext;

/**
 * �鼮������
 * 
 * @author �Ρ��롢��
 *
 */
public class BookManageInterFrm extends JInternalFrame {
	private JTable bookTypeTable;

	private DbUtil dbUtil = new DbUtil();
	private BookDao bookTypeDao = new BookDao();

	/**application
	 * Launch the .
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookManageInterFrm frame = new BookManageInterFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookManageInterFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("ͼ����Ϣͳ�� ");
		// setBounds(100, 100, 1200, 800);
		setBounds(0, 0, 1100, 800);

		// ��ʾͼ�������Ϣ
		JPanel scrollPane = new JPanel();
		bookTypeTable = new JTable();
		bookTypeTable.setTableHeader(new JTableHeader());
		bookTypeTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "���id", "�������", "�������" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookTypeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// �رձ���е��Զ���������
		bookTypeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// ����ѡ��ģʽΪ��ѡ
		bookTypeTable.setRowHeight(50);// ���õ�Ԫ����и�Ϊ50
		bookTypeTable.getColumnModel().getColumn(1).setPreferredWidth(110);
		bookTypeTable.getColumnModel().getColumn(2).setPreferredWidth(123);
		bookTypeTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ����Ϣ",
				TitledBorder.CENTER, TitledBorder.TOP));
		bookTypeTable.setFont(new Font("����", Font.BOLD, 20));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(); // set column align center
		renderer.setHorizontalAlignment(JTextField.CENTER);
		bookTypeTable.getColumnModel().getColumn(0).setCellRenderer(renderer);

		scrollPane.add(bookTypeTable);
		this.fillTable(new BookType());

		// ��ʾ�����Ϣ�ֲ�
		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookTypeDao.showBookCount(con);
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("num"));
				dataset.setValue(Integer.parseInt(rs.getString("num")), rs.getString("name"), rs.getString("name"));
			}
			// �޸�
			StandardChartTheme mChartTheme = new StandardChartTheme("CN");
			mChartTheme.setLargeFont(new Font("����", Font.BOLD, 20));
			mChartTheme.setExtraLargeFont(new Font("����", Font.PLAIN, 15));
			mChartTheme.setRegularFont(new Font("����", Font.PLAIN, 15));
			ChartFactory.setChartTheme(mChartTheme);

			JfreeChartTest chart = new JfreeChartTest("ͼ��ͳ�ƽ��", dataset, "ͼ������ͳ����״ͼ", "ͼ������", "��ͼ������");
			scrollPane.add(chart, BorderLayout.EAST);
			chart.pack();
			chart.setVisible(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.add(scrollPane);
	}

	/**
	 * ��ʼ�����
	 * 
	 * @param bookType
	 */
	private void fillTable(BookType bookType) {
		DefaultTableModel dtm = (DefaultTableModel) bookTypeTable.getModel();
		dtm.setRowCount(0); // ���ó�0��
		Vector<String> v_head = new Vector<String>();
		v_head.add("ͼ��id");
		v_head.add("ͼ������");
		v_head.add("ͼ�����");
		dtm.addRow(v_head);

		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookTypeDao.listView(con, bookType);
			while (rs.next()) {
				Vector<String> v = new Vector<String>();
				v.add(rs.getString("id"));
				v.add(rs.getString("bookName"));
				v.add(rs.getString("bookTypeName"));
				dtm.addRow(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class JfreeChartTest extends JInternalFrame {

	public JfreeChartTest(String title, DefaultCategoryDataset dataset, String ti, String heng, String shu) {
		super(title);
		this.setContentPane(createPanel(dataset, ti, heng, shu));
	}

	private static JFreeChart createJFreeChart(CategoryDataset dataset, String ti, String heng, String shu) {
		/**
		 * ����JFreeChart
		 */
		JFreeChart jfreeChart = ChartFactory.createBarChart3D(ti, heng, shu, dataset, PlotOrientation.VERTICAL, true,
				false, false);
		/**
		 * ����JFreeChart������
		 */
		jfreeChart.setTitle(new TextTitle("ͼ������ͳ����״ͼ", new Font("����", Font.BOLD + Font.ITALIC, 20)));
		CategoryPlot plot = (CategoryPlot) jfreeChart.getPlot();
		CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setLabelFont(new Font("����", Font.ROMAN_BASELINE, 20));
		return jfreeChart;
	}

	public static JPanel createPanel(DefaultCategoryDataset dataset, String ti, String heng, String shu) {
		JFreeChart chart = createJFreeChart(dataset, ti, heng, shu);
		return new ChartPanel(chart);
	}
}