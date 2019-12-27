package userTask;

import user.TaskPerformer;

import java.util.List;

public class UserTask {
    Task task;
    TaskPerformer taskPerformer;
    List<UserTaskAction> userTaskActionList;
    UserTaskStatus status;
    public TaskPerformer getTaskPerformer() {
        return taskPerformer;
    }

    public void setTaskPerformer(TaskPerformer taskPerformer) {
        this.taskPerformer = taskPerformer;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
