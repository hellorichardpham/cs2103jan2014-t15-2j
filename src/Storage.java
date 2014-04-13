import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.logging.*;

//@author A0097961M
public class Storage {

	private static final String FILENAME = "Storage.txt";
	private static final String LOG_FILE_NAME = "MyLogFile.txt";
	private static final String LOG_START = "Start of retrieving tasks";
	private static final String LOG_ERROR = "Processing error";
	private static final String LOG_END = "Retrieve task successful";
	private static final String EQUAL_LOCATION = "//location";
	private static final String EQUAL_CATEGORY = "//category";
	private static final String EQUAL_PRIORITY = "//priority";
	private static final String EQUAL_COMPLETE = "//completed";
	private static final String EQUAL_TASKID = "//taskid";
	private static final String EQUAL_BLANK_SPACE = " ";
	private static final String EQUAL_EMPTY_STRING = "";

	private static final int ZERO_CONST = 0;
	private static final int ONE_CONST = 1;
	private static final int TEN_CONST = 10;

	private static Logger logger = Logger.getLogger("MyLog");

	//@author A0097961M
	/**
	 * 
	 * loadStorage: retrieve tasks found in the external file called Storage.txt
	 * to taskList
	 * 
	 * @param void
	 * @return void
	 */
	public void loadStorage() throws Exception {
		ExeCom.getTaskListInstance().clear();
		FileHandler fh;
		// to disable log message on output console
		logger.setUseParentHandlers(false);
		// log message written to MyLogFile.txt
		fh = new FileHandler(LOG_FILE_NAME);

		BufferedReader fileReader;
		fileReader = createFileIfNotExist();

		String[] retrieve;
		int arraySize;
		int counter;
		String text;
		try {
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			// log message written to MyLogFile.txt during start
			logger.log(Level.INFO, LOG_START);

			while ((text = fileReader.readLine()) != null) {
				Task task = new Task();

				String location = EQUAL_EMPTY_STRING;
				String category = EQUAL_EMPTY_STRING;
				String priority = EQUAL_EMPTY_STRING;
				String details = EQUAL_EMPTY_STRING;
				String complete = EQUAL_EMPTY_STRING;

				int i;
				int endPosition = ZERO_CONST;
				int startPosition = ZERO_CONST;

				retrieve = text.split(EQUAL_BLANK_SPACE);
				arraySize = retrieve.length;

				for (i = ZERO_CONST; i < arraySize; i++) {
					if (retrieve[i].equals(EQUAL_LOCATION)) {
						endPosition = i;
						for (int j = ONE_CONST; j < arraySize; j++) {
							if (retrieve[j + i].equals(EQUAL_CATEGORY))
								break;
							location += retrieve[j + i] + " ";
						}
					}
					if (retrieve[i].equals(EQUAL_CATEGORY)) {
						for (int j = ONE_CONST; j < arraySize; j++) {
							if (retrieve[j + i].equals(EQUAL_PRIORITY))
								break;
							category += retrieve[j + i] + " ";
						}
					}
					if (retrieve[i].equals(EQUAL_PRIORITY)) {
						for (int j = ONE_CONST; j < arraySize; j++) {
							if (retrieve[j + i].equals(EQUAL_COMPLETE))
								break;
							priority += retrieve[j + i] + " ";
						}
					}
					if (retrieve[i].equals(EQUAL_COMPLETE)) {
						for (int j = ONE_CONST; j < arraySize; j++) {
							if (retrieve[j + i].equals(EQUAL_TASKID))
								break;
							complete += retrieve[j + i] + " ";
						}
					}
				}
				if (location.endsWith(EQUAL_BLANK_SPACE))
					location = location.substring(ZERO_CONST, location.length()
							- ONE_CONST);
				if (category.endsWith(EQUAL_BLANK_SPACE))
					category = category.substring(ZERO_CONST, category.length()
							- ONE_CONST);
				if (priority.endsWith(EQUAL_BLANK_SPACE))
					priority = priority.substring(ZERO_CONST, priority.length()
							- ONE_CONST);
				if (complete.endsWith(EQUAL_BLANK_SPACE))
					complete = complete.substring(ZERO_CONST, complete.length()
							- ONE_CONST);

				setLocationToTaskIDAttribute(retrieve, arraySize, task,
						location, category, priority, complete);

				startPosition = endPosition - TEN_CONST;
				endPosition = startPosition;

				setDayToMinsAttributes(retrieve, task, startPosition);

				for (counter = ZERO_CONST; counter < endPosition; counter++) {
					if (counter == endPosition - ONE_CONST) {
						details += retrieve[counter];
					} else {
						details += retrieve[counter] + EQUAL_BLANK_SPACE;
					}
				}

				setDetailsAttibutes(task, details);
				ExeCom.getTaskListInstance().add(task);
			}
			fileReader.close();
		} catch (Exception ex) {
			// log message written to MyLogFile.txt if error
			logger.log(Level.WARNING, LOG_ERROR, ex);
		}
		// log message written to MyLogFile.txt if successful
		logger.log(Level.INFO, LOG_END);
		fh.close();
	}

	//@author A0097961M
	/**
	 * 
	 * setDetailsAttibutes: set attributes for details into arrayList
	 * 
	 * @param Task
	 *            , String
	 * @return void
	 */
	private void setDetailsAttibutes(Task task, String details) {
		task.setDetails(details);
	}

	//@author A0097961M
	/**
	 * 
	 * setLocationToTaskIDAttribute: set attributes for location, category,
	 * priority, taskID into arrayList
	 * 
	 * @param String
	 *            [], int, Task, String, String, String
	 * @return void
	 */
	private void setLocationToTaskIDAttribute(String[] retrieve, int arraySize,
			Task task, String location, String category, String priority,
			String complete) {
		task.setLocation(location);
		task.setCategory(category);
		task.setPriority(priority);
		task.setCompleted(complete);
		task.setTaskID(retrieve[arraySize - ONE_CONST]);
	}

	//@author A0097961M
	/**
	 * 
	 * setDayToMinsAttributes: set attributes for startDay, startMonth,
	 * startYear, endDay, endMonth, endYear, startHours, startMins, endHours,
	 * endMins into arrayList to taskList
	 * 
	 * @param String
	 *            [], Task, int
	 * @return void
	 */
	private void setDayToMinsAttributes(String[] retrieve, Task task,
			int startPosition) {
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
	}

	//@author A0083093E
	/**
	 * 
	 * createFileIfNotExist: automatically creates an external .txt file for
	 * user if storage.txt is not found
	 * 
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

	//@author A0097961M
	/**
	 * 
	 * saveStorage: save taskList to external file fileName
	 * 
	 * @param void
	 * @return void
	 */
	public void saveStorage() throws Exception {
		File currentFile = new File(FILENAME);
		currentFile.delete();
		PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME));
		for (Task t : ExeCom.getTaskListInstance()) {
			pw.println(t.displayToStorage() + EQUAL_TASKID + EQUAL_BLANK_SPACE
					+ retrieveCurrentTaskID(t));

		}
		pw.close();
	}

	//@author A0085107J
	/**
	 * printTaskIdToStorage: return task ID of current Task
	 * 
	 * @param Task
	 * @return int
	 */
	private int retrieveCurrentTaskID(Task t) {
		return ExeCom.getTaskListInstance().indexOf(t) + ONE_CONST;
	}
}