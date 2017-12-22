package com.task04.main.dao;

import java.util.HashMap;
import java.util.List;

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
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private RowMapper<StatisticVO> mapper = BeanPropertyRowMapper.newInstance(StatisticVO.class);

	public List<StatisticVO> getPieData() {
		String sql = "SELECT `type`, COUNT(*) value FROM event_tbl GROUP BY `type` ORDER BY value DESC";
		
//		return jdbcTemplate.queryForMap(sql, new HashMap<>());
		return jdbcTemplate.query(sql, new HashMap<>(), mapper);
//		return jdbcTemplate.queryForObject(sql, new HashMap<>(), new ColumnMapRowMapper());
	}

	public List<StatisticVO> getLineData() {
		String sql = "SELECT `type`, FROM_UNIXTIME(FLOOR(l_date/1000), '%Y-%m-%d') AS `date`, COUNT(*) AS cnt " 
						+ " FROM event_tbl " 
						+ " WHERE l_date BETWEEN 1513349999000 AND 1513954799000 " 
						+ " GROUP BY `date`, `type` ORDER BY `date`, `type` ";
		
		return jdbcTemplate.query(sql, new HashMap<>(), mapper);
	}
	
	

}
