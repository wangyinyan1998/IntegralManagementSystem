package transaction;

import user.TaskPerformer;
import userTask.Task;
import userTask.UserTask;
import userTask.UserTaskAction;

public abstract class Transaction {
    abstract boolean commit(Task task,TaskPerformer taskPerformer);

}
