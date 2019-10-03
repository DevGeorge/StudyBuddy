public class Timer {

	private final long time;
	private long startTime;
	private String last;
	private Thread thread;
	private Thread timerThread;
	private boolean studying;
	
	public Timer(long time){
		this.time = time;
	}


	public void stop(){
		studying = false;
	}
	@SuppressWarnings("deprecation")
	// initializes starting variables and creates thread
	public void start(){
		// Checks to make sure thread is not currently running
		if(timerThread != null)
			//Stop thread if running
			timerThread.stop();
		//creates thread
		this.createThread();
		// thread start time
		this.startTime = System.currentTimeMillis();
		//starts thread
		this.thread.start();
	}
	public boolean isComplete(){
		//Checks if time is up
		return startTime + time < System.currentTimeMillis();
	}
	//Creating thread
	private void createThread(){
		
		thread = new Thread(() -> {
			//sets the global if running boolean
			studying = true;
			while(studying){
				//counting down function is called
				this.update();
				// checks if time is up
				if(isComplete()){
					// 
					studying = false;
					this.onComplete();
					// thread no longer needs to sleep therefore breaks the loop
					return;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		});

	}
	private String formatTime(){
		long remaining = startTime + time - System.currentTimeMillis();
		int seconds = (int) (remaining / 1000) % 60;
		int minutes = (int) ((remaining / (1000*60)) % 60);
		int hours   = (int) (remaining / (1000*60*60));
		return String.format("%02d%02d%02d", hours, minutes, seconds);
	}
	
	private void update(){
		String current = this.formatTime();
		if(last == null || !current.equals(last)){
			this.last = current;
			this.onUpdate();
		}
	}
	//Calls outside methods
	public void onUpdate(){
		StudyBuddy.frame.update(this.formatTime());
		StudyBuddy.frame.killer();
	}
	public void onComplete(){
		StudyBuddy.frame.setComplete();
		
	}
}


