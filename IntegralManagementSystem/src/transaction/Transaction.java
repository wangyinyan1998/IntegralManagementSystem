package transaction;

import user.TaskPerformer;
import userTask.Task;
import userTask.UserTask;
import userTask.UserTaskAction;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Transaction {
    abstract void addFlow(TaskPerformer taskPerformer,int amount,String desc);
     void writeObject(String fileName, Object object) {
        try {
            File file = new File(fileName);
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(object);
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
