import java.util.ArrayList;
import java.util.List;

import my.random.api.constant.YourLucky;

import org.json.JSONArray;


public class CreateTest {

    public static void main(String[] args) {
        int odd_per = 50;
        int even_per = 50;
        int continuity_per = 60;
        int lastnum_per = 60;
        int exceptLocation = 0;

        List<Integer> lastnum = new ArrayList<Integer>();
        lastnum.add(1);
        lastnum.add(10);
        lastnum.add(20);
        lastnum.add(30);
        lastnum.add(40);
        lastnum.add(45);

        Integer[] nums = YourLucky.getNums(odd_per, even_per, continuity_per, lastnum_per, lastnum, exceptLocation);
        System.err.println(new JSONArray(nums));

        /*HashSet<Integer> nums_set = new HashSet<Integer>();
        nums_set.add(11);
        nums_set.add(13);
        nums_set.add(15);
        nums_set.add(17);
        nums_set.add(19);
        nums_set.add(21);
        int aa = YourLucky2.ContinuityCount(nums_set, 3);
        System.err.println(aa);*/


    }

}
