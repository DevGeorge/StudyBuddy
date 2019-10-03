import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import javax.swing.*;
import java.util.*;
import javax.swing.text.MaskFormatter;

import javax.swing.border.EtchedBorder;
import java.io.*;
@SuppressWarnings("serial")
public class GUI extends JFrame {
	/**
	 * 
	 */
	private JTextField programTxt;
	private JLabel lblTime;
	private JButton btnStart;
	private JButton btnAdd;
	private JPanel panel;
	private MaskFormatter mask = null;
	private JFormattedTextField formattedTextField;
	private JButton btnRemove;
	private DefaultListModel<String> programModelList = new DefaultListModel<String>();
	private JList<String> programList;
	private boolean studying = false;
	private File processFile = new File("PROCESS_LIST.txt");
	private final ArrayList<String> essentialWinProcesses = new ArrayList<String>(Arrays.asList(
			"taskmgr.exe",
			"explorer.exe",
			"svchost.exe",
			"spoolsv.exe",
			"lsass.exe",
			"services.exe",
			"winlogon.exe",
			"csrss.exe",
			"smss.exe"
			));

	private JScrollPane scrollPane;
	private JMenuItem mntmSave;
	private JMenuItem mntmLoad;
	private JMenu mnHelp;
	private JMenuItem mntmHowTo;
	private JLabel lblTextField;

