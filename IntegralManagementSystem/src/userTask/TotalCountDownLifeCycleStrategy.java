package userTask;

import java.util.List;

public class TotalCountDownLifeCycleStrategy extends TaskLifeCycleStrategy{
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
