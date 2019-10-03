import java.awt.EventQueue;

public class StudyBuddy {
	/**
	 * Launch the application.
	 */
	public static GUI frame;
	//Windows operating system for cross platform compatibility
	public final static boolean WINDOWS = System.getProperty("os.name").toLowerCase().indexOf("windows") > -1; 
	public static void main(String[] args) {
		//Start GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

}

