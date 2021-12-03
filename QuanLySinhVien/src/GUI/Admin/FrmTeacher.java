package GUI.Admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import GUI.InitGUI;
import Model.Teacher;
import javax.swing.ImageIcon;


public class FrmTeacher extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn = null;

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
	private static JTextField txtEmail;
	private static JTextField txtSDT;
	private static JTextField txtDiaChi;
	private static JTextField txtCID;
	private static ArrayList<Teacher> lisTeacher = new ArrayList<Teacher>();
	private static String[] columnName = {"TID", "Tên Giảng Viên", "Email", "Số Điện Thoại", "Địa Chỉ","AID"};
	static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabTeacher = new JTable(model) ;
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

	public FrmTeacher(Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		this.conn = conn;
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);


		tabTeacher.setBounds(10, 168, 870, 305);

		tabTeacher.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabTeacher.getSelectedRow();
				if(row != -1) {
					txtID.setText(tabTeacher.getValueAt(row, 0).toString());
					txtName.setText(tabTeacher.getValueAt(row, 1).toString());
					txtEmail.setText(tabTeacher.getValueAt(row, 2).toString());
					txtSDT.setText(tabTeacher.getValueAt(row, 3).toString());
					txtDiaChi.setText(tabTeacher.getValueAt(row, 4).toString());
					txtCID.setText(tabTeacher.getValueAt(row, 5).toString());
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabTeacher);
		scrollPane.setBounds(0, 173, 955, 279);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("TID");
		lblNewLabel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel.setBounds(20, 20, 122, 38);
		contentPane.add(lblNewLabel);

		JLabel lblTnGingVin = new JLabel("Tên Giảng Viên");
		lblTnGingVin.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblTnGingVin.setBounds(20, 56, 122, 38);
		contentPane.add(lblTnGingVin);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblEmail.setBounds(20, 93, 122, 38);
		contentPane.add(lblEmail);

		JLabel lblSinThoi = new JLabel("Số Điện Thoại");
		lblSinThoi.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSinThoi.setBounds(422, 20, 122, 38);
		contentPane.add(lblSinThoi);

		JLabel lblNewLabel_1_1 = new JLabel("Địa Chỉ");
		lblNewLabel_1_1.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel_1_1.setBounds(422, 56, 122, 38);
		contentPane.add(lblNewLabel_1_1);

		txtID = new JTextField();
		txtID.setBounds(137, 27, 96, 27);
		contentPane.add(txtID);
		txtID.setColumns(10);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(137, 63, 237, 27);
		contentPane.add(txtName);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(137, 100, 237, 27);
		contentPane.add(txtEmail);

		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		txtSDT.setBounds(539, 29, 237, 27);
		contentPane.add(txtSDT);

		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(539, 65, 237, 27);
		contentPane.add(txtDiaChi);

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
		btnCancel.setBounds(731, 453, BUTTON_WIDTH, BUTTON_HEIGHT);
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
		btnSave.setBounds(616, 453, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnSave.setIcon(new ImageIcon("resources/save.jpg"));
		contentPane.add(btnSave);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del();
			}
		});


		btnDelete.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnDelete.setBounds(499, 453, BUTTON_WIDTH, BUTTON_HEIGHT);
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
		btnEdit.setBounds(384, 453, BUTTON_WIDTH, BUTTON_HEIGHT);
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
		btnCreate.setBounds(269, 453, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnCreate.setIcon(new ImageIcon("resources/create.png"));
		contentPane.add(btnCreate);

		btnFind.setIcon(new ImageIcon("resources/find.png"));
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Find();
			}
		});


		btnFind.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFind.setBounds(154, 453, BUTTON_WIDTH, BUTTON_HEIGHT);
		contentPane.add(btnFind);

		JLabel lblNewLabel_1_1_1 = new JLabel("AID");
		lblNewLabel_1_1_1.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel_1_1_1.setBounds(422, 93, 122, 38);
		contentPane.add(lblNewLabel_1_1_1);

		txtCID = new JTextField();
		txtCID.setColumns(10);
		txtCID.setBounds(539, 102, 237, 27);
		contentPane.add(txtCID);
		load();
	}
	public static void load() {
		ArrayList<Teacher> lisTeacher = new ArrayList<Teacher>();
		lisTeacher = Teacher.load(conn);
		DefaultTableModel model = (DefaultTableModel)tabTeacher.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[6];
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		for(int i=0; i <lisTeacher.size();i++ )
		{    
			rows[0]=(lisTeacher.get(i).getId()); 
			rows[1]=(lisTeacher.get(i).getName()); 
			rows[2]=(lisTeacher.get(i).getEmail()); 
			rows[3]=(lisTeacher.get(i).getPhone()); 
			rows[4]=(lisTeacher.get(i).getAddress()); 
			rows[5]=(lisTeacher.get(i).getAid()); 

			model.addRow(rows); 
		}
	}
	public static void Add() {
		Teacher tc = new Teacher();
		tc.setId(txtID.getText().toString());
		tc.setName(txtName.getText().toString());
		tc.setEmail(txtEmail.getText().toString());
		tc.setPhone(txtSDT.getText().toString());
		tc.setAddress(txtDiaChi.getText().toString());
		tc.setAid(txtCID.getText().toString());
		try {
			if(Teacher.Insert(tc, conn) == 1) {
				JOptionPane.showMessageDialog(tabTeacher, "Thêm Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				lisTeacher.add(tc);
				DefaultTableModel model = (DefaultTableModel)tabTeacher.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog(tabTeacher, "Thêm thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void Edit() {
		String[] teacher = new String[6];

		teacher[0] =txtID.getText().toString();
		teacher[1] =txtName.getText().toString();
		teacher[2] =txtEmail.getText().toString();
		teacher[3] =txtSDT.getText().toString();
		teacher[4] =txtDiaChi.getText().toString();
		teacher[5] =txtCID.getText().toString();
		Teacher tc = new Teacher();
		tc.setId(teacher[0]);
		tc.setName(teacher[1]);
		tc.setEmail(teacher[2]);
		tc.setPhone(teacher[3]);
		tc.setAddress(teacher[4]);
		tc.setAid(teacher[5]);
		try {
			if(Teacher.Edit(tc, conn) == 1) {
				JOptionPane.showMessageDialog(tabTeacher, "Sửa Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				for(int i=0; i< lisTeacher.size();i++){
					if(lisTeacher.get(i).getId() == teacher[0]) {
						lisTeacher.get(i).setName(teacher[1]);
						lisTeacher.get(i).setEmail(teacher[2]);
						lisTeacher.get(i).setPhone(teacher[3]);
						lisTeacher.get(i).setAddress(teacher[4]);
						lisTeacher.get(i).setAid(teacher[5]);
					}

				}
				DefaultTableModel model = (DefaultTableModel)tabTeacher.getModel();
				model.setRowCount(0);
				load();	
			}
			else
				JOptionPane.showMessageDialog(tabTeacher, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);


		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void Del() {
		String index=txtID.getText().toString();

		try {
			if(Teacher.Del(index, conn)==1)
			{
				lisTeacher.remove(index);
				JOptionPane.showMessageDialog( tabTeacher, "Xoa thanh cong!",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = (DefaultTableModel)tabTeacher.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog( tabTeacher, "Xay ra loi",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void Find() {
		ArrayList<Teacher> lisTeacher = new ArrayList<Teacher>();
		lisTeacher = Teacher.load(conn);
		DefaultTableModel model = (DefaultTableModel)tabTeacher.getModel();
		Object[] rows = new Object[6]; 
		String index = txtID.getText().toString();
		if(Teacher.findTeacher(index, conn) != null) {
			model.setRowCount(0);
			rows[0]=(lisTeacher.get(Integer.valueOf(index)-1).getId()); 
			rows[1]=(lisTeacher.get(Integer.valueOf(index)-1).getName()); 
			rows[2]=(lisTeacher.get(Integer.valueOf(index)-1).getEmail()); 
			rows[3]=(lisTeacher.get(Integer.valueOf(index)-1).getPhone());
			rows[4]=(lisTeacher.get(Integer.valueOf(index)-1).getAddress()); 
			rows[5]=(lisTeacher.get(Integer.valueOf(index)-1).getAid()); 

			model.addRow(rows); 
		}
		else {
			JOptionPane.showConfirmDialog(tabTeacher, "Không Tìm Thấy!!","Thông Báo",JOptionPane.OK_OPTION);

		}
	}
	public static void clear() {
		txtID.setText("");
		txtName.setText("");
		txtEmail.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtCID.setText("");
	}
}
