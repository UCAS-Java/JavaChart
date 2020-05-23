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
 * �鼮��������
 * 
 * @author �Ρ��롢��
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
		setTitle("ͼ�������Ϣͳ�� ");
		// setBounds(100, 100, 1200, 800);
		// ���Ľ��޸��˴˴�
		setBounds(0, 0, 1100, 600);

		// ��ʾͼ�������Ϣ
		JPanel scrollPane = new JPanel();
		bookTypeTable = new JTable();

		bookTypeTable.setTableHeader(new JTableHeader());
		bookTypeTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "���222id", "�������", "�������" }) {
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
		bookTypeTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ͼ�������Ϣ",
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
			ResultSet rs = bookTypeDao.showTypeCount(con);

			DefaultPieDataset dataset = new DefaultPieDataset();
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("num"));
				dataset.setValue(rs.getString("name"), Integer.parseInt(rs.getString("num")));
			}

			JfreeChartPie chart = new JfreeChartPie("ͼ�����ͳ�ƽ��", dataset, "ͼ�����ͳ�Ʊ�״ͼ", "ͼ�����", "�������ͼ������");

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
		v_head.add("���id");
		v_head.add("�������");
		v_head.add("�������");
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
		 * ����JFreeChart
		 */
		JFreeChart jfreeChart = ChartFactory.createPieChart(ti, dataset, true, true, false);
		/**
		 * ����JFreeChart������
		 */
		setChartProperties(jfreeChart);
		return jfreeChart;
	}

	private static void setChartProperties(JFreeChart jfreeChart) {
		// TODO Auto-generated method stub
		// ����������������ķ����ֱ�����:
		TextTitle textTitle = jfreeChart.getTitle();
		textTitle.setFont(new Font("����", Font.BOLD, 20));
		LegendTitle legend = jfreeChart.getLegend();
		if (legend != null) {
			legend.setItemFont(new Font("����", Font.BOLD, 20));
		}
		PiePlot pie = (PiePlot) jfreeChart.getPlot();
		pie.setLabelFont(new Font("����", Font.BOLD, 20));
		pie.setNoDataMessage("No data available");
		// ����PieChart�Ƿ���ʾΪԲ��
		pie.setCircular(true);
		// ��ʾ�ٷֱ�
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
		// ���
		pie.setLabelGap(0.01D);
	}

	public static JPanel createPanel(DefaultPieDataset dataset, String ti, String heng, String shu) {
		JFreeChart chart = createJFreeChart(dataset, ti, heng, shu);
		return new ChartPanel(chart);
	}
}
