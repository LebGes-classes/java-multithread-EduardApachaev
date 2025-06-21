package process;

import data.saveExcel;

public class afterDay implements Runnable{
    @Override
    public void run() {
        if (Process.completedTasks < Process.totalTasks) {
            System.out.println("Прошел день. Выполнено задач: " + Process.completedTasks + "/" + Process.totalTasks);
        } else {
            Process.isActive = false;
            saveExcel.generateStatistics("src/main/resources/Statistics.xlsx");
            System.out.println("Все задачи выполнены");
        }
    }
}
