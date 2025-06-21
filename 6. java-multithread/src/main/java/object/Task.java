package object;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Task {
    private static ArrayList<Task> tasks;
    private final int id;
    private final int duration;
    private int progress;

    public Task(int id, int duration){
        this.id = id;
        this.duration = duration;
        progress = 0;
    }

    public static Task getTask(){
        synchronized (tasks){
            return tasks.isEmpty() ? null : tasks.removeFirst();
        }
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getProgress() {
        return progress;
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public static void setTasks(ArrayList<Task> tasks) {
        Task.tasks = tasks;
    }
}
