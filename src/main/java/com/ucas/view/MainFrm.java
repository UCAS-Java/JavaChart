package com.ucas.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * ���������������
 * 
 * @author �Ρ��롢��
 *
 */
public class MainFrm extends JFrame {

	private JPanel contentPane;
	private JDesktopPane table = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrm frame = new MainFrm();
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
	public MainFrm() {
		setTitle("ͼ�����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(600, 100, 600, 600);

		// �ܲ˵�
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// һ���ܲ˵�
		JMenu mnNewMenu = new JMenu("���ܲ˵�");
		menuBar.add(mnNewMenu);

		// �����ֲ˵�����Ϣͳ�Ʋ˵�
		JMenu mnNewMenu_1 = new JMenu("ͼ����Ϣͳ��");
		mnNewMenu.add(mnNewMenu_1);
		JMenu jMenu������ͳ�� = new JMenu("����������");  // �����
		menuBar.add(jMenu������ͳ��);

		JMenuItem menuItem = new JMenuItem("ͼ�����ͳ��");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookTypeManageInterFrm bookTypeAddInterFrm = new BookTypeManageInterFrm();
				bookTypeAddInterFrm.setVisible(true);
				table.add(bookTypeAddInterFrm);
			}
		});
		mnNewMenu_1.add(menuItem);

		JMenuItem menuItem1 = new JMenuItem("ͼ����Ŀͳ��");
		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookManageInterFrm bookFrm = new BookManageInterFrm();
				bookFrm.setVisible(true);
				table.add(bookFrm);
			}
		});
		mnNewMenu_1.add(menuItem1);
		// �����ֲ˵����˳���ť
		JMenuItem menuItem_4 = new JMenuItem("�˳�ϵͳ");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ��˳�ϵͳ");
				if (result == 0) {
					dispose();
				}
			}
		});
		mnNewMenu.add(menuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		table = new JDesktopPane();
		contentPane.add(table, BorderLayout.CENTER);
		// this.setBounds(200, 10, 1500, 1000);
		// ���Ľ��޸��˴˴�����Ϊ�ҵĵ��Էֱ�����1366*768��������ʾ���꣬�ʵ�С��
		this.setBounds(0, 0, 1200, 730);
	}
}
