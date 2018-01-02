package com.task04.main.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task04.main.dao.MainDAO;
import com.task04.statistic.vo.StatisticVO;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class MainService {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MainDAO dao;

	public Map<String, Object> getData(long start, long end) {
		log.info("dao.getPieData: " + dao.getPieData(start, end));
		log.info("dao.getLineData: " + dao.getLineData(start, end));

		Map<String, Object> result = new HashMap<>();

		// 파이 차트
		result.put("pie_data", dao.getPieData(start, end));

		// 라인 차트
		List<StatisticVO> line_source = dao.getLineData(start, end);
		result.put("line_data", getParsingLineData(line_source, start, end));

		// 테이블
		result.put("grid_data", getParsingGridData(line_source, start, end));

		return result;
	}

	// report 용 데이터
	public Map<String, Object> getJRBeanData(long start, long end) {
		List<StatisticVO> line_source = dao.getLineData(start, end);
		
		JRBeanCollectionDataSource tableJRBean = new JRBeanCollectionDataSource(getParsingReportLineData(line_source, start, end));
//		JRBeanCollectionDataSource tableJRBean = new JRBeanCollectionDataSource(getParsingReportTableData(line_source, start, end));
				
		JRBeanCollectionDataSource pieJRBean = new JRBeanCollectionDataSource(dao.getPieData(start, end));
		
		JRBeanCollectionDataSource lineJRBean = new JRBeanCollectionDataSource(getParsingReportLineData(line_source, start, end));
		
		JRBeanCollectionDataSource barJRBean = new JRBeanCollectionDataSource(getParsingReportLineData(line_source, start, end));
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("TableDataSource", tableJRBean);
		parameters.put("PieDataSource", pieJRBean);
		parameters.put("LineDataSource", lineJRBean);
		parameters.put("BarDataSource", barJRBean);
		
		return parameters;
	}
	
	private List getParsingReportTableData(List<StatisticVO> line_source, long start, long end) {
		// 타입 리스트
		List type_lst = getTypeList(line_source);
		// log.info("type_lst: " + type_lst);

		// 날짜 리스트
		List date_lst = getDateList(start, end);
		// log.info("date_lst: " + date_lst);

		// 틀
		List init_line_data = getInitReportLineData(line_source, type_lst, date_lst);

		// 틀에 데이터(value) 삽입
		return getReportTableData(line_source, init_line_data);
	}

	private List getReportTableData(List<StatisticVO> source, List init_line_data) {
		int total = source.size();

		Map map = new HashMap<>();
		for (int i = 0; i < total; i++) {
			StatisticVO item = (StatisticVO) source.get(i);

			// db 데이터와 틀 비교하여 값 삽입
			getMatchReportTableItem(init_line_data, item);
			// vo.value = item.value;
		}
		
		return init_line_data;
	}
	
	public void getMatchReportTableItem(List $result, StatisticVO source_item) {
		int total = $result.size();
		Map result_item = null;
		int temp = -1;
		for (int i = 0; i < total; i++) {
			result_item = new HashMap<>();
			result_item = (Map) $result.get(i);
			String type = "type(" + source_item.getType() + ")";
			if ( type.equals(result_item.get("type")) && source_item.getDate().equals(result_item.get("date")) ) {
				result_item.put("value", source_item.getValue());
			}
			$result.set(i, result_item);
		}
	}

	public List getParsingReportLineData(List<StatisticVO> line_source, long start, long end) {
		// 타입 리스트
		List type_lst = getTypeList(line_source);
		// log.info("type_lst: " + type_lst);

		// 날짜 리스트
		List date_lst = getDateList(start, end);
		// log.info("date_lst: " + date_lst);

		// 틀
		List init_line_data = getInitReportLineData(line_source, type_lst, date_lst);

		// 틀에 데이터(value) 삽입
		return getReportLineData(line_source, init_line_data);
	}

	private List getReportLineData(List<StatisticVO> source, List result) {
		int total = source.size();

		Map map = new HashMap<>();
		for (int i = 0; i < total; i++) {
			StatisticVO item = (StatisticVO) source.get(i);

			// db 데이터와 틀 비교하여 값 삽입
			getMatchReportItem(result, item);
			// vo.value = item.value;
		}

		log.info("***getReportLineData result: " + result);
		
		return result;
	}

	private List getInitReportLineData(List<StatisticVO> line_source, List type_lst, List date_lst) {
		// 틀
		List result = new ArrayList();
		int type_total = type_lst.size();
		int date_total = date_lst.size();

		for (int i = 0; i < type_total; i++) {
			setReportDateValue(type_lst.get(i).toString(), date_lst, result);
			// item.put("value", 0);
		}

		log.info("***result: " + result.toString());
		return result;
	}
	
	public void setReportDateValue(String type, List $date_lst, List result) {
		int date_total = $date_lst.size();
		Map item = null;

		for (int i = 0; i < date_total; i++) {
			item = new HashMap<>();
			item.put("type", type);
			item.put("date", $date_lst.get(i));
			item.put("value", 0);
			result.add(item);
		}
	}

	public Map getParsingGridData(List<StatisticVO> line_source, long start, long end) {
		// 타입 리스트
		List type_lst = getTypeList(line_source);
		// log.info("type_lst: " + type_lst);

		// 날짜 리스트
		List date_lst = getDateList(start, end);
		// log.info("date_lst: " + date_lst);

		// 틀
		Map init_grid_data = getInitGridData(line_source, type_lst, date_lst);
//		log.info("***init_line_data.size(): " + init_grid_data.size());
//		log.info("***init_line_data.size(): " + init_grid_data.toString());

		// 틀에 데이터(value) 삽입
		return getGridData(line_source, init_grid_data, date_lst);
	}

	private Map getGridData(List<StatisticVO> line_source, Map init_grid_data, List date_lst) {
		int total = line_source.size();
		log.info("***total: " + total);

		for (int i = 0; i < total; i++) {
			StatisticVO item = (StatisticVO) line_source.get(i);

			// db 데이터와 틀 비교하여 값 삽입
			getMatchGridItem(init_grid_data, item, date_lst);
			// vo.value = item.value;
		}
		
		log.info("init_grid_data: " + init_grid_data);
		
		return init_grid_data;
	}

	private void getMatchGridItem(Map result, StatisticVO item, List date_lst) {
//		log.info("result: " + result);
		
		List init_grid_data = (List) result.get("result");
//		log.info("init_grid_data: " + init_grid_data);
		
		int length = init_grid_data.size();
		Map result_item = null;
		int temp = -1;
		
		String type = "type(" + item.getType() + ")";
		
		for (int i = 0; i < length; i++) {
//			log.info("init_grid_data.get(i): " + init_grid_data.get(i));
			result_item = (Map) init_grid_data.get(i);
			
			if( type.equals(result_item.get("type")) ) {
//				log.info("result_item.get(0): " + result_item.get(0));
				int item_size = result_item.size() - 1;
//				log.info("item_size: " + item_size);
//				log.info("(int)result_item.get(item_size): " + (int)result_item.get(item_size-1));
				
				result_item.put("total", ((int)result_item.get("total") + item.getValue()));
				
				result_item.put(item.getDate(), item.getValue());
//				temp = date_lst.indexOf(item.getDate());
//				log.info("temp: " + temp);
				
//				if (temp != -1) {
//					temp += 1;
//					result_item.set(temp, item.getValue());
//				}
			}
		}
		
//		log.info("init_grid_data: " + init_grid_data);
	}

	private Map getInitGridData(List<StatisticVO> line_source, List type_lst, List date_lst) {
		// 틀
		List result = new ArrayList();
		List columns = new ArrayList();
		Map result_item = null;
		Map columns_item = null;
		int type_total = type_lst.size();
		int date_total = date_lst.size();

		columns_item = new HashMap<>();
		columns_item.put("title", "type");
		columns_item.put("data", "type");
		columns.add(columns_item);
		for (int i = 0; i < type_total; i++) {
			result_item = new HashMap<>();
			result_item.put("type", type_lst.get(i));
			
			for (int j = 0; j < date_total; j++) {
				if (i == 0) {
					columns_item = new HashMap<>();
					columns_item.put("title", date_lst.get(j));
					columns_item.put("data", date_lst.get(j));
					columns.add(columns_item);
				}
				result_item.put(date_lst.get(j), 0);
			}
			result_item.put("total", 0);
			result.add(result_item);
		}
		columns_item = new HashMap<>();
		columns_item.put("title", "total");
		columns_item.put("data", "total");
		columns.add(columns_item);
		
//		log.info("***result: " + result.toString());
//		log.info("***colums: " + colums.toString());
		
		Map map = new HashMap<>();
		map.put("result", result);
		map.put("columns", columns);

		return map;
	}

	public List getParsingLineData(List<StatisticVO> line_source, long start, long end) {
		// 타입 리스트
		List type_lst = getTypeList(line_source);
		// log.info("type_lst: " + type_lst);

		// 날짜 리스트
		List date_lst = getDateList(start, end);
		// log.info("date_lst: " + date_lst);

		// 틀
		List init_line_data = getInitLineData(line_source, type_lst, date_lst);
		log.info("***init_line_data.size(): " + init_line_data.size());

		log.info("***init_line_data.size(): " + init_line_data.toString());

		// 틀에 데이터(value) 삽입
		return getLineData(line_source, init_line_data);
	}

	public List getTypeList(List<StatisticVO> line_source) {
		List<String> result = new ArrayList<>();
		int size = line_source.size();
		String temp = "";
		for (int i = 0; i < size; i++) {
			StatisticVO vo = line_source.get(i);
			temp = "type(" + vo.getType() + ")";
			if (!result.contains(temp)) {
				result.add(temp);
			}
		}
		return result;
	}

	public List getDateList(long start_long, long end_long) {
		List<String> result = new ArrayList<>();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// String start = df.format(new Date(start_long));
		// String end = df.format(new Date(end_long));

		Date start_df = new Date(start_long);
		Date end_df = new Date(end_long + (24 * 60 * 60));

		// try {
		// start_df = df.parse(start);
		// end_df = df.parse(end);
		//
		// log.info("start_df: " + start_df);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		result = getDaysBetweenDates(start_df, end_df);

		return result;
	}

	public List<String> getDaysBetweenDates(Date startdate, Date enddate) {
		List<Date> dates = new ArrayList<Date>();
		List<String> date_lst = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);

		while (calendar.getTime().before(enddate)) {
			Date result = calendar.getTime();
			date_lst.add(df.format(result));
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}

		log.info("date_lst: " + date_lst);
		return date_lst;
	}

	public List getInitLineData(List $source, List $type_lst, List $date_lst) {
		// 틀
		List result = new ArrayList();
		Map item = null;
		int type_total = $type_lst.size();
		int date_total = $date_lst.size();

		for (int i = 0; i < type_total; i++) {
			item = new HashMap<>();
			item.put("type", $type_lst.get(i));
			setDateValue($date_lst, item);
			// item.put("value", 0);
			result.add(item);
		}

		return result;
	}

	public List getLineData(List $source, List $result) {
		int total = $source.size();

		Map map = new HashMap<>();
		for (int i = 0; i < total; i++) {
			StatisticVO item = (StatisticVO) $source.get(i);

			// db 데이터와 틀 비교하여 값 삽입
			getMatchItem($result, item);
			// vo.value = item.value;
		}
		
		return $result;
	}

	public void getMatchReportItem(List $result, StatisticVO source_item) {
		int total = $result.size();
		Map result_item = null;
		int temp = -1;
		for (int i = 0; i < total; i++) {
			result_item = new HashMap<>();
			result_item = (Map) $result.get(i);
			String type = "type(" + source_item.getType() + ")";
			if ( type.equals(result_item.get("type")) && source_item.getDate().equals(result_item.get("date")) ) {
				result_item.put("value", source_item.getValue());
			}
			$result.set(i, result_item);
		}
	}
	
	public void getMatchItem(List $result, StatisticVO source_item) {
		// log.info("$result: " + $result);
		// log.info("$result: " + $result.size());
		// log.info("total: " + total);
		int total = $result.size();
		Map result_item = null;
		int temp = -1;
		for (int i = 0; i < total; i++) {
			result_item = (Map) $result.get(i);
			String type = "type(" + source_item.getType() + ")";
			if (type.equals(result_item.get("type"))) {
				// log.info("result_date.type: " + result_item.get("type") );
				// log.info("result_date.date: " + result_item.get("date") );
				// log.info("result_date.value: " + result_item.get("value") );

				List date_list = (List) result_item.get("date");
				List value_list = (List) result_item.get("value");
				temp = date_list.indexOf(source_item.getDate());
				if (temp != -1) {
					value_list.set(temp, source_item.getValue());
				}
			}
			$result.set(i, result_item);
		}
	}

	public void setDateValue(List $date_lst, Map $item) {
		int date_total = $date_lst.size();
		List date_list = new ArrayList<>();
		List value_list = new ArrayList<>();
		for (int i = 0; i < date_total; i++) {
			date_list.add($date_lst.get(i));
			value_list.add(0);
		}
		$item.put("date", date_list);
		$item.put("value", value_list);
	}

	public void downloadReport() {
//		Map paramterMap = new HashMap<>();
//		paramterMap.put("AAA", "aaa"); //key value 형태의 parameterMap을 만듭니다.
//
//		JasperReport jasperReport = JasperCompileManager.compileReport(session.getServletContext().getRealPath("/WEB-INF/jasper/reports/issuer_report.jrxml")); //..jrxml의 경로를 잡아줍니다.
//
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap, DriverManager.getConnection("jdbc:oracle:thin:@!!!:###:bbb","aaa","ccc")); //디비정보를 넣어주세요.
//
//		ServletOutputStream sos=response.getOutputStream();
//		response.setHeader("Content-disposition", "attachment; filename=" + "이름.pdf");
//		JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
		
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
	}

}
