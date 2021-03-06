package com.citic.zxyjs.zwlscx.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.citic.zxyjs.zwlscx.bean.Conf;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.JoinTask;
import com.citic.zxyjs.zwlscx.bean.Rule;
import com.citic.zxyjs.zwlscx.bean.Source;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.bean.future.Field;

/**
 * 解析并校验配置文件工具类 TODO 解析 校验
 * 
 * @author JoyoungZhang@gmail.com
 */
public class ParseXmlUtils {

    private static final String XML = "etc/job.xml";
    private static final Log LOG = LogFactory.getLog(ParseXmlUtils.class);

    public static Conf parseAndVerifyXml() {
	File file1 = new File();
	file1.setName("HDP_TX_O_F_DSDSA");
	file1.setPath("hdfs://200master:9000/user/root/zxyh/HDP_TX_O_F_DSDSA.txt");

	Table table1 = new Table();
	table1.setName("HDP_CM_O_F_PMTPA");

	File file2 = new File();
	file2.setName("HDP_TX_O_F_DSDSA&HDP_CM_O_F_PMTPA");
	file2.setPath("hdfs://200master:9000/user/root/zxyh/tmp/HDP_TX_O_F_DSDSA&HDP_CM_O_F_PMTPA");
	List<Source> parentSource = new ArrayList<Source>();
	parentSource.add(file1);
	parentSource.add(table1);
	file2.setParentSource(parentSource);

	JoinTask joinTask1 = new JoinTask();
	joinTask1.setLeftSource(file1);
	joinTask1.setRightSource(table1);
	joinTask1.setOutput(file2);
	joinTask1.setErrorOutput("hdfs://200master:9000/user/root/zxyh/error/HDP_TX_O_F_DSDSA&HDP_CM_O_F_PMTPA/HDP_TX_O_F_DSDSA");
	List<Rule> rules = new ArrayList<Rule>();
	Rule rule = new Rule();
	rule.setLeftField(new Field("DSTRCD"));
	rule.setRightField(new Field("TPTRCD"));
	rule.setOutput("DSTRCD");
	rules.add(rule);
	joinTask1.setJoinRule(rules);

	Conf conf = new Conf();
	List<Task> tasks = new ArrayList<Task>();
	tasks.add(joinTask1);
	conf.setTasks(tasks);
	conf.setClear(false);

	return conf;
    }

