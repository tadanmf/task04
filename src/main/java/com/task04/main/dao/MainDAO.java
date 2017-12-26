package com.task04.main.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.task04.statistic.vo.StatisticVO;

@Repository
public class MainDAO {
	Logger log = LoggerFactory.getLogger(this.getClass());
	final String l_date = " WHERE l_date >= :start AND l_date <= :end ";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private RowMapper<StatisticVO> mapper = BeanPropertyRowMapper.newInstance(StatisticVO.class);

	public List<StatisticVO> getPieData(long start, long end) {
		String sql = "SELECT `type`, COUNT(*) AS value FROM event_tbl "
				+ l_date
				+ " GROUP BY `type` ORDER BY value DESC ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		
//		return jdbcTemplate.queryForMap(sql, new HashMap<>());
		return jdbcTemplate.query(sql, map, mapper);
//		return jdbcTemplate.queryForObject(sql, new HashMap<>(), new ColumnMapRowMapper());
	}

	public List<StatisticVO> getLineData(long start, long end) {
		String sql = "SELECT `type`, FROM_UNIXTIME(FLOOR(l_date/1000), '%Y-%m-%d') AS `date`, COUNT(*) AS value " 
						+ " FROM event_tbl " 
						+ l_date
						+ " GROUP BY `date`, `type` ORDER BY `date`, `type` ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		
		return jdbcTemplate.query(sql, map, mapper);
	}
	
	

}
