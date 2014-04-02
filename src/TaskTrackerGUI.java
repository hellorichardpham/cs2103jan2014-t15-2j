import javax.swing.JOptionPane;

/**
 * Class TaskTrackerGUI This class acts as the GUI class to handle user events
 * 
 * @author Tian Weizhou
 */
public class TaskTrackerGUI extends javax.swing.JFrame {

	/**
	 * Creates new form TaskTrackerGUI
	 */
	public TaskTrackerGUI() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
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

	private void commandLineActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_commandLineActionPerformed
		String userInput = commandLine.getText();
		commandLine.setText("");
		ProcessCommand pc = new ProcessCommand();
		ExeCom ec = ExeCom.getInstance();

		Command com = pc.process(userInput);
		try {
			ec.executeCommand(com);
			String feedback;
			feedback = ec.getFeedback();

			if (ExeCom.getConflict()==true) {
				int n = JOptionPane.showConfirmDialog(null, "Add Task anyway?",
						"There is a conflict!", JOptionPane.YES_NO_OPTION);

				if (n == JOptionPane.YES_OPTION) {
					ec.addTask(com);	
					//System.out.println("ADDED");
				} else {
					//do nothing
				}
			}

			displayTextBox.append(feedback);
		} catch (Exception e) {
		}

	}// GEN-LAST:event_commandLineActionPerformed

	private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_undoButtonActionPerformed
		ExeCom.undo();
		String feedback = ExeCom.getFeedback();
		displayTextBox.append(feedback);
	}// GEN-LAST:event_undoButtonActionPerformed

	private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_redoButtonActionPerformed
		ExeCom.redo();
		String feedback = ExeCom.getFeedback();
		displayTextBox.append(feedback);
	}// GEN-LAST:event_redoButtonActionPerformed

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
