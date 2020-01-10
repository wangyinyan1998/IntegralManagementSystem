package transaction;

public class Flow {
    int amount;
    String desc="";

    public Flow(String desc,int amount){
        this.desc = desc;
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc+" - "+amount;
    }
}
