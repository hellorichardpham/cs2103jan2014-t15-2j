/**
 * Class TaskTrackerGUI This class acts as the GUI class to handle user events
 * 
 * @author Tian Weizhou
 */
public class TaskTrackerGUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//code placed at front of feedback for GUI to recognize there is a conflict
	private final static String CONFLICTED_CODE = "-cs2103--conflicted";
	private final static String CANCELLED = "cancel";
	private String conflictedUserInput;
	
	/**
	 * Creates new form TaskTrackerGUI
	 * @author Tian Weizhou
	 */
	public TaskTrackerGUI() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * @author Tian Weizhou
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		displayTextBox = new javax.swing.JTextArea();
		commandLine = new javax.swing.JTextField();
		undoButton = new javax.swing.JButton();
		redoButton = new javax.swing.JButton();
		exitButton = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();

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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
														.addComponent(
																undoButton)
																.addGap(18, 18,
																		18)
																		.addComponent(
																				redoButton)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																						662,
																						Short.MAX_VALUE)
																						.addComponent(
																								exitButton))
																								.addGroup(
																										javax.swing.GroupLayout.Alignment.TRAILING,
																										layout.createSequentialGroup()
																										.addComponent(
																												jLabel1,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												101,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addPreferredGap(
																														javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																commandLine)))
																																.addContainerGap()));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { exitButton, redoButton, undoButton });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								363, Short.MAX_VALUE)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														commandLine,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel1))
														.addGap(18, 18, 18)
														.addGroup(
																layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(undoButton)
																		.addComponent(redoButton)
																		.addComponent(exitButton))
																		.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	/**
	 * This line describes the action taken by the program when the user enters a line of command
	 * @author Tian Weizhou
	 */
	private void commandLineActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_commandLineActionPerformed
		String userInput = commandLine.getText().trim();
		commandLine.setText("");
		ProcessCommand pc = new ProcessCommand();
		ExeCom ec = ExeCom.getInstance();
		Command com = pc.process(userInput);
		if(conflictedUserInput!=null) {
		switch(userInput.toLowerCase()) {
		case "yes":
		case "y":
		case "ya":
				com = pc.process(conflictedUserInput);
				conflictedUserInput=null;
				break;
		case "no":
		case "n":
			com.setKeyword(CANCELLED);
		}
		}

		try {
			ec.executeCommand(com);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String feedback;
		feedback = ExeCom.getFeedback();

		if(feedback.contains(CONFLICTED_CODE)) {
			//change command keyword to justadd
			conflictedUserInput ="just" + userInput;
			feedback = feedback.replace(CONFLICTED_CODE, "").trim();
		}
		displayTextBox.append(feedback);
		
	}// GEN-LAST:event_commandLineActionPerformed
	
	/**
	 * This line describes the action taken by the program when the user clicks on the Undo button
	 * @author Tian Weizhou
	 */
	private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_undoButtonActionPerformed
		ExeCom.undo();
		String feedback = ExeCom.getFeedback();
		displayTextBox.append(feedback);
	}// GEN-LAST:event_undoButtonActionPerformed
	
	/**
	 * This line describes the action taken by the program when the user clicks on the Redo button
	 *
	 * @author Tian Weizhou
	 */
	private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_redoButtonActionPerformed
		ExeCom.redo();
		String feedback = ExeCom.getFeedback();
		displayTextBox.append(feedback);
	}// GEN-LAST:event_redoButtonActionPerformed
	
	/**
	 * This line closes the program when the user clicks on the Exit button
	 * @author Tian Weizhou
	 */
	private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitButtonActionPerformed
		System.exit(0);
	}// GEN-LAST:event_exitButtonActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
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
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton redoButton;
	private javax.swing.JButton undoButton;
	// End of variables declaration//GEN-END:variables
}
