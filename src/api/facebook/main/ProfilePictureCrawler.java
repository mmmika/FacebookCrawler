package api.facebook.main;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import api.facebook.bean.Seeds;
import api.facebook.dao.SeedsDao;
import api.facebook.method.GetPicture;
import api.facebook.util.AppContext;
import api.facebook.util.Params;

/**
 * 解析公共主页人物头像的链接，存入数据库
 * @author chenkedi
 *
 */
@Controller
public class ProfilePictureCrawler
{
	@Resource
	private SeedsDao seedsDao;
	@Resource
	private Params params;
	
	//初始化API的getPosts方法，这个对象只需要实例化一次
	private GetPicture getPicture=new GetPicture();
	private static final Logger log = Logger.getLogger(ProfilePictureCrawler.class);
	public static void main(String[] args){
		log.info("正在创建数据库连接和缓冲池...");
	    AppContext.initAppCtx();
	    log.info("数据库连接已连接！缓冲池已建立");
	    
	    ProfilePictureCrawler crawler= (ProfilePictureCrawler) AppContext.appCtx.getBean(ProfilePictureCrawler.class);
	    crawler.run();
	}
	
	
	public void run(){
		
		while(true){
			//获得图片文件夹中最后一个图片的ID，如果为空，则从id=6开始
			int lastId=getPicture.obtainLastId();
			List<Seeds> seeds=seedsDao.readSeedsForPicture(params.getSeedsInfoLength(),lastId);
			if(seeds.size()!=0){
				for(Seeds seed : seeds){
					String profilePictureUrl=getPicture.callMainPage(seed.getFacebookId());
					System.out.println(profilePictureUrl);
					if(profilePictureUrl!=null){
						byte[] pictureBit=getPicture.obtainPicture(profilePictureUrl);
						getPicture.save(seed.getSeedsId(),pictureBit);
					}else continue;
					
				}
			}else{
				log.info("所有种子的头像链接采集完成，睡眠30分钟后重新扫描！\n\n");
	    		try {
					Thread.sleep(1800*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		
	}
	
	
	
	
	
	
}
