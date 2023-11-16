package cdTimer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	
	  static timerSet time;
	  static Thread countdownThread;
	
	public static void main(String[] args) {
		createInterface();
	}
	
	public static void createInterface() {
		
		// - Pause set up
		Boolean isPaused = false;
		
		// - Basic JFrame Set up
		   JFrame frame = new JFrame("Countdown Timer");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        // Panel fun
	        JPanel instructionsPanel = new JPanel();
	        JPanel entryPanel = new JPanel();
	        JPanel resultsPanel = new JPanel();
	        
	        frame.add(instructionsPanel, BorderLayout.NORTH);
	        frame.add(entryPanel, BorderLayout.CENTER);
	        frame.add(resultsPanel, BorderLayout.SOUTH);
	        
	        
	        // - Creating Elements
	        
	        JLabel instructions = new JLabel("Please Enter the Length of Time you would like to Count Down From");
	        JLabel results = new JLabel ("No Current Timer");
	        JTextField dayEnt = new JTextField(10);
	        JTextField hourEnt = new JTextField(10);
	        JTextField minuteEnt = new JTextField(10);
	        JTextField secondEnt = new JTextField(10);
	        JButton submitButton = new JButton("Enter" );
	        JButton resetButton = new JButton("Reset");
	        JButton pauseButton = new JButton ("Pause");
	        
	        // - Adding Elements
	        
	        instructionsPanel.add(instructions);
	        entryPanel.add(dayEnt); entryPanel.add(hourEnt); entryPanel.add(minuteEnt); entryPanel.add(secondEnt); entryPanel.add(submitButton);  entryPanel.add(resetButton); entryPanel.add(pauseButton);
	        resultsPanel.add(results);

	        // - Button Handling
	        submitButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Long dayVal = Long.parseLong(dayEnt.getText());
	                Long hourVal = Long.parseLong(hourEnt.getText());
	                Long minuteVal = Long.parseLong(minuteEnt.getText());
	                Long secondVal = Long.parseLong(secondEnt.getText());
	                
	                System.out.println(" " + dayVal +  hourVal +  minuteVal + secondVal);

	                // If there's an existing thread, interrupt it
	                if (countdownThread != null && countdownThread.isAlive()) {
	                    countdownThread.interrupt();
	                }
	                

	                // Assuming timerSet has a method to set the countdown duration
	                time = new timerSet(dayVal, hourVal, minuteVal, secondVal);
	                time.updateTime(dayVal, hourVal, minuteVal, secondVal);
	                
	                // Starting the new countdown thread
	                countdownThread = new Thread(() -> time.start(results));
	                countdownThread.start();
	                
	                submitButton.setEnabled(false);
                    pauseButton.setEnabled(true);
	            }  	
	        });
	        
	        
	        pauseButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (time != null) {
	                    time.pause();            
	            }}
	        });
	        
	        
	        resetButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // If there's an existing thread, interrupt it
	                if (countdownThread != null && countdownThread.isAlive()) {
	                    countdownThread.interrupt();
	                }

	                if (time != null) {
	                    time.reset();
	                	time.stopCountdown();
	                    
	                    results.setText("Timer Reset");
	                    submitButton.setEnabled(true);
	                    pauseButton.setEnabled(false);
	                }
	            }
	        });

	        frame.setVisible(true);
	        frame.pack();
	       
	        }

}
