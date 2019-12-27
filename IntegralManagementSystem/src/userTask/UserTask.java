package userTask;

import user.TaskPerformer;

import java.util.List;

public class UserTask {
    TaskDef taskDef;
    TaskPerformer taskPerformer;
    TaskLifeCycleStrategy taskLifeCycleStrategy;
    TaskPointCalcStrategy taskPointCalcStrategy;
    List<UserTaskAction> userTaskActionList;
    UserTaskStatus status;

    public TaskDef getTaskDef() {
        return taskDef;
    }

    public void setTaskDef(TaskDef taskDef) {
        this.taskDef = taskDef;
    }

    public TaskPerformer getTaskPerformer() {
        return taskPerformer;
    }

    public void setTaskPerformer(TaskPerformer taskPerformer) {
        this.taskPerformer = taskPerformer;
    }

    public TaskLifeCycleStrategy getTaskLifeCycleStrategy() {
        return taskLifeCycleStrategy;
    }

    public void setTaskLifeCycleStrategy(TaskLifeCycleStrategy taskLifeCycleStrategy) {
        this.taskLifeCycleStrategy = taskLifeCycleStrategy;
    }

    public TaskPointCalcStrategy getTaskPointCalcStrategy() {
        return taskPointCalcStrategy;
    }

    public void setTaskPointCalcStrategy(TaskPointCalcStrategy taskPointCalcStrategy) {
        this.taskPointCalcStrategy = taskPointCalcStrategy;
    }

    public List<UserTaskAction> getUserTaskActionList() {
        return userTaskActionList;
    }

    public void setUserTaskActionList(List<UserTaskAction> userTaskActionList) {
        this.userTaskActionList = userTaskActionList;
    }

    public UserTaskStatus getStatus() {
        return status;
    }

    public void setStatus(UserTaskStatus status) {
        this.status = status;
    }
}
