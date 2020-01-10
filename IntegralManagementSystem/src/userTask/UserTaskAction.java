package userTask;


import java.util.Date;
/*
* 用户做的一次任务
* */
public class UserTaskAction {
    Date actionTime;
    public UserTaskAction(Date actionTime){
        this.actionTime = actionTime;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

}
