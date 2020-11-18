package logic;

import dao.TaskDao;
import entities.Subtask;
import entities.Task;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class AppOperations {

  private static Logger logger = Logger.getLogger(AppOperations.class);

  private AppOperations() {}

  public static void run() {
    Scanner scanner = new Scanner(System.in);
    boolean quit = false;
    printMenu();
    while (!quit) {
      String action = scanner.nextLine();
      switch (action) {
        case "0":
          quit = true;
          break;
        case "1":
          logger.info("\n***PRINTING ALL TASKS****");
          getAllTasks();
          printBackToMenu();
          break;
        case "2":
          logger.info("\n***PRINTING ALL OVERDUE TASKS****");
          getOverdueTasks();
          printBackToMenu();
          break;
        case "3":
          logger.info("\n***PRINTING ALL TASKS FOR SELECTED CATEGORY****");
          logger.info("Enter category for search: ");
          String taskCategory = scanner.nextLine();
          getTasksWithCategory(taskCategory);
          printBackToMenu();
          break;
        case "4":
          logger.info("\n***PRINTING ALL SUBTASKS FOR SELECTED CATEGORY****");
          logger.info("Enter category for search: ");
          String subCategory = scanner.nextLine();
          getSubtasksWithCategory(subCategory);
          printBackToMenu();
          break;
        case "5":
          logger.info("\n***PRINTING ALL TASKS WITH SPECIFIED TEXT IN DESCRIPTION****");
          logger.info("Enter text for search in tasks description: ");
          String textInTaskDescription = scanner.nextLine();
          getTasksByTextInDescription(textInTaskDescription);
          printBackToMenu();
          break;
        case "6":
          logger.info("\n***PRINTING ALL SUBTASKS WITH SPECIFIED TEXT IN NAME****");
          logger.info("Enter text for search in subtasks names: ");
          String textInSubtaskName = scanner.nextLine();
          getSubtasksByTextInName(textInSubtaskName);
          printBackToMenu();
          break;
        case "7":
          logger.info("\n***ADDING NEW TASK WITH SUBTASKS****");
          logger.info("Enter name for new task: ");
          String name = scanner.nextLine();
          logger.info("Enter description for new task: ");
          String description = scanner.nextLine();
          logger.info("Enter category for new task: ");
          String category = scanner.nextLine();
          logger.info("Enter deadline in format yyyy-MM-dd for new task: ");
          String deadline = scanner.nextLine();
          logger.info("Specify number of subtasks: ");
          int numOfSubtasks = scanner.nextInt();
          scanner.nextLine();
          List<Subtask> subtasks = new ArrayList<>();
          for (int i = 0; i < numOfSubtasks; i++) {
            Subtask subtask = new Subtask();
            logger.info("Enter name for subtask " + (i + 1) + " :");
            subtask.setName(scanner.nextLine());
            logger.info("Enter description for subtask " + (i + 1) + " :");
            subtask.setDescription(scanner.nextLine());
            subtasks.add(subtask);
          }
          addTaskWithSubtasks(name, description, category, deadline, subtasks);
          printBackToMenu();
          break;
        case "8":
          logger.info("\n***DELETING TASK WITH SUBTASKS****\n");
          logger.info("Enter name for task to delete: ");
          String taskName = scanner.nextLine();
          deleteTaskWithSubTasks(taskName);
          printBackToMenu();
          break;
        case "9":
          printMenu();
          break;
        default:
          logger.info("Incorrect action.");
          printBackToMenu();
      }
    }
  }

  private static void printMenu() {
    logger.info(
        "\n*** MENU ***\n"
            + "Available actions:\n"
            + "press\n"
            + "0 - to quit\n"
            + "1 - show all tasks\n"
            + "2 - show overdue tasks \n"
            + "3 - show all tasks with specific category\n"
            + "4 - show all subtasks related to tasks with specific category\n"
            + "5 - search task by text in description\n"
            + "6 - search subtask by name\n"
            + "7 - add new task with subtasks\n"
            + "8 - delete task with subtasks\n"
            + "9 - print available actions.\n");
  }

  private static void printBackToMenu() {
    logger.info("\nPress 9 for back to menu");
  }

  private static void printTaskAndSubtasksDetails(List<Task> tasks) {
    tasks.forEach(
        task -> {
          logger.info(
              "\n<TASK DETAILS>"
                  + " \nName: "
                  + task.getName()
                  + " \nDescription: "
                  + task.getDescription()
                  + " \nCategory: "
                  + task.getCategory()
                  + " \nCreation date: "
                  + task.getDateOfCreating()
                  + " \nDeadline: "
                  + task.getDeadline()
                  + " \nNumber of Subtasks: "
                  + task.getSubtask().size());
          task.getSubtask()
              .forEach(
                  subtask ->
                      logger.info(
                          "\t<SUBTASK DETAILS>"
                              + " \n\tName: "
                              + subtask.getName()
                              + " \n\tDescription: "
                              + subtask.getDescription()));
        });
  }

  private static void getAllTasks() {
    List<Task> tasks = TaskDao.readAllTasks();
    if (tasks.isEmpty()) {
      logger.info("No tasks.");
    } else {
      printTaskAndSubtasksDetails(tasks);
    }
  }

  private static void getOverdueTasks() {
    List<Task> tasks = TaskDao.readOverdueTasks();
    if (tasks.isEmpty()) {
      logger.info("No overdue tasks.");
    } else {
      printTaskAndSubtasksDetails(tasks);
    }
  }

  private static void getTasksWithCategory(String category) {
    List<Task> tasks = TaskDao.readTasksWithCategory(category);
    if (tasks.isEmpty()) {
      logger.info("\nNo tasks in this category found");
    } else {
      printTaskAndSubtasksDetails(tasks);
    }
  }

  private static void getSubtasksWithCategory(String category) {
    List<Task> tasks = TaskDao.readTasksWithCategory(category);
    if (tasks.isEmpty()) {
      logger.info("\nNo subtasks in this category found");
    } else {
      tasks.forEach(
          task -> {
            logger.info("\nTASK: " + task.getName());
            task.getSubtask()
                .forEach(
                    subtask ->
                        logger.info(
                            "\tSUBTASK DETAILS"
                                + " \n\tName: "
                                + subtask.getName()
                                + " \n\tDescription: "
                                + subtask.getDescription()));
          });
    }
  }

  private static void getTasksByTextInDescription(String textInDescription) {
    List<Task> tasks = TaskDao.readTasksByTextInDescription(textInDescription);
    if (tasks.isEmpty()) {
      logger.info("\nNo tasks with such text in description found");
    } else {
      printTaskAndSubtasksDetails(tasks);
    }
  }

  private static void getSubtasksByTextInName(String textInName) {
    List<Subtask> subtasks = TaskDao.readSubtasksByTextInName(textInName);
    if (subtasks.isEmpty()) {
      logger.info("\nNo subtasks with such text in name found");
    } else {
      subtasks.forEach(
          subtask ->
              logger.info(
                  "\nSUBTASK DETAILS"
                      + " \nName: "
                      + subtask.getName()
                      + " \nDescription: "
                      + subtask.getDescription()));
    }
  }

  private static void addTaskWithSubtasks(
      String name, String description, String category, String deadline, List<Subtask> subtasks) {
    Date deadlineDate = null;
    try {
      deadlineDate = new SimpleDateFormat("yyyy-MM-dd").parse(deadline);
    } catch (ParseException e) {
      logger.error(e);
    }
    Task task = new Task(name, description, category, new Date(), deadlineDate, subtasks);
    TaskDao.insertTask(task);
    logger.info("Task with subtasks saved!");
  }

  private static void deleteTaskWithSubTasks(String name) {
    List<Task> tasks = TaskDao.readTasksByName(name);
    if (tasks.isEmpty()) {
      logger.info("\nNo tasks found with such name: " + name);
    } else {
      tasks.forEach(TaskDao::deleteTask);
      logger.info("Tasks deleted.");
    }
  }
}
