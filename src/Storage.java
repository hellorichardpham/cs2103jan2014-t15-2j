import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class Storage {
	
	private static Storage theOne;
	
	public static Storage getInstance() {
		if(theOne==null){
			theOne = new Storage();
		}
		return theOne;
	}

	/**
	 * 
	 * loadStorage: retrieve tasks found in the external file called Storage.txt to taskList
	 * 
	 * @author 	Khaleef
	 * @param 	void
	 * @return 	void
	 */
	public void loadStorage() throws Exception {

		File f = new File("Storage.txt");
		if (!f.exists()) {
			//System.out.println("File do not exists");
		} else {
			String[] retrieve;

			BufferedReader fileReader;
			int arraySize;
			int content;
			int setContent;
			int counter;
			String text;
			fileReader = new BufferedReader(new FileReader(f));

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
				task.setStartMin(retrieve[setContent]);
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
				ExeCom.taskList.add(task);
			}
			fileReader.close();
		}
	}

	/**
	 * 
	 * saveStorage: save taskList to external file called Storage.txt
	 * 
	 * @author 	Khaleef
	 * @param 	void
	 * @return 	void
	 */
	public void saveStorage() throws Exception {
		File currentFile = new File("Storage.txt");
		currentFile.delete();
		PrintWriter pw = new PrintWriter(new FileOutputStream("Storage.txt"));
		for (Task t : ExeCom.taskList)
			pw.println(t.display() + (ExeCom.taskList.indexOf(t)+1));
		pw.close();
	}
}
