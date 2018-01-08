package my.random.service;

import java.util.List;

import my.random.bean.CustomSetting;
import my.random.bean.DestinyInfo;
import my.random.bean.Luckylog;

public interface DestinyService {
    public DestinyInfo getLastDestinyInfo();
    public DestinyInfo getDestinyInfoByGno(int gno);
    public int insert(DestinyInfo destinyInfo);
    public void insertRange(int startGno, int endGno);
    public List<Integer[]> create(String ukey, int count);
    public List<Integer[]> create(String ukey, int count, CustomSetting setting);
    public DestinyInfo getLastinfo();
	public List<Luckylog> getMyList(String ukey, int gno);
	public List<List<Integer>> getMyLuckyList(String ukey);
	public void updateGrade(int gno);
}
