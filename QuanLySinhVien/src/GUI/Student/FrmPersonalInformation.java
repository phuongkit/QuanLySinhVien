package GUI.Student;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.toedter.calendar.JDateChooser;

import GUI.InitGUI;
import Model.Account;
import Model.Faculty;
import Model.Student;

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
	private static JComboBox cbbGender;
	private static DefaultComboBoxModel cbbGenderModel;
	private static JDateChooser dtDateofBirth;
	private static JTextField txtEmail;
	private static JTextField txtPhone;
	private static JTextField txtAddress;
	private static JComboBox cbbFid;
	private static DefaultComboBoxModel cbbFacultyModel;
	private static JTextField txtAid;
	private static JTextField txtUserName;
	private static JTextField txtPermission;
	private static JTextField txtDateCreate;

	private static JButton btnUpdate;
	private static JButton btnSave;
	private static JButton btnCancel;
	private static JLabel lblAid;

	public FrmPersonalInformation(Connection conn, String userName) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		formatter = new SimpleDateFormat("dd/MM/yyyy");  
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
		lblID.setBounds(35, 55, 82, 20);
		contentPane.add(lblID);

		JLabel lblName = new JLabel("Tên:");
		lblName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblName.setBounds(35, 115, 82, 20);
		contentPane.add(lblName);

		JLabel lblGender = new JLabel("Giới tính:");
		lblGender.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblGender.setBounds(35, 175, 82, 20);
		contentPane.add(lblGender);
		
		JLabel lblDateofBirth = new JLabel("Ngày sinh:");
		lblDateofBirth.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblDateofBirth.setBounds(35, 235, 82, 20);
		contentPane.add(lblDateofBirth);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblEmail.setBounds(35, 295, 82, 20);
		contentPane.add(lblEmail);

		JLabel lblPhone = new JLabel("Số điện thoại:");
		lblPhone.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPhone.setBounds(35, 355, 100, 20);
		contentPane.add(lblPhone);

		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblAddress.setBounds(532, 55, 82, 20);
		contentPane.add(lblAddress);
		
		JLabel lblFid = new JLabel("FID:");
		lblFid.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblFid.setBounds(532, 115, 82, 20);
		contentPane.add(lblFid);

		lblAid = new JLabel("AID:");
		lblAid.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblAid.setBounds(532, 175, 82, 20);
		contentPane.add(lblAid);
		
		JLabel lblUserName = new JLabel("UserName:");
		lblUserName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblUserName.setBounds(532, 235, 82, 20);
		contentPane.add(lblUserName);

		JLabel lblPermission = new JLabel("Loại tài khoản:");
		lblPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPermission.setBounds(532, 295, 113, 20);
		contentPane.add(lblPermission);

		JLabel lblDateCreate = new JLabel("Ngày tạo:");
		lblDateCreate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblDateCreate.setBounds(532, 355, 82, 20);
		contentPane.add(lblDateCreate);

		txtID = new JTextField();
		txtID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtID.setBounds(152, 55, 113, 20);
		txtID.setEditable(false);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtName = new JTextField();
		txtName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtName.setColumns(10);
		txtName.setBounds(152, 115, 163, 20);
		contentPane.add(txtName);
		
		ArrayList<Gender> genders = new ArrayList<Gender>();
		genders.add(new Gender(true, "Nam"));
		genders.add(new Gender(false, "Nữ"));
		cbbGenderModel = new DefaultComboBoxModel();
		for(Gender gd : genders) {
			cbbGenderModel.addElement(gd);
		}
		
		cbbGender = new JComboBox();
		cbbGender.setModel(cbbGenderModel);
		cbbGender.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbGender.setBounds(152, 175, 163, 20);
		cbbGender.setRenderer(new GenderRenderer());
		contentPane.add(cbbGender);

		dtDateofBirth = new JDateChooser();
		dtDateofBirth.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		dtDateofBirth.setBounds(152, 235, 163, 20);
		contentPane.add(dtDateofBirth);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtEmail.setColumns(10);
		txtEmail.setBounds(152, 295, 163, 20);
		contentPane.add(txtEmail);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtPhone.setColumns(10);
		txtPhone.setBounds(152, 355, 163, 20);
		contentPane.add(txtPhone);

		txtAddress = new JTextField();
		txtAddress.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtAddress.setColumns(10);
		txtAddress.setBounds(638, 55, 300, 20);
		contentPane.add(txtAddress);
		
		ArrayList<Faculty> facultys = new ArrayList<Faculty>();
		try {
			facultys = Faculty.load(conn);
		}
		catch(ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		cbbFacultyModel = new DefaultComboBoxModel();
		for(Faculty fl : facultys) {
			cbbFacultyModel.addElement(fl);
		}
		
		cbbFid = new JComboBox();
		cbbFid.setModel(cbbFacultyModel);
		cbbFid.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbFid.setBounds(638, 115, 223, 20);
		cbbFid.setRenderer(new FacultyRenderer());
		contentPane.add(cbbFid);
		
		txtAid = new JTextField();
		txtAid.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtAid.setEnabled(false);
		txtAid.setColumns(10);
		txtAid.setBounds(638, 175, 121, 20);
		contentPane.add(txtAid);
		
		txtUserName = new JTextField();
		txtUserName.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtUserName.setEditable(false);
		txtUserName.setColumns(10);
		txtUserName.setBounds(638, 235, 163, 20);
		contentPane.add(txtUserName);

		txtPermission = new JTextField();
		txtPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtPermission.setEditable(false);
		txtPermission.setColumns(10);
		txtPermission.setBounds(638, 295, 163, 20);
		contentPane.add(txtPermission);

		txtDateCreate = new JTextField();
		txtDateCreate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtDateCreate.setEditable(false);
		txtDateCreate.setColumns(10);
		txtDateCreate.setBounds(638, 355, 163, 20);
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
	public static void Load(){
		txtName.setEditable(false);
		cbbGender.setEnabled(false);
		dtDateofBirth.setEnabled(false);
		txtEmail.setEditable(false);
		txtPhone.setEditable(false);
		txtAddress.setEditable(false);
		cbbFid.setEnabled(false);

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
		Student sd = new Student();
		try {
			sd = Student.findStudentofAID(ac.getAid(), conn);
		}catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtID.setText(sd.getId());
		txtName.setText(sd.getName());
		if(sd.isGender()) {
			cbbGender.setSelectedIndex(0);
		}
		else {
			cbbGender.setSelectedIndex(1);
		}
		dtDateofBirth.setDate(sd.getDateOfBirth());
		txtEmail.setText(sd.getEmail());
		
		Faculty fl = new Faculty();
		String FID = sd.getFid();
		int fid = -1;
		for(int i = 0;i<cbbFacultyModel.getSize();i++) {
			Faculty fli = (Faculty)cbbFacultyModel.getElementAt(i);
			if(fli.getFid().equals(FID)) {
				fid=i;
				break;
			}
		}
		cbbFid.setSelectedIndex(fid);
		
		txtPhone.setText(sd.getPhone());
		txtAddress.setText(sd.getAddress());
		txtAid.setText(sd.getAid());
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
		txtDateCreate.setText(formatter.format(ac.getDateOfCreate()));
	}
	public static void Update() {
		txtName.setEditable(true);;
		txtEmail.setEditable(true);
		cbbGender.setEnabled(true);
		dtDateofBirth.setEnabled(true);
		txtPhone.setEditable(true);
		txtAddress.setEditable(true);
		cbbFid.setEnabled(true);

		btnUpdate.setEnabled(false);
		btnSave.setEnabled(true);;
		btnCancel.setEnabled(true);
	}
	public static void Save() {
		try {
			Student tc = Student.findStudent(ID, conn);
			tc.setName(txtName.getText());
			boolean gender = ((Gender)cbbGender.getSelectedItem()).type;
			tc.setGender(gender);
			Date dt = dtDateofBirth.getDate();
			tc.setDateOfBirth(dt);
			tc.setEmail(txtEmail.getText());
			tc.setPhone(txtPhone.getText());
			tc.setAddress(txtAddress.getText());
			Student.Edit(tc, conn);
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
	class Gender{
		private boolean type;
		private String name;
		public Gender(boolean type, String name) {
			this.type = type;
			this.name = name;
		}
		public boolean isType() {
			return type;
		}
		public void setType(boolean type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	class GenderRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof Gender){
                Gender val = (Gender) value;
                setText(val.getName());
            }
            return this;
        }
	}
	class FacultyRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof Faculty){
                Faculty val2 = (Faculty) value;
                setText(val2.getName());
            }
            return this;
        }
	}
}
