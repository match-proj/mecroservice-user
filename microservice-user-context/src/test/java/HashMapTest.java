import java.util.HashMap;

/**
 * @Author zhangchao
 * @Date 2019/8/21 15:12
 * @Version v1.0
 */
public class HashMapTest {

    public static void main(String[] args) {
        System.out.println(1<<30);
        HashMap hashMap = new HashMap(17);

        hashMap.put("1",1);
        hashMap.put("3",1);
        hashMap.put("2",1);
        hashMap.put("4",1);
        hashMap.put("5",1);

    }
}
