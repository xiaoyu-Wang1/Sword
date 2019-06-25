package demo.json;

import com.alibaba.fastjson.JSON;

public class JsonTest {
    public static void main(String[] args) {
        Student student = new Student();
        student.setFirstName("xiaoyu");
        student.setLastName("xiaoyu");
        System.out.println(JSON.toJSONString(student));
        System.out.println(JSON.toJSONString(null));

    }
}

class Student {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
