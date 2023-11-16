package cdTimer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class timerSet {
	
	// time/ Boolean declarations
	Long days;
	Long hours;
	Long minutes;
	Long seconds;
	
	static Duration duration = Duration.ZERO;
	private volatile boolean stopRequested = false;
	private volatile boolean pauseReq = false;
	
	
// - Initial Declarations

	
	public timerSet (Long Days, Long Hours, Long Minutes, Long Seconds) {
		days = Days;
		hours = Hours;
		minutes = Minutes;
		seconds = Seconds;
	}
	
	public  void updateTime(Long Days, Long Hours, Long Minutes, Long Seconds) {
		days = Days;
		hours = Hours;
		minutes = Minutes;
		seconds = Seconds;
	}
	
	// -- Timer functions
	
	public void start(JLabel label) {
		duration = duration.plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
		
		while (!duration.isNegative()&& stopRequested == false) {
			if (!pauseReq) {
				duration = duration.minusSeconds(1);
				label.setText(formatDuration(duration));
			}
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException g) {
                g.printStackTrace();
            }
		}
        stopRequested = false;
	}
	
	
    public void stopCountdown() {
        stopRequested = true;
    }
	
	public void pause() {
		if (pauseReq == true) {
			pauseReq = false;
			
		}
		else {
			pauseReq = true;
		}
	}
	
	
	public void reset() {
		duration = duration.ZERO;
		days = 0L;
		hours = 0L;
		minutes = 0L;
		seconds = 0L;
	}
	
	// - formatter for the JLabel
	
	 private static String formatDuration(Duration duration) {
		    long days = duration.toDays();
		    long hours = duration.minusDays(days).toHours();
		    long minutes = duration.minusDays(days).minusHours(hours).toMinutes();
		    long seconds = duration.minusDays(days).minusHours(hours).minusMinutes(minutes).getSeconds();

		    return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
	    }

	
	
	

}
