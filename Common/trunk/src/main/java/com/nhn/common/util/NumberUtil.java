package com.nhn.common.util;

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

}
