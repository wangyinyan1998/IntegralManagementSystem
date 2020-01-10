package Controller;

import item.Item;
import transaction.Account;
import transaction.ConsumerTransaction;
import transaction.Flow;
import transaction.PerformTaskTransaction;
import user.TaskPerformer;
import userTask.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    static ArrayList<Task> taskArrayList = new ArrayList<>();
    static ArrayList<TaskPerformer> taskPerformers = new ArrayList<>();
    static ArrayList<Item> items = new ArrayList<>();


    public static ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public static ArrayList<TaskPerformer> getTaskPerformers() {
        return taskPerformers;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static void addUser(String username) {

        Account account = new Account();
        taskPerformers.add(new TaskPerformer(username, account));
        System.out.println("用户 " + username + " 添加成功");

    }

    public static void addTask(Scanner input) {
        System.out.println("请输入要创建的task的类型，\n" +
                "如果是一次性task，请输入 once taskname point \n" +
                "如果是可无限重复Task，请输入 repeat1 taskname point\n" +
                "如果是每日可重复Task，请输入 repeat2 taskname point lifeCycle");
        String instr = input.next();
        String taskName;
        int point;
        int count;

        switch (instr) {
            case "once":
                taskName = input.next();
                point = input.nextInt();
                addOnceTask(taskName, point);
                System.out.println("一次性任务 " + taskName + " 添加成功");

                break;
            case "repeat1":
                taskName = input.next();
                point = input.nextInt();
                addRepeat1Task(taskName, point);
                System.out.println("无限重复任务 " + taskName + " 添加成功");

                break;
            case "repeat2":
                taskName = input.next();
                point = input.nextInt();
                count = input.nextInt();
                addRepeat2Task(taskName, point, count);
                System.out.println("按天可重复任务 " + taskName + " 添加成功");

                break;
        }

    }

    public static void doTask(String taskname, String username) {
        TaskPerformer taskPerformer = findTaskPerformer(username);
        Task task = findTask(taskname);

        try {
            PerformTaskTransaction performTaskTransaction = new PerformTaskTransaction();
            boolean success = performTaskTransaction.commit(task, taskPerformer);
            if (success) {
                System.out.println("做任务成功！");
            } else
                System.out.println("做任务失败！任务已经做过啦！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void consume(String username, String itemname) {
        TaskPerformer taskPerformer = findTaskPerformer(username);
        Item item = findItem(itemname);
        ConsumerTransaction transaction = new ConsumerTransaction();
        if (transaction.commit(item, taskPerformer)) {
            System.out.println("您的商品已兑换~");
        } else {
            System.out.println("您的商品兑换失败啦");
        }
    }

    public static void newItem(String itemname, int cost) {
        Item fdItem = findItem(itemname);
        if (fdItem == null) {
            Item item = new Item(itemname, itemname, cost);
            items.add(item);
        }
        System.out.println("您的商品新建成功啦");
    }

    public static void checkFlow(String username) {
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer == null) {
            System.out.println("您的用户信息找不到啦");
            return;
        }

        List<Flow> flows = taskPerformer.getAccount().getFlows();
        for (Flow flow : flows) {
            System.out.println(flow);
        }
        System.out.println("您的流水线已显示完全啦");
    }

    public static void checkAccount(String username) {
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer == null) {
            System.out.println("您的用户信息找不到啦");
            return;
        }
        System.out.println("account - " + taskPerformer.getAccount().getBalance());
        System.out.println("您的积分已显示啦");
    }

    public static int findCanDoTask(String username) {
        ArrayList<Task> canDoTaskList = new ArrayList<>();
        canDoTaskList.addAll(taskArrayList);
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer == null) {
            System.out.println("您的用户信息找不到啦");
            return 0;
        }
        ArrayList<UserTask> userTaskArrayList = PerformTaskTransaction.getUserTaskArrayList();

        for (UserTask userTask : userTaskArrayList) {
            if (userTask.getTaskPerformer().equals(taskPerformer) && !userTask.canDo()) {
                canDoTaskList.remove(userTask.getTask());
            }
        }
        if (canDoTaskList != null && canDoTaskList.size() != 0) {
            System.out.println("您的待完成任务有：");
            for (Task task : canDoTaskList) {
                System.out.println(task.getTaskDef().getName());
            }
        } else {
            System.out.println("没有待完成的任务啦，享受清闲时光吧！");
        }
        return canDoTaskList.size();
    }
    public static int findCanNotDoTask(String username) {
        ArrayList<Task> canNotDoTaskList = new ArrayList<>();
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer == null) {
            return 0;
        }
        ArrayList<UserTask> userTaskArrayList = PerformTaskTransaction.getUserTaskArrayList();

        for (UserTask userTask : userTaskArrayList) {
            if (userTask.getTaskPerformer().equals(taskPerformer) && !userTask.canDo()) {
                canNotDoTaskList.add(userTask.getTask());
            }
        }
        if (canNotDoTaskList != null && canNotDoTaskList.size() != 0) {
            System.out.println("您的已完成任务有：");
            for (Task task : canNotDoTaskList) {
                System.out.println(task.getTaskDef().getName());
            }
        } else {
            System.out.println("您没有已完成任务哦~");
        }
        return canNotDoTaskList.size();
    }

    private static TaskPerformer findTaskPerformer(String username) {
        for (TaskPerformer taskPerformer : taskPerformers) {
            if (taskPerformer.getName().equals(username))
                return taskPerformer;
        }
        return null;
    }

    private static Item findItem(String itemname) {
        for (Item item : items) {
            if (item.getName().equals(itemname))
                return item;
        }
        return null;
    }

    private static Task findTask(String taskname) {
        for (Task task : taskArrayList) {
            if (task.getTaskDef().getName().equals(taskname))
                return task;
        }
        return null;
    }

    private static void addOnceTask(String taskName, int point) {
        TaskDef taskDef;
        FixPointStrategy fixPointStrategy;
        taskDef = new TaskDef();
        taskDef.setName(taskName);

        fixPointStrategy = new FixPointStrategy();
        fixPointStrategy.setPoint(point);

        OnceLifeCycleStrategy onceLifeCycleStrategy = new OnceLifeCycleStrategy();
        Task onceTask = new Task();
        onceTask.setTaskLifeCycleStrategy(onceLifeCycleStrategy);
        onceTask.setTaskPointCalcStrategy(fixPointStrategy);
        onceTask.setTaskDef(taskDef);

        taskArrayList.add(onceTask);
    }

    private static void addRepeat1Task(String taskName, int point) {
        TaskDef taskDef;
        FixPointStrategy fixPointStrategy;
        taskDef = new TaskDef();
        taskDef.setName(taskName);

        fixPointStrategy = new FixPointStrategy();
        fixPointStrategy.setPoint(point);

        NotLimitedLifeCycleStrategy notLimitedLifeCycleStrategy = new NotLimitedLifeCycleStrategy();

        Task totalCountTask = new Task();
        totalCountTask.setTaskLifeCycleStrategy(notLimitedLifeCycleStrategy);
        totalCountTask.setTaskPointCalcStrategy(fixPointStrategy);
        totalCountTask.setTaskDef(taskDef);

        taskArrayList.add(totalCountTask);
    }

    private static void addRepeat2Task(String taskName, int point, int count) {
        TaskDef taskDef;
        FixPointStrategy fixPointStrategy;
        taskDef = new TaskDef();
        taskDef.setName(taskName);

        fixPointStrategy = new FixPointStrategy();
        fixPointStrategy.setPoint(point);

        DailyCountDownLifeCycleStrategy dailyCountDownLifeCycleStrategy = new DailyCountDownLifeCycleStrategy();
        dailyCountDownLifeCycleStrategy.setCount(count);

        Task dailyTask = new Task();
        dailyTask.setTaskLifeCycleStrategy(dailyCountDownLifeCycleStrategy);
        dailyTask.setTaskPointCalcStrategy(fixPointStrategy);
        dailyTask.setTaskDef(taskDef);

        taskArrayList.add(dailyTask);
    }

}
