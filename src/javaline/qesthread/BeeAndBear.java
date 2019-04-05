package javaline.qesthread;

public class BeeAndBear {


    public static void main(String[] args) {
        Box box = new Box();
        for (int i = 0; i < 40; i++) {
            new Bee("Bee-" + i, box).start();
        }
        new Bear("XD1", box).run();
        new Bear("XD2", box).run();


    }
}

/*
 * 资源
 * */
class Box {
    /*
     * 蜂蜜最多20瓶
     * */
    private int MAX = 20;
    /*
     * 当前蜂蜜瓶数
     * */
    private int count;

    /*
     * 添加蜂蜜
     * */
    public synchronized int add() {
        while (count >= MAX) {
            try {
                //通知熊吃蜂蜜
                this.notify();
                //让出cup，失去对象的监控权，解锁
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ++count;

    }

    /*
     * 消耗蜂蜜
     * */
    public synchronized void remove() {

        while (count < MAX) {
            try {
                //通知熊等待
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //每次熊吃掉20瓶蜂蜜
        count = 0;
        //通知蜜蜂生产
        this.notify();

    }
}

/*
 * 熊作为消费之会一直消耗蜂蜜
 * */
class Bear extends Thread {

    private String name;
    private Box box;

    public Bear(String name, Box box) {
        this.name = name;
        this.box = box;
    }

    public Bear() {
        super();
    }

    public void run() {
        while (true) {
            box.remove();
            System.out.println(name + ": 吃掉了20瓶蜂蜜");
        }

    }
}

/*
 * 蜜蜂作为消费者
 * */
class Bee extends Thread {
    private String name;
    private Box box;

    public Bee(String name, Box box) {
        this.name = name;
        this.box = box;
    }

    public Bee() {
        super();
    }

    public void run() {
        while (true) {
            int count = box.add();
            System.out.println(name + ": 生产蜂蜜 ,当前蜂蜜量 :" + count);
        }
    }
}