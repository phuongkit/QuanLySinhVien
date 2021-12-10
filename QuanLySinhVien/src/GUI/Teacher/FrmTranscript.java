package GUI.Teacher;

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
import Model.Course;
import Model.Course_Class;
import Model.InfoCourse_Class;
import Model.Room;
import Model.Student;
import Model.Teacher;
import Model.Transcript;
import javax.swing.SwingConstants;

public class FrmTranscript extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn = null;
	private static String tid;
	private static String ccid;

	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;

	private static JTextField txtStudent = new JTextField();
	private static JTextField txtScore = new JTextField();
	private static JComboBox cbbCourse_Class = new JComboBox();
	private static DefaultComboBoxModel cbbCourse_ClassModel;
	private static JTextField txtSearch;
	private static ArrayList<Transcript> lisInfoCourse_Class = new ArrayList<Transcript>();
	private static String[] columnName = {"Mã Lớp Học Phần", "Sinh Viên", "Điểm"};
	private static DefaultTableModel model = new DefaultTableModel(columnName,0);
	private static JTable tabTranscript = new JTable(model) ;
	private static JButton btnReLoad = new JButton("ReLoad");
	private static JButton btnEdit = new JButton("Sửa");
	private static JButton btnSave = new JButton("Lưu");
	private static JButton btnCancel = new JButton("Hủy");
	private static JButton btnFind = new JButton("Tìm theo lớp học phần");
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
			this.tid = Teacher.getTIDofUserName(userName, conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.ccid = "";
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

		tabTranscript.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabTranscript.getSelectedRow();
				if(row != -1) {
					String ccid = tabTranscript.getValueAt(row, 0).toString();
					int index = -1;
					for(int i=0;i<cbbCourse_ClassModel.getSize();i++) {
						Course_Class css =(Course_Class)cbbCourse_ClassModel.getElementAt(i);
						if(css.getCcid().equals(ccid)) {
							index=i;
							break;
						}
					}
					cbbCourse_Class.setSelectedIndex(index);
					String sid = tabTranscript.getValueAt(row, 1).toString();
					Student sd;
					try {
						sd = Student.findStudent(sid, conn);
						txtStudent.setText(sd.getName());
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					txtScore.setText(tabTranscript.getValueAt(row, 2).toString());
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabTranscript);
		scrollPane.setBounds(10, 133, 938, 307);
		contentPane.add(scrollPane);

		JLabel lblCourse_Class = new JLabel("Lớp Học Phần");
		lblCourse_Class.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblCourse_Class.setBounds(10, 15, 107, 20);
		contentPane.add(lblCourse_Class);

		ArrayList<Course_Class> course_Classs = new ArrayList<Course_Class>();
		try {
			course_Classs = Course_Class.load(conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cbbCourse_ClassModel = new DefaultComboBoxModel();
		for(Course_Class css : course_Classs) {
			cbbCourse_ClassModel.addElement(css);
		}

		cbbCourse_Class.setModel(cbbCourse_ClassModel);
		cbbCourse_Class.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		cbbCourse_Class.setBounds(121, 10, 292, 30);
		cbbCourse_Class.setRenderer(new Course_ClassRenderer());
		cbbCourse_Class.setSelectedIndex(0);
		contentPane.add(cbbCourse_Class);

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFind.setEnabled(false);
				btnEdit.setEnabled(false);
				btnSave.setEnabled(true);
				btnCancel.setEnabled(true);
				cbbCourse_Class.setEnabled(false);
			}
		});
		btnEdit.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnEdit.setBounds(257, 450, 107, 40);
		btnEdit.setIcon(new ImageIcon("resources/edit.png"));
		contentPane.add(btnEdit);

		btnSave.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnSave.setBounds(385, 450, 107, 40);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ccid = "";
				btnFind.setEnabled(true);
				btnEdit.setEnabled(true);
				cbbCourse_Class.setEnabled(true);
				edit();
			}
		});
		btnSave.setIcon(new ImageIcon("resources/save.jpg"));
		contentPane.add(btnSave);

		btnCancel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCancel.setBounds(516, 450, 107, 40);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ccid = "";
				btnFind.setEnabled(true);
				btnEdit.setEnabled(true);
				cbbCourse_Class.setEnabled(true);
				load();
			}
		});
		btnCancel.setIcon(new ImageIcon("resources/cancel.png"));
		contentPane.add(btnCancel);

		txtScore.setBounds(598, 45, 196, 33);
		txtScore.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		contentPane.add(txtScore);
		txtScore.setColumns(10);

		JLabel lblStudent = new JLabel("Sinh Viên");
		lblStudent.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblStudent.setBounds(10, 81, 107, 20);
		contentPane.add(lblStudent);

		JLabel lblScore = new JLabel("Điểm");
		lblScore.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblScore.setBounds(485, 48, 107, 20);
		contentPane.add(lblScore);

		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Course_Class css = (Course_Class)cbbCourse_Class.getSelectedItem();
				if(css == null) {
					JOptionPane.showMessageDialog(tabTranscript, "Vui chọn chọn lớp học phần cần tìm!",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					ccid = css.getCcid();
					load();
					btnCancel.setEnabled(true);
				}
			}
		});
		btnFind.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFind.setBounds(20, 450, 209, 40);
		contentPane.add(btnFind);

		txtStudent.setColumns(10);
		txtStudent.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		txtStudent.setBounds(121, 68, 292, 33);
		txtStudent.setEnabled(false);
		contentPane.add(txtStudent);

		load();
	}
	public static void load() {
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		ArrayList<Transcript> lisInfoCourse_Class = new ArrayList<Transcript>();
		try {
			lisInfoCourse_Class = Transcript.findTranscriptOfTID(tid, ccid, conn);
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
			rows[0]=lisInfoCourse_Class.get(i).getCcid();
			rows[1]=lisInfoCourse_Class.get(i).getSid();
			float score = Float.valueOf(lisInfoCourse_Class.get(i).getScore()); 
			if(score == -1) {
				rows[2] = "Chưa có điểm";
			}else {
				rows[2]= score;
			}

			model.addRow(rows); 
		}
	}
	public static void edit() {
		String ccid = ((Course_Class)cbbCourse_Class.getSelectedItem()).getCcid();
		int row = tabTranscript.getSelectedRow();
		if(row!= -1) {
			String sid = tabTranscript.getValueAt(row, 1).toString();
			String score_org = txtScore.getText().trim();
			float score;
			try {
				if(score_org.equals("") || score_org.equalsIgnoreCase("chưa có điểm")) {
					score = -1;
				}
				else {
					try {
						score = Float.valueOf(score_org);
						Transcript ts = new Transcript();
						ts.setCcid(ccid);
						ts.setSid(sid);
						ts.setScore(score);
						try {
							if(Transcript.Edit(ts, conn) == 1) {
								JOptionPane.showMessageDialog(tabTranscript, "Sửa Thành Công",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
								DefaultTableModel model = (DefaultTableModel)tabTranscript.getModel();
								model.setRowCount(0);
								load();	
							}
							else {
								JOptionPane.showMessageDialog(tabTranscript, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (HeadlessException | ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(tabTranscript, "Vui lòng nhập điểm là một số hoặc để trống (nếu chưa có điểm)",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
						ex.printStackTrace();
						return;
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				score = -1;
			}
		}
		else {
			JOptionPane.showMessageDialog(tabTranscript, "Sửa thất bại",  "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	class Course_ClassRenderer extends BasicComboBoxRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof Course_Class){
				Course_Class val = (Course_Class) value;
				String name = "";
				try {
					name = Course_Class.getNameCourseClass(val, conn);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setText(name);
			}
			return this;
		}
	}
}
