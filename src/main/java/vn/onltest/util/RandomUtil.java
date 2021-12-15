package vn.onltest.util;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    public static String randomTestCode(List<String> listTestCode) {
        Random random = new Random();
        int indexTestCode = random.nextInt(listTestCode.size());
        return listTestCode.get(indexTestCode);
    }
}
