package test;

import Controller.Controller;
import item.Item;
import org.junit.Assert;
import org.junit.Test;
import transaction.Flow;
import transaction.PerformTaskTransaction;
import user.TaskPerformer;
import userTask.DailyCountDownLifeCycleStrategy;
import userTask.OnceLifeCycleStrategy;
import userTask.TotalCountDownLifeCycleStrategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ControllerTest {

    /*
    * 添加新用户
    * */
    @Test
    public void addUser() {
        Controller.getTaskPerformers().clear();
        Controller.getItems().clear();
        Controller.getTaskArrayList().clear();
        String username = "u1";
        Controller.addUser(username);
        ArrayList<TaskPerformer> performers = Controller.getTaskPerformers();
        Assert.assertTrue(performers.size()==1 && performers.get(0).getName().equals(username));
    }

    /*
     * 对应用例1
     * 测试增加新一次性的task*/
    @Test
    public void addOnceTask() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Controller controller = new Controller();
        Method method = Controller.class.getDeclaredMethod("addOnceTask", String.class, int.class);
        method.setAccessible(true);
        String taskName = "t1";
        int point = 2;
        Controller.getTaskArrayList().clear();
        Assert.assertEquals(Controller.getTaskArrayList().size(), 0);
        method.invoke(controller, taskName, point);
        Assert.assertEquals(Controller.getTaskArrayList().size(), 1);
        Assert.assertEquals(Controller.getTaskArrayList().get(0).getTaskLifeCycleStrategy().getClass(), OnceLifeCycleStrategy.class);
    }

    /*
     * 对应用例1
     * 测试增加新的可重复性的task*/
    @Test
    public void addRepeat1Task() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Controller controller = new Controller();
        Method method = Controller.class.getDeclaredMethod("addRepeat1Task", String.class, int.class, int.class);
        method.setAccessible(true);
        String taskName = "t1";
        int point = 2;
        int count = 3;
        Controller.getTaskArrayList().clear();
        Assert.assertEquals(Controller.getTaskArrayList().size(), 0);
        method.invoke(controller, taskName, point, count);
        Assert.assertEquals(Controller.getTaskArrayList().size(), 1);
        Assert.assertEquals(Controller.getTaskArrayList().get(0).getTaskLifeCycleStrategy().getClass(), TotalCountDownLifeCycleStrategy.class);

    }

    /*
     * 对应用例1
     * 测试增加新的按天可重复性的task（每天零点刷新）*/
    @Test
    public void addRepeat2Task() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Controller controller = new Controller();
        Method method = Controller.class.getDeclaredMethod("addRepeat2Task", String.class, int.class, int.class);
        method.setAccessible(true);
        String taskName = "t1";
        int point = 2;
        int count = 3;
        Controller.getTaskArrayList().clear();
        Assert.assertEquals(Controller.getTaskArrayList().size(), 0);
        method.invoke(controller, taskName, point, count);
        Assert.assertEquals(Controller.getTaskArrayList().size(), 1);
        Assert.assertEquals(Controller.getTaskArrayList().get(0).getTaskLifeCycleStrategy().getClass(), DailyCountDownLifeCycleStrategy.class);
    }

    /*
     * 对应用例2
     * 测试查看可做任务
     * 测试做任务*/
    @Test
    public void doTask() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //添加新用户
        Controller.addUser("u1");
        Controller.getTaskArrayList().clear();
        //添加新的一次性Task
        Controller controller = new Controller();
        Method method = Controller.class.getDeclaredMethod("addOnceTask", String.class, int.class);
        method.setAccessible(true);
        String taskName = "t1";
        int point = 2;
        method.invoke(controller, taskName, point);
        //添加新的可重复的Task
        method = Controller.class.getDeclaredMethod("addRepeat1Task", String.class, int.class, int.class);
        method.setAccessible(true);
        taskName = "t2";
        point = 2;
        int count = 2;
        method.invoke(controller, taskName, point, count);
        //做任务前可做任务
        Assert.assertEquals(Controller.findCanDoTask("u1"), 2);
        Controller.doTask("t2", "u1");
        //做一次t2任务后的可做任务
        Assert.assertEquals(Controller.findCanDoTask("u1"), 2);
        Controller.doTask("t2", "u1");
        //做两次t2任务后的可做任务
        Assert.assertEquals(Controller.findCanDoTask("u1"), 1);
        Controller.doTask("t1", "u1");
        //做一次t1任务后的可做任务
        Assert.assertEquals(Controller.findCanDoTask("u1"), 0);

    }


    /**
     * 测试消费商品
     * 2. App用户可以使用他账户中积分完成兑换，获取相关的商品，比如一杯可乐。
     *  并可以查看该用户的流水线信息和账户余额
     */
    @Test
    public void consume() {
        Controller.getTaskPerformers().clear();
        Controller.getItems().clear();
        Controller.getTaskArrayList().clear();
        /*添加新用户信息*/
        String username = "u1";
        Controller.addUser(username);
        //判断初始账户为0
        Assert.assertTrue( Controller.getTaskPerformers().get(0).getAccount().getBalance()==0);
        //设置账户为10
        Controller.getTaskPerformers().get(0).getAccount().setBalance(10);
        Assert.assertTrue( Controller.getTaskPerformers().get(0).getAccount().getBalance()==10);
        String itemname = "i1";
        int cost = 10;
        //新增商品信息
        Controller.newItem(itemname,cost);
        //消费商品
        Controller.consume(username,itemname);
        Assert.assertTrue( Controller.getTaskPerformers().get(0).getAccount().getBalance()==0);
        //判断流水线信息
        List<Flow> flows =  Controller.getTaskPerformers().get(0).getAccount().getFlows();
        Assert.assertTrue(flows.size()==1 && flows.get(0).toString().equals("i1 : -10"));
    }

    /**
     * 运营商新增一件商品
     */
    @Test
    public void newItem() {
        Controller.getTaskPerformers().clear();
        Controller.getItems().clear();
        Controller.getTaskArrayList().clear();
        String itemname = "i1";
        int cost = 2;
        Controller.newItem(itemname,cost);
        ArrayList<Item> items = Controller.getItems();
        //查看商品信息
        Assert.assertTrue(items.size()==1 && items.get(0).getName().equals(itemname) && items.get(0).getPointCost()==cost);
    }

    /**
     * 2. App用户查看可做的任务和任务的状态
     * @throws Exception
     */
    @Test
    public void findTask() throws Exception{
        PerformTaskTransaction.getUserTaskArrayList().clear();
        Controller.getTaskPerformers().clear();
        Controller.getItems().clear();
        Controller.getTaskArrayList().clear();

        //添加用户信息
        Controller controller = new Controller();
        String username = "u1";
        Controller.addUser(username);

        //新增一个一次性任务
        Method method = Controller.class.getDeclaredMethod("addOnceTask", String.class, int.class);
        method.setAccessible(true);
        String taskname = "t1";
        int point =2;
        method.invoke(controller,taskname,point);

        //新增一个可做多次的任务
        Method method1 = Controller.class.getDeclaredMethod("addRepeat1Task", String.class, int.class,int.class);
        method1.setAccessible(true);
        String taskname2 = "t2";
        int point2 =2;
        int count = 3;
        method1.invoke(controller,taskname2,point2,count);

        //查看可做任务的信息
        Assert.assertTrue(Controller.findCanDoTask(username)==2);
        //做一个一次性任务
        Controller.doTask(taskname,username);
        //查看已完成任务的信息
        Assert.assertTrue(Controller.findCanNotDoTask(username)==1);
        //做一次多次性任务
        Controller.doTask(taskname2,username);
        //查看可做任务的信息
        Assert.assertTrue(Controller.findCanDoTask(username)==1);

    }
}