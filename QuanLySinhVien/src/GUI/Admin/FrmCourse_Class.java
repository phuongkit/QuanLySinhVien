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
import java.util.ArrayList;

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

import GUI.InitGUI;
import GUI.Admin.FrmCourse.NumberOfCredits;
import Model.Course;
import Model.Course_Class;
import Model.Room;
import Model.Teacher;

public class FrmCourse_Class extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static Connection conn = null;

	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int COMPONENTS_HEIGHT;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;
	private static JComboBox cbbCID;
	private static DefaultComboBoxModel cbbCIDModel;
	private static JTextField txtCCID;
	private static JComboBox cbbRID;
	private static DefaultComboBoxModel cbbRIDModel;
	private static JComboBox cbbTID;
	private static DefaultComboBoxModel cbbTIDModel;
	private static JComboBox cbbStatus;
	private static DefaultComboBoxModel cbbStatusModel;
	private static JComboBox cbbSemester;
	private static DefaultComboBoxModel cbbSemesterModel;
	private static JTextField txtDescription;
	private static ArrayList<Course_Class> lisCourse_Class = new ArrayList<Course_Class>();
	private static String[] columnName = {"CID", "CCID", "RID", "TID", "Trạng thái", "Học Kỳ", "Mô Tả"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabCourse_Class = new JTable(model) ;
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

	public FrmCourse_Class(Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		FrmCourse_Class.conn = conn;
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		tabCourse_Class.setBounds(10, 168, 870, 305);

		tabCourse_Class.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabCourse_Class.getSelectedRow();
				if(row != -1 && flag == 0) {
					int index;
					
					String cid = tabCourse_Class.getValueAt(row, 0).toString();
					index = -1;
					for(int i=0;i<cbbCIDModel.getSize();i++) {	
						Course cs = (Course) cbbCIDModel.getElementAt(i);
						if(cid.equals(cs.getCid())) {
							index=i;
							break;
						}
					}
					cbbCID.setSelectedIndex(index);
					
					txtCCID.setText(tabCourse_Class.getValueAt(row, 1).toString());
					
					String rid = tabCourse_Class.getValueAt(row, 2).toString();
					index = -1;
					for(int i=0;i<cbbRIDModel.getSize();i++) {	
						Room r = (Room) cbbRIDModel.getElementAt(i);
						if(rid.equals(r.getRid())) {
							index=i;
							break;
						}
					}
					cbbRID.setSelectedIndex(index);
					
					String tid = tabCourse_Class.getValueAt(row, 3).toString();
					index = -1;
					for(int i=0;i<cbbTIDModel.getSize();i++) {	
						Teacher tc = (Teacher) cbbTIDModel.getElementAt(i);
						if(tid.equals(tc.getId())) {
							index=i;
							break;
						}
					}
					cbbTID.setSelectedIndex(index);
					
					Boolean status = Boolean.valueOf(tabCourse_Class.getValueAt(row, 4).toString());
					if(status) {
						cbbStatus.setSelectedIndex(0);
					}
					else {
						cbbStatus.setSelectedIndex(1);
					}
					
					int semester = Integer.valueOf(tabCourse_Class.getValueAt(row, 5).toString());
					index = -1;
					for(int i=0;i<cbbSemesterModel.getSize();i++) {	
						Semester tc = (Semester) cbbSemesterModel.getElementAt(i);
						if(semester == tc.getType()) {
							index=i;
							break;
						}
					}
					cbbSemester.setSelectedIndex(index);
					
					txtDescription.setText(tabCourse_Class.getValueAt(row, 6).toString());
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabCourse_Class);
		scrollPane.setBounds(0, 173, 955, 279);
		contentPane.add(scrollPane);

		JLabel lblCID = new JLabel("CID");
		lblCID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblCID.setBounds(20, 20, 122, 38);
		contentPane.add(lblCID);

		JLabel lblCCID = new JLabel("CCID");
		lblCCID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblCCID.setBounds(20, 48, 122, 38);
		contentPane.add(lblCCID);

		JLabel lblRID = new JLabel("RID");
		lblRID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblRID.setBounds(20, 88, 122, 38);
		contentPane.add(lblRID);

		JLabel lblTID = new JLabel("TID");
		lblTID.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblTID.setBounds(20, 125, 122, 38);
		contentPane.add(lblTID);

		JLabel lblStatus = new JLabel("Trạng thái");
		lblStatus.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblStatus.setBounds(407, 20, 122, 38);
		contentPane.add(lblStatus);

		JLabel lblSemester = new JLabel("Học Kỳ");
		lblSemester.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSemester.setBounds(407, 68, 122, 38);
		contentPane.add(lblSemester);

		JLabel lblDescription = new JLabel("Mô Tả");
		lblDescription.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblDescription.setBounds(407, 116, 122, 38);
		contentPane.add(lblDescription);

		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			courses = Course.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cbbCIDModel = new DefaultComboBoxModel();
		for(Course cs : courses) {
			cbbCIDModel.addElement(cs);
		}

		cbbCID = new JComboBox();
		cbbCID.setModel(cbbCIDModel);
		cbbCID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbCID.setFont(new Font("Dialog", Font.PLAIN, 16));
		cbbCID.setBounds(152, 20, 216, 27);
		cbbCID.setRenderer(new CIDRenderer());
		contentPane.add(cbbCID);

		txtCCID = new JTextField();
		txtCCID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtCCID.setColumns(10);
		txtCCID.setBounds(152, 57, 167, 27);
		txtCCID.setEnabled(false);
		contentPane.add(txtCCID);

		ArrayList<Room> rooms = new ArrayList<Room>();
		try {
			rooms = Room.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cbbRIDModel = new DefaultComboBoxModel();
		for(Room r : rooms) {
			cbbRIDModel.addElement(r);
		}

		cbbRID = new JComboBox();
		cbbRID.setModel(cbbRIDModel);
		cbbRID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbRID.setBounds(152, 94, 167, 27);
		cbbRID.setRenderer(new RIDRenderer());
		contentPane.add(cbbRID);
		
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		try {
			teachers = Teacher.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cbbTIDModel = new DefaultComboBoxModel();
		for(Teacher tc : teachers) {
			cbbTIDModel.addElement(tc);
		}

		cbbTID = new JComboBox();
		cbbTID.setModel(cbbTIDModel);
		cbbTID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbTID.setBounds(152, 136, 216, 27);
		cbbTID.setRenderer(new TIDRenderer());
		contentPane.add(cbbTID);
		
		ArrayList<Status> statuss = new ArrayList<Status>();
		statuss.add(new Status(true, "Mở"));
		statuss.add(new Status(false, "Đóng"));
		cbbStatusModel = new DefaultComboBoxModel();
		for(Status sts : statuss) {
			cbbStatusModel.addElement(sts);
		}

		cbbStatus = new JComboBox();
		cbbStatus.setModel(cbbStatusModel);
		cbbStatus.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbStatus.setBounds(522, 26, 167, 27);
		cbbStatus.setRenderer(new StatusRenderer());
		contentPane.add(cbbStatus);

		ArrayList<Semester> semesters = new ArrayList<Semester>();
		semesters.add(new Semester(1, "Học Kỳ I"));
		semesters.add(new Semester(2, "Học Kỳ II"));
		semesters.add(new Semester(3, "Học Kỳ III"));
		semesters.add(new Semester(4, "Học Kỳ IV"));
		semesters.add(new Semester(5, "Học Kỳ V"));
		semesters.add(new Semester(6, "Học Kỳ VI"));
		semesters.add(new Semester(7, "Học Kỳ VII"));
		semesters.add(new Semester(8, "Học Kỳ VIII"));
		cbbSemesterModel = new DefaultComboBoxModel();
		for(Semester smt : semesters) {
			cbbSemesterModel.addElement(smt);
		}

		cbbSemester = new JComboBox();
		cbbSemester.setModel(cbbSemesterModel);
		cbbSemester.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbSemester.setBounds(522, 80, 167, 27);
		cbbSemester.setRenderer(new SemesterRenderer());
		contentPane.add(cbbSemester);

		txtDescription = new JTextField();
		txtDescription.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtDescription.setColumns(10);
		txtDescription.setBounds(522, 127, 395, 27);
		contentPane.add(txtDescription);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFind.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
				load();
			}
		});
		btnCancel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCancel.setBounds(814, 462, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnCancel.setIcon(new ImageIcon("resources/cancel.png"));
		contentPane.add(btnCancel);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flag == -1)
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
				flag = -1;
				cbbCID.setEnabled(true);
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
				cbbCID.setEnabled(true);
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
		flag = 0;
		cbbCID.setEnabled(false);
		ArrayList<Course_Class> lisCourse_Class = new ArrayList<Course_Class>();
		try {
			lisCourse_Class = Course_Class.load(conn);
		} catch(ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabCourse_Class.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[7];
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		for(int i=0; i <lisCourse_Class.size();i++ )
		{    
			rows[0]=lisCourse_Class.get(i).getCid(); 
			rows[1]=lisCourse_Class.get(i).getCcid(); 
			rows[2]=lisCourse_Class.get(i).getRid();
			rows[3]=lisCourse_Class.get(i).getTid();
			rows[4]=lisCourse_Class.get(i).isStatus();
			rows[5]=lisCourse_Class.get(i).getSemester();
			rows[6]=lisCourse_Class.get(i).getDescription();

			model.addRow(rows); 
		}
	}
	public static void Add() {
		Course_Class cs = new Course_Class();
		cs.setCid(((Course)cbbCID.getSelectedItem()).getCid());
		cs.setCcid(txtCCID.getText());
		cs.setRid(((Room)cbbRID.getSelectedItem()).getRid());
		cs.setTid(((Teacher)cbbTID.getSelectedItem()).getId());
		cs.setStatus(((Status)cbbStatus.getSelectedItem()).isType());
		cs.setSemester(((Semester)cbbSemester.getSelectedItem()).getType());
		cs.setDescription(txtDescription.getText());
		try {
			if(Course_Class.Insert(cs, conn) == 1) {
				JOptionPane.showMessageDialog(tabCourse_Class, "Thêm Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				lisCourse_Class.add(cs);
				DefaultTableModel model = (DefaultTableModel)tabCourse_Class.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog(tabCourse_Class, "Thêm thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void Edit() {
		Course_Class cs = new Course_Class();
		cs.setCid(((Course)cbbCID.getSelectedItem()).getCid());
		cs.setCcid(txtCCID.getText());
		cs.setRid(((Room)cbbRID.getSelectedItem()).getRid());
		cs.setTid(((Teacher)cbbTID.getSelectedItem()).getId());
		cs.setStatus(((Status)cbbStatus.getSelectedItem()).isType());
		cs.setSemester(((Semester)cbbSemester.getSelectedItem()).getType());
		cs.setDescription(txtDescription.getText());
		try {
			if(Course_Class.Edit(cs, conn) == 1) {
				JOptionPane.showMessageDialog(tabCourse_Class, "Sửa Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				for(int i=0; i< lisCourse_Class.size();i++){
					if(lisCourse_Class.get(i).getCcid() == cs.getCcid()) {
						lisCourse_Class.get(i).setRid(cs.getRid());
						lisCourse_Class.get(i).setTid(cs.getTid());
						lisCourse_Class.get(i).setStatus(cs.isStatus());
						lisCourse_Class.get(i).setSemester(cs.getSemester());
						lisCourse_Class.get(i).setDescription(cs.getDescription());
						break;
					}

				}
				DefaultTableModel model = (DefaultTableModel)tabCourse_Class.getModel();
				model.setRowCount(0);
				load();	
			}
			else
				JOptionPane.showMessageDialog(tabCourse_Class, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);


		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	@SuppressWarnings("unlikely-arg-type")
	public static void Del() {
		String index=txtCCID.getText().toString();
		try {
			if(Course_Class.Del(index, conn)==1)
			{
				lisCourse_Class.remove(index);
				JOptionPane.showMessageDialog( tabCourse_Class, "Xoa thanh cong!",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = (DefaultTableModel)tabCourse_Class.getModel();
				model.setRowCount(0);
				load();
			}
			else
				JOptionPane.showMessageDialog( tabCourse_Class, "Xay ra loi",  "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void Find() {
		ArrayList<Course_Class> lisCourse_Class = new ArrayList<Course_Class>();
		try {
			lisCourse_Class = Course_Class.load(conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabCourse_Class.getModel();
		Object[] rows = new Object[7]; 
		String index = txtCCID.getText();
		try {
			Course_Class cs = Course_Class.findCourse_Class(index, conn);
			if(cs != null) {
				model.setRowCount(0);
				rows[0]=cs.getCid();
				rows[1]=cs.getCcid();
				rows[2]=cs.getRid();
				rows[3]=cs.getTid();
				rows[4]=cs.isStatus();
				rows[5]=cs.getSemester();
				rows[6]=cs.getDescription();

				model.addRow(rows); 
			}
			else {
				JOptionPane.showConfirmDialog(tabCourse_Class, "Không Tìm Thấy!!","Thông Báo",JOptionPane.OK_OPTION);

			}
		} catch (NumberFormatException | HeadlessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void clear() {
		cbbCID.setSelectedIndex(-1);
		txtCCID.setText("");
		cbbRID.setSelectedIndex(-1);
		cbbTID.setSelectedIndex(-1);
		cbbStatus.setSelectedIndex(-1);
		cbbSemester.setSelectedIndex(-1);
		txtDescription.setText("");
	}
	class Status{
		private boolean type;
		private String name;
		public Status(boolean type, String name) {
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
	class StatusRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof Status){
				Status val = (Status) value;
				setText(val.getName());
			}
			return this;
		}
	}
	class Semester{
		private int type;
		private String name;
		public Semester(int type, String name) {
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
	class SemesterRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof Semester){
				Semester val = (Semester) value;
				setText(val.getName());
			}
			return this;
		}
	}
	class CIDRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof Course){
				Course val = (Course) value;
				setText(val.getName());
				try {
					txtCCID.setText(Course_Class.getCCIDCourse_Class(val.getCid(), conn));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return this;
		}
	}
	class RIDRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof Room){
				Room val = (Room) value;
				setText(val.getArea() + " - " + val.getName());
			}
			return this;
		}
	}
	class TIDRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof Teacher){
				Teacher val = (Teacher) value;
				setText(val.getName());
			}
			return this;
		}
	}
}
