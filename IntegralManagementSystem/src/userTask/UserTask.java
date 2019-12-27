package userTask;

import org.omg.PortableInterceptor.ACTIVE;
import user.TaskPerformer;

import java.util.List;

public class UserTask {
    Task task;
    TaskPerformer taskPerformer;
    List<UserTaskAction> userTaskActionList;
    UserTaskStatus status=UserTaskStatus.ACTIVE;
    public UserTask(TaskPerformer taskPerformer,Task task,List<UserTaskAction> userTaskActionList){
        this.task = task;
        this.userTaskActionList=userTaskActionList;
        this.taskPerformer = taskPerformer;
    }
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
