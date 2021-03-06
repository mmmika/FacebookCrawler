package api.facebook.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import api.facebook.bean.To;
import api.facebook.dao.ToDao;

@Repository
public class ToDaoImpl implements ToDao
{
	private JdbcTemplate jdbcTemplate;
	private static final Logger log=Logger.getLogger(PostDaoImpl.class);
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate=new JdbcTemplate(dataSource);
	}

	@Override
	public int[] batchInsert(List<To> tos) {
		//由于to为mysql的保留字，所以用  ` 符号处理。具体可以参考navicat中的sql生成，所有字段、表名都打了这个符号
		String SQL_INSERT_TO=
				"INSERT INTO to_other (page_name,category,facebook_id,post_id,seeds_id) VALUES (?,?,?,?,?)";
		List<Object[]> batch = new ArrayList<Object[]>();
		for(To to : tos){
			Object[] values =new Object[]{
					to.getPageName(),
					to.getCategory(),
					to.getFacebookId(),
					to.getPostId(),
					to.getSeedsId()
			};
			batch.add(values);
		}
		
		try{
			int[] updateCounts = jdbcTemplate.batchUpdate(
					SQL_INSERT_TO,
	                batch);
	        return updateCounts;
		}catch(Exception e){
				log.error("数据库批量插入种子人物“"+tos.get(0).getSeedsId()+"号”的To数据出错，错误信息："+e.getMessage());
				return new int[] {1};
		}
	}

	@Override
	public List<To> readCandidateSeeds() {
		String SQL_SELECT_SEED=
				"SELECT to_id,facebook_id,page_name FROM to_other where category IN('Politician','Political Party','Public Figure','Political Organization','Government Organization') AND has_add_to_seeds=0 ORDER BY to_id";
		List<To> tos=jdbcTemplate.query(
				SQL_SELECT_SEED,
				new RowMapper<To>(){
					public To mapRow(ResultSet rs,int rowNum) throws SQLException{
						To to=new To();
						to.setToId(rs.getInt("to_id"));
						to.setFacebookId(rs.getString("facebook_id"));
						to.setPageName(rs.getString("page_name"));
						return to;
					}
				});
		return tos;
	}

	@Override
	public void updateHasAddToSeeds(To to) {
		String SQL_UPDATE_TO=
				"UPDATE to_other SET has_add_to_seeds=1 WHERE to_id=?";
		
		jdbcTemplate.update(
				SQL_UPDATE_TO,
				to.getToId()
				);
	}

}
