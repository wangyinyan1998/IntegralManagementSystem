package userTask;

import java.util.List;

public class NotLimitedLifeCycleStrategy extends TaskLifeCycleStrategy{
    public NotLimitedLifeCycleStrategy(){
        count = Integer.MAX_VALUE;
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean taskFinished(List<UserTaskAction> useTaskActionList) {
        return useTaskActionList.size()==count;
    }
}
