package GUI.Teacher;

import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
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
import Model.InfoCourse_Class;
import Model.Student;
import Model.Transcript;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class FrmCourseRegister extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn = null;
	private static String sid;

	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;

	private static JComboBox cbbSearchType;
	private static DefaultComboBoxModel cbbSearchTypeModel;
	private static JTextField txtSearch;
	private static ArrayList<InfoCourse_Class> lisInfoCourse_Class = new ArrayList<InfoCourse_Class>();
	private static String[] columnName = {"Mã Môn Học", "Mã Lớp Học Phần", "Tên Học Phần", "Phòng", "Giảng Viên", "Học Kỳ", "Mô Tả Lớp", "Số Tín Chỉ", "Mô Tả Môn Học"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static DefaultTableModel modelStudent = new DefaultTableModel(columnName,0);
	private static JTable tabInfoCourse_Class = new JTable(model) ;
	private static JTable tabInfoCourse_ClassStudent = new JTable(modelStudent) ;
	private static JButton btnReLoad = new JButton("ReLoad");
	private static JButton btnSearch = new JButton("Tìm");
	private static JButton btnRegister = new JButton("Đăng Ký");
	private static JButton btnRegisterCancel = new JButton("Hủy Đăng Ký");

	public void Init() {
		InitGUI init = new InitGUI();
		this.FONT_TYPE = init.getFONT_TYPE();
		this.FONT = init.getFONT();
		this.FONT_SIZE = init.getFONT_SIZE();
		this.BUTTON_HEIGHT = init.getBUTTON_HEIGHT();
		this.BUTTON_WIDTH = init.getBUTTON_WIDTH();
		this.SCREEN_WIDTH = init.getSCREEN_WIDTH();
		this.SCREEN_HEIGHT=init.getSCREEN_HEIGHT();
	}

	public FrmCourseRegister(String userName, Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		try {
			this.sid = Student.getSIDofUserName(userName, conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.conn = conn;
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		tabInfoCourse_Class.setBounds(10, 168, 870, 305);

		JScrollPane scrollPane = new JScrollPane(tabInfoCourse_Class);
		scrollPane.setBounds(0, 66, 948, 171);
		contentPane.add(scrollPane);

		btnReLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearch.setText("");
				load();
			}
		});
		btnReLoad.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnReLoad.setBounds(734, 247, BUTTON_WIDTH + 30, BUTTON_HEIGHT);
		btnReLoad.setIcon(new ImageIcon("resources/reload.png"));
		contentPane.add(btnReLoad);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		btnSearch.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnSearch.setBounds(594, 5, 150, 40);
		contentPane.add(btnSearch);

		JLabel lblSearch = new JLabel("Tìm theo");
		lblSearch.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSearch.setBounds(70, 15, 82, 20);
		contentPane.add(lblSearch);

		ArrayList<SearchType> searchTypes = new ArrayList<SearchType>();
		searchTypes.add(new SearchType(true, "Tìm Theo Mã Môn Học"));
		searchTypes.add(new SearchType(false, "Tìm Theo Tên Môn Học"));
		cbbSearchTypeModel = new DefaultComboBoxModel();
		for(SearchType sts : searchTypes) {
			cbbSearchTypeModel.addElement(sts);
		}

		cbbSearchType = new JComboBox();
		cbbSearchType.setModel(cbbSearchTypeModel);
		cbbSearchType.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbSearchType.setBounds(146, 10, 196, 30);
		cbbSearchType.setRenderer(new SearchTypeRenderer());
		contentPane.add(cbbSearchType);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtSearch.setBounds(352, 10, 232, 30);
		contentPane.add(txtSearch);

		JLabel lblDanhSachHocPhan = new JLabel("Danh sách học phần đã đăng ký");
		lblDanhSachHocPhan.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDanhSachHocPhan.setBounds(10, 283, 242, 20);
		contentPane.add(lblDanhSachHocPhan);
		
		JScrollPane scrollPaneStudent = new JScrollPane(tabInfoCourse_ClassStudent );
		scrollPaneStudent.setBounds(0, 313, 948, 202);
		contentPane.add(scrollPaneStudent);

		btnRegisterCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tabInfoCourse_ClassStudent.getSelectedRow();
				if(row != -1) {
					String ccid = tabInfoCourse_ClassStudent.getValueAt(row, 1).toString();
					try {
						if(Transcript.Del(ccid, sid, conn)==0) {
							JOptionPane.showMessageDialog(contentPane, "Hủy đăng ký thất bại!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(contentPane, "Hủy đăng ký thành công!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (HeadlessException | ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn trong danh sách môn học đã đăng ký!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				}
				load();
			}
		});
		btnRegisterCancel.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnRegisterCancel.setBounds(502, 247, 150, 40);
		contentPane.add(btnRegisterCancel);

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tabInfoCourse_Class.getSelectedRow();
				if(row != -1) {
					String cid = tabInfoCourse_Class.getValueAt(row, 0).toString();
					try {
						if(Transcript.existsCourseStudent(cid, sid, conn)) {
							JOptionPane.showMessageDialog(contentPane, "Môn học này bạn đã đăng ký! Vui lòng xóa đăng ký trước khi thử lại!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							String ccid = tabInfoCourse_Class.getValueAt(row, 1).toString();
							int semester = Integer.valueOf(tabInfoCourse_Class.getValueAt(row, 5).toString());
							Transcript ts = new Transcript();
							ts.setCcid(ccid);
							ts.setSemester(semester);
							ts.setSid(sid);
							if(Transcript.Insert(ts, conn)==0) {
								JOptionPane.showMessageDialog(contentPane, "Đăng ký thất bại!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
							}
							else {
								JOptionPane.showMessageDialog(contentPane, "Đăng ký thành công!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} catch (HeadlessException | ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn môn học cần thêm trước!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				}
				load();
			}
		});
		btnRegister.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnRegister.setBounds(289, 247, 150, 40);
		contentPane.add(btnRegister);

		load();
	}
	public static void load() {
		String search = txtSearch.getText();
		boolean searchCID = ((SearchType)cbbSearchType.getSelectedItem()).isType();
		ArrayList<InfoCourse_Class> lisInfoCourse_Class = new ArrayList<InfoCourse_Class>();
		try {
			lisInfoCourse_Class = InfoCourse_Class.loadInfoOpen(search, searchCID, conn);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabInfoCourse_Class.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[9];
		for(int i=0; i <lisInfoCourse_Class.size();i++ )
		{    
			rows[0]=lisInfoCourse_Class.get(i).getCid(); 
			rows[1]=lisInfoCourse_Class.get(i).getCcid(); 
			rows[2]=lisInfoCourse_Class.get(i).getName(); 
			rows[3]=lisInfoCourse_Class.get(i).getRid();
			rows[4]=lisInfoCourse_Class.get(i).getTid();
			rows[5]=lisInfoCourse_Class.get(i).getSemester();
			rows[6]=lisInfoCourse_Class.get(i).getDescription();
			rows[7]=lisInfoCourse_Class.get(i).getNumOfCredits();
			rows[8]=lisInfoCourse_Class.get(i).getDescriptionCourse(); 

			model.addRow(rows); 
		}
		ArrayList<InfoCourse_Class> lisInfoCourse_ClassStudent = new ArrayList<InfoCourse_Class>();
		try {
			lisInfoCourse_ClassStudent = InfoCourse_Class.loadInfoOfStudent(sid, conn);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultTableModel modelStudent = (DefaultTableModel)tabInfoCourse_ClassStudent.getModel();
		if(modelStudent.getRowCount() > 0) {
			modelStudent.setRowCount(0);
		}
		for(int i=0; i < lisInfoCourse_ClassStudent.size();i++ )
		{    
			rows[0]=lisInfoCourse_ClassStudent.get(i).getCid(); 
			rows[1]=lisInfoCourse_ClassStudent.get(i).getCcid();
			rows[2]=lisInfoCourse_ClassStudent.get(i).getName();  
			rows[3]=lisInfoCourse_ClassStudent.get(i).getRid();
			rows[4]=lisInfoCourse_ClassStudent.get(i).getTid();
			rows[5]=lisInfoCourse_ClassStudent.get(i).getSemester();
			rows[6]=lisInfoCourse_ClassStudent.get(i).getDescription();
			rows[7]=lisInfoCourse_ClassStudent.get(i).getNumOfCredits();
			rows[8]=lisInfoCourse_ClassStudent.get(i).getDescriptionCourse(); 

			modelStudent.addRow(rows); 
		}
	}
	class SearchType{
		private boolean type;
		private String name;
		public SearchType(boolean type, String name) {
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
	class SearchTypeRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof SearchType){
				SearchType val = (SearchType) value;
				setText(val.getName());
			}
			return this;
		}
	}
}
