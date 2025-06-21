package object;

import java.util.concurrent.BrokenBarrierException;
import process.Process;

public class Employee implements Runnable {
    private final int id;
    private final String name;
    private Task completeTask;
    private final int workTime;
    private int amountTask;
    private int hourInDay;
    private int plainTime;
    private int countWork;


    public Employee(int id, String name, int workTime) {
        this.id = id;
        this.name = name;
        this.workTime = workTime;
        amountTask = 0;
        hourInDay = 0;
        completeTask = null;
        plainTime = 0;
        countWork = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getAmountTask(){
        return amountTask;
    }

    public int getCountWork() {
        return countWork;
    }

    public int getHourInDay() {
        return hourInDay;
    }

    public int getPlainTime() {
        return plainTime;
    }

    @Override
    public void run() {
        while (Process.isActive) {
            try {
                // Если нет текущей задачи, пытаемся взять новую
                if (completeTask == null) {
                    completeTask = Task.getTask();
                    if (completeTask != null) {
                        System.out.println("Работник с id " + id + ", " + name + ", принял задачу " + completeTask.getId());
                    }
                }

                // Если есть задача - работаем над ней
                if (completeTask != null) {
                    int remainingWork = completeTask.getDuration() - completeTask.getProgress();
                    int availableTime = workTime - hourInDay;

                    if (remainingWork > availableTime) {
                        // Не успеваем выполнить задачу за день
                        completeTask.setProgress(completeTask.getProgress() + availableTime);
                        hourInDay = workTime;
                    } else {
                        // Завершаем задачу
                        completeTask.setProgress(completeTask.getDuration());
                        hourInDay += remainingWork;
                        Process.completedTasks++;
                        amountTask++;
                        completeTask = null;
                    }
                }

                // Если рабочий день закончен или нет задач
                if (hourInDay >= workTime || Task.getTasks().isEmpty()) {
                    plainTime += 8 - hourInDay;
                    countWork += hourInDay;
                    hourInDay = 0;

                    Thread.sleep(500);

                    Process.synchronizer.await();
                }

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
