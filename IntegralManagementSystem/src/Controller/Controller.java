package Controller;

import item.Item;
import user.TaskPerformer;
import userTask.Task;

import java.util.ArrayList;
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
                    break;
                case "t":
                    break;
                case "doTask":
                    break;
                case "fd":
                    break;
                case "account":
                    break;
                case "flow":
                    break;
                case "item":
                    break;
                case "consume":
                    break;
            }
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
//    public static void consume(String username,String itemname){
//        TaskPerformer taskPerformer;
//        for (TaskPerformer tempPerformer:taskPerformers){
//            if (taskPerformer)
//        }
//    }
}
