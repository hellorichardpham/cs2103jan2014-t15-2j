import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.logging.*;

public class Storage {

	private static final String FILENAME = "Storage.txt";
	private static Logger logger = Logger.getLogger("MyLog");

	/**
	 * 
	 * loadStorage: retrieve tasks found in the external file called Storage.txt
	 * to taskList
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public void loadStorage() throws Exception {
		ExeCom.getTaskListInstance().clear();
		FileHandler fh;
		logger.setUseParentHandlers(false);		// to disable log message on output screen
		fh = new FileHandler("MyLogFile.txt");	// log message written to MyLogFile.txt

		BufferedReader fileReader;
		fileReader = createFileIfNotExist();

		String[] retrieve;
		int arraySize;
		int content;
		int setContent;
		int counter;
		String text;
		try {
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.log(Level.INFO, "Start of retrieving tasks");

			while ((text = fileReader.readLine()) != null) {
				Task task = new Task();
				retrieve = text.split(" ");
				arraySize = retrieve.length;
				content = arraySize - 14;
				setContent = content;

				task.setStartDay(retrieve[setContent]);
				setContent++;
				task.setStartMonth(retrieve[setContent]);
				setContent++;
				task.setStartYear(retrieve[setContent]);
				setContent++;
				task.setEndDay(retrieve[setContent]);
				setContent++;
				task.setEndMonth(retrieve[setContent]);
				setContent++;
				task.setEndYear(retrieve[setContent]);
				setContent++;
				task.setStartHours(retrieve[setContent]);
				setContent++;
				task.setStartMins(retrieve[setContent]);
				setContent++;
				task.setEndHours(retrieve[setContent]);
				setContent++;
				task.setEndMins(retrieve[setContent]);
				setContent++;
				task.setLocation(retrieve[setContent]);
				setContent++;
				task.setPriority(retrieve[setContent]);
				setContent++;
				task.setCategory(retrieve[setContent]);
				setContent++;
				task.setTaskID(retrieve[setContent]);

				String details = "";

				for (counter = 0; counter < content; counter++) {
					if (counter == content - 1) {
						details += retrieve[counter];
					} else {
						details += retrieve[counter] + " ";
					}
				}
				task.setDetails(details);
				ExeCom.getTaskListInstance().add(task);
			}
			fileReader.close();
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Processing error", ex);
		}
		logger.log(Level.INFO, "Retrieve task successful");
		fh.close();
	}

	private BufferedReader createFileIfNotExist() throws FileNotFoundException {
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(FILENAME));
		} catch (FileNotFoundException ex) {
			PrintWriter writer = new PrintWriter(FILENAME);
			input = new BufferedReader(new FileReader(FILENAME));
			writer.close();
		}
		return input;
	}

	/**
	 * 
	 * saveStorage: save taskList to external file fileName
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public void saveStorage() throws Exception {
		File currentFile = new File(FILENAME);
		currentFile.delete();
		PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME));
		for (Task t : ExeCom.getTaskListInstance())
			pw.println(t.display() + (ExeCom.getTaskListInstance().indexOf(t) + 1));
		pw.close();
	}
}
