package item;

public class Item {
    String name;
    String desc;
    int pointCost;

    public Item(String name,String desc,int pointCost){
        this.name = name;
        this.desc =desc;
        this.pointCost = pointCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPointCost() {
        return pointCost;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }
}
