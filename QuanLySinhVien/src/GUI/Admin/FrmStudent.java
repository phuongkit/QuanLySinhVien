package GUI.Admin;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import GUI.InitGUI;
import Model.Account;
import Model.Faculty;
import Model.Student;

public class FrmStudent extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn = null;
	private SimpleDateFormat formatter;

	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int COMPONENTS_HEIGHT;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;

	private static JTextField txtID;
	private static JTextField txtName;
	private static JComboBox cbbGender;
	private static DefaultComboBoxModel cbbGenderModel;
	private static JDateChooser dtDateofBirth;
	private static JTextField txtEmail;
	private static JTextField txtPhone;
	private static JTextField txtAddress;
	private static JComboBox cbbAccount;
	private static DefaultComboBoxModel cbbAccountModel;
	private static JComboBox cbbFaculty;
	private static DefaultComboBoxModel cbbFacultyModel;
	private static ArrayList<Student> lisStudent = new ArrayList<Student>();
	private static String[] columnName = {"SID", "Tên Sinh Viên", "Giới Tính", "Ngày Sinh", "Email", "Số Điện Thoại", "Địa Chỉ","Tài Khoản", "Khoa"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabStudent = new JTable(model) ;
	private static JButton btnCancel = new JButton("Hủy");
	private static JButton btnSave = new JButton("Lưu");
	private static JButton btnDelete = new JButton("Xóa");
	private static JButton btnFind = new JButton("Tìm");
	private static JButton btnCreate = new JButton("Thêm");
	private static JButton btnEdit = new JButton("Sửa");
	private static int flag = 0;

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

	public FrmStudent(Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		FrmStudent.conn = conn;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		tabStudent.setBounds(10, 168, 870, 305);

		tabStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabStudent.getSelectedRow();
				if(row != -1) {
					txtID.setText(tabStudent.getValueAt(row, 0).toString());
					txtName.setText(tabStudent.getValueAt(row, 1).toString());
					boolean gender = Boolean.valueOf(tabStudent.getValueAt(row, 2).toString());
					if(gender) {
						cbbGender.setSelectedIndex(0);
					}
					else {
						cbbGender.setSelectedIndex(1);
					}
					try {
						Date dt =formatter.parse(tabStudent.getValueAt(row, 3).toString());
						dtDateofBirth.setDate(dt);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					txtEmail.setText(tabStudent.getValueAt(row, 4).toString());
					txtPhone.setText(tabStudent.getValueAt(row, 5).toString());
					txtAddress.setText(tabStudent.getValueAt(row, 6).toString());
					String AID = tabStudent.getValueAt(row, 7).toString();
					int aid=-1;
					for(int i = 0;i<cbbAccountModel.getSize();i++) {
						Account ac = (Account)cbbAccountModel.getElementAt(i);
						if(ac.getAid().equals(AID)) {
							aid=i;
							break;
						}
					}
					cbbAccount.setSelectedIndex(aid);
					String FID = tabStudent.getValueAt(row, 8).toString();
					int fid = -1;
					for(int i = 0;i<cbbFacultyModel.getSize();i++) {
						Faculty fl = (Faculty)cbbFacultyModel.getElementAt(i);
						if(fl.getFid().equals(FID)) {
							fid=i;
							break;
						}
					}
					cbbFaculty.setSelectedIndex(fid);;
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabStudent);
		scrollPane.setBounds(0, 173, 955, 279);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("TID");
		lblNewLabel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel.setBounds(20, 20, 122, 38);
		contentPane.add(lblNewLabel);

		JLabel lblTnGingVin = new JLabel("Tên Sinh Viên");
		lblTnGingVin.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblTnGingVin.setBounds(20, 56, 122, 38);
		contentPane.add(lblTnGingVin);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblEmail.setBounds(314, 56, 122, 38);
		contentPane.add(lblEmail);

		JLabel lblSinThoi = new JLabel("Số Điện Thoại");
		lblSinThoi.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSinThoi.setBounds(678, 20, 122, 38);
		contentPane.add(lblSinThoi);

		JLabel lblNewLabel_1_1 = new JLabel("Địa Chỉ");
		lblNewLabel_1_1.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel_1_1.setBounds(314, 104, 122, 38);
		contentPane.add(lblNewLabel_1_1);

		txtID = new JTextField();
		txtID.setBounds(137, 27, 96, 27);
		contentPane.add(txtID);
		txtID.setColumns(10);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(137, 63, 167, 27);
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
		cbbGender.setBounds(137, 113, 167, 27);
		cbbGender.setRenderer(new GenderRenderer());
		contentPane.add(cbbGender);

		dtDateofBirth = new JDateChooser();
		dtDateofBirth.setBounds(407, 31, 245, 27);
		contentPane.add(dtDateofBirth);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(407, 66, 245, 27);
		contentPane.add(txtEmail);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(407, 113, 245, 27);
		contentPane.add(txtAddress);

		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(791, 29, 147, 27);
		contentPane.add(txtPhone);

		ArrayList< Account> accounts = new ArrayList< Account>();
		try {
			accounts = Account.load(conn);

		}
		catch(ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		cbbAccountModel = new DefaultComboBoxModel();
		for(Account ac : accounts) {
			cbbAccountModel.addElement(ac);
		}

		cbbAccount = new JComboBox(cbbAccountModel);
		cbbAccount.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbAccount.setBounds(791, 66, 147, 27);
		cbbAccount.setRenderer(new AccountRenderer());
		contentPane.add(cbbAccount);

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

		cbbFaculty = new JComboBox(cbbFacultyModel);
		cbbFaculty.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbFaculty.setBounds(731, 113, 207, 27);
		cbbFaculty.setRenderer(new FacultyRenderer());
		contentPane.add(cbbFaculty);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFind.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
				txtID.setEnabled(true);
			}
		});
		btnCancel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCancel.setBounds(814, 462, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnCancel.setIcon(new ImageIcon("resources/cancel.png"));
		contentPane.add(btnCancel);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flag == 0)
					Add();
				else if(flag == 1)
					Edit();

				btnFind.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
			}
		});
		btnSave.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnSave.setBounds(654, 461, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnSave.setIcon(new ImageIcon("resources/save.jpg"));
		contentPane.add(btnSave);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del();
			}
		});
		btnDelete.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnDelete.setBounds(493, 462, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnDelete.setIcon(new ImageIcon("resources/delete.png"));
		contentPane.add(btnDelete);

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = 1; 
				txtID.setEnabled(false);
				btnFind.setEnabled(false);
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(false);
				btnCancel.setEnabled(true);
				btnSave.setEnabled(true);
				btnCreate.setEnabled(false);
			}
		});
		btnEdit.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnEdit.setBounds(332, 461, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnEdit.setIcon(new ImageIcon("resources/edit.png"));
		contentPane.add(btnEdit);

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = 0;
				btnFind.setEnabled(false);
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(false);
				btnCancel.setEnabled(true);
				btnSave.setEnabled(true);
				btnCreate.setEnabled(false);
				clear();
			}
		});
		btnCreate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCreate.setBounds(171, 461, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnCreate.setIcon(new ImageIcon("resources/create.png"));
		contentPane.add(btnCreate);

		btnFind.setIcon(new ImageIcon("resources/find.png"));
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Find();
			}
		});

		btnFind.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFind.setBounds(10, 462, BUTTON_WIDTH, BUTTON_HEIGHT);
		contentPane.add(btnFind);

		JLabel lblNewLabel_1_1_1 = new JLabel("Tài Khoản");
		lblNewLabel_1_1_1.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel_1_1_1.setBounds(678, 56, 122, 38);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblGiiTnh = new JLabel("Giới Tính");
		lblGiiTnh.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblGiiTnh.setBounds(20, 104, 122, 38);
		contentPane.add(lblGiiTnh);

		JLabel lblNgySinh = new JLabel("Ngày Sinh");
		lblNgySinh.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNgySinh.setBounds(314, 20, 122, 38);
		contentPane.add(lblNgySinh);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Khoa");
		lblNewLabel_1_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1.setBounds(678, 104, 122, 38);
		contentPane.add(lblNewLabel_1_1_1_1);
		load();
	}
	public static void load() {
		ArrayList<Student> lisStudent = new ArrayList<Student>();
		try {
			lisStudent = Student.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabStudent.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[9];
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		for(int i=0; i <lisStudent.size();i++ )
		{    
			rows[0]=(lisStudent.get(i).getId()); 
			rows[1]=(lisStudent.get(i).getName()); 
			rows[2]=(lisStudent.get(i).isGender());
			rows[3]=(lisStudent.get(i).getDateOfBirth());
			rows[4]=(lisStudent.get(i).getEmail()); 
			rows[5]=(lisStudent.get(i).getPhone());
			rows[6]=(lisStudent.get(i).getAddress()); 
			rows[7]=(lisStudent.get(i).getAid()); 
			rows[8]=(lisStudent.get(i).getFid());

			model.addRow(rows); 
		}
	}
	public static void Add() {
		Student sd = new Student();
		sd.setId(txtID.getText().toString());
		sd.setName(txtName.getText().toString());
		sd.setGender(((Gender)cbbGender.getSelectedItem()).isType());
		sd.setDateOfBirth(dtDateofBirth.getDate());
		sd.setEmail(txtEmail.getText().toString());
		sd.setPhone(txtPhone.getText().toString());
		sd.setAddress(txtAddress.getText().toString());
		sd.setAid(((Account)cbbAccount.getSelectedItem()).getAid());
		sd.setFid(((Faculty)cbbFaculty.getSelectedItem()).getFid());
		try {
			if(Student.Insert(sd, conn) == 1) {
				JOptionPane.showMessageDialog(tabStudent, "Thêm Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				lisStudent.add(sd);
				DefaultTableModel model = (DefaultTableModel)tabStudent.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog(tabStudent, "Thêm thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void Edit() {
		Student sd = new Student();
		sd.setId(txtID.getText().toString());
		sd.setName(txtName.getText().toString());
		sd.setGender(((Gender)cbbGender.getSelectedItem()).isType());
		sd.setDateOfBirth(dtDateofBirth.getDate());
		sd.setEmail(txtEmail.getText().toString());
		sd.setPhone(txtPhone.getText().toString());
		sd.setAddress(txtAddress.getText().toString());
		sd.setAid(((Account)cbbAccount.getSelectedItem()).getAid());
		sd.setFid(((Faculty)cbbFaculty.getSelectedItem()).getFid());
		try {
			if(Student.Edit(sd, conn) == 1) {
				JOptionPane.showMessageDialog(tabStudent, "Sửa Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				for(int i=0; i< lisStudent.size();i++){
					if(lisStudent.get(i).getId() == sd.getId()) {
						lisStudent.get(i).setName(sd.getName());
						lisStudent.get(i).setGender(sd.isGender());
						lisStudent.get(i).setDateOfBirth(sd.getDateOfBirth());
						lisStudent.get(i).setEmail(sd.getEmail());
						lisStudent.get(i).setPhone(sd.getPhone());
						lisStudent.get(i).setAddress(sd.getAddress());
						lisStudent.get(i).setAid(sd.getAid());
						lisStudent.get(i).setFid(sd.getFid());
						break;
					}

				}
				DefaultTableModel model = (DefaultTableModel)tabStudent.getModel();
				model.setRowCount(0);
				load();	
			}
			else
				JOptionPane.showMessageDialog(tabStudent, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);


		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	@SuppressWarnings("unlikely-arg-type")
	public static void Del() {
		String index=txtID.getText().toString();
		try {
			if(Student.Del(index, conn)==1)
			{
				lisStudent.remove(index);
				JOptionPane.showMessageDialog( tabStudent, "Xoa thanh cong!",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = (DefaultTableModel)tabStudent.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog( tabStudent, "Xay ra loi",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void Find() {
		ArrayList<Student> lisStudent = new ArrayList<Student>();
		try {
			lisStudent = Student.load(conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabStudent.getModel();
		Object[] rows = new Object[9]; 
		String index = txtID.getText().toString();
		if(Student.findStudent(index, conn) != null) {
			model.setRowCount(0);
			rows[0]=(lisStudent.get(Integer.valueOf(index)-1).getId()); 
			rows[1]=(lisStudent.get(Integer.valueOf(index)-1).getName()); 
			rows[2]=(lisStudent.get(Integer.valueOf(index)-1).isGender());
			rows[3]=(lisStudent.get(Integer.valueOf(index)-1).getDateOfBirth());
			rows[4]=(lisStudent.get(Integer.valueOf(index)-1).getEmail()); 
			rows[5]=(lisStudent.get(Integer.valueOf(index)-1).getPhone());
			rows[6]=(lisStudent.get(Integer.valueOf(index)-1).getAddress()); 
			rows[7]=(lisStudent.get(Integer.valueOf(index)-1).getAid()); 
			rows[8]=(lisStudent.get(Integer.valueOf(index)-1).getFid());

			model.addRow(rows); 
		}
		else {
			JOptionPane.showConfirmDialog(tabStudent, "Không Tìm Thấy!!","Thông Báo",JOptionPane.OK_OPTION);

		}
	}
	public static void clear() {
		txtID.setText("");
		txtName.setText("");
		txtEmail.setText("");
		txtPhone.setText("");
		txtAddress.setText("");
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
	class AccountRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof Account){
                Account val1 = (Account) value;
                setText(val1.getUserName());
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
