package transaction;

import user.TaskPerformer;

public abstract class Transaction {
    abstract void addFlow(TaskPerformer taskPerformer,int amount,String desc);

}
