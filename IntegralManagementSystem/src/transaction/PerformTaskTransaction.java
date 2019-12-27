package transaction;

import user.TaskPerformer;
import userTask.Task;
import userTask.UserTask;
import userTask.UserTaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerformTaskTransaction extends Transaction {
    boolean preCommit(Task task,TaskPerformer taskPerformer){
        //先查找txt中是否存在该用户对应的usertask，如果有，读出来，如果没有，新建，再写进去。
        ObjectInputStream reader = null;
        try {
            File filename = new File("usertask.txt");
            reader = new ObjectInputStream(new FileInputStream(filename));
            ArrayList<UserTask> userTaskArrayList = (ArrayList<UserTask>) reader.readObject();
            for (UserTask userTask : userTaskArrayList) {
                if (userTask.getTask().equals(task) && userTask.getTaskPerformer().equals(taskPerformer)) {
                    List<UserTaskAction> userTaskActions = userTask.getUserTaskActionList();
                    Task tempTask = userTask.getTask();
                    //如果记录的usertask没有finish，则新加入一个action，然后写回
                    if (!tempTask.getTaskLifeCycleStrategy().taskFinished(userTaskActions)) {
                        UserTaskAction newAction = new UserTaskAction(new Date());
                        userTaskActions.add(newAction);
                        userTask.setUserTaskActionList(userTaskActions);
                        //如何把更改后的写回
                        reader.close();
                        writeObject("usertask.txt", userTaskArrayList);
                        return true;
                    } else {
                        reader.close();
                        return false;
                    }
                }
            }
            //新建action，跟usertask
            List<UserTaskAction> userTaskActionsList = new ArrayList<>();
            UserTaskAction userTaskAction = new UserTaskAction(new Date());
            userTaskActionsList.add(userTaskAction);
            UserTask userTask = new UserTask(taskPerformer, task, userTaskActionsList);
            userTaskArrayList.add(userTask);
            reader.close();
            writeObject("usertask.txt", userTaskArrayList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean commit(Task task,TaskPerformer taskPerformer){
        boolean iscommit = preCommit(task,taskPerformer);
        if (iscommit)
            addFlow(taskPerformer,task.getTaskPointCalcStrategy().calcPoint(),task.getTaskDef().getName());
        return iscommit;
    }
    @Override
    public void addFlow(TaskPerformer taskPerformer,int amount,String desc){
        Flow flow = new Flow(desc,amount);
        taskPerformer.getAccount().addFlow(flow);
        taskPerformer.getAccount().addBalance(amount);
    }
}
