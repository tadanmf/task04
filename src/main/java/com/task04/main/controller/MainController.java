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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class MainController {

	@Autowired
	MainService main_service;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("report")
	public void report(HttpServletRequest req) {
		JasperPrint print = null;
		JasperReport report = null;

		HttpSession session = req.getSession();
		String path = session.getServletContext().getRealPath("/WEB-INF/reports");
		log.info(path);
		
		JRBeanCollectionDataSource tableJRBean = getTableData();
		JRBeanCollectionDataSource pieJRBean = getPieData();
		JRBeanCollectionDataSource lineJRBean = getLineData();
		JRBeanCollectionDataSource barJRBean = getBarData();
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("TableDataSource", tableJRBean);
		parameters.put("PieDataSource", pieJRBean);
		parameters.put("LineDataSource", lineJRBean);
		parameters.put("BarDataSource", barJRBean);
		//log.info(jasper_file);
		
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
	}

	private JRBeanCollectionDataSource getTableData() {
		List statList = new ArrayList<>();
		
		Map firstMap = new HashMap<>();
		firstMap.put("type", "A");
		firstMap.put("total", 20);
		statList.add(firstMap);
		
		Map secondMap = new HashMap<>();
		secondMap.put("type", "B");
		secondMap.put("total", 10);
		statList.add(secondMap);
		
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
		secondMap.put("date", "2017-12-29");
		secondMap.put("value", 81);
		statList.add(secondMap);
		
		log.info("statList: " + statList);
		
		JRBeanCollectionDataSource statJRBean = new JRBeanCollectionDataSource(statList);
		return statJRBean;
	}

}