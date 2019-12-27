package userTask;

public class FixPointStrategy extends TaskPointCalcStrategy{
    int point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public int calcPoint() {
        return point;
    }
}
