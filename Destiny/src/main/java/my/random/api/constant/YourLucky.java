package my.random.api.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import my.random.api.exception.CustomException;
import my.random.api.util.NumberUtil;

import org.json.JSONArray;

public class YourLucky {
    public static enum BasicSetting{
        Odd_per(40),
        Even_per(60),
        Continuity_per(50),
        Lastnum_per(40),
        ExceptLocation(0); //1, 10, 20, 30, 40, 0=랜덤

        private int percent;
        private BasicSetting(int percent){
            this.percent = percent;
        }
        public int getPercent(){
            return percent;
        }

    }

    public static synchronized Integer[] getNums(int odd_per, int even_per, int continuity_per, int lastnum_per, List<Integer> lastnum, int exceptLocation){
        if(exceptLocation == 0){
            int r = randomRange(1, 5);
            if(r == 1){
                exceptLocation = 1;
            }else if(r == 2){
                exceptLocation = 10;
            }else if(r == 3){
                exceptLocation = 20;
            }else if(r == 4){
                exceptLocation = 30;
            }else if(r == 5){
                exceptLocation = 40;
            }
            //System.err.println("r:"+r);
        }

        HashSet<Integer> nums = new HashSet<Integer>();
        while (nums.size() < 6) {
            int num = randomRange(1, 45);
            if(OddOrEven(num, odd_per, even_per) == false) continue;
            if(Continuity(num, nums, continuity_per) == false) continue;
            if(LastContain(num, nums, lastnum, lastnum_per) == false) continue;
            if(ExceptLocation(num, nums, exceptLocation) == false) continue;

            //System.err.println(nums+", "+lastCount);
            nums.add(num);
        }
        
        //System.err.println(nums);
        Integer[] arr = nums.toArray(new Integer[nums.size()]);
        Arrays.sort(arr);
        return arr;
    }
    public static List<Integer> getLocationRange(int exceptLocation){
        List<Integer> lst = new ArrayList<Integer>();
        if(exceptLocation == 1){
            lst.add(1);
            lst.add(2);
            lst.add(3);
            lst.add(4);
            lst.add(5);
            lst.add(6);
            lst.add(7);
            lst.add(8);
            lst.add(9);
        }else if(exceptLocation == 10){
            lst.add(10);
            lst.add(11);
            lst.add(12);
            lst.add(13);
            lst.add(14);
            lst.add(15);
            lst.add(16);
            lst.add(17);
            lst.add(18);
            lst.add(19);
        }else if(exceptLocation == 20){
            lst.add(20);
            lst.add(21);
            lst.add(22);
            lst.add(23);
            lst.add(24);
            lst.add(25);
            lst.add(26);
            lst.add(27);
            lst.add(28);
            lst.add(29);
        }else if(exceptLocation == 30){
            lst.add(30);
            lst.add(31);
            lst.add(32);
            lst.add(33);
            lst.add(34);
            lst.add(35);
            lst.add(36);
            lst.add(37);
            lst.add(38);
            lst.add(39);
        }else if(exceptLocation == 40){
            lst.add(40);
            lst.add(41);
            lst.add(42);
            lst.add(43);
            lst.add(44);
            lst.add(45);
        }
        return lst;
    }
    public static boolean ExceptLocation(int num, HashSet<Integer> nums, int exceptLocation){
        List<Integer> dumy = new ArrayList<Integer>(nums);
        dumy.add(num);
        Iterator<Integer> iter = dumy.iterator();
        while(iter.hasNext()){
            int tmpno = iter.next();
            List<Integer> lst = getLocationRange(exceptLocation);
            if(lst.contains(tmpno)){
                return false;
            }
        }
        return true;
    }
    /**
     * 지난주 
     * @param nums
     * @param num
     * @return
     */
    public static boolean isLastContains(HashSet<Integer> nums, List<Integer> lastnum, int num){
        boolean flag = false;
        List<Integer> dumy = new ArrayList<Integer>(nums);
        dumy.add(num);

        Iterator<Integer> iter = dumy.iterator();
        while(iter.hasNext()){
            int tmpno = iter.next();

            for(int i=0;i<lastnum.size();i++){
                if(tmpno == (lastnum.get(i)) || tmpno == (lastnum.get(i))){
                    flag = true;
                }
            }
        }
        return flag;
    }
    /**
     * 지난주 번호 2개이상
     * @param nums
     * @param lastnum
     * @param num
     * @return
     */
    public static boolean isLastContainsTwiceMore(HashSet<Integer> nums, List<Integer> lastnum, int num){
        boolean flag = false;
        List<Integer> dumy = new ArrayList<Integer>(nums);
        dumy.add(num);

        Iterator<Integer> iter = dumy.iterator();
        int count = 0;
        while(iter.hasNext()){
            int tmpno = iter.next();

            for(int i=0;i<lastnum.size();i++){
                if(tmpno == (lastnum.get(i)) || tmpno == (lastnum.get(i))){
                	count++;
                }
            }
        }
        if(count > 1) {
        	flag = true;
        }
        return flag;
    }

