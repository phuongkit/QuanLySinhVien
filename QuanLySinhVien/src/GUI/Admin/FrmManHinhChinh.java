package GUI.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GUI.FrmLogin;
import GUI.InitGUI;

public class FrmManHinhChinh extends JFrame{
	private static int SCREEN_HEIGHT;
	private static int SCREEN_WIDTH;

	private static Connection conn;
	private static String userName;

	private JPanel pnlMain;
	private JMenuBar menuBar;
	private JMenu mnTaiKhoan;
	private JMenuItem mnItemTaiKhoan;
	private JMenuItem mnItemDoiMatKhau;
	private JMenuItem mnItemThoat;
	private JMenu mnQuanLy;
	private JMenuItem mnItemQLTaiKhoan;
	private JMenuItem mnItemQLGiangVien;
	private JMenuItem mnItemQLSinhVien;
	private JMenuItem mnItemQLBangDiem;
	private JMenuItem mnItemQLLop;
	private JMenuItem mnItemQLChiTietLop;
	private static JDesktopPane desktopPane;
	
	private static FrmLogin frmLG;
	private static FrmManHinhChinh frmMHC;
	private static FrmPersonalInformation frmTTCN;
	private static FrmAccount frmAC;
	private static FrmTeacher frmGV;
	private static FrmStudent frmSV;
	private static FrmTranscript frmTS;
	private static FrmCourse frmCS;
	private static FrmCourse_Class frmCSS;

	public FrmManHinhChinh(FrmLogin frmLG, Connection conn, String userName) {
		this.frmLG = frmLG;
		this.frmMHC = this;
		this.frmTTCN = null;
		this.frmGV = null;
		this.frmSV = null;
		this.userName = userName;
		Init();
		this.conn = conn;
		setTitle("Màn hình chính - Admin");
		pnlMain = new JPanel();
		pnlMain.setLayout(null);
		pnlMain = (JPanel) getContentPane();

		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		setNonMovableDesktopPane(desktopPane);
		getContentPane().add(desktopPane);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnTaiKhoan = new JMenu("Tài Khoản");
		menuBar.add(mnTaiKhoan);

		mnItemTaiKhoan = new JMenuItem("Tài Khoản Của Tôi");
		mnItemTaiKhoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Load();
			}
		});
		mnTaiKhoan.add(mnItemTaiKhoan);

		mnItemDoiMatKhau = new JMenuItem("Đổi Mật Khẩu");
		mnItemDoiMatKhau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmChangePassword frmCP = new FrmChangePassword(frmLG, frmMHC, conn, userName);
				frmCP.setVisible(true);
			}
		});
		mnTaiKhoan.add(mnItemDoiMatKhau);

		mnItemThoat = new JMenuItem("Thoát");
		mnItemThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(pnlMain, "Bạn có muốn thoát!", "Thông tin", JOptionPane.OK_CANCEL_OPTION);
				if(choose == JOptionPane.OK_OPTION) {
					frmLG.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
		});
		mnTaiKhoan.add(mnItemThoat);
		
		mnQuanLy = new JMenu("Quản Lý");
		menuBar.add(mnQuanLy);

		mnItemQLTaiKhoan = new JMenuItem("Quản Lý Tài Khoản");
		mnItemQLTaiKhoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmAC == null || frmAC.isClosed()) {
					frmAC = new FrmAccount(conn);
					desktopPane.add(frmAC);
					frmAC.setVisible(true);
				}
				frmAC.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmAC.setResizable(false);
			}
		});
		mnQuanLy.add(mnItemQLTaiKhoan);

		mnItemQLGiangVien = new JMenuItem("Quản Lý Giảng Viên");
		mnItemQLGiangVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmGV == null || frmGV.isClosed()) {
					frmGV = new FrmTeacher(conn);
					desktopPane.add(frmGV);
					frmGV.setVisible(true);
				}
				frmGV.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmGV.setResizable(false);
			}
		});
		mnQuanLy.add(mnItemQLGiangVien);

		mnItemQLSinhVien = new JMenuItem("Quản Lý Sinh Viên");
		mnItemQLSinhVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmSV == null || frmSV.isClosed()) {
					frmSV = new FrmStudent(conn);
					desktopPane.add(frmSV);
					frmSV.setVisible(true);
				}
				frmSV.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmSV.setResizable(false);
			}
		});
		mnQuanLy.add(mnItemQLSinhVien);

		mnItemQLBangDiem = new JMenuItem("Quản Lý Bảng Điểm");
		mnItemQLBangDiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmTS == null || frmTS.isClosed()) {
					frmTS = new FrmTranscript(conn);
					desktopPane.add(frmTS);
					frmTS.setVisible(true);
				}
				frmTS.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmTS.setResizable(false);
			}
		});
		mnQuanLy.add(mnItemQLBangDiem);
		
		mnItemQLLop = new JMenuItem("Quản Lý Học Phần");
		mnItemQLLop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmCS == null || frmCS.isClosed()) {
					frmCS = new FrmCourse(conn);
					desktopPane.add(frmCS);
					frmCS.setVisible(true);
				}
				frmCS.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmCS.setResizable(false);
			}
		});
		mnQuanLy.add(mnItemQLLop);

		mnItemQLChiTietLop = new JMenuItem("Quản Lý Học Phần Con");
		mnItemQLChiTietLop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmCSS == null || frmCSS.isClosed()) {
					frmCSS = new FrmCourse_Class(conn);
					desktopPane.add(frmCSS);
					frmCSS.setVisible(true);
				}
				frmCSS.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmCSS.setResizable(false);
			}
		});
		mnQuanLy.add(mnItemQLChiTietLop);

		// Frame Size
		setSize(SCREEN_WIDTH+15, SCREEN_HEIGHT+65);
		//setBounds(0, 0, FRM_WIDTH, FRM_HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		Load();
	}
	public void Load() {
		for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
			frmChild.dispose();
		}
		if(frmTTCN == null || frmTTCN.isClosed()) {
			frmTTCN = new FrmPersonalInformation(conn, userName);
			desktopPane.add(frmTTCN);
		}
		frmTTCN.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
		frmTTCN.setResizable(false);
		frmTTCN.setVisible(true);
	}
	public void Init() {
		InitGUI init = new InitGUI();
		SCREEN_HEIGHT = init.getSCREEN_HEIGHT();
		SCREEN_WIDTH = init.getSCREEN_WIDTH();
	}
	public void setNonMovableDesktopPane( JDesktopPane pPane )
	{
		JDesktopPane mNonMovableDesktopPane;
		mNonMovableDesktopPane = pPane;
		try
		{
			NonMovableDesktopManager d =
					(NonMovableDesktopManager) mNonMovableDesktopPane.getDesktopManager();
		}
		catch( ClassCastException cce )
		{
			mNonMovableDesktopPane.setDesktopManager(new NonMovableDesktopManager());
		}
	}
	private class NonMovableDesktopManager extends DefaultDesktopManager
	{
		/**
		 * Moves the visible location of the frame being dragged
		 * to the location specified. The means by which this occurs can vary depending
		 * on the dragging algorithm being used. The actual logical location of the frame
		 * might not change until <code>endDraggingFrame</code> is called.
		 */
		public void dragFrame(JComponent f, int newX, int newY) {
		}

		// implements javax.swing.DesktopManager
		public void beginDraggingFrame(JComponent f) {
		}

		// implements javax.swing.DesktopManager
		public void endDraggingFrame(JComponent f) {
		}
	}
}
