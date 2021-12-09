package GUI.Student;

import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import GUI.Student.FrmCourseRegister.SearchType;
import GUI.Student.FrmCourseRegister.SearchTypeRenderer;
import Model.InfoCourse_Class;
import Model.Student;
import Model.Transcript;

public class FrmTranscript extends JInternalFrame {

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

	private static JComboBox cbbSemester;
	private static DefaultComboBoxModel cbbSemesterModel;
	private static JTextField txtSearch;
	private static ArrayList<Transcript> lisInfoCourse_Class = new ArrayList<Transcript>();
	private static String[] columnName = {"Mã Môn Học", "Mã Lớp Học Phần", "Tên Học Phần", "Điểm" , "Học Kỳ"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabTranscript = new JTable(model) ;
	private static JButton btnReLoad = new JButton("ReLoad");
	private static JButton btnFilter = new JButton("Lọc");
	private static int semester;

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

	public FrmTranscript(String userName, Connection conn) {
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		try {
			this.sid = Student.getSIDofUserName(userName, conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.semester = -1;
		this.conn = conn;
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		tabTranscript.setBounds(10, 168, 870, 305);

		JScrollPane scrollPane = new JScrollPane(tabTranscript);
		scrollPane.setBounds(0, 77, 948, 363);
		contentPane.add(scrollPane);

		JLabel lblSemster = new JLabel("Học Kỳ:");
		lblSemster.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSemster.setBounds(70, 15, 82, 20);
		contentPane.add(lblSemster);

		ArrayList<Semester> semesters = new ArrayList<Semester>();
		semesters.add(new Semester(-1, "Tất cả"));
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
		cbbSemester.setBounds(162, 10, 196, 30);
		cbbSemester.setRenderer(new SemesterRenderer());
		cbbSemester.setSelectedIndex(0);
		contentPane.add(cbbSemester);
		
		btnReLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				semester = -1;
				cbbSemester.setSelectedIndex(0);
				load();
			}
		});
		btnReLoad.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnReLoad.setBounds(788, 450, BUTTON_WIDTH + 30, BUTTON_HEIGHT);
		btnReLoad.setIcon(new ImageIcon("resources/reload.png"));
		contentPane.add(btnReLoad);
		
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				semester = ((Semester)cbbSemester.getSelectedItem()).getType();
				load();
			}
		});
		btnFilter.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnFilter.setBounds(403, 5, 150, 40);
		contentPane.add(btnFilter);

		load();
	}
	public static void load() {
		
		ArrayList<InfoCourse_Class> lisInfoCourse_Class = new ArrayList<InfoCourse_Class>();
		try {
			lisInfoCourse_Class = InfoCourse_Class.loadInfoTranscriptOfStudent(sid, semester, conn);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel)tabTranscript.getModel();
		if(model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[10];
		for(int i=0; i <lisInfoCourse_Class.size();i++ )
		{    
			rows[0]=lisInfoCourse_Class.get(i).getCid();
			rows[1]=lisInfoCourse_Class.get(i).getCcid(); 
			rows[2]=lisInfoCourse_Class.get(i).getName(); 
			float score = Float.valueOf(lisInfoCourse_Class.get(i).getScore()); 
			if(score == -1) {
				rows[3] = "Chưa có điểm";
			}else {
				rows[3]= score;
			}
			rows[4]=lisInfoCourse_Class.get(i).getSemester();

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
}
