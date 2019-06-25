package demo;

class StaticCode{
    int age;
    // static 代码块
    static{
        System.out.print("static ");
    }
    //构造代码块
    {
        System.out.print("55 ");
    }
    // 构造函数
    StaticCode(int age){
        this.age=age;
        System.out.print(age+",");
    }
    void show(){
        // 局部代码块
        {
            int age=30;
        }
        System.out.print("show:"+age+",");
    }
}

class A {
    static {
        System.out.println("static block execute");
    }
}
public class Main{
    public static void main(String[] args) throws ClassNotFoundException {
//        StaticCode p1=new StaticCode(20);
//        p1.show();
//        new A();
//        Class.forName("demo.A");
        Main main = new Main();
        main.method();
    }

    void method() throws ClassNotFoundException {
        Class.forName("demo.A", false, this.getClass().getClassLoader());
    }
}