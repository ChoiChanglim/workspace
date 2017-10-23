import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import my.random.api.constant.YourLucky;


public class ValidTest {

    public static void main(String[] args) {
        List<Integer> lastnum = new ArrayList<Integer>();
        lastnum.add(1);
        lastnum.add(10);
        lastnum.add(20);
        lastnum.add(30);
        lastnum.add(40);
        lastnum.add(45);

        HashSet<Integer> nums = new HashSet<Integer>();
        nums.add(17);
        nums.add(22);
        nums.add(43);
        nums.add(12);
        nums.add(13);
        boolean aa = YourLucky.LastContain(2, nums, lastnum, 50);
        System.err.println(aa);
    }

}
