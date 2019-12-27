package transaction;

import java.util.ArrayList;
import java.util.List;

public class Account {
    List<Flow> flows=new ArrayList<>();
    int balance;

    public List<Flow> getFlows() {
        return flows;
    }

    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }
    public void  addFlow(Flow flow){
        flows.add(flow);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void addBalance(int amount){
        this.balance += amount;
    }
    public void subBalance(int amount){
        this.balance-=amount;
    }
}
