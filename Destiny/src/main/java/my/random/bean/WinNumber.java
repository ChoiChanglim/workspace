package my.random.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WinNumber {
    int gno = 0;
    Date gdate = new Date();
    List<Integer> nums = new ArrayList<Integer>();
    int bonus = 0;

    String firstCount;
    String firstAmount;

    String secondCount;
    String secondAmount;

    String thirdCount;
    String thirdAmount;

    public int getGno() {
        return gno;
    }
    public void setGno(int gno) {
        this.gno = gno;
    }
    public Date getGdate() {
        return gdate;
    }
    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
    public List<Integer> getNums() {
        return nums;
    }
    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }
    public int getBonus() {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public String getFirstCount() {
        return firstCount;
    }
    public void setFirstCount(String firstCount) {
        this.firstCount = firstCount;
    }
    public String getFirstAmount() {
        return firstAmount;
    }
    public void setFirstAmount(String firstAmount) {
        this.firstAmount = firstAmount;
    }
    public String getSecondCount() {
        return secondCount;
    }
    public void setSecondCount(String secondCount) {
        this.secondCount = secondCount;
    }
    public String getSecondAmount() {
        return secondAmount;
    }
    public void setSecondAmount(String secondAmount) {
        this.secondAmount = secondAmount;
    }
    public String getThirdCount() {
        return thirdCount;
    }
    public void setThirdCount(String thirdCount) {
        this.thirdCount = thirdCount;
    }
    public String getThirdAmount() {
        return thirdAmount;
    }
    public void setThirdAmount(String thirdAmount) {
        this.thirdAmount = thirdAmount;
    }
}
