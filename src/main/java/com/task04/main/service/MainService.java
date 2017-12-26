package com.task04.main.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task04.main.dao.MainDAO;

@Service
public class MainService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MainDAO dao;

	public Map getData(long start, long end) {
		log.info("dao.getPieData: " + dao.getPieData(start, end));
		log.info("dao.getLineData: " + dao.getLineData());
		
		Map result = new HashMap<>();
		
		// 파이 차트
		result.put("pie_data", dao.getPieData(start, end));
		
		// 라인 차트
//		List<StatisticVO> line_source = dao.getLineData();
		
		// 타입 리스트
//		List type_lst = getTypeList(line_source);
//		// 날짜 리스트
//		List date_lst = getDateList(start, end);
//		
//		List init_line_data = getInitLineData(line_source, type_lst, date_lst);
//		List line_data = getLineData(line_source, init_line_data);
//		result.put("line_data", line_data);
		
		return result;
		
	}
//	
//	public List getTypeList(List<StatisticVO> line_source) {
//		List<String> result = new ArrayList<>();
//		int size = line_source.size();
//		for(int i = 0 ; i < size ; i++) {
//			StatisticVO vo = line_source.get(i);
//			if(! result.contains(vo.getType())) {
//				result.add(vo.getType());
//			}
//		}
//		return result;
//	}
//	
//	public List getDateList(String start, String end) {
//		List<Date> result = new ArrayList<>();
//		
//		SimpleDateFormat df = new SimpleDateFormat("yyyyy-mm-dd");
//		Date start_df = null;
//		Date end_df = null;
//		
//		try {
//			start_df = df.parse(start);
//			end_df = df.parse(end);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		result = getDaysBetweenDates(start_df, end_df);
//		
//		log.info("result: " + result);
//		
//		return result;
//	}	
//	
//	public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
//	{
//	    List<Date> dates = new ArrayList<Date>();
//	    Calendar calendar = new GregorianCalendar();
//	    calendar.setTime(startdate);
//
//	    while (calendar.getTime().before(enddate))
//	    {
//	        Date result = calendar.getTime();
//	        dates.add(result);
//	        calendar.add(Calendar.DATE, 1);
//	    }
//	    return dates;
//	}
//	
//	public List getInitLineData(List $source, List $type_lst, List $date_lst) {
//		List result = new ArrayList();
//		Map item = null;
//		int type_total = $type_lst.size();
//		int date_total = $date_lst.size();
//		for(int i = 1 ; i <= type_total ; i++) {
//			item = new HashMap<>();
//			item.put("type", $type_lst.get(i-1));
//			setDate($date_lst, item);
//			item.put("value", 0);
//			result.add(item);
//		}
//		return result;
//	}
//	
//	public List getLineData(List $source, List $result) {
//		int total = $source.size();
//		for(int i = 1 ; i <= total ; i++) {
//			StatisticVO item = (StatisticVO) $source.get(i-1);
//			Map vo = getMatchItem($result, item, total);
//			vo.value = item.value;
//		}
//		return $result;
//	}
//	
//	public Map getMatchItem(List $result, StatisticVO source_item, int total) {
//		Map item = null;
//		for(int i = 1 ; i <= total ; i++) {
//			Map result_item = (Map) $result.get(i-1);
//			if(source_item.getType().equals(result_item.get("type")) && source_item.getDate().equals(result_item.get("date"))) {
//				item.put("value", source_item.getValue());
//			}
//			return item;
//		}
//		return null;
//	}
//	
//	public void setDate(List $date_lst, Map $item) {
//		int date_total = $date_lst.size();
//		for(int i = 1 ; i <= date_total ; i++) {
//			$item.put("date", $date_lst.get(i-1));
//		}
//	}
	
//	public List test() throws ParseException {
//		String str = "2017-10-19 16:18:03.779";
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		Date date = (Date) df.parse(str);
//		long epoch = date.getTime();
//		System.out.println(epoch);
//		
//		getData(start, end)
//		
//	}

}
