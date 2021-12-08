package GUI.Teacher;

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
import GUI.Teacher.*;

public class FrmManHinhChinh  extends JFrame{
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
	private JMenu mnDanhMuc;
	private static JMenuItem mnItemDanhSachHocPhan;
	private static JMenuItem mnItemDangKyHocPhan;
	private static JMenuItem mnItemTraCuuHocPhan;
	private static JMenuItem mnItemBangDiem;
	private static JDesktopPane desktopPane;

	private static FrmLogin frmLG;
	private static FrmManHinhChinh frmMHC;
	private static FrmPersonalInformation frmTTCN;
	private static FrmCourseList frmCL;
	private static FrmCourseRegister frmCR;
	private static FrmCourseSearch frmCS;
	private static FrmTranscript frmTS;

	public FrmManHinhChinh(FrmLogin frmLG, Connection conn, String userName) {
		this.frmMHC = this;
		this.frmTTCN = null;
		this.frmLG = frmLG;
		this.userName = userName;
		Init();
		this.conn = conn;
		setTitle("Màn hình chính - Giảng viên");
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
				}
			}
		});
		mnTaiKhoan.add(mnItemThoat);

		mnDanhMuc = new JMenu("Danh Mục");
		menuBar.add(mnDanhMuc);

		mnItemDanhSachHocPhan = new JMenuItem("Danh Sách Học Phần");
		mnItemDanhSachHocPhan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmCL == null || frmCL.isClosed()) {
					frmCL = new FrmCourseList(conn);
					desktopPane.add(frmCL);
				}
				frmCL.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmCL.setResizable(false);
				frmCL.setVisible(true);
			}
		});
		mnDanhMuc.add(mnItemDanhSachHocPhan);

		mnItemDangKyHocPhan = new JMenuItem("Đăng Ký Học Phần");
		mnItemDangKyHocPhan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
//					frmChild.dispose();
//				}
//				if(frmCR == null || frmCR.isClosed()) {
//					frmCR = new FrmCourseRegister(userName, conn);
//					desktopPane.add(frmCR);
//				}
//				frmCR.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
//				frmCR.setResizable(false);
//				frmCR.setVisible(true);
			}
		});
		mnDanhMuc.add(mnItemDangKyHocPhan);
		
		mnItemTraCuuHocPhan = new JMenuItem("Tra Cứu Học Phần");
		mnItemTraCuuHocPhan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
					frmChild.dispose();
				}
				if(frmCS == null || frmCS.isClosed()) {
					frmCS = new FrmCourseSearch(conn);
					desktopPane.add(frmCS);
				}
				frmCS.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
				frmCS.setResizable(false);
				frmCS.setVisible(true);
			}
		});
		mnDanhMuc.add(mnItemTraCuuHocPhan);
		
		
		mnItemBangDiem = new JMenuItem("Bảng Điểm");
		mnItemBangDiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				for (JInternalFrame frmChild : desktopPane.getAllFrames()) {
//					frmChild.dispose();
//				}
//				if(frmTS == null || frmCS.isClosed()) {
//					frmTS = new FrmTranscript(userName, conn);
//					desktopPane.add(frmTS);
//				}
//				frmTS.setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
//				frmTS.setResizable(false);
//				frmTS.setVisible(true);
			}
		});
		mnDanhMuc.add(mnItemBangDiem);

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
		frmLG = null;
		frmMHC = null;
		frmTTCN = null;
		frmCL = null;
		frmCR = null;
		frmCS = null;
		frmTS = null;
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
