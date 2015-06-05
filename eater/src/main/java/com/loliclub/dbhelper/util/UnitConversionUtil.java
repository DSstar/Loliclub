package com.loliclub.dbhelper.util;

import java.math.BigDecimal;

/**
 * 单位转换工具类 KG-ST-LB-OZ， mmHg-kpa, cm-m-kg-ft-in
 */
public class UnitConversionUtil {

	/**
	 * kg
	 */
	public final static String UNIT_KG = "kg";

	/**
	 * st
	 */
	public final static String UNIT_ST = "st";

	/**
	 * lb
	 */
	public final static String UNIT_LB = "lb";

	/**
	 * oz
	 */
	public final static String UNIT_OZ = "oz";

	/**
	 * mmHg
	 */
	public final static String UNIT_MMHG = "mmHg";

	/**
	 * kpa
	 */
	public final static String UNIT_KPA = "kpa";

	/**
	 * cm
	 */
	public final static String UNIT_CM = "cm";

	/**
	 * m
	 */
	public final static String UNIT_M = "m";

	/**
	 * ft
	 */
	public final static String UNIT_FT = "ft";

	/**
	 * in
	 */
	public final static String UNIT_IN = "in";

	/**
	 * km
	 */
	public final static String UNIT_KM = "km";

	/**
	 * mile
	 */
	public final static String UNIT_MILE = "mile";

	public final static double KG2LB = 2.20462262d;
	public final static double ST2LB = 14d;
	public final static double LB2OZ = 16d;
	public final static double MMHG2KPA = 7.5006168d;
	public final static double M2FT = 3.2808399d;
	public final static double FT2IN = 12d;
	public final static double KM2MILE = 0.6213712d;

	/**
	 * 获取所有单位的字符串
	 * 
	 * @return
	 */
	public static String[] getAllUnit() {
		return new String[] { UNIT_KG, UNIT_ST, UNIT_LB, UNIT_OZ, UNIT_MMHG,
				UNIT_KPA, UNIT_CM, UNIT_M, UNIT_KM, UNIT_FT, UNIT_IN, UNIT_MILE };
	}

	/**
	 * 将KG转换成LB
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionKG2LB(double number, int accuracy) {
		number = number * KG2LB;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将LB转换成KG
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionLB2KG(double number, int accuracy) {
		number = number / KG2LB;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将STLB转换成KG
	 * 
	 * @param numbers
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionSTLB2KG(double[] numbers, int accuracy) {
		if (numbers.length != 2)
			return Double.NaN;
		double number = numbers[0] * ST2LB + numbers[1];
		number = number / KG2LB;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将LBOZ转换成KG
	 * 
	 * @param numbers
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionLBOZ2KG(double[] numbers, int accuracy) {
		if (numbers.length != 2)
			return Double.NaN;
		double number = numbers[0] + numbers[1] / LB2OZ;
		number = number / KG2LB;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将STLBOZ转换成KG
	 * 
	 * @param numbers
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionSTLBOZ2KG(double[] numbers, int accuracy) {
		if (numbers.length != 3)
			return Double.NaN;
		double number = numbers[0] * ST2LB + numbers[1] + numbers[2] / LB2OZ;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将KG转换成STLB
	 * 
	 * @param number
	 * @return
	 */
	public static double[] unitConversionKG2STLB(double number, int accuracy) {
		double[] stlb = new double[2];
		number *= KG2LB;
		stlb[0] = (int) (number / ST2LB);
		stlb[1] = saveAccuracy((number % ST2LB), accuracy);
		return stlb;
	}

	/**
	 * 将KG转换成LBOZ
	 * 
	 * @param number
	 * @return
	 */
	public static double[] unitConversionKG2LBOZ(double number) {
		double[] lboz = new double[2];
		number *= KG2LB;
		lboz[0] = (int) number;
		lboz[1] = (int) ((number - lboz[0]) * LB2OZ);
		return lboz;
	}

	/**
	 * 将KG转换成STLBOZ
	 * 
	 * @param number
	 * @return
	 */
	public static double[] unitConversionKG2STLBOZ(double number) {
		number *= KG2LB;
		int lb = (int) number;
		int st = (int) (number / ST2LB);
		int oz = (int) ((number - lb) * LB2OZ);
		lb = (int) (lb % ST2LB);
		return new double[] { st, lb, oz };
	}

	/**
	 * 将MMHG转换成KPA
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionMMHG2KPA(double number, int accuracy) {
		number /= MMHG2KPA;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将KPA转换成MMHG
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionKPA2MMHG(double number, int accuracy) {
		number *= MMHG2KPA;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将M转换成FT
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionCM2FT(double number, int accuracy) {
		number *= M2FT;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将FT转换成M
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionFT2CM(double number, int accuracy) {
		number /= M2FT;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将M转换成FTIN
	 * 
	 * @param number
	 * @return
	 */
	public static double[] unitConversionCM2FTIN(double number) {
		double[] ftin = new double[2];
		number *= M2FT;
		ftin[0] = (int) number;
		ftin[1] = (int) ((number - ftin[0]) * FT2IN);
		return ftin;
	}

	/**
	 * 将FTIN转换成M
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionFTIN2CM(double[] number, int accuracy) {
		if (number.length != 2)
			return Double.NaN;
		double ft = number[0] + (number[1] / FT2IN);
		return saveAccuracy(ft / M2FT, accuracy);
	}

	/**
	 * 将公里转换成英里
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionKM2MILE(double number, int accuracy) {
		number *= KM2MILE;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 将英里转换成公里
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，保留几位小数
	 * @return
	 */
	public static double unitConversionMILE2KM(double number, int accuracy) {
		number /= KM2MILE;
		return saveAccuracy(number, accuracy);
	}

	/**
	 * 使用和电子秤上相同的方法，将KG转换成LB,保留一位小数,精度0.2LB
	 * 
	 * @param number
	 * @return
	 */
	public static double scaleKG2LB(double number) {
		number = number * 10;
		number = number * 1.1023;
		number = Math.round(number);
		number /= 10;
		number *= 2;
		return number;
	}

	/**
	 * 使用和电子秤上相同的方法，将KG转换成STLB，保留一位小数,精度0.2LB
	 * 
	 * @param number
	 * @return
	 */
	public static double[] scaleKG2STLB(double number) {
		double[] stlb = new double[2];
		number = number * 10;
		number = number * 1.1023;
		number = Math.round(number);
		number /= 10;
		number *= 2;
		stlb[0] = (int) (number / ST2LB);
		stlb[1] = number % ST2LB;
		return stlb;
	}

	/**
	 * 对小数按精度四舍五入
	 * 
	 * @param number
	 * @param accuracy
	 *            精度，若该参数小于0，则返回该number
	 * @return
	 */
	public static double saveAccuracy(double number, int accuracy) {
		if (accuracy < 0) {
			return number;
		} else {
			BigDecimal bigDecimal = new BigDecimal(number);
			return bigDecimal.setScale(accuracy, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		}
	}

}
