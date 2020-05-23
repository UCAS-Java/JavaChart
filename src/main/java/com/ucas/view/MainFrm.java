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
 * 主函数，程序入口
 * 
 * @author 涛、想、杰
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
		setTitle("图书管理");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(600, 100, 600, 600);

		// 总菜单
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 一级总菜单
		JMenu mnNewMenu = new JMenu("功能菜单");
		menuBar.add(mnNewMenu);

		// 二级分菜单：信息统计菜单
		JMenu mnNewMenu_1 = new JMenu("图书信息统计");
		mnNewMenu.add(mnNewMenu_1);
		JMenu jMenu访问量统计 = new JMenu("访问量报表");  // 杰添加
		menuBar.add(jMenu访问量统计);

		JMenuItem menuItem = new JMenuItem("图书类别统计");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookTypeManageInterFrm bookTypeAddInterFrm = new BookTypeManageInterFrm();
				bookTypeAddInterFrm.setVisible(true);
				table.add(bookTypeAddInterFrm);
			}
		});
		mnNewMenu_1.add(menuItem);

		JMenuItem menuItem1 = new JMenuItem("图书数目统计");
		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookManageInterFrm bookFrm = new BookManageInterFrm();
				bookFrm.setVisible(true);
				table.add(bookFrm);
			}
		});
		mnNewMenu_1.add(menuItem1);
		// 二级分菜单：退出按钮
		JMenuItem menuItem_4 = new JMenuItem("退出系统");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "是否退出系统");
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
		// 秦文杰修改了此处，因为我的电脑分辨率是1366*768，界面显示不完，故调小。
		this.setBounds(0, 0, 1200, 730);
	}
}
