package GUI.Admin;

import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
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
import javax.swing.JPasswordField;
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
import Model.Account;

public class FrmAccount extends JInternalFrame {

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

	private static JTextField txtAID;
	private static JTextField txtUserName;
	private static JPasswordField pwfPassword;
	private static JComboBox cbbPermission;
	private static DefaultComboBoxModel cbbPermissionModel;
	private static JDateChooser dtDateofCreate;
	private static ArrayList<Account> lisAccount = new ArrayList<Account>();
	private static String[] columnName = {"AID", "Tên Đăng Nhập", "Mật Khẩu", "Quyền", "Ngày Tạo"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabAccount = new JTable(model) ;
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

	public FrmAccount(Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		FrmAccount.conn = conn;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		tabAccount.setBounds(10, 168, 870, 305);

		tabAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabAccount.getSelectedRow();
				if(row != -1) {
					txtAID.setText(tabAccount.getValueAt(row, 0).toString());
					txtUserName.setText(tabAccount.getValueAt(row, 1).toString());
					pwfPassword.setText(tabAccount.getValueAt(row, 2).toString());
					int permission = Integer.valueOf(tabAccount.getValueAt(row, 3).toString());
					cbbPermission.setSelectedIndex(permission);
					try {
						Date dt =formatter.parse(tabAccount.getValueAt(row, 4).toString());
						dtDateofCreate.setDate(dt);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabAccount);
		scrollPane.setBounds(0, 173, 955, 279);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel.setBounds(20, 20, 122, 38);
		contentPane.add(lblNewLabel);

		JLabel lblTnGingVin = new JLabel("Tên Đăng Nhập");
		lblTnGingVin.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblTnGingVin.setBounds(20, 64, 122, 38);
		contentPane.add(lblTnGingVin);

		JLabel lblEmail = new JLabel("Mật Khẩu");
		lblEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblEmail.setBounds(20, 104, 122, 38);
		contentPane.add(lblEmail);
		
		JLabel lblPermission = new JLabel("Quyền");
		lblPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblPermission.setBounds(539, 26, 122, 38);
		contentPane.add(lblPermission);

		JLabel lblNewLabel_1_1 = new JLabel("Ngày Tạo");
		lblNewLabel_1_1.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel_1_1.setBounds(539, 104, 122, 38);
		contentPane.add(lblNewLabel_1_1);

		txtAID = new JTextField();
		txtAID.setBounds(137, 27, 140, 27);
		contentPane.add(txtAID);
		txtAID.setColumns(10);

		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		txtUserName.setBounds(137, 63, 232, 27);
		contentPane.add(txtUserName);
		
		pwfPassword = new JPasswordField();
		pwfPassword.setColumns(10);
		pwfPassword.setBounds(137, 104, 167, 27);
		contentPane.add(pwfPassword);

		ArrayList<Permission> permissions = new ArrayList<Permission>();
		permissions.add(new Permission(0, "Admin"));
		permissions.add(new Permission(1, "Sinh Viên"));
		permissions.add(new Permission(2, "Giảng Viên"));
		cbbPermissionModel = new DefaultComboBoxModel();
		for(Permission pms : permissions) {
			cbbPermissionModel.addElement(pms);
		}

		cbbPermission = new JComboBox();
		cbbPermission.setModel(cbbPermissionModel);
		cbbPermission.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbPermission.setBounds(634, 26, 282, 27);
		cbbPermission.setRenderer(new PermissionRenderer());
		contentPane.add(cbbPermission);

		dtDateofCreate = new JDateChooser();
		dtDateofCreate.setBounds(634, 115, 282, 27);
		contentPane.add(dtDateofCreate);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFind.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
				txtAID.setEnabled(true);
				txtUserName.setEnabled(true);
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
				txtAID.setEnabled(false);
				txtUserName.setEnabled(false);
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
		load();
	}
	public static void load() {
		txtAID.setEnabled(true);
		txtUserName.setEnabled(true);
		ArrayList<Account> lisAccount = new ArrayList<Account>();
		try {
			lisAccount = Account.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabAccount.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[9];
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		for(int i=0; i <lisAccount.size();i++ )
		{    
			rows[0]=(lisAccount.get(i).getAid()); 
			rows[1]=(lisAccount.get(i).getUserName());
			rows[2]=(lisAccount.get(i).getPassWord());
			rows[3]=(lisAccount.get(i).getPermission());
			rows[4]=(lisAccount.get(i).getCreate_Date());

			model.addRow(rows); 
		}
	}
	public static void Add() {
		Account sd = new Account();
		sd.setAid(txtAID.getText().toString());
		sd.setUserName(txtUserName.getText().toString());
		sd.setPassWord(String.valueOf(pwfPassword.getPassword()));
		sd.setPermission(((Permission)cbbPermission.getSelectedItem()).getType());
		sd.setCreate_Date(dtDateofCreate.getDate());
		try {
			if(Account.Insert(sd, conn) == 1) {
				JOptionPane.showMessageDialog(tabAccount, "Thêm Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				lisAccount.add(sd);
				DefaultTableModel model = (DefaultTableModel)tabAccount.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog(tabAccount, "Thêm thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void Edit() {
		Account ac = new Account();
		ac.setAid(txtAID.getText().toString());
		ac.setUserName(txtUserName.getText().toString());
		ac.setPassWord(String.valueOf(pwfPassword.getPassword()));
		ac.setPermission(((Permission)cbbPermission.getSelectedItem()).getType());
		ac.setCreate_Date(dtDateofCreate.getDate());
		try {
			if(Account.Edit(ac, conn) == 1) {
				JOptionPane.showMessageDialog(tabAccount, "Sửa Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				for(int i=0; i< lisAccount.size();i++){
					if(lisAccount.get(i).getAid() == ac.getAid()) {
						lisAccount.get(i).setPassWord(ac.getPassWord());
						lisAccount.get(i).setPermission(ac.getPermission());
						lisAccount.get(i).setCreate_Date(ac.getCreate_Date());
						break;
					}
				}
				DefaultTableModel model = (DefaultTableModel)tabAccount.getModel();
				model.setRowCount(0);
				load();	
			}
			else
				JOptionPane.showMessageDialog(tabAccount, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);


		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	@SuppressWarnings("unlikely-arg-type")
	public static void Del() {
		String index=txtAID.getText().toString();
		try {
			if(Account.Del(index, conn)==1)
			{
				lisAccount.remove(index);
				JOptionPane.showMessageDialog( tabAccount, "Xoa thanh cong!",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = (DefaultTableModel)tabAccount.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog( tabAccount, "Xay ra loi",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void Find() {
		ArrayList<Account> lisAccount = new ArrayList<Account>();
		try {
			lisAccount = Account.load(conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabAccount.getModel();
		Object[] rows = new Object[9]; 
		String index = txtAID.getText().toString();
		try {
			if(Account.findAccount(index, conn) != null) {
				model.setRowCount(0);
				rows[0]=(lisAccount.get(Integer.valueOf(index)-1).getAid()); 
				rows[1]=(lisAccount.get(Integer.valueOf(index)-1).getUserName()); 
				rows[2]=(lisAccount.get(Integer.valueOf(index)-1).getPassWord());
				rows[3]=(lisAccount.get(Integer.valueOf(index)-1).getPermission());
				rows[4]=(lisAccount.get(Integer.valueOf(index)-1).getCreate_Date()); 

				model.addRow(rows); 
			}
			else {
				JOptionPane.showConfirmDialog(tabAccount, "Không Tìm Thấy!!","Thông Báo",JOptionPane.OK_OPTION);

			}
		} catch (NumberFormatException | HeadlessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void clear() {
		txtAID.setText("");
		txtUserName.setText("");
		pwfPassword.setText("");
		cbbPermission.setSelectedIndex(-1);
	}
	class Permission{
		private int type;
		private String name;
		public Permission(int type, String name) {
			this.type = type;
			this.name = name;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	class PermissionRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof Permission){
            	Permission val = (Permission) value;
                setText(val.getName());
            }
            return this;
        }
	}
}
