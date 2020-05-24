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
 * 书籍管理类
 * 
 * @author 涛、想、杰
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
		setTitle("图书信息统计 ");
		// setBounds(100, 100, 1200, 800);
		setBounds(0, 0, 1100, 800);

		// 显示图书类别信息
		JPanel scrollPane = new JPanel();
		bookTypeTable = new JTable();
		bookTypeTable.setTableHeader(new JTableHeader());
		bookTypeTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "类别id", "类别名称", "类别描述" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookTypeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 关闭表格列的自动调整功能
		bookTypeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设置选择模式为单选
		bookTypeTable.setRowHeight(50);// 设置单元格的行高为50
		bookTypeTable.getColumnModel().getColumn(1).setPreferredWidth(110);
		bookTypeTable.getColumnModel().getColumn(2).setPreferredWidth(123);
		bookTypeTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "图书信息",
				TitledBorder.CENTER, TitledBorder.TOP));
		bookTypeTable.setFont(new Font("宋体", Font.BOLD, 20));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(); // set column align center
		renderer.setHorizontalAlignment(JTextField.CENTER);
		bookTypeTable.getColumnModel().getColumn(0).setCellRenderer(renderer);

		scrollPane.add(bookTypeTable);
		this.fillTable(new BookType());

		// 显示类别信息分布
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
			// 修改
			StandardChartTheme mChartTheme = new StandardChartTheme("CN");
			mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
			mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
			mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
			ChartFactory.setChartTheme(mChartTheme);

			JfreeChartTest chart = new JfreeChartTest("图书统计结果", dataset, "图书数量统计柱状图", "图书名称", "该图书数量");
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
	 * 初始化表格
	 * 
	 * @param bookType
	 */
	private void fillTable(BookType bookType) {
		DefaultTableModel dtm = (DefaultTableModel) bookTypeTable.getModel();
		dtm.setRowCount(0); // 设置成0行
		Vector<String> v_head = new Vector<String>();
		v_head.add("图书id");
		v_head.add("图书名称");
		v_head.add("图书类别");
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
		 * 构建JFreeChart
		 */
		JFreeChart jfreeChart = ChartFactory.createBarChart3D(ti, heng, shu, dataset, PlotOrientation.VERTICAL, true,
				false, false);
		/**
		 * 设置JFreeChart的属性
		 */
		jfreeChart.setTitle(new TextTitle("图书数量统计柱状图", new Font("宋体", Font.BOLD + Font.ITALIC, 20)));
		CategoryPlot plot = (CategoryPlot) jfreeChart.getPlot();
		CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setLabelFont(new Font("仿宋", Font.ROMAN_BASELINE, 20));
		return jfreeChart;
	}

	public static JPanel createPanel(DefaultCategoryDataset dataset, String ti, String heng, String shu) {
		JFreeChart chart = createJFreeChart(dataset, ti, heng, shu);
		return new ChartPanel(chart);
	}
}