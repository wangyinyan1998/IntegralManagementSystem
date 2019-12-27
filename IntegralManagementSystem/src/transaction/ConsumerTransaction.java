package transaction;

import item.Item;
import user.TaskPerformer;


public class ConsumerTransaction extends Transaction {
    public boolean commit(Item item, TaskPerformer taskPerformer){
        int cost = item.getPointCost();
        if (taskPerformer.getAccount().getBalance()>cost) {
            addFlow(taskPerformer,cost , item.getName());
            return true;
        }
        return false;
    }
    @Override
    public void addFlow(TaskPerformer taskPerformer,int amount,String desc){
        Flow flow = new Flow(desc,(-1)*amount);
        taskPerformer.getAccount().addFlow(flow);
        taskPerformer.getAccount().subBalance(amount);
    }

}
