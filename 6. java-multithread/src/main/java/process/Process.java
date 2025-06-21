package process;

import data.loadExcel;
import object.Employee;
import object.Task;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Process {
    public static int totalTasks;
    public static int completedTasks;
    public static ArrayList<Employee> employees;
    public static int countEmployers;
    public static CyclicBarrier synchronizer;
    public static boolean isActive;

    private static final String TASKS_PATH = "src/main/resources/Tasks.xlsx";
    private static final String EMPLOYERS_PATH = "src/main/resources/Employers.xlsx";

    public static void startProcess(){
        isActive = true;
        loadData();
        for (Employee employee : employees){
            Thread thread = new Thread(employee);
            thread.start();
        }
    }

    private static void loadData(){
        Task.setTasks(loadExcel.loadTask(TASKS_PATH));
        employees = loadExcel.loadEmployee(EMPLOYERS_PATH);
        countEmployers = employees.size();
        completedTasks = 0;
        totalTasks = Task.getTasks().size();
        synchronizer = new CyclicBarrier(Process.countEmployers, new afterDay());
    }
}