	/**
	 * Create the frame.
	 */
	public GUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(!studying){
					System.exit(0);
				}else{
					JOptionPane.showMessageDialog(panel, "Can't close the program while you should be studying","Nope",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBackground(Color.WHITE);
		setResizable(false);
		setTitle("StudyBuddy");
		setSize(new Dimension(400, 500));
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setBackground(new Color(240, 255, 240));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLUE, Color.BLACK));
		panel.setBounds(0, 0, 394, 471);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 200, 175, 237);
		panel.add(scrollPane);
		programList = new JList<String>(programModelList);
		programList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
					removeProcess(programList.getSelectedIndex());
				if(e.getKeyCode() == KeyEvent.VK_DELETE)
					removeProcess(programList.getSelectedIndex());
			}
		});
		scrollPane.setViewportView(programList);
		programList.setToolTipText("The List of processes that your buddy will keep you away from. ");
		programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		programList.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(0, 255, 255), Color.CYAN));
		programList.setLayoutOrientation(JList.VERTICAL);
		lblTime = new JLabel("Time");
		lblTime.setBounds(154, 17, 31, 14);
		panel.add(lblTime);

		btnStart = new JButton("Start");
		btnStart.setBounds(114, 79, 113, 23);
		panel.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!studying && splitConv(formattedTextField.getText()) > 0){
					formattedTextField.setEditable(false);
					Timer timer = new Timer(splitConv(formattedTextField.getText()));
					timer.start();
					studying = true;


				}
			}
		});

		programTxt = new JTextField();
		programTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					addProcess(programTxt.getText());
			}
		});
		programTxt.setForeground(new Color(255, 255, 255));
		programTxt.setBackground(new Color(0, 0, 0));
		programTxt.setBounds(10, 164, 175, 20);
		panel.add(programTxt);
		programTxt.setColumns(10);

		btnAdd = new JButton("Add");

		btnAdd.setBounds(195, 163, 83, 23);
		panel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addProcess(programTxt.getText());
			}
		});

		btnRemove = new JButton("Remove");
		btnRemove.setBounds(288, 163, 96, 23);
		panel.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!studying){
					removeProcess(programList.getSelectedIndex());
				}
			}
		});

		try {
			mask = new MaskFormatter("##:##:##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mask.setPlaceholderCharacter('0');
		formattedTextField = new JFormattedTextField(mask);
		formattedTextField.setBackground(new Color(173, 216, 230));
		formattedTextField.setBounds(74, 37, 180, 31);
		formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 27));
		panel.add(formattedTextField);

		lblTextField = new JLabel("Type processes here");
		lblTextField.setBounds(28, 146, 125, 14);
		panel.add(lblTextField);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFileOptions = new JMenu("File");
		menuBar.add(mnFileOptions);

		JMenuItem mntmSetFileLocation = new JMenuItem("Set file location");
		mntmSetFileLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePath();
			}
		});
		mnFileOptions.add(mntmSetFileLocation);

		mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}

		});
		mnFileOptions.add(mntmSave);

		mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLoad();

			}


		});
		mnFileOptions.add(mntmLoad);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmHowTo = new JMenuItem("How To");
		mntmHowTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Really?", "brah",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Add process name in Text field that you want blocked, enter length of time, click start ");
				} else {

				}
			}
		});
		mnHelp.add(mntmHowTo);


		doLoad();
	}

	//Load to Program list
	public void doLoad() {
		try{
			Scanner input = new Scanner(processFile);
			for(int i = 0; i < FileUtil.countLines(processFile); i++){
				addProcess(input.nextLine());
			}
			input.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	//Save to File
	public void doSave(){
		try{
			//Write to file
			PrintWriter output = new PrintWriter(processFile);
			for(String s: getList())
				output.write(s);
			output.close();

		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	//Change file 
	public void changePath(){
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		processFile = chooser.getSelectedFile();

	}




	//Adds process to program list
	private void addProcess(String process){
		String subExe;
		process = process.toLowerCase();
		if(process.length() <= 3 && process.length() != 0){
			if(StudyBuddy.WINDOWS)
				process += ".exe";
			if (processCheck(process)){
				programTxt.setText("");
				return;
			}
			programModelList.addElement(process);
			programTxt.setText("");
		}else if(process.length() == 0){
			JOptionPane.showMessageDialog(panel,
					"Please enter process name first","Error",JOptionPane.ERROR_MESSAGE);
		}else{
			subExe = process.substring(process.length() - 4);
			System.out.println(subExe);
			if (subExe.equals(".exe")){
				if (processCheck(process)){
					programTxt.setText("");
					return;
				}
				programModelList.addElement(process);
			}else{
				if(StudyBuddy.WINDOWS)
					process += ".exe";
				if (processCheck(process)){
					programTxt.setText("");
					return;
				}
				programModelList.addElement(process);
			}
			programTxt.setText("");
		}

	}
	public void killer(){
		//	while(StudyBuddy.studying){
		for (String x: this.getList()){
			ProcessHandler.kill(x);
		}
	}

	private long splitConv(String time){
		// splits at each ':'
		StringTokenizer st = new StringTokenizer(time, ":");
		String hours,secs,mins;
		//time = ##:##:##
		//hours= ##
		//mins =    ##
		//secs =       ##
		hours = st.nextToken();
		mins = st.nextToken();
		secs = st.nextToken();
		long hoursL = Long.parseLong(hours) *60*60*1000;
		long minsL = Long.parseLong(mins) * 60*1000;
		long secsL = Long.parseLong(secs) * 1000;
		//change from there time unit to milliseconds 
		return hoursL+minsL+secsL;

	}
	//Remove from Jlist
	private void removeProcess(int index){
		if (index != -1) {
			programModelList.removeElementAt(index);
		}else{
			JOptionPane.showMessageDialog(panel,
					"Please select a process first","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	//Getter and converter to string[]
	public String[] getList(){
		Object[] temp = programModelList.toArray();
		return Arrays.asList(temp).toArray(new String[temp.length]);
	}

	public void update(String t){
		formattedTextField.setText(t);
	}
	public void setComplete(){
		formattedTextField.setEditable(true);
		JOptionPane.showMessageDialog(panel,
				"Studying Over. Procrastinate at will","Time Up",JOptionPane.DEFAULT_OPTION);
		studying = false;
	}
	//Checks if windows process is trying to be added to killList
	private boolean processCheck(String process){
		if(essentialWinProcesses.contains(process)){
			JOptionPane.showMessageDialog(panel,
					"Can't add an essential windows process to list","Error",JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
}

