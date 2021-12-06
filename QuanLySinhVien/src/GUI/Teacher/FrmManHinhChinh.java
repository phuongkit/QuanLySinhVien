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
import GUI.Teacher.FrmPersonalInformation;

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
	private static JDesktopPane desktopPane;

	private static FrmLogin frmLG;
	private static FrmPersonalInformation frmTTCN;

	public FrmManHinhChinh(FrmLogin frmLG, Connection conn, String userName) {
		frmTTCN = null;
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
