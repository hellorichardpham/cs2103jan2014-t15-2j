import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class CompletedTest {
	Completed completed = new Completed();
	private static ArrayList<Task> taskListTest = new ArrayList<Task>();
	String feedback = "";
	String taskId;

	//@author A0085107J
	/**
	 * testMarkCompleted: test if markCompleted methods will mark completed attribute to true
	 * 
	 * NOTE:As the markCompleted() edit the original taskList, this method has to retrieve the updated instance of the taskList
	 * after every step that edits the taskList.
	 *
	 * @param void
	 * @return void
	 */
	@Test
	public void testMarkCompleted() {
		taskListTest = ExeCom.getTaskListInstance();
		Add add = new Add(taskListTest);
		Command c = generateCommandForAddingTask(taskListTest);
		try {
			taskId = dummyAdd(add, c);
			markTask();	
			checkifTaskIsCompleted();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//@author A0085107J
	/**
	 * markTask: mark task based on taskId
	 * @param void
	 * @return void
	 */
	private void markTask() {
		Command c;
		c = generateCommandTargetedTask(taskId);
		completed.markCompleted(c);
	}

	//@author A0085107J
	/**
	 * checkifTaskIsCompleted: assert if completed attribute is true
	 * @param void
	 * @return void
	 */
	private void checkifTaskIsCompleted() {
		taskListTest = ExeCom.getTaskListInstance();
		int index = Integer.parseInt(taskId) - 1;
		assert(taskListTest.get(index).isCompleted());
	}

	//@author A0085107J
	/**
	 * dummyAdd: used for testing MarkCompleted()
	 * 
	 * @param add, c
	 * @return String
	 * @throws Exception
	 */
	private String dummyAdd(Add add, Command c) throws Exception {
		String taskId = add.addToTaskList(c);
		taskListTest = ExeCom.getTaskListInstance();
		return taskId;
	}

	//@author A0085107J
	/**
	 * generateCommandTargetedTask: generate command used for testing markCompleted()
	 * 
	 * @param String
	 * @return Command
	 */
	private Command generateCommandTargetedTask(String taskId) {
		Command c = new Command();
		ArrayList<String> target = generateArrayListForTargetedTask(taskId);
		c.setTargetedTasks(target);
		return c;
	}

	//@author A0085107J
	/**
	 * generateArrayListForTargetedTask: generate arraylist used for setting targetedTask attribute
	 * 
	 * @param String
	 * @return ArrayList<String>
	 */
	private ArrayList<String> generateArrayListForTargetedTask(String taskId) {
		ArrayList<String> target = new ArrayList<String>();
		target.add(taskId);
		return target;
	}


	//@author A0085107J
	/**
	 * generateCommand: creates a dummy command
	 * @param void
	 * @return Command
	 */
	private Command generateCommandForAddingTask(ArrayList<Task> list) {
		Command c = new Command();
		c.setDetails("testingMarkCompleted");
		String taskID = list.size() + 1 + "";
		c.setTaskID(taskID);
		return c;
	}

}
