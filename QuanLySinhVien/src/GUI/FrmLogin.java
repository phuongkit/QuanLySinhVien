package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DAO.DBConnection;
import Model.Account;

public class FrmLogin extends JFrame{
	private static Connection conn = null;
	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int COMPONENTS_HEIGHT;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;

	private JPanel pnlMain;
	private JLabel lblUserName;
	private JLabel lblPassWord;
	private JLabel lblPermission;
	private JLabel lblShowPassWord;
	private JLabel lblAdmin;
	private JLabel lblStudent;
	private JLabel lblTeacher;
	private JTextField txtUserName;
	private JPasswordField pwfPassWord;
	private JCheckBox cbShowPassWord;
	private JRadioButton rbAdmin;
	private JRadioButton rbStudent;
	private JRadioButton rbTeacher;
	private JButton btnLogin;
	private JButton btnExit;
	
	private FrmLogin frmLG;
	private GUI.Admin.FrmManHinhChinh frmMHCAdmin;
	private GUI.Teacher.FrmManHinhChinh frmMHCTeacher;
	private GUI.Student.FrmManHinhChinh frmMHCStudent;
	public FrmLogin() {
		frmLG = this;
		setBackground(new Color(138, 43, 226));
		Init();
		setTitle("Form login");

		pnlMain = new JPanel();
		pnlMain.setLayout(null);
		pnlMain = (JPanel) getContentPane();

		lblUserName = new JLabel("Username:");
		lblUserName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblUserName.setBounds(30, 42, 120, COMPONENTS_HEIGHT);
		pnlMain.add(lblUserName);

		txtUserName = new JTextField();
		txtUserName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtUserName.setBounds(160, 42, 200, COMPONENTS_HEIGHT);
		pnlMain.add(txtUserName);

		lblPassWord = new JLabel("PassWord:");
		lblPassWord.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPassWord.setBounds(30, 92, 120, COMPONENTS_HEIGHT);
		pnlMain.add(lblPassWord);

		pwfPassWord = new JPasswordField(10);
		pwfPassWord.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		pwfPassWord.setBounds(160, 92, 200, COMPONENTS_HEIGHT);
		pnlMain.add(pwfPassWord);

		lblPermission = new JLabel("Permission:");
		lblPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPermission.setBounds(30, 138, 120, COMPONENTS_HEIGHT);
		pnlMain.add(lblPermission);
		
		rbAdmin = new JRadioButton();
		rbAdmin.setBounds(160, 138, 20, 40);
		rbAdmin.setSelected(true);
		pnlMain.add(rbAdmin);
		
		lblAdmin = new JLabel("Admin");
		lblAdmin.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblAdmin.setBounds(180, 138, 50, 40);
		pnlMain.add(lblAdmin);
		
		rbStudent = new JRadioButton();
		rbStudent.setBounds(240, 138, 20, 40);
		pnlMain.add(rbStudent);
		
		lblStudent = new JLabel("Student");
		lblStudent.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblStudent.setBounds(260, 138, 60, 40);
		pnlMain.add(lblStudent);

		rbTeacher = new JRadioButton();
		rbTeacher.setBounds(330, 138, 20, 40);
		pnlMain.add(rbTeacher);
		
		lblTeacher = new JLabel("Teacher");
		lblTeacher.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblTeacher.setBounds(350, 138, 60, 40);
		pnlMain.add(lblTeacher);
		
		// Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(rbAdmin);
        group.add(rbStudent);
        group.add(rbTeacher);
		
		cbShowPassWord = new JCheckBox();
		cbShowPassWord.setBounds(150, 180, 30, COMPONENTS_HEIGHT);
		cbShowPassWord.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(cbShowPassWord.isSelected()) {
            		pwfPassWord.setEchoChar((char)0);
            	}
            	else {
            		pwfPassWord.setEchoChar('*');
            	}
            }
		});
		pnlMain.add(cbShowPassWord);

		lblShowPassWord = new JLabel("Show PassWord");
		lblShowPassWord.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblShowPassWord.setBounds(180, 180, 120, COMPONENTS_HEIGHT);
		pnlMain.add(lblShowPassWord);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnLogin.setBounds(110, 240, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnLogin.setIcon(new ImageIcon("resources/login.png"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account ac = new Account();
				String userName = txtUserName.getText();
				String passWord = String.valueOf(pwfPassWord.getPassword());
				int permission = 0;
				if(rbAdmin.isSelected()) {
					permission = 0;
				}
				else if(rbStudent.isSelected()) {
					permission = 1;
				}
				else if(rbTeacher.isSelected()) {
					permission = 2;
				}
				else {
					JOptionPane.showMessageDialog(pnlMain, "Vui lòng chọn quyền trước khi đăng nhập",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				}
				try {
					if(ac.checkLogin(conn, userName, passWord, permission)) {
						switch(permission) {
						case 0:
							frmMHCAdmin = new GUI.Admin.FrmManHinhChinh(frmLG, conn, userName);
							frmMHCAdmin.setVisible(true);
							setVisible(false);
							break;
						case 1:
							frmMHCStudent = new GUI.Student.FrmManHinhChinh(frmLG, conn, userName);
							frmMHCStudent.setVisible(true);
							setVisible(false);
							break;
						default:
							frmMHCTeacher = new GUI.Teacher.FrmManHinhChinh(frmLG, conn, userName);
							frmMHCTeacher.setVisible(true);
							setVisible(false);
							break;
						}
					}
					else {
						JOptionPane.showMessageDialog(pnlMain, "Tài khoản, mật khẩu hoặc quyền đăng nhập không đúng! Vui long kiểm tra lại!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
					
					JOptionPane.showMessageDialog(pnlMain, ex.getMessage(),  "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pnlMain.add(btnLogin);
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnExit.setBounds(290, 240, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnExit.setIcon(new ImageIcon("resources/exit.png"));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(pnlMain, "Bạn có muốn thoát!", "Thông tin", JOptionPane.OK_CANCEL_OPTION);
				if(choose == JOptionPane.OK_OPTION) {
					setVisible(false);
				}
			}
		});
		pnlMain.add(btnExit);
		
		// Frame Size
		setSize(504, 336);
		//setBounds(0, 0, FRM_WIDTH, FRM_HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
	}
	public void Init() {
		InitGUI init = new InitGUI();
		this.FONT_TYPE = init.getFONT_TYPE();
		this.FONT = init.getFONT();
		this.FONT_SIZE = init.getFONT_SIZE();
		this.COMPONENTS_HEIGHT = init.getCOMPONENTS_HEIGHT();
		this.BUTTON_HEIGHT = init.getBUTTON_HEIGHT();
		this.BUTTON_WIDTH = init.getBUTTON_WIDTH();
	}
	public static void main(String[] args) {
		try {
			conn = DBConnection.initializeDatabase();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		FrmLogin frmLogin = new FrmLogin();
		frmLogin.setVisible(true);
	}
}
