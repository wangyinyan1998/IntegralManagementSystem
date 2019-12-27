package userTask;

import java.util.List;

public abstract class TaskLifeCycleStrategy {
    int count;

    public abstract boolean taskFinished(List<UserTaskAction> useTaskActionList);
}
