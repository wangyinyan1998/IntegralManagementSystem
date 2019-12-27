package userTask;

import java.util.List;

public abstract class TaskLifeCycleStrategy {
    boolean shouldFinish;
    public abstract boolean taskFinished(List<UserTaskAction> useTaskActionList);
}
