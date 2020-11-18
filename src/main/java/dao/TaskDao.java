package dao;

import entities.Subtask;
import entities.Task;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDao {

  private static Logger logger = Logger.getLogger(TaskDao.class);
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-mongodb");
  private static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
  private static EntityManager em;

  private TaskDao() {}

  public static void insertTask(Task task) {
    try {
      tm.begin();
      em = emf.createEntityManager();
      em.persist(task);
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
  }

  public static void deleteTask(Task task) {
    try {
      tm.begin();
      em = emf.createEntityManager();
      em.remove(em.contains(task) ? task : em.merge(task));
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
  }

  public static void updateTask(Task task) {
    try {
      tm.begin();
      em = emf.createEntityManager();
      em.merge(task);
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
  }

  public static List<Task> readAllTasks() {
    List<Task> tasks = new ArrayList<>();
    try {
      tm.begin();
      em = emf.createEntityManager();
      tasks = em.createNativeQuery("{}", Task.class).getResultList();
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
    return tasks;
  }

  public static List<Task> readOverdueTasks() {
    List<Task> tasks = new ArrayList<>();
    try {
      tm.begin();
      em = emf.createEntityManager();
      tasks =
          em.createNativeQuery(
                  String.format(
                      "{'deadline': {'$lt': ISODate('%s')}}}",
                      new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
                  Task.class)
              .getResultList();
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
    return tasks;
  }

  public static List<Task> readTasksWithCategory(String category) {
    List<Task> tasks = new ArrayList<>();
    try {
      tm.begin();
      em = emf.createEntityManager();
      tasks =
          em.createNativeQuery(String.format("{'category': '%s' }", category), Task.class)
              .getResultList();
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
    return tasks;
  }

  public static List<Task> readTasksByTextInDescription(String textInTaskDescription) {
    List<Task> tasks = new ArrayList<>();
    try {
      tm.begin();
      em = emf.createEntityManager();
      tasks =
          em.createNativeQuery(
                  String.format("{$text: {$search: '%s'}}", textInTaskDescription), Task.class)
              .getResultList();
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
    return tasks;
  }

  public static List<Task> readTasksByName(String taskName) {
    List<Task> tasks = new ArrayList<>();
    try {
      tm.begin();
      em = emf.createEntityManager();
      tasks =
          em.createNativeQuery(String.format("{'name': '%s'}", taskName), Task.class)
              .getResultList();
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
    return tasks;
  }

  public static List<Subtask> readSubtasksByTextInName(String textInSubtaskName) {
    List<Subtask> tasks = new ArrayList<>();
    try {
      tm.begin();
      em = emf.createEntityManager();
      tasks =
          em.createNativeQuery(
                  String.format("{$text: {$search: '%s'}}", textInSubtaskName), Subtask.class)
              .getResultList();
      em.flush();
      em.close();
      tm.commit();
    } catch (Exception e) {
      logger.error(e);
    }
    return tasks;
  }
}
