package userTask;

import java.util.List;

public class OnceLifeCycleStrategy extends TaskLifeCycleStrategy {

    @Override
    public boolean taskFinished(List<UserTaskAction> useTaskActionList) {
        return useTaskActionList.size()==1;
    }
}
