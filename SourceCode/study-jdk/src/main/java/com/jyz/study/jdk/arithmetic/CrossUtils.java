package com.jyz.study.jdk.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *	@author zhaoyong.zhang
 *	create time 2014-3-10
 */
public class CrossUtils {

	public static List<List<String>> cross(List<List<String>> crossArgs) {
		// 计算出笛卡尔积行数
		int rows = crossArgs.size() > 0 ? 1 : 0;

		for (List<String> data : crossArgs) {
			rows *= data.size();
		}

		// 笛卡尔积索引记录
		int[] record = new int[crossArgs.size()];

		List<List<String>> results = new ArrayList<List<String>>();

		// 产生笛卡尔积
		for (int i = 0; i < rows; i++) {
			List<String> row = new ArrayList<String>();

			// 生成笛卡尔积的每组数据
			for (int index = 0; index < record.length; index++) {
				row.add(crossArgs.get(index).get(record[index]));
			}

			results.add(row);
			crossRecord(crossArgs, record, crossArgs.size() - 1);
		}

		return results;
	}

	/**
	 * 产生笛卡尔积当前行索引记录.
	 * @param sourceArgs 要产生笛卡尔积的源数据
	 * @param record 每行笛卡尔积的索引组合
	 * @param level 索引组合的当前计算层级
	 */
	private static void crossRecord(List<List<String>> sourceArgs,
			int[] record, int level) {
		record[level] = record[level] + 1;

		if (record[level] >= sourceArgs.get(level).size() && level > 0) {
			record[level] = 0;
			crossRecord(sourceArgs, record, level - 1);
		}
	}
}
