package Controller;

import item.Item;
import transaction.Account;
import transaction.ConsumerTransaction;
import transaction.Flow;
import transaction.PerformTaskTransaction;
import user.TaskPerformer;
import userTask.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    static ArrayList<Task> taskArrayList=new ArrayList<>();
    static ArrayList<TaskPerformer> taskPerformers = new ArrayList<>();
    static ArrayList<Item> items = new ArrayList<>();
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println(
                "如果您想新增一个用户，请输入 u username\n" +
                        "如果您想新增一个任务，请输入 t\n" +
                        "如果想完成一个任务，请输入  doTask taskname username\n" +
                        "如果想查看某个用户的可做任务，请输入 fd username\n" +
                        "如果想查看某个用户的当前积分，请输入 account username\n" +
                        "如果想查看某个用户的流水，请输入 flow username\n" +
                        "如果您想创建商品，请输入 item itemname cost\n" +
                        "如果您想兑换商品，请输入 consume username itemname\n"
        );
        while(true){
            String instr = input.next();
            switch(instr){
                case "u":
                    addUser(input.next());
                    break;
                case "t":
                    addTask(input);
                    break;
                case "doTask":
                    doTask(input.next(), input.next());
                    break;
                case "fd":
                    findCanDoTask(input.next());
                    break;
                case "account":
                    checkAccount(input.next());
                    break;
                case "flow":
                    checkFlow(input.next());
                    break;
                case "item":
                    newItem(input.next(),input.nextInt());
                    break;
                case "consume":
                    consume(input.next(),input.next());
                    break;
            }
        }
    }
    public static void addUser(String username) {
        Account account = new Account();
        taskPerformers.add(new TaskPerformer(username, account));
    }

    public static void addTask(Scanner input) {
        System.out.println("请输入要创建的task的类型，如果是一次性task，请输入 once taskname point" +
                "如果是可重复Task，请输入 repeat1 taskname point lifeCycle" +
                "如果是每日可重复Task，请输入 repeat2 taskname point lifeCycle");
        String instr = input.next();
        String taskName;
        int point;
        int count;
        TaskDef taskDef;
        FixPointStrategy fixPointStrategy;

        switch (instr) {
            case "once":
                taskName = input.next();
                taskDef = new TaskDef();
                taskDef.setName(taskName);

                point = input.nextInt();
                fixPointStrategy = new FixPointStrategy();
                fixPointStrategy.setPoint(point);

                OnceLifeCycleStrategy onceLifeCycleStrategy = new OnceLifeCycleStrategy();
                Task onceTask = new Task();
                onceTask.setTaskLifeCycleStrategy(onceLifeCycleStrategy);
                onceTask.setTaskPointCalcStrategy(fixPointStrategy);
                onceTask.setTaskDef(taskDef);

                taskArrayList.add(onceTask);

                break;
            case "repeat1":
                taskName = input.next();
                taskDef = new TaskDef();
                taskDef.setName(taskName);

                point = input.nextInt();
                fixPointStrategy = new FixPointStrategy();
                fixPointStrategy.setPoint(point);

                count = input.nextInt();
                TotalCountDownLifeCycleStrategy totalCountDownLifeCycleStrategy = new TotalCountDownLifeCycleStrategy();
                totalCountDownLifeCycleStrategy.setCount(count);

                Task totalCountTask = new Task();
                totalCountTask.setTaskLifeCycleStrategy(totalCountDownLifeCycleStrategy);
                totalCountTask.setTaskPointCalcStrategy(fixPointStrategy);
                totalCountTask.setTaskDef(taskDef);

                taskArrayList.add(totalCountTask);

                break;
            case "repeat2":
                taskName = input.next();
                taskDef = new TaskDef();
                taskDef.setName(taskName);

                point = input.nextInt();
                fixPointStrategy = new FixPointStrategy();
                fixPointStrategy.setPoint(point);

                count = input.nextInt();
                DailyCountDownLifeCycleStrategy dailyCountDownLifeCycleStrategy = new DailyCountDownLifeCycleStrategy();
                dailyCountDownLifeCycleStrategy.setCount(count);

                Task dailyTask = new Task();
                dailyTask.setTaskLifeCycleStrategy(dailyCountDownLifeCycleStrategy);
                dailyTask.setTaskPointCalcStrategy(fixPointStrategy);
                dailyTask.setTaskDef(taskDef);

                taskArrayList.add(dailyTask);
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
    public static void consume(String username,String itemname){
        TaskPerformer taskPerformer=findTaskPerformer(username);
        Item item = findItem(itemname);
        ConsumerTransaction transaction = new ConsumerTransaction();
        if (transaction.commit(item,taskPerformer)) {
            System.out.println("您的商品已兑换~");
        }else {
            System.out.println("您的商品兑换失败啦");
        }
    }
    public static void newItem(String itemname,int cost){
        Item fdItem = findItem(itemname);
        if (fdItem==null) {
            Item item = new Item(itemname, itemname, cost);
            items.add(item);
        }
        System.out.println("您的商品新建成功啦");
    }
    public static void checkFlow(String username){
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer==null){
            System.out.println("您的用户信息找不到啦");
            return;
        }

        List<Flow> flows = taskPerformer.getAccount().getFlows();
        for (Flow flow:flows){
            System.out.println(flow);
        }
        System.out.println("您的流水线已显示完全啦");
    }
    public static void checkAccount(String username){
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer==null){
            System.out.println("您的用户信息找不到啦");
            return;
        }
        System.out.println(taskPerformer.getAccount().getBalance());
        System.out.println("您的积分已显示啦");
    }
    public static void findCanDoTask(String username){
        ArrayList<Task> canDoTaskList = new ArrayList<>();
        canDoTaskList.addAll(taskArrayList);
        TaskPerformer taskPerformer = findTaskPerformer(username);
        if (taskPerformer==null){
            System.out.println("您的用户信息找不到啦");
            return;
        }
        ArrayList<UserTask> userTaskArrayList = new ArrayList<>();
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("usertask.txt")));
            userTaskArrayList = (ArrayList<UserTask>)ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        for (UserTask userTask:userTaskArrayList){
            if (userTask.getTaskPerformer().equals(taskPerformer) && !userTask.canDo()) {
                canDoTaskList.remove(userTask.getTask());
            }
        }
        System.out.println("您的待完成任务有：");
        for (Task task:canDoTaskList){
            System.out.println(task.getTaskDef().getName());
        }
    }
    private static TaskPerformer findTaskPerformer(String username){
        for (TaskPerformer taskPerformer:taskPerformers){
            if (taskPerformer.getName().equals(username))
                return taskPerformer;
        }
        return null;
    }
    private static Item findItem(String itemname){
        for (Item item:items){
            if (item.getName().equals(itemname))
                return item;
        }
        return null;
    }
    private static Task findTask(String taskname){
        for (Task task:taskArrayList){
            if (task.getTaskDef().getName().equals(taskname))
                return task;
        }
        return null;
    }


}
