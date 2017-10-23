package com.nhn.common.util;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NumberUtil {

	/**
	 * 반올림
	 *
	 * NumberUtil.round(13.45, 1) => 13.5 [소수 둘째자리 반올림] NumberUtil.round(13.503,
	 * 0) => 14.0 [소수 첫째자리 반올림] NumberUtil.round(145.22, -1) => 150.0 [첫째자리 반올림]
	 *
	 * @param num
	 * @param roundIndex
	 * @return
	 */
	public static double round(double num, int roundIndex) {
		double p = Math.pow(10, roundIndex);
		num = num * p;
		double tmp = Math.round(num);
		return tmp / p;
	}

	/**
	 * 버림
	 *
	 * @param num
	 * @param roundIndex
	 * @return
	 */
	public static double floor(double num, int roundIndex) {
		double p = Math.pow(10, roundIndex);
		num = num * p;
		double tmp = Math.floor(num);
		return tmp / p;
	}

	/**
	 * 범위중 랜덤으로 선택
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }

	/**
     * 단일 확률 계산 소수점2자리 까지 지원
     * @param percent
     * @return
     */
    public static boolean isInProbability(double percent){
        DecimalFormat dformat = new DecimalFormat(".##");
        String tmp = dformat.format(percent);
        String[] dots = tmp.split("[.]");
        if(dots.length < 2){
            throw new RuntimeException();
        }
        String decimalPoint = dots[1];
        int scale = (int)(Math.pow(10, (decimalPoint.length())));

        //System.err.println("decimalPoint:"+decimalPoint+", scale:"+scale);
        double b = Double.valueOf(tmp);

        int range = (int)(b * scale);
        int max = 100 * scale;


        int selected = NumberUtil.randomRange(1, max);
        //System.err.println("max:"+max+", range:"+range+", selected:"+selected);
        if(selected <= range){
            return true;
        }
        return false;
    }


    /**
     * 확률 추출
     * @param prob_rates
     * @param decimalFormatPattern ex)소수점둘째 자리: .##
     * @return
     */
    public static String ProbabilityRate(HashMap prob_rates, String decimalFormatPattern){
        DecimalFormat dformat = new DecimalFormat(decimalFormatPattern);
        int scale = (int)(Math.pow(10, (decimalFormatPattern.replace(".", "").length())));
        int max = 100 * scale;
        int selected = NumberUtil.randomRange(1, max);
        //System.err.println("scale:"+scale+" / "+"selected:"+selected+" / max:"+max);
        //System.err.println("------------------------");
        String result = null;
        int scaled_prob_sum = 0;
        prob_rates = (HashMap) sortByValue(prob_rates);
        Iterator<String> iter = prob_rates.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            double prob = Double.valueOf(dformat.format(prob_rates.get(key)));
            int scaled_prob = (int)Math.round(prob * scale);
            scaled_prob_sum = scaled_prob_sum + scaled_prob;
            //System.err.println(key+" : "+scaled_prob+" : "+scaled_prob_sum);
            if(selected <= scaled_prob_sum){
                return key;
            }
            result = key;
        }

        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map ){
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>(){
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ){
                return ( o1.getValue() ).compareTo( o2.getValue() );
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

}
