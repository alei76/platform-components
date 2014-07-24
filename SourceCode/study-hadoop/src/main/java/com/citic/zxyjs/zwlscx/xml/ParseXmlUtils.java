package com.citic.zxyjs.zwlscx.xml;

import java.util.ArrayList;
import java.util.List;

import com.citic.zxyjs.zwlscx.bean.Conf;
import com.citic.zxyjs.zwlscx.bean.Field;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.bean.TaskType;

/**
 * 解析xml工具类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class ParseXmlUtils {

    public static Conf parseXml() {
	Conf conf = new Conf();
	//file1
	File file1 = new File();
	Field field11 = new Field();Field field12 = new Field();Field field13 = new Field();
	field11.setId("1");field11.setName("1");
	field12.setId("2");field12.setName("2");
	field13.setId("3");field13.setName("3");
	List<Field> fields1 = new ArrayList<Field>();
	fields1.add(field11);fields1.add(field12);fields1.add(field13);
	file1.setFields(fields1);
	file1.setId("1");
	file1.setName("1");
	file1.setPath("hdfs://200master:9000/user/root/zxyh/tt1.txt");
	//file2
	File file2 = new File();
	Field field21 = new Field();Field field22 = new Field();Field field23 = new Field();
	field21.setId("1");field21.setName("1");
	field22.setId("2");field22.setName("2");
	field23.setId("3");field23.setName("3");
	List<Field> fields2 = new ArrayList<Field>();
	fields2.add(field21);fields2.add(field22);fields2.add(field23);
	file2.setFields(fields2);
	file2.setId("2");
	file2.setName("2");
	file2.setPath("hdfs://200master:9000/user/root/zxyh/tt2.txt");
	//table1
	Table table1 = new Table();
	Field fieldt11 = new Field();Field fieldt12 = new Field();Field fieldt13 = new Field();
	fieldt11.setId("1");fieldt11.setName("1");
	fieldt12.setId("2");fieldt12.setName("2");
	fieldt13.setId("3");fieldt13.setName("3");
	List<Field> fieldst1 = new ArrayList<Field>();
	fieldst1.add(fieldt11);fieldst1.add(fieldt12);fieldst1.add(fieldt13);
	table1.setFields(fieldst1);
	table1.setId("1");
	table1.setName("join");
	table1.setHasZipperTable(true);
	table1.setZipperTableName("1Z");
	
	Task t1 = new Task();
	t1.setLeftSource(file1);
	List<Field> leftFields1 = new ArrayList<Field>();leftFields1.add(field11);leftFields1.add(field12);
	t1.setLeftFields(leftFields1);
	t1.setRightSource(file2);
	List<Field> rightFields1 = new ArrayList<Field>();rightFields1.add(field21);rightFields1.add(field23);
	t1.setRightFields(rightFields1);
	t1.setOutput("hdfs://200master:9000/user/root/zxyh/output1");
	t1.setTaskType(TaskType.Join);
	
	
	
	Task t2 = new Task();
	t2.setLeftSource(file1);
	List<Field> leftFields2 = new ArrayList<Field>();leftFields2.add(field11);leftFields2.add(field11);
	t2.setLeftFields(leftFields2);
	t2.setRightSource(table1);
	List<Field> rightFields2 = new ArrayList<Field>();rightFields2.add(fieldt11);rightFields2.add(fieldt13);
	t2.setRightFields(rightFields2);
	t2.setOutput("hdfs://200master:9000/user/root/zxyh/output2");
	t2.setTaskType(TaskType.Join);
	
	List<Task> tasks = new ArrayList<Task>();
//	tasks.add(t1);
	tasks.add(t2);
	
	conf.setTasks(tasks);
	conf.setInit(false);
	
	return conf;
    }

}
