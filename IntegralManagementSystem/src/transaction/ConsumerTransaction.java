package transaction;

import user.TaskPerformer;
import userTask.Task;
import userTask.UserTask;
import userTask.UserTaskAction;

import java.io.*;
import java.util.Date;
import java.util.List;

public class ConsumerTransaction extends Transaction {
    @Override
    boolean commit(Task task, TaskPerformer taskPerformer) {
        //先查找txt中是否存在该用户对应的usertask，如果有，读出来，如果没有，新建，再写进去。
        ObjectInputStream reader = null;
        try {
            File filename = new File("usertask.txt");
            reader = new ObjectInputStream(new FileInputStream(filename));
            UserTask userTask;
            while ((userTask = (UserTask) reader.readObject()) != null) {
                if (userTask.getTask().equals(task) && userTask.getTaskPerformer().equals(taskPerformer)) {
                    List<UserTaskAction> userTaskActions = userTask.getUserTaskActionList();
                    Task tempTask = userTask.getTask();
                    //如果记录的usertask没有finish，则新加入一个action，然后写回
                    if (!tempTask.getTaskLifeCycleStrategy().taskFinished(userTaskActions)) {
                        UserTaskAction newAction = new UserTaskAction(new Date());
                        userTaskActions.add(newAction);
                        userTask.setUserTaskActionList(userTaskActions);
                        //如何把更改后的写回

                        return true;
                    } else {
                        return false;
                    }
                }
            }
            //新建action，跟usertask

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
