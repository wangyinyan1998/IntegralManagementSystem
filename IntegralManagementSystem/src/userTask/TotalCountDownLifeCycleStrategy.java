package userTask;

import java.util.List;

public class TotalCountDownLifeCycleStrategy extends TaskLifeCycleStrategy{
    int count;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    boolean taskFinished(List<UserTaskAction> useTaskActionList) {
        return useTaskActionList.size()==count;
    }
}