    /**
     * 지난주
     * @param num
     * @param nums
     * @param per
     * @return
     */
    public static boolean LastContain(int num, HashSet<Integer> nums, List<Integer> lastnum, int per){
        if(per < 0 || per > 100){
            throw CustomException.INVELID_PERCENT_RANGE;
        }
        if(isLastContainsTwiceMore(nums, lastnum, num)) {
        	return false;
        }

        if(nums.size() < 5){
            return true;
        }

        boolean isLastContains = isLastContains(nums, lastnum, num);
        boolean isSuccess = NumberUtil.isInProbability(per);
        //System.err.println("isSuccessLastnums:"+isSuccess+", isLastContains:"+isLastContains(nums, lastnum, num)+", num: "+num+", nums:"+new JSONArray(nums));
        if(isSuccess == true){  //지난주 번호가 들어가야되는 확률이 당첨
            if(isLastContains == true){
                return true;
            }else{
                return false;
            }
        }else{  //지난주 번호가 아니어도 될때
            return true;
        }
    }
    /**
     * 연속적 횟수
     * @param nums
     * @param num
     * @return
     */
    public static boolean isContinuityNum(HashSet<Integer>nums, int num){
        boolean flag = false;
        List<Integer> dumy = new ArrayList<Integer>(nums);
        dumy.add(num);

        Iterator<Integer> iter = dumy.iterator();
        while(iter.hasNext()){
            int tmpno = iter.next();

            for(int i=0;i<dumy.size();i++){
                if(tmpno == (dumy.get(i)-1) || tmpno == (dumy.get(i)+1)){
                    flag = true;
                }
            }
        }
        return flag;
    }
    /**
     * 연속번호가 두번이상인지
     * @param nums
     * @param num
     * @return
     */
    public static boolean isContinuityNumTwiceMore(HashSet<Integer>nums, int num){
        boolean flag = false;
        List<Integer> dumy = new ArrayList<Integer>(nums);
        dumy.add(num);

        Iterator<Integer> iter = dumy.iterator();
        int count = 0;
        while(iter.hasNext()){
            int tmpno = iter.next();

            for(int i=0;i<dumy.size();i++){
                if(tmpno == (dumy.get(i)-1) || tmpno == (dumy.get(i)+1)){
                	count++;
                }
            }
        }
        if(count > 2) {
        	flag = true;
        }
        return flag;
    }

    /**
     * 연속적
     * @param num
     * @param nums
     * @param per
     * @return
     */
    public static boolean Continuity(int num, HashSet<Integer> nums, int per){
        if(per < 0 || per > 100){
            throw CustomException.INVELID_PERCENT_RANGE;
        }
        boolean isContinuityNumTwiceMore = isContinuityNumTwiceMore(nums, num);
        if(isContinuityNumTwiceMore) {
        	return false;
        }

        if(nums.size() < 5){
            return true;
        }

        boolean isContinuityNum = isContinuityNum(nums, num);
        boolean isSuccess = NumberUtil.isInProbability(per);
        //System.err.println("isSuccessContinuity:"+isSuccess+", isContinuityNum:"+isContinuityNum(nums, num)+", num: "+num+", nums:"+new JSONArray(nums));
        if(isSuccess == true){  //연속숫자의 확률이 당첨
            if(isContinuityNum == true){
                return true;
            }else{
                return false;
            }
        }else{  //연속숫자가 아니어도 될때
            return true;
        }
    }

    /**
     * 홀짝
     * @param num
     * @param odd_per
     * @param even_per
     * @return
     */
    public static boolean OddOrEven(int num, int odd_per, int even_per){
        if(100 != (odd_per + even_per)){
            throw CustomException.INVELID_SUM_ODDEVEN;
        }
        int tmp = randomRange(1, odd_per + even_per);
        String flag = "no";
        if(tmp <= odd_per){
            flag = "odd";
        }else{
            flag = "even";
        }
        if(flag.equals("odd")){
            if(num % 2 != 0 ) return true;
        }
        if(flag.equals("even")){
            if(num % 2 == 0 ) return true;
        }
        //System.err.println("OddOrEven");
        return false;
    }

    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }
}
