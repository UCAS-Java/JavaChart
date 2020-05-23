package com.ucas.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.ucas.dao.BookDao;
import com.ucas.model.BookType;
import com.ucas.util.DbUtil;

/**
 * 书籍类别管理类
 * 
 * @author 涛、想、杰
 *
 */
public class BookTypeManageInterFrm extends JInternalFrame {
	private JTable bookTypeTable;

	private DbUtil dbUtil = new DbUtil();
	private BookDao bookTypeDao = new BookDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeManageInterFrm frame = new BookTypeManageInterFrm();
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
	public BookTypeManageInterFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("图书类别信息统计 ");
		// setBounds(100, 100, 1200, 800);
		// 秦文杰修改了此处
		setBounds(0, 0, 1100, 600);

		// 显示图书类别信息
		JPanel scrollPane = new JPanel();
		bookTypeTable = new JTable();

		bookTypeTable.setTableHeader(new JTableHeader());
		bookTypeTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "类别222id", "类别名称", "类别描述" }) {
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
		bookTypeTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "图书类别信息",
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
			ResultSet rs = bookTypeDao.showTypeCount(con);

			DefaultPieDataset dataset = new DefaultPieDataset();
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("num"));
				dataset.setValue(rs.getString("name"), Integer.parseInt(rs.getString("num")));
			}

			JfreeChartPie chart = new JfreeChartPie("图书分类统计结果", dataset, "图书类别统计饼状图", "图书类别", "该类别下图书数量");

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
		v_head.add("类别id");
		v_head.add("类别名称");
		v_head.add("类别描述");
		dtm.addRow(v_head);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookTypeDao.list(con, bookType);
			while (rs.next()) {
				Vector<String> v = new Vector<String>();
				v.add(rs.getString("id"));
				v.add(rs.getString("bookTypeName"));
				v.add(rs.getString("bookTypeDesc"));
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

class JfreeChartPie extends JInternalFrame {

	public JfreeChartPie(String title, DefaultPieDataset dataset, String ti, String heng, String shu) {
		super(title);
		this.setContentPane(createPanel(dataset, ti, heng, shu));
	}

	private static JFreeChart createJFreeChart(DefaultPieDataset dataset, String ti, String heng, String shu) {
		/**
		 * 构建JFreeChart
		 */
		JFreeChart jfreeChart = ChartFactory.createPieChart(ti, dataset, true, true, false);
		/**
		 * 设置JFreeChart的属性
		 */
		setChartProperties(jfreeChart);
		return jfreeChart;
	}

	private static void setChartProperties(JFreeChart jfreeChart) {
		// TODO Auto-generated method stub
		// 三个部分设置字体的方法分别如下:
		TextTitle textTitle = jfreeChart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));
		LegendTitle legend = jfreeChart.getLegend();
		if (legend != null) {
			legend.setItemFont(new Font("宋体", Font.BOLD, 20));
		}
		PiePlot pie = (PiePlot) jfreeChart.getPlot();
		pie.setLabelFont(new Font("宋体", Font.BOLD, 20));
		pie.setNoDataMessage("No data available");
		// 设置PieChart是否显示为圆形
		pie.setCircular(true);
		// 显示百分比
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
		// 间距
		pie.setLabelGap(0.01D);
	}

	public static JPanel createPanel(DefaultPieDataset dataset, String ti, String heng, String shu) {
		JFreeChart chart = createJFreeChart(dataset, ti, heng, shu);
		return new ChartPanel(chart);
	}
}
