package userTask;


import java.util.Date;

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
