import Controller.Controller;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println(
                "如果您想新增一个用户，请输入 u username\n" +
                        "如果您想新增一个任务，请输入 t\n" +
                        "如果想完成一个任务，请输入  doTask taskname username\n" +
                        "如果想查看某个用户的可做任务，请输入 fd username\n" +
                        "如果想查看某个用户的当前积分，请输入 account username\n" +
                        "如果想查看某个用户的流水，请输入 flow username\n" +
                        "如果您想创建商品，请输入 item itemname cost\n" +
                        "如果您想兑换商品，请输入 consume username itemname\n"
        );
        while(true){
            String instr = input.next();
            switch(instr){
                case "u":
                    Controller.addUser(input.next());
                    break;
                case "t":
                    Controller.addTask(input);
                    break;
                case "doTask":
                    Controller.doTask(input.next(), input.next());
                    break;
                case "fd":
                    Controller.findCanDoTask(input.next());
                    break;
                case "account":
                    Controller.checkAccount(input.next());
                    break;
                case "flow":
                    Controller.checkFlow(input.next());
                    break;
                case "item":
                    Controller.newItem(input.next(),input.nextInt());
                    break;
                case "consume":
                    Controller.consume(input.next(),input.next());
                    break;
            }
        }
    }
}
