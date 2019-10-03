
public class ProcessHandler {
	private final static String KILLWINDOWS = "taskkill /F /IM ";
	private final static String KILLUNIX = "kill -9 ";

	public static void kill(String process){
		Runtime rt = Runtime.getRuntime();
		try{
			if (StudyBuddy.WINDOWS)
				rt.exec(KILLWINDOWS + process);
			else
				rt.exec(KILLUNIX + process);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void killerTest(String process){
		try {
			ProcessBuilder pb = new ProcessBuilder(process);
			Process p = pb.start();
			p.destroy();
			if(p.waitFor() != 0)
				p.destroyForcibly();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
