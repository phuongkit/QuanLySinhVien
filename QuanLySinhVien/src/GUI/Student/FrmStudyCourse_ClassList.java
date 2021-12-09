package GUI.Student;

import java.awt.Component;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class FrmStudyCourse_ClassList extends JInternalFrame {

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
	private static JTable tabInfoCourse_Class = new JTable(model) ;
	private static JButton btnReLoad = new JButton("ReLoad");
	private static JButton btnSearch = new JButton("Tìm");

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

	public FrmStudyCourse_ClassList(String userName, Connection conn) {
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
		scrollPane.setBounds(0, 103, 948, 348);
		contentPane.add(scrollPane);

		btnReLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearch.setText("");
				load();
			}
		});
		btnReLoad.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnReLoad.setBounds(788, 461, BUTTON_WIDTH + 30, BUTTON_HEIGHT);
		btnReLoad.setIcon(new ImageIcon("resources/reload.png"));
		contentPane.add(btnReLoad);


		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		btnSearch.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnSearch.setBounds(594, 25, 150, 40);
		contentPane.add(btnSearch);

		JLabel lblSearch = new JLabel("Tìm theo");
		lblSearch.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSearch.setBounds(70, 35, 82, 20);
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
		cbbSearchType.setBounds(146, 30, 196, 30);
		cbbSearchType.setRenderer(new SearchTypeRenderer());
		contentPane.add(cbbSearchType);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtSearch.setBounds(352, 30, 232, 30);
		contentPane.add(txtSearch);

		load();
	}
	public static void load() {
		ArrayList<InfoCourse_Class> lisInfoCourse_Class = new ArrayList<InfoCourse_Class>();
		try {
			lisInfoCourse_Class = InfoCourse_Class.loadInfoOfStudent(sid, false, conn);
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
			rows[1]=lisInfoCourse_Class.get(i).getName(); 
			rows[2]=lisInfoCourse_Class.get(i).getCcid(); 
			rows[3]=lisInfoCourse_Class.get(i).getRid();
			rows[4]=lisInfoCourse_Class.get(i).getTid();
			rows[5]=lisInfoCourse_Class.get(i).getSemester();
			rows[6]=lisInfoCourse_Class.get(i).getDescription();
			rows[7]=lisInfoCourse_Class.get(i).getNumOfCredits();
			rows[8]=lisInfoCourse_Class.get(i).getDescriptionCourse(); 

			model.addRow(rows); 
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
