package GUI.Teacher;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.InitGUI;
import javax.swing.JScrollBar;

import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Model.Account;
import Model.Admin;
import Model.Teacher;

public class FrmPersonalInformation  extends JInternalFrame{
	private static Connection conn = null;
	private static String userName;
	private static String ID;
	private static SimpleDateFormat formatter;

	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int COMPONENTS_HEIGHT;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;

	private static JPanel contentPane;
	private static JTextField txtID;
	private static JTextField txtName;
	private static JTextField txtEmail;
	private static JTextField txtPhone;
	private static JTextField txtAddress;
	private static JTextField txtUserName;
	private static JTextField txtPermission;
	private static JTextField txtDateCreate;

	private static JButton btnUpdate;
	private static JButton btnSave;
	private static JButton btnCancel;
	private static JLabel lblAid;
	private static JTextField txtAID;

	public FrmPersonalInformation(Connection conn, String userName) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		this.userName = userName;
		Init();
		FrmPersonalInformation.conn = conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);

		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Thông tin cá nhân");
		lblNewLabel_1.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE+4));
		lblNewLabel_1.setBounds(352, 10, 184, 26);
		contentPane.add(lblNewLabel_1);

		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblID.setBounds(35, 60, 82, 13);
		contentPane.add(lblID);

		JLabel lblName = new JLabel("Tên:");
		lblName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblName.setBounds(35, 118, 82, 13);
		contentPane.add(lblName);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblEmail.setBounds(35, 190, 82, 13);
		contentPane.add(lblEmail);

		JLabel lblPhone = new JLabel("Số điện thoại:");
		lblPhone.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPhone.setBounds(35, 275, 100, 13);
		contentPane.add(lblPhone);

		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblAddress.setBounds(35, 358, 82, 13);
		contentPane.add(lblAddress);

		JLabel lblUserName = new JLabel("UserName:");
		lblUserName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblUserName.setBounds(532, 161, 82, 13);
		contentPane.add(lblUserName);

		JLabel lblPermission = new JLabel("Loại tài khoản:");
		lblPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPermission.setBounds(532, 249, 113, 13);
		contentPane.add(lblPermission);

		JLabel lblDateCreate = new JLabel("Ngày tạo:");
		lblDateCreate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblDateCreate.setBounds(532, 358, 82, 13);
		contentPane.add(lblDateCreate);

		txtID = new JTextField();
		txtID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtID.setBounds(152, 57, 113, 19);
		txtID.setEditable(false);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtName = new JTextField();
		txtName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtName.setColumns(10);
		txtName.setBounds(152, 115, 163, 19);
		contentPane.add(txtName);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtEmail.setColumns(10);
		txtEmail.setBounds(152, 187, 163, 19);
		contentPane.add(txtEmail);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtPhone.setColumns(10);
		txtPhone.setBounds(152, 272, 163, 19);
		contentPane.add(txtPhone);

		txtAddress = new JTextField();
		txtAddress.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtAddress.setColumns(10);
		txtAddress.setBounds(152, 355, 319, 19);
		contentPane.add(txtAddress);

		txtUserName = new JTextField();
		txtUserName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtUserName.setEditable(false);
		txtUserName.setColumns(10);
		txtUserName.setBounds(638, 158, 163, 19);
		contentPane.add(txtUserName);

		txtPermission = new JTextField();
		txtPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtPermission.setEditable(false);
		txtPermission.setColumns(10);
		txtPermission.setBounds(638, 246, 163, 19);
		contentPane.add(txtPermission);

		txtDateCreate = new JTextField();
		txtDateCreate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtDateCreate.setEditable(false);
		txtDateCreate.setColumns(10);
		txtDateCreate.setBounds(638, 355, 163, 19);
		contentPane.add(txtDateCreate);

		btnUpdate = new JButton("Sửa");
		btnUpdate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnUpdate.setBounds(190, 439, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnUpdate.setIcon(new ImageIcon("resources/update.jpg"));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update();
			}
		});
		contentPane.add(btnUpdate);

		btnSave = new JButton("Lưu");
		btnSave.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnSave.setBounds(396, 439, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnSave.setIcon(new ImageIcon("resources/save.jpg"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		contentPane.add(btnSave);

		btnCancel = new JButton("Hủy");
		btnCancel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCancel.setBounds(619, 439, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnCancel.setIcon(new ImageIcon("resources/cancel.png"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		contentPane.add(btnCancel);
		
		lblAid = new JLabel("AID:");
		lblAid.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblAid.setBounds(532, 57, 82, 13);
		contentPane.add(lblAid);
		
		txtAID = new JTextField();
		txtAID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtAID.setEnabled(false);
		txtAID.setColumns(10);
		txtAID.setBounds(638, 57, 121, 19);
		contentPane.add(txtAID);
		Load();
		ID = txtID.getText();
	}
	public void Init() {
		InitGUI init = new InitGUI();
		this.FONT_TYPE = init.getFONT_TYPE();
		this.FONT = init.getFONT();
		this.FONT_SIZE = init.getFONT_SIZE();
		this.COMPONENTS_HEIGHT = init.getCOMPONENTS_HEIGHT();
		this.BUTTON_HEIGHT = init.getBUTTON_HEIGHT();
		this.BUTTON_WIDTH = init.getBUTTON_WIDTH();
		this.SCREEN_WIDTH = init.getSCREEN_WIDTH();
		this.SCREEN_HEIGHT=init.getSCREEN_HEIGHT();
	}
	public static void Load() {
		txtName.setEditable(false);;
		txtEmail.setEditable(false);
		txtPhone.setEditable(false);
		txtAddress.setEditable(false);

		btnUpdate.setEnabled(true);
		btnSave.setEnabled(false);;
		btnCancel.setEnabled(false);
		
		Account ac = new Account();
		try {
			ac = Account.findAccountofUserName(userName, conn);
		}catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(ac.getAid());
		Teacher tc = new Teacher();
		try {
			tc = Teacher.findTeacherofAID(ac.getAid(), conn);
		}catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtID.setText(tc.getId());
		txtName.setText(tc.getName());
		txtEmail.setText(tc.getEmail());
		txtPhone.setText(tc.getPhone());
		txtAddress.setText(tc.getAddress());
		txtAID.setText(tc.getAid());
		txtUserName.setText(ac.getUserName());
		switch(ac.getPermission()) {
		case 0:
			txtPermission.setText("Admin");
			break;
		case 1:
			txtPermission.setText("Sinh viên");
			break;
		default:
			txtPermission.setText("Giảng viên");
			break;
		}
		formatter = new SimpleDateFormat("dd/MM/yyyy");  
		txtDateCreate.setText(formatter.format(ac.getDateOfCreate()));
	}
	public static void Update() {
		txtName.setEditable(true);;
		txtEmail.setEditable(true);
		txtPhone.setEditable(true);
		txtAddress.setEditable(true);

		btnUpdate.setEnabled(false);
		btnSave.setEnabled(true);;
		btnCancel.setEnabled(true);
	}
	public static void Save() {
		try {
			Teacher tc = Teacher.findTeacher(ID, conn);
			tc.setName(txtName.getText());
			tc.setEmail(txtEmail.getText());
			tc.setPhone(txtPhone.getText());
			tc.setAddress(txtAddress.getText());
			Teacher.Edit(tc, conn);
			JOptionPane.showMessageDialog(contentPane, "Cập nhật thông tin thành công!",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Load();
	}
	public static void Cancel() {
		Load();
	}
}
