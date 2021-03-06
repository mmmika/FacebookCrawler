package api.facebook.main;


import java.util.List;




import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import api.facebook.bean.Seeds;
import api.facebook.dao.SeedsDao;
import api.facebook.method.GetSeeds;
import api.facebook.util.AppContext;
import api.facebook.util.Params;

/**
 * 爬取种子节点的公众人物的个人信息
 * @author chenkedi
 *
 */
@Controller
public class SeedsInfoCrawler
{
	@Resource
	private SeedsDao seedsDao;
	@Resource
	private Params params;
	private static final Logger log = Logger.getLogger(SeedsInfoCrawler.class);
	
	public static void main(String[] args){
		log.info("正在创建数据库连接和缓冲池...");
	    AppContext.initAppCtx();
	    log.info("数据库连接已连接！缓冲池已建立");
	    
	    SeedsInfoCrawler crawler= (SeedsInfoCrawler) AppContext.appCtx.getBean(SeedsInfoCrawler.class);
	    crawler.run();
	}
	public void run(){
		while(true){
	    	List<Seeds> seeds=seedsDao.readSeeds(params.getSeedsInfoLength());//获得需要爬取的种子队列
	    	
	    	if(seeds.size()!=0){
	    		for(Seeds temp : seeds){
	    			//初始化API的getSeeds方法
	    			GetSeeds getSeed=new GetSeeds();
	    			//调用api得到seeds的json数据
	    			//有可能出现code为100的错误，即不支持的graphapi链接错误，这里暂时将其category字段存储为-1，使其不影响爬虫正常运行
	    			JSONObject jsonObject=getSeed.callAPI((temp.getFacebookId()!=null)?temp.getFacebookId():temp.getUserName(),"");
	    			//数据抽取，将json转换为bean兼容的格式
	    			Seeds seed=getSeed.dataExtract(jsonObject);//如果出现请求错误，seed可能为空，需要做处理
	    			if(seed!=null){
	    				seedsDao.addSeedsInfo(seed,temp.getSeedsId());
	    				log.info(temp.getName()+"的信息插入成功！\n\n");
	    			}else{
	    				log.error(temp.getName()+"的信息获取失败，继续采集下一个种子！\n\n");
	    			}
	    		}
	    	}else{
	    		
	    		log.info("所有种子的个人信息采集完成，睡眠30分钟后重新扫描！\n\n");
	    		try {
					Thread.sleep(1800*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    	
	    }
	}
}
