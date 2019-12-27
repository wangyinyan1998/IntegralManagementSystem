package userTask;


import java.util.Date;

public class UserTaskAction {
    boolean done = false;
    Date actionTime;
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }


}
