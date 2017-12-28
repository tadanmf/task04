package com.task04.main.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.task04.main.service.MainService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class MainController2 {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MainService service;

	@RequestMapping(path = {"/main", "/"}, method = RequestMethod.GET)
	public String goMain() {
		return "main";
	}
	
	@RequestMapping("getData")
	@ResponseBody
	public Map getData(long start, long end) {
		log.info("start: " + start + ", end: " + end);
		
		return service.getData(start, end);
	}
	
	@RequestMapping("downloadReport")
	public void downloadReport(HttpServletRequest request, HttpServletResponse response) {
		log.info("*** downloadReport1 ***");
		
		HttpSession session = request.getSession();
		Map<String,Object> parameters = new HashMap<String,Object>();
		String jasper_file = session.getServletContext().getRealPath("/WEB-INF/reports/report1.jasper");
		
		log.info("jasper_file: " + jasper_file);
		
		log.info(new File(jasper_file).exists()+"");

//		parameters.put("test", "과연");
		
		JasperReport report = null;
		JasperPrint print = null;
		
		
		
		//InputStream reportStreamEar = (InputStream) this.getClass().getResourceAsStream("/WEB-INF/reports/report1.jasper");
		    
		try {
			report = (JasperReport) JRLoader.loadObject(new File(jasper_file));
		} catch (JRException e1) {
			e1.printStackTrace();
			//e1.printStackTrace();
		}
		log.info("-FILL-");
		try {
			print = JasperFillManager.fillReport(report, parameters);
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		
//		Map<String,Object> parameterMap = new HashMap<String,Object>();
//        
//        List<Map<String, Object>> test_list = new ArrayList();
//        Map<String, Object> map = new HashMap<>();
//        map.put("test", "과연");
//        test_list.add(map);
//	        
//        // JRBeanCollectionDataSource: collection 형태로 넘겨준다
//		JRDataSource JRdataSource = new JRBeanCollectionDataSource(test_list);
//		
//		parameterMap.put( "datasource", JRdataSource);
//		
//		//pdfReport bean has ben declared in the jasper-views.xml file
//		modelAndView = new ModelAndView("pdfReport" , parameterMap);
		
//		HttpSession session = request.getSession();
//		
//		log.info("session.getServletContext().getRealPath: " + session.getServletContext().getRealPath("/WEB-INF/reports/report1.jrxml"));
//		
//		Map parameterMap = new HashMap<>();
//		parameterMap.put("test", "aaa"); //key value 형태의 parameterMap을 만듭니다.
//
//		try {
//			log.info("JasperCompileManager.compileReport: " + JasperCompileManager.compileReport(session.getServletContext().getRealPath("/WEB-INF/reports/report1.jrxml")));
//			
//			JasperReport jasperReport = JasperCompileManager.compileReport(session.getServletContext().getRealPath("/WEB-INF/reports/report1.jrxml")); //..jrxml의 경로를 잡아줍니다.
//
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap);
////			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap, 
////			DriverManager.getConnection("jdbc:oracle:thin:@!!!:###:bbb","aaa","ccc")); //디비정보를 넣어주세요.
//
//			ServletOutputStream sos = response.getOutputStream();
//			response.setHeader("Content-disposition", "attachment; filename=" + "report1.pdf");
//			JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		
//		JasperReportsPdfView view = new JasperReportsPdfView();
//		view.setJdbcDataSource(dataSource);
//		view.setUrl("/WEB-INF/reports/sample.jrxml");
//		Map<String, Object> params = new HashMap<>();
//		params.put("param1", "param1 value");
//		view.setApplicationContext(appContext);
//		return new ModelAndView(view, params);
		
//		File reportFile = new File(application.getRealPath("report_name.jasper"));//your report_name.jasper file
//        Map parameters = new HashMap();
//        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
//
//        response.setContentType("application/pdf");
//        response.setContentLength(bytes.length);
//        ServletOutputStream outStream = response.getOutputStream();
//        outStream.write(bytes, 0, bytes.length);
//        outStream.flush();
//        outStream.close();
		
//		service.downloadReport();
	}
	
}
