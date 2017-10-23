package com.nhn.common.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Collection에 관련된 유틸리티
 * 
 */
public final class CollectionUtils {
	/**
	 * 정렬된 리스트를 생성하여 반환한다.
	 * <p>
	 * ※ 입력된 원본리스트의 순서는 변경되지 않으므로 원본 리스트를 유지하고자 할때 사용한다. 참고 :
	 * <code>ArrayList</code>를 생성하여 반환한다.
	 * 
	 * @param list
	 *            정렬할 리스트
	 * @param c
	 *            비교
	 * @return 정렬된 리스트
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> sort(List<T> list, Comparator<? super T> c) {
		T[] orderedValues = (T[]) list.toArray();
		Arrays.sort(orderedValues, c);
		return Arrays.asList(orderedValues);
	}

	/**
	 * 입력된 로(raw)타입의 리스트를 반환받을 타입의 리스트로 캐스팅하여 반환한다.
	 * <p>
	 * ※ 단, 타입변환에 따른 안정성을 제공하지는 않으며 단지 레거시 시스템연동과 같이 해당 메소드의 반환 타입을 변경할 수 없을 경우
	 * 경고메시지를 회피하기 위한 수단으로만 사용한다. 이 메소드는 불필요한 경고성메시지를 제거하여 실제 발생되어야할 경고성메시지를 쉽게
	 * 식별할 목적으로만 사용하며 해당 메소드가 변경되면 이 메소드의 사용도 고려해야만 한다.
	 * 
	 * @param list
	 *            로(raw)타입 리스트
	 * @return 반환받을 타입의 리스트
	 */
	public static <T> List<T> castList(List<T> list) {
		return (List<T>) list;
	}

	/**
	 * 입력된 로(raw)타입의 맵을 반환받을 타입의 맵으로 캐스팅하여 반환한다.
	 * <p>
	 * ※ 단, 타입변환에 따른 안정성을 제공하지는 않으며 단지 레거시 시스템연동과 같이 해당 메소드의 반환 타입을 변경할 수 없을 경우
	 * 경고메시지를 회피하기 위한 수단으로만 사용한다. 이 메소드는 불필요한 경고성메시지를 제거하여 실제 발생되어야할 경고성메시지를 쉽게
	 * 식별할 목적으로만 사용하며 해당 메소드가 변경되면 이 메소드의 사용도 고려해야만 한다.
	 * 
	 * @param map
	 *            로(raw)타입 맵
	 * @return 반환받을 타입의 맵
	 */
	public static <K, V> Map<K, V> castMap(Map<K, V> map) {
		return (Map<K, V>) map;
	}

	/**
	 * 원배열로부터 채워야할 갯수만큼 배열을 생성하고 시작인덱스에 해당하는 데이터로 채운뒤 종료인덱스까지 설정된 배열을 반환한다.
	 * <p>
	 * 반환되는 배열의 총크기는 채워야할 갯수(dummyCount)만큼이다.
	 * 
	 * @param array
	 *            원배열. Not Null을 보장해야만 한다.
	 * @param startIndex
	 *            시작인덱스
	 * @param endIndex
	 *            종료인덱스
	 * @param dummyCount
	 *            채워야할 갯수
	 * @return 시작인덱스 ~ 종료인덱스까지 데이터가 채워진후 나머지 인덱스까지 시작인덱스 데이터로 채워진 배열
	 * @throws NullPointerException
	 *             원배열이 널이 입력된 경우 발생
	 * @throws ArrayIndexOutOfBoundsException
	 *             시작인덱스, 종료인덱스가 원배열의 범위내에 존재하지 않을 경우 발생
	 */
	public static String[] fill(String[] array, int startIndex, int endIndex, int dummyCount) {
		String[] filledArray = new String[dummyCount];
		Arrays.fill(filledArray, array[startIndex]);
		for (int index = startIndex, filledIndex = 0; index < endIndex && filledIndex < dummyCount; index++, filledIndex++) {
			filledArray[filledIndex] = array[index];
		}

		return filledArray;
	}

	/**
	 * 원리스트로부터 채워야할 갯수만큼 배열을 생성하고 시작인덱스에 해당하는 데이터로 채운뒤 종료인덱스까지 설정된 배열을 반환한다.
	 * <p>
	 * 반환되는 배열의 총크기는 채워야할 갯수(dummyCount)만큼이다.
	 * 
	 * @param array
	 *            원리스트. Not Null을 보장해야만 한다.
	 * @param startIndex
	 *            시작인덱스
	 * @param endIndex
	 *            종료인덱스
	 * @param dummyCount
	 *            채워야할 갯수
	 * @return 시작인덱스 ~ 종료인덱스까지 데이터가 채워진후 나머지 인덱스까지 시작인덱스 데이터로 채워진 배열
	 * @throws NullPointerException
	 *             원리스트가 널이 입력된 경우 발생
	 * @throws IndexOutOfBoundsException
	 *             시작인덱스, 종료인덱스가 원리스트의 범위내에 존재하지 않을 경우 발생
	 */
	public static String[] fill(List<String> list, int startIndex, int endIndex, int dummyCount) {
		String[] filledArray = new String[dummyCount];
		Arrays.fill(filledArray, list.get(startIndex));
		for (int index = startIndex, filledIndex = 0; index < endIndex && filledIndex < dummyCount; index++, filledIndex++) {
			filledArray[filledIndex] = list.get(index);
		}

		return filledArray;
	}
}
