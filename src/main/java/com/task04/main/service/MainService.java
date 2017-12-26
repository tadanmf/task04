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
		
		return result;
		
	}

	public List getParsingLineData(List<StatisticVO> line_source, long start, long end) {
		// 타입 리스트
		List type_lst = getTypeList(line_source);
//		log.info("type_lst: " + type_lst);
		
		// 날짜 리스트
		List date_lst = getDateList(start, end);
//		log.info("date_lst: " + date_lst);
		
		// 틀
		List init_line_data = getInitLineData(line_source, type_lst, date_lst);
		
		// 틀에 데이터(value) 삽입
		return getLineData(line_source, init_line_data);
	}
	
	public List getTypeList(List<StatisticVO> line_source) {
		List<String> result = new ArrayList<>();
		int size = line_source.size();
		for(int i = 0 ; i < size ; i++) {
			StatisticVO vo = line_source.get(i);
			
			if(! result.contains(vo.getType())) {
				result.add(vo.getType());
			}
		}
		return result;
	}
	
	public List getDateList(long start_long, long end_long) {
		List<String> result = new ArrayList<>();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String start = df.format(new Date(start_long));
//		String end = df.format(new Date(end_long));
		
		Date start_df = new Date(start_long);
		Date end_df = new Date(end_long + (24 * 60 * 60));
		
//		try {
//			start_df = df.parse(start);
//			end_df = df.parse(end);
//			
//			log.info("start_df: " + start_df);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
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
		
		for(int i = 0 ; i < type_total ; i++) {
			item = new HashMap<>();
			item.put("type", $type_lst.get(i));
			setDateValue($date_lst, item);
//			item.put("value", 0);
			result.add(item);
		}
		
		return result;
	}
	
	public List getLineData(List $source, List $result) {
		int total = $source.size();
		for(int i = 0 ; i < total ; i++) {
			StatisticVO item = (StatisticVO) $source.get(i);
			
			// db 데이터와 틀 비교하여 값 삽입
			getMatchItem($result, item);
//			vo.value = item.value;
		}
		return $result;
	}
	
	public void getMatchItem(List $result, StatisticVO source_item) {
//		log.info("$result: " + $result);
//		log.info("$result: " + $result.size());
//		log.info("total: " + total);
		int total = $result.size();
		Map result_item = null;
		int temp = -1;
		for(int i = 0 ; i < total ; i++) {
			result_item = (Map) $result.get(i);
			if(source_item.getType().equals(result_item.get("type"))) {
//				log.info("result_date.type: " + result_item.get("type") );
//				log.info("result_date.date: " + result_item.get("date") );
//				log.info("result_date.value: " + result_item.get("value") );
				
				List date_list = (List) result_item.get("date");
				List value_list = (List) result_item.get("value");
				temp = date_list.indexOf(source_item.getDate());
				if(temp != -1) {
					value_list.add(temp, source_item.getValue());
				}
			}
			$result.set(i, result_item);
		}
	}
	
	public void setDateValue(List $date_lst, Map $item) {
		int date_total = $date_lst.size();
		List date_list = new ArrayList<>();
		List value_list = new ArrayList<>();
		for(int i = 0 ; i < date_total ; i++) {
			date_list.add( $date_lst.get(i) );
			value_list.add( 0 );
		}
		$item.put("date", date_list);
		$item.put("value", value_list);
	}

}
