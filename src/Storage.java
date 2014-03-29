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
		// to disable log message on output console
		logger.setUseParentHandlers(false);
		// log message written to MyLogFile.txt
		fh = new FileHandler("MyLogFile.txt");

		BufferedReader fileReader;
		fileReader = createFileIfNotExist();

		String[] retrieve;
		int arraySize;
		// int content;
		// int setContent;
		int counter;
		String text;
		try {
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.log(Level.INFO, "Start of retrieving tasks");

			while ((text = fileReader.readLine()) != null) {
				Task task = new Task();

				String location = "";
				String category = "";
				String priority = "";
				int i;
				int endPosition = 0;
				int startPosition = 0;

				retrieve = text.split(" ");
				arraySize = retrieve.length;

				for (i = 0; i < arraySize; i++) {
					if (retrieve[i].equals("//location")) {
						endPosition = i;
						for (int j = 1; j < arraySize; j++) {
							if (retrieve[j + i].equals("//category"))
								break;
							location += retrieve[j + i] + " ";
						}
					}
					if (retrieve[i].equals("//category")) {
						for (int j = 1; j < arraySize; j++) {
							if (retrieve[j + i].equals("//priority"))
								break;
							category += retrieve[j + i] + " ";
						}
					}
					if (retrieve[i].equals("//priority")) {
						for (int j = 1; j < arraySize; j++) {
							if (retrieve[j + i].equals("//taskid"))
								break;
							priority += retrieve[j + i] + " ";
						}
					}
				}
				if (location.endsWith(" "))
					location = location.substring(0, location.length() - 1);
				if (category.endsWith(" "))
					category = category.substring(0, category.length() - 1);
				if (priority.endsWith(" "))
					priority = priority.substring(0, priority.length() - 1);

				task.setLocation(location);
				task.setCategory(category);
				task.setPriority(priority);
				task.setTaskID(retrieve[arraySize - 1]);

				startPosition = endPosition - 10;
				endPosition = startPosition;

				task.setStartDay(retrieve[startPosition]);
				startPosition++;
				task.setStartMonth(retrieve[startPosition]);
				startPosition++;
				task.setStartYear(retrieve[startPosition]);
				startPosition++;
				task.setEndDay(retrieve[startPosition]);
				startPosition++;
				task.setEndMonth(retrieve[startPosition]);
				startPosition++;
				task.setEndYear(retrieve[startPosition]);
				startPosition++;
				task.setStartHours(retrieve[startPosition]);
				startPosition++;
				task.setStartMins(retrieve[startPosition]);
				startPosition++;
				task.setEndHours(retrieve[startPosition]);
				startPosition++;
				task.setEndMins(retrieve[startPosition]);

				String details = "";

				for (counter = 0; counter < endPosition; counter++) {
					if (counter == endPosition - 1) {
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

	/**
	 * 
	 * createFileIfNotExist: automatically creates an external .txt file for
	 * user if storage.txt is not found
	 * 
	 * @author Wei Zhou
	 * @param void
	 * @return Buffered Reader
	 */
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
		for (Task t : ExeCom.getTaskListInstance()) {
			pw.println(t.displayToStorage() + "//taskid "
					+ retrieveCurrentTaskID(t));

		}
		pw.close();
	}

	/**
	 * printTaskIdToStorage: return task ID of current Task
	 * 
	 * @author Ying Yun
	 * @param Task
	 * @return int
	 */
	private int retrieveCurrentTaskID(Task t) {
		return ExeCom.getTaskListInstance().indexOf(t) + 1;
	}
}