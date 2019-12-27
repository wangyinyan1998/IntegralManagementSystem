package userTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyCountDownLifeCycleStrategy extends TaskLifeCycleStrategy{
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    boolean taskFinished(List<UserTaskAction> useTaskActionList) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCurrFormat = dateFormat.format(currentDate);
        Date lastDate = useTaskActionList.get(useTaskActionList.size()-1).getActionTime();
        String lastDateFormat= dateFormat.format(lastDate);
        if (currentDate.before(lastDate) && !dateCurrFormat.equals(lastDateFormat))
            useTaskActionList.clear();
        return useTaskActionList.size()==count;
    }
}
