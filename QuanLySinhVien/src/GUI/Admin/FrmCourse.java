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
import Model.Course;

public class FrmCourse extends JInternalFrame {

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
	private static JTextField txtDescription;
	private static JComboBox cbbNumberOfCredits;
	private static DefaultComboBoxModel cbbNumberOfCreditsModel;
	private static ArrayList<Course> lisCourse = new ArrayList<Course>();
	private static String[] columnName = {"CID", "Tên Khóa Học", "Mô Tả", "Số Tín Chỉ"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabCourse = new JTable(model) ;
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

	public FrmCourse(Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		FrmCourse.conn = conn;
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		tabCourse.setBounds(10, 168, 870, 305);

		tabCourse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabCourse.getSelectedRow();
				if(row != -1) {
					txtID.setText(tabCourse.getValueAt(row, 0).toString());
					txtName.setText(tabCourse.getValueAt(row, 1).toString());
					txtDescription.setText(tabCourse.getValueAt(row, 2).toString());
					int numberOfCredits = Integer.valueOf(tabCourse.getValueAt(row, 3).toString());
					cbbNumberOfCredits.setSelectedIndex(numberOfCredits - 1);
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabCourse);
		scrollPane.setBounds(0, 173, 955, 279);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("CID");
		lblNewLabel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblNewLabel.setBounds(20, 20, 122, 38);
		contentPane.add(lblNewLabel);

		JLabel lblTnGingVin = new JLabel("Tên Khóa Học");
		lblTnGingVin.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblTnGingVin.setBounds(20, 107, 122, 38);
		contentPane.add(lblTnGingVin);

		JLabel lblEmail = new JLabel("Mô Tả");
		lblEmail.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblEmail.setBounds(474, 20, 122, 38);
		contentPane.add(lblEmail);

		JLabel lblSinThoi = new JLabel("Số Tín Chỉ");
		lblSinThoi.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSinThoi.setBounds(474, 102, 122, 38);
		contentPane.add(lblSinThoi);

		txtID = new JTextField();
		txtID.setBounds(152, 29, 120, 27);
		contentPane.add(txtID);
		txtID.setColumns(10);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(152, 111, 167, 27);
		contentPane.add(txtName);
		
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		txtDescription.setBounds(619, 29, 250, 27);
		contentPane.add(txtDescription);

		ArrayList<NumberOfCredits> numberOfCredits = new ArrayList<NumberOfCredits>();
		numberOfCredits.add(new NumberOfCredits(1, "1"));
		numberOfCredits.add(new NumberOfCredits(2, "2"));
		numberOfCredits.add(new NumberOfCredits(3, "3"));
		cbbNumberOfCreditsModel = new DefaultComboBoxModel();
		for(NumberOfCredits noc : numberOfCredits) {
			cbbNumberOfCreditsModel.addElement(noc);
		}

		cbbNumberOfCredits = new JComboBox();
		cbbNumberOfCredits.setModel(cbbNumberOfCreditsModel);
		cbbNumberOfCredits.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbNumberOfCredits.setBounds(619, 113, 137, 27);
		cbbNumberOfCredits.setRenderer(new GenderRenderer());
		contentPane.add(cbbNumberOfCredits);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFind.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
				txtID.setEnabled(true);
				load();
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
				btnCancel.setEnabled(true);
				Find();
			}
		});

		btnFind.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFind.setBounds(10, 462, BUTTON_WIDTH, BUTTON_HEIGHT);
		contentPane.add(btnFind);
		
		load();
	}
	public static void load() {
		txtID.setEnabled(true);
		ArrayList<Course> lisCourse = new ArrayList<Course>();
		try {
			lisCourse = Course.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabCourse.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[4];
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		for(int i=0; i <lisCourse.size();i++ )
		{    
			rows[0]=(lisCourse.get(i).getCid()); 
			rows[1]=(lisCourse.get(i).getName()); 
			rows[2]=(lisCourse.get(i).getDescription());
			rows[3]=(lisCourse.get(i).getNumOfCredits());

			model.addRow(rows); 
		}
	}
	public static void Add() {
		Course cs = new Course();
		cs.setCid(txtID.getText().toString());
		cs.setName(txtName.getText().toString());
		cs.setDescription(txtDescription.getText().toString());
		cs.setNumOfCredits(((NumberOfCredits)cbbNumberOfCredits.getSelectedItem()).getType());
		try {
			if(Course.Insert(cs, conn) == 1) {
				JOptionPane.showMessageDialog(tabCourse, "Thêm Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				lisCourse.add(cs);
				DefaultTableModel model = (DefaultTableModel)tabCourse.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog(tabCourse, "Thêm thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void Edit() {
		Course cs = new Course();
		cs.setCid(txtID.getText().toString());
		cs.setName(txtName.getText().toString());
		cs.setDescription(txtDescription.getText().toString());
		cs.setNumOfCredits(((NumberOfCredits)cbbNumberOfCredits.getSelectedItem()).getType());
		try {
			if(Course.Edit(cs, conn) == 1) {
				JOptionPane.showMessageDialog(tabCourse, "Sửa Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				for(int i=0; i< lisCourse.size();i++){
					if(lisCourse.get(i).getCid() == cs.getCid()) {
						lisCourse.get(i).setName(cs.getName());
						lisCourse.get(i).setDescription(cs.getDescription());
						lisCourse.get(i).setNumOfCredits(cs.getNumOfCredits());
						break;
					}

				}
				DefaultTableModel model = (DefaultTableModel)tabCourse.getModel();
				model.setRowCount(0);
				load();	
			}
			else
				JOptionPane.showMessageDialog(tabCourse, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);


		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	@SuppressWarnings("unlikely-arg-type")
	public static void Del() {
		String index=txtID.getText().toString();
		try {
			if(Course.Del(index, conn)==1)
			{
				lisCourse.remove(index);
				JOptionPane.showMessageDialog( tabCourse, "Xoa thanh cong!",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = (DefaultTableModel)tabCourse.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog( tabCourse, "Xay ra loi",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void Find() {
		ArrayList<Course> lisCourse = new ArrayList<Course>();
		try {
			lisCourse = Course.load(conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabCourse.getModel();
		Object[] rows = new Object[4]; 
		String index = txtID.getText();
		System.out.print(1);
		try {
			Course cs = Course.findCourse(index, conn);
			if(cs != null) {
				model.setRowCount(0);
				rows[0]=cs.getCid(); 
				rows[1]=cs.getName(); 
				rows[2]=cs.getDescription();
				rows[3]=cs.getNumOfCredits();

				model.addRow(rows); 
			}
			else {
				JOptionPane.showConfirmDialog(tabCourse, "Không Tìm Thấy!!","Thông Báo",JOptionPane.OK_OPTION);

			}
		} catch (NumberFormatException | HeadlessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void clear() {
		txtID.setText("");
		txtName.setText("");
		txtDescription.setText("");
		cbbNumberOfCredits.setSelectedIndex(-1);
	}
	class NumberOfCredits{
		private int type;
		private String name;
		public NumberOfCredits(int type, String name) {
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
	class GenderRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof NumberOfCredits){
				NumberOfCredits val = (NumberOfCredits) value;
				setText(val.getName());
			}
			return this;
		}
	}
}
