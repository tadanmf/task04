package com.task04.main.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.task04.main.service.MainService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@RequestMapping("api")
@Controller
//@RestController
public class MainController {

	@Autowired
	MainService service;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
//	@RequestMapping(path = {"/main", "/"})
	public String goMain() {
		return "main";
	}
	
//	@RequestMapping("getData")
	@ResponseBody
	public Map getData(long start, long end) {
		log.info("start: " + start + ", end: " + end);
		
		return service.getData(start, end);
	}

	@GetMapping("report")
	@ResponseBody
	public void report(HttpServletRequest req) {
		long start = 1513036800000L;
		long end = 1513468800000L;

		log.info("start: " + start + ", end: " + end);
		
		JasperPrint print = null;
		JasperReport report = null;

		HttpSession session = req.getSession();
		String path = session.getServletContext().getRealPath("/WEB-INF/reports");
//		log.info(path);
		
//		JRBeanCollectionDataSource tableJRBean = getTableData();

		Map<String, Object> parameters = service.getJRBeanData(start, end);
//		parameters.put("TableDataSource", tableJRBean);
		
		try {
			report = (JasperReport) JRLoader.loadObject(new File(path + "/report1.jasper"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		log.debug("-FILL-");
		try {
			print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
		} catch (JRException e) {
			e.printStackTrace();
		}

		try {
			JasperExportManager.exportReportToPdfFile(print, path + "/test.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		log.info("--- end ---");
	}

	private JRBeanCollectionDataSource getTableData() {
		List statList = new ArrayList<>();
		
//		StatisticVO vo1 = new StatisticVO("type1", 11, "date1");
//		StatisticVO vo2 = new StatisticVO("type2", 21, "date1");
//		StatisticVO vo3 = new StatisticVO("type3", 31, "date1");
//		StatisticVO vo4 = new StatisticVO("type1", 12, "date2");
//		statList.add(vo1);
//		statList.add(vo2);
//		statList.add(vo3);
//		statList.add(vo4);
		
		Map firstMap = new HashMap<>();
		firstMap.put("type", "1");
		firstMap.put("date", "2018-01-01");
		firstMap.put("value", 20);
		statList.add(firstMap);
		
		firstMap = new HashMap<>();
		firstMap.put("type", "1");
		firstMap.put("date", "2018-01-02");
		firstMap.put("value", 20);
		statList.add(firstMap);
		
		firstMap = new HashMap<>();
		firstMap.put("type", "2");
		firstMap.put("date", "2018-01-01");
		firstMap.put("value", 20);
		statList.add(firstMap);
		
//		Map secondMap = new HashMap<>();
//		secondMap.put("type", "B");
//		firstMap.put("header1", "value1");
//		secondMap.put("total", 10);
//		statList.add(secondMap);
		
		log.info("statList: " + statList);
		
		JRBeanCollectionDataSource statJRBean = new JRBeanCollectionDataSource(statList);
		return statJRBean;
	}
	
	private JRBeanCollectionDataSource getPieData() {
		List statList = new ArrayList<>();
		
		Map firstMap = new HashMap<>();
		firstMap.put("type", "A");
		firstMap.put("value", 60);
		statList.add(firstMap);
		
		Map secondMap = new HashMap<>();
		secondMap.put("type", "B");
		secondMap.put("value", 10);
		statList.add(secondMap);
		
		log.info("statList: " + statList);
		
		JRBeanCollectionDataSource statJRBean = new JRBeanCollectionDataSource(statList);
		return statJRBean;
	}
	
	private JRBeanCollectionDataSource getLineData() {
		List statList = new ArrayList<>();
		
		Map firstMap = new HashMap<>();
		firstMap.put("type", "line A");
		firstMap.put("date", "2017-12-28");
		firstMap.put("value", 50);
		statList.add(firstMap);
		
		firstMap = new HashMap<>();
		firstMap.put("type", "line A");
		firstMap.put("date", "2017-12-29");
		firstMap.put("value", 20);
		statList.add(firstMap);
		
		
		Map secondMap = new HashMap<>();
		secondMap.put("type", "line B");
		secondMap.put("date", "2017-12-29");
		secondMap.put("value", 22);
		statList.add(secondMap);
		
		log.info("statList: " + statList);
		
		JRBeanCollectionDataSource statJRBean = new JRBeanCollectionDataSource(statList);
		return statJRBean;
	}
	
	private JRBeanCollectionDataSource getBarData() {
		List statList = new ArrayList<>();
		
		Map firstMap = new HashMap<>();
		firstMap.put("type", "bar A");
		firstMap.put("date", "2017-12-28");
		firstMap.put("value", 100);
		statList.add(firstMap);
		
		firstMap = new HashMap<>();
		firstMap.put("type", "bar A");
		firstMap.put("date", "2017-12-29");
		firstMap.put("value", 51);
		statList.add(firstMap);
		
		Map secondMap = new HashMap<>();
		secondMap.put("type", "bar B");
		secondMap.put("date", "2017-12-28");
		secondMap.put("value", 81);
		statList.add(secondMap);
		
		secondMap = new HashMap<>();
		secondMap.put("type", "bar B");
		secondMap.put("date", "2017-12-29");
		secondMap.put("value", 50);
		statList.add(secondMap);
		
		log.info("statList: " + statList);
		
		JRBeanCollectionDataSource statJRBean = new JRBeanCollectionDataSource(statList);
		return statJRBean;
	}

}