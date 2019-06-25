package demo.changeprivate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SubApple extends Apple {

    public static void main(String[] args) {
        List<Apple> apples = null;
        apples.stream().count();
    }

    public void test() {
        Field[] fields = super.getClass().getDeclaredFields();
        Arrays.toString(fields);
        isAppleTrue();
    }
}
