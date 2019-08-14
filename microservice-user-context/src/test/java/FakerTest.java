import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * @Author zhangchao
 * @Date 2019/8/14 14:05
 * @Version v1.0
 */
public class FakerTest {

    public static void main(String[] args) {
        Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
        System.out.println(faker.funnyName().name());
        System.out.println(faker.funnyName().name());
        System.out.println(faker.name().name());
        System.out.println(faker.name().name());
        System.out.println(faker.name().name());
        System.out.println(faker.name().name());
        System.out.println(faker.name().name());
        System.out.println(faker.funnyName().name());
    }
}
