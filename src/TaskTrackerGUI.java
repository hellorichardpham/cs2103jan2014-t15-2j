import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

//@author A0083093E
/**
 * Class TaskTrackerGUI This class acts as the GUI class to handle user events
 */
public class TaskTrackerGUI extends javax.swing.JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	// code placed at front of feedback for GUI to recognize there is a conflict
	private final static String CONFLICTED_CODE = "-cs2103--conflicted";
	private final static String CANCELLED = "cancel";
	private final static String WELCOME_MESSAGE = "======= Welcome to TaskTracker! =======\n";

	private static StyledDocument doc;
	private static Style blue1;
	private static Style blue2;
	private static Style green;
	private static Style black;
	private static Style gray;
	private static Style red;
	private static Style header;

	private String conflictedUserInput;

	//@author A0083093E
	/**
	 * Creates new form TaskTrackerGUI
	 */
	public TaskTrackerGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));
		setTitle("TaskTracker");
		try {
			initComponents();
			Alarm.setNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@author A0083093E generated
	/**
	 * initComponents: initialise components of GUI
	 * 
	 * @param void
	 * @return void
	 */
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        displayTextBox = new javax.swing.JTextArea();
        commandLine = new javax.swing.JTextField();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        helpButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        displayTextBox.setEditable(false);
        displayTextBox.setColumns(20);
        displayTextBox.setRows(5);
        displayTextBox.setText("Welcome to TaskTracker :)\n\n\n");
        jScrollPane1.setViewportView(displayTextBox);

        commandLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineActionPerformed(evt);
            }
        });

        undoButton.setText("Undo");
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        redoButton.setText("Redo");
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Enter Command:");

        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(undoButton)
                        .addGap(18, 18, 18)
                        .addComponent(redoButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 587, Short.MAX_VALUE)
                        .addComponent(helpButton)
                        .addGap(18, 18, 18)
                        .addComponent(exitButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commandLine)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {exitButton, helpButton, redoButton, undoButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commandLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(undoButton)
                    .addComponent(redoButton)
                    .addComponent(exitButton)
                    .addComponent(helpButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        Command help = new Command();
        help.setKeyword("help");
        ExeCom ex = ExeCom.getInstance();
        
            try {
                String helpDisplay = ex.executeCommand(help);
            } catch(Exception e) {
            }
            
        print(helpDisplay,blue1);
    }//GEN-LAST:event_helpButtonActionPerformed

	//@author A0083093E
	/**
	 * This method prints the welcome message
	 */
	private void displayWelcomeMessage(){
		printLine(WELCOME_MESSAGE, header);
		displayTaskForTheDay();
	}

	//@author A0085107J
	/**
	 * displayTaskForTheDay: displays tasks that ends today
	 * 
	 * @param void
	 * @return void
	 */
	private void displayTaskForTheDay() {
		Storage s = new Storage();
		try {
			s.loadStorage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Task> taskList = ExeCom.getTaskListInstance();
		Display d = new Display(taskList);

		String feedback = d.displayTaskForToday();
		printFeedback(feedback);
	}

	//@author A0083093E
	/**
	 * This method prints the string onto the displaybox in the specified style with a newline
	 * @param string
	 * @param style
	 */
	private void printLine(String s, Style style) {
		try {
			doc.insertString(doc.getLength(), s + "\n", style);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	//@author A0083093E
	/**
	 * This method prints the string onto the displaybox in the specified style without a newline
	 * @param string
	 * @param style
	 */
	private void print(String s, Style style) {
		try {
			doc.insertString(doc.getLength(), s, style);
		} catch(BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	//@author A0083093E
	/**
	 * This method sets the styles available for printing
	 */
	private void setStyles() {
	
		Color darkBlue = new Color(27,67,118);
		blue1 = displayTextBox.addStyle("This prints in dark blue", null);
		StyleConstants.setForeground(blue1, darkBlue);
		StyleConstants.setFontSize(blue1, 15);
		StyleConstants.setFontFamily(blue1, "arial");

		Color lighterBlue = new Color(96,117,158);
		blue2 = displayTextBox.addStyle("This prints in lighter blue", null);
		StyleConstants.setForeground(blue2, lighterBlue);
		StyleConstants.setFontSize(blue2, 15);
		StyleConstants.setFontFamily(blue2, "arial");

		Color greenish = new Color(46,139,87);
		green = displayTextBox.addStyle("This prints in green", null);
		StyleConstants.setForeground(green, greenish);
		StyleConstants.setFontSize(green, 15);
		StyleConstants.setFontFamily(green, "arial");

		Color darkRed = new Color(128,0,128);
		red = displayTextBox.addStyle("This prints in red",null);
		StyleConstants.setForeground(red, darkRed);
		StyleConstants.setFontSize(red, 15);
		StyleConstants.setFontFamily(red, "arial");

		black = displayTextBox.addStyle("This prints in black", null);
		StyleConstants.setForeground(black, Color.black);
		StyleConstants.setFontSize(black, 15);
		StyleConstants.setFontFamily(black, "arial");

		gray = displayTextBox.addStyle("This prints in gray", null);
		StyleConstants.setForeground(gray, Color.gray);
		StyleConstants.setFontSize(gray, 15);
		StyleConstants.setFontFamily(gray, "arial");

		Color dark = new Color(154,50,50);
		header = displayTextBox.addStyle("This prints the headers",null);
		StyleConstants.setForeground(header, dark);
		StyleConstants.setFontFamily(header, "times new roman");
		StyleConstants.setFontSize(header, 20);
	}

	//@author A0083093E
	/**
	 * This method describes the action taken by the program when the user enters
	 * a line of command
	 */
	private void commandLineActionPerformed(java.awt.event.ActionEvent evt) {
		String userInput = commandLine.getText().trim();
		commandLine.setText("");

		ProcessCommand pc = new ProcessCommand();
		ExeCom ec = ExeCom.getInstance();
		Command com = pc.process(userInput);

		if (com.getKeyword().contains("clear")) {
			displayTextBox.setText("");
			printLine(WELCOME_MESSAGE,header);
		}

		if (conflictedUserInput != null) {
			switch (userInput.toLowerCase()) {
			case "yes":
			case "y":
			case "ya":
			case "yeah":
				com = pc.process(conflictedUserInput);
				conflictedUserInput = null;
				break;
			default :
				com.setKeyword(CANCELLED);
				conflictedUserInput = null;
			}
		}

		try {
			ec.executeCommand(com);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String feedback;
		feedback = ExeCom.getFeedback();

		feedback = replaceConflictCode(userInput, feedback);
		setUndoRedoButtons();	
		printFeedback(feedback);
	}

	//@author A0083093E
	/**
	 * This method stamps the "just" keyword over the original add or edit keyword so that ExeCom executes
	 * the command without checking for conflicts
	 * Subsequently, it removes the codeword for printing
	 * 
	 * @param String,String
	 * @return String
	 */
	private String replaceConflictCode(String userInput, String feedback) {
		if (feedback.contains(CONFLICTED_CODE)) {
			// change command keyword to justadd
			conflictedUserInput = "just" + userInput;
			feedback = feedback.replace(CONFLICTED_CODE, "").trim();
		}
		return feedback;
	}

	//@author A0083093E
	/**
	 * This method describes the action taken by the program when the user clicks
	 * on the Undo button
	 */
	private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {
		ExeCom ec = ExeCom.getInstance();
		Command command = new Command();
		command.setKeyword("undo");
		try {
			ec.executeCommand(command);
		} catch (Exception e) {
			System.out.println("Could not perform undo command");
		}
		String feedback = ExeCom.getFeedback();
		printFeedback(feedback);
		setUndoRedoButtons();
	}

	//@author A0083093E
	/**
	 * This method describes the action taken by the program when the user clicks
	 * on the Redo button
	 */
	private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {
		ExeCom ec = ExeCom.getInstance();
		Command command = new Command();
		command.setKeyword("redo");
		try {
			ec.executeCommand(command);
		} catch (Exception e) {
			System.out.println("Could not perform redo command");
		}
		String feedback = ExeCom.getFeedback();
		printFeedback(feedback);
		setUndoRedoButtons();
	}

	//@author A0083093E
	/**
	 * This method checks the undo and redo Stacks and updates the buttons accordingly
	 */
	private void setUndoRedoButtons() {
		undoButton.setEnabled(ExeCom.checkUndoStack());
		redoButton.setEnabled(ExeCom.checkRedoStack());
	}

	//@author A0083093E
	/**
	 * This method prints the String feedback returned by ExeCom
	 * @param String
	 */
	private void printFeedback(String feedback) {
		String[] feedbackArray = feedback.split("\n");

		for(int i=0;i<feedbackArray.length;i++) {
			if(feedbackArray[i].length()!=0 && Character.isDigit(feedbackArray[i].charAt(0))) {

				String details = "";
				String startDate = "";
				String endDate = "";
				String startTime = "";
				String endTime = "";
				String location = "";	
				String priority = "";
				String category = "";

				//print index
				print(feedbackArray[i].substring(0,feedbackArray[i].indexOf(":")) + ". ",black);
				String[] componentArray = feedbackArray[i].split("//");
				for(int j=0;j<componentArray.length;j++) {
					if(componentArray[j].contains("DECS2103:")) {
						details = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("SDCS2103:")) {
						startDate = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("EDCS2103:")) {
						endDate = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("STCS2103:")) {
						startTime = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("ETCS2103:")) {
						endTime = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("LOCS2103:")) {
						location = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("CACS2103:")) {
						category = componentArray[j].substring(10);
					}
					if(componentArray[j].contains("PRCS2103:")) {
						priority = componentArray[j].substring(10);
					}
				}
				printTime(startTime,endTime);
				printDetails(details);
				printLocation(location);
				printDate(startDate,endDate);
				printCategory(category);
				printPriority(priority);
				printLine("",black);

			}
			else if(feedbackArray[i].contains("=====")) {
				printLine(feedbackArray[i],header);
			}
			else {
				printLine(feedbackArray[i],blue1);
			}
		}

		printLine("",black);
		displayTextBox.setCaretPosition(displayTextBox.getDocument().getLength());
	}

	//@author A0083093E
	/**
	 * printDetails: displays detail attribute on the GUI
	 * 
	 * @param String
	 * @return void
	 */
	private void printDetails(String details) {
		print(details,blue1);
	}

	//@author A0083093E
	/**
	 * printPriority: displays priority attribute on the GUI
	 * 
	 * @param String
	 * @return void
	 */
	private void printPriority(String priority) {
		if(!priority.equals("")) {
			print("[" + priority + "]",red);
		}
	}

	//@author A0083093E
	/**
	 * printCategory: displays category attribute on the GUI
	 * 
	 * @param String
	 * @return void
	 */
	private void printCategory(String category) {
		if(!category.equals("")) {
			print(" {" + category + "} ",blue2);
		}

	}

	//@author A0083093E
	/**
	 * printLocation: displays location attribute on the GUI
	 * 
	 * @param String
	 * @return void
	 */
	private void printLocation(String location) {
		if(!location.equals("")) {
			print(" @" + location,red);
		}
	}

	//@author A0083093E
	/**
	 * printDate: displays date attributes on the GUI
	 * 
	 * @param String, String
	 * @return void
	 */
	private void printDate(String startDate, String endDate) {
		if(startDate.equals("")) {
			print(" on " + endDate , green);
		} else {
			print(" from " + startDate + " to " + endDate , green);
		}
	}

	//@author A0083093E
	/**
	 * printTime: displays time attributes on the GUI
	 * 
	 * @param String, String
	 * @return void
	 */
	private void printTime(String startTime, String endTime) {
		if(startTime.equals("") && endTime.equals("")) {
			return;
		}
		else if(!startTime.equals("") && !endTime.equals("")) {
			print("[" + startTime + "-" + endTime + "] ",gray);
		}
		else if(!startTime.equals("")) {
			print("[" + startTime + "] ",gray);
		}
		else {
			print("[" + endTime + "] ",gray);
		}
	}

	//@author A0083093E
	/**
	 * This line closes the program when the user clicks on the Exit button
	 */
	private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	//@author A0083093E generated
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(TaskTrackerGUI.class.getName())
			.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(TaskTrackerGUI.class.getName())
			.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(TaskTrackerGUI.class.getName())
			.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(TaskTrackerGUI.class.getName())
			.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TaskTrackerGUI().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField commandLine;
    private javax.swing.JTextArea displayTextBox;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton redoButton;
    private javax.swing.JButton undoButton;
    // End of variables declaration//GEN-END:variables

	//@author A0083093E
	/**
	 * This method allows User to press the up and down directional keys to scroll the textField
	 * @Override
	 **/
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		//if User presses UP key
		if(keyCode==38) {
			jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getValue()-30);
		}
		//if User presses DOWN key
		if(keyCode==40) {
			jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getValue()+30);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Do Nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do Nothing

	}
}