    //    public static Conf parseXml() {
    //	try {
    //	    Conf conf = new Conf();
    //
    //	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //	    DocumentBuilder db = dbf.newDocumentBuilder();
    //	    Document document = db.parse(XML);
    //	    NodeList confs = document.getChildNodes();
    //
    //	    Node confNodes = null;
    //	    for (int i = 0; i < confs.getLength(); i++) {
    //		Node node = confs.item(i);
    //		if (node instanceof Element && node.getNodeName().equals("conf")) {
    //		    confNodes = node;
    //		}
    //	    }
    //
    //	    Node sourceNodes = null;
    //	    Node taskNodes = null;
    //
    //	    for (int i = 0; i < confNodes.getChildNodes().getLength(); i++) {
    //		Node node = confNodes.getChildNodes().item(i);
    //		if (node instanceof Element) {
    //		    if (node.getNodeName().equals("sources")) {
    //			sourceNodes = node;
    //		    } else if (node.getNodeName().equals("tasks")) {
    //			taskNodes = node;
    //		    }
    //		}
    //	    }
    //
    //	    Map<String, Source> sources = new HashMap<String, Source>();
    //
    //	    for (int j = 0; j < sourceNodes.getChildNodes().getLength(); j++) {
    //		Node sourceNode = sourceNodes.getChildNodes().item(j);
    //		if (sourceNode instanceof Element) {
    //		    if (sourceNode.getNodeName().equals("file")) {
    //			File file = new File();
    //			List<Field> fields = new ArrayList<Field>();
    //			for (int k = 0; k < sourceNode.getChildNodes().getLength(); k++) {
    //			    Node fieldNode = sourceNode.getChildNodes().item(k);
    //			    if (fieldNode instanceof Element) {
    //				Field field = new Field();
    //				field.setId(((Element) fieldNode).getAttribute("id"));
    //				field.setName(((Element) fieldNode).getAttribute("name"));
    //				if (StringUtils.isBlank(((Element) fieldNode).getAttribute("sourceId"))) {
    //				    field.setSourceId(((Element) sourceNode).getAttribute("id"));
    //				} else {
    //				    field.setSourceId(((Element) fieldNode).getAttribute("sourceId"));
    //				}
    //				if (StringUtils.isBlank(((Element) fieldNode).getAttribute("sourceFieldId"))) {
    //				    field.setSourceFieldId(((Element) fieldNode).getAttribute("id"));
    //				} else {
    //				    field.setSourceFieldId(((Element) fieldNode).getAttribute("sourceFieldId"));
    //				}
    //				fields.add(field);
    //			    }
    //			}
    //			file.setFields(fields);
    //			file.setId(((Element) sourceNode).getAttribute("id"));
    //			file.setName(((Element) sourceNode).getAttribute("name"));
    //			file.setPath(((Element) sourceNode).getAttribute("path"));
    //			file.setErrorPath(((Element) sourceNode).getAttribute("errorpath"));
    //			sources.put(file.getId(), file);
    //		    } else if (sourceNode.getNodeName().equals("table")) {
    //			Table table = new Table();
    //			List<Field> fields = new ArrayList<Field>();
    //			for (int k = 0; k < sourceNode.getChildNodes().getLength(); k++) {
    //			    Node fieldNode = sourceNode.getChildNodes().item(k);
    //			    if (fieldNode instanceof Element) {
    //				Field field = new Field();
    //				field.setId(((Element) fieldNode).getAttribute("id"));
    //				field.setName(((Element) fieldNode).getAttribute("name"));
    //				if (StringUtils.isBlank(((Element) fieldNode).getAttribute("sourceId"))) {
    //				    field.setSourceId(((Element) sourceNode).getAttribute("id"));
    //				} else {
    //				    field.setSourceId(((Element) fieldNode).getAttribute("sourceId"));
    //				}
    //				if (StringUtils.isBlank(((Element) fieldNode).getAttribute("sourceFieldId"))) {
    //				    field.setSourceFieldId(((Element) fieldNode).getAttribute("id"));
    //				} else {
    //				    field.setSourceFieldId(((Element) fieldNode).getAttribute("sourceFieldId"));
    //				}
    //				fields.add(field);
    //			    }
    //			}
    //			table.setFields(fields);
    //			table.setId(((Element) sourceNode).getAttribute("id"));
    //			table.setName(((Element) sourceNode).getAttribute("name"));
    //			String hasZipperTable = ((Element) sourceNode).getAttribute("hasZipperTable");
    //			table.setHasZipperTable(hasZipperTable == null ? false : Boolean.valueOf(hasZipperTable));
    //			table.setZipperTableName(((Element) sourceNode).getAttribute("zipperTableName"));
    //			table.setPath(((Element) sourceNode).getAttribute("path"));
    //			sources.put(table.getId(), table);
    //		    }
    //		}
    //	    }
    //
    //	    List<Task> tasks = new ArrayList<Task>();
    //	    for (int m = 0; m < taskNodes.getChildNodes().getLength(); m++) {
    //		Node taskNode = taskNodes.getChildNodes().item(m);
    //		if (taskNode instanceof Element) {
    //		    Task task = new Task();
    //		    if (taskNode.getNodeName().equals("join")) {
    //			task.setTaskType(TaskType.Join);
    //		    } else if (taskNode.getNodeName().equals("append")) {
    //			task.setTaskType(TaskType.Append);
    //		    }
    //		    String leftsource = ((Element) taskNode).getAttribute("leftsource");
    //		    String rightsource = ((Element) taskNode).getAttribute("rightsource");
    //		    task.setLeftSource(sources.get(leftsource));
    //		    task.setRightSource(sources.get(rightsource));
    //
    //		    List<Field> leftFields = new ArrayList<Field>();
    //		    for (String fieldId : ((Element) taskNode).getAttribute("leftfield").split(",")) {
    //			for (Field field : task.getLeftSource().getFields()) {
    //			    if (fieldId.equals(field.getId())) {
    //				leftFields.add(field);
    //			    }
    //			}
    //		    }
    //		    task.setLeftFields(leftFields);
    //
    //		    List<Field> rightFields = new ArrayList<Field>();
    //		    for (String fieldId : ((Element) taskNode).getAttribute("rightfield").split(",")) {
    //			for (Field field : task.getRightSource().getFields()) {
    //			    if (fieldId.equals(field.getId())) {
    //				rightFields.add(field);
    //			    }
    //			}
    //		    }
    //		    task.setRightFields(rightFields);
    //		    String output = ((Element) taskNode).getAttribute("output");
    //		    Source source = sources.get(output);
    //		    task.setOutput(source);
    //		    task.setMapperExtension(((Element) taskNode).getAttribute("mapperExtension"));
    //		    task.setReducerExtension(((Element) taskNode).getAttribute("reducerExtension"));
    //		    task.setJobExtension(((Element) taskNode).getAttribute("jobExtension"));
    //
    //		    tasks.add(task);
    //		}
    //	    }
    //	    conf.setTasks(tasks);
    //	    conf.setClear(Boolean.valueOf(((Element) taskNodes).getAttribute("clear")));
    //	    return conf;
    //	} catch (Exception e) {
    //	    LOG.error("Parse xml[ " + XML + "] fail.", e);
    //	}
    //	return null;
    //    }
}