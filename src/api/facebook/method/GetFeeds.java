package api.facebook.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import api.facebook.bean.Feeds;

/**
 * 此类对应于API中获取涂鸦的方法
 * @author chenkedi
 *
 */
@Component
public class GetFeeds extends GraphAPI
{
	/**
	 * 通过APIcall函数返回的”顶层“json对象，进行数据的解析与容错处理
	 * @param jsonObject
	 * @param seedsId
	 * @return
	 */
	public List<Feeds> dataExtract(JSONObject jsonObject,int seedsId){
		List<Feeds> feedList=new ArrayList<Feeds>();	
		//如果请求没有成功，则返回错误信息和错误code
		if(json.isErrorJson(jsonObject)){
			Map<String,String> map=null;
			map=json.jsonErrorMessage(jsonObject);
			log.error("错误代码："+map.get("code")+"，错误信息："+map.get("message"));
			Feeds feed=new Feeds();
			feed.setStatus("error");
			feed.setCodeMessage(map.get("code"));
			feedList.add(feed);
		}else{
			
			//检测当前的顶层json对象是否还有paging这个键，没有的话说明这个json是空的
			if(jsonObject.has("paging")){

				//获取翻页的api链接
				JSONObject pageLinkJson=jsonObject.getJSONObject("paging");
				JSONArray jsonArray=jsonObject.getJSONArray("data");
				for(int i=0;i<jsonArray.length();i++){
					Feeds feed= new Feeds();
					JSONObject feedJsonObj=jsonArray.getJSONObject(i);
					
					//先处理feed本表内部对应json对象中的一级键值
					if(feedJsonObj.has("id")){
						feed.setMessageId(feedJsonObj.getString("id"));
					}else{
						feed.setMessageId(null);
					}
					
					if(feedJsonObj.has("message")){
						feed.setMessage(feedJsonObj.getString("message"));
					}else{
						feed.setMessage(null);
					}
					
					if(feedJsonObj.has("from")){
						feed.setFromUserId(feedJsonObj.getJSONObject("from").getString("id"));
						feed.setFromUserName(feedJsonObj.getJSONObject("from").has("name")?
								feedJsonObj.getJSONObject("from").getString("name"):null);
					}else{
						feed.setFromUserId(null);
						feed.setFromUserName(null);
					}
					
					
					if(feedJsonObj.has("created_time")){
						feed.setCreatedTime(string2Timestamp( feedJsonObj.getString("created_time"), null));
					}else{
						feed.setCreatedTime(null);
					}
										
					feed.setSeedsId(seedsId);	
					
					if(feedJsonObj.has("link")){
						feed.setLink(feedJsonObj.getString("link"));
					}else{
						feed.setLink(null);
					}
					
					if(feedJsonObj.has("picture")){
						feed.setPicture(feedJsonObj.getString("picture"));
					}else{
						feed.setPicture(null);
					}
					
					if(feedJsonObj.has("description")){
						feed.setDescription(feedJsonObj.getString("description"));
					}else{
						feed.setDescription(null);
					}
					
					if(feedJsonObj.has("name")){
						feed.setName(feedJsonObj.getString("name"));
					}else{
						feed.setName(null);
					}
					
					if(feedJsonObj.has("type")){
						feed.setType(feedJsonObj.getString("type"));
					}else{
						feed.setType(null);
					}
					
					/*===============为了防止每个feed对象都存储PageLink，仅在循环第一次时写入PageLink进入feed对象，取出时只需要取出index索引为0即可=====================*/
					if(i==0){
						if(pageLinkJson.has("previous")){
							feed.setFeedsPreviousPage(pageLinkJson.getString("previous"));
						}else{
							feed.setFeedsPreviousPage(null);
						}
						
						if(pageLinkJson.has("next")){
							feed.setFeedsNextPage(pageLinkJson.getString("next"));
						}else{
							feed.setFeedsNextPage(null);
						}
					}
					
					
					feedList.add(feed);

					if(jsonArray.length()-1==i){
						try{
						log.info("获得\""+feedJsonObj.getJSONObject("to").getJSONArray("data").getJSONObject(0).getString("name")+"\"的涂鸦信息成功！准备写入数据库！");
						}catch(Exception e){
							log.error("“"+seedsId+"”的feed没有to这个属性（仅仅方便log信息的区别记录），但是feed信息获取成功！准备插入数据库"+e.getMessage());
					}	
					}
				}
			
			}else{//Feeds 顶层不含有paging这个key，说明Feeds为空，爬取走到尽头
				
				Feeds feed =new Feeds();
				feed.setStatus("empty");
				feedList.add(feed);
			}
			
		}
		return feedList;
	}
}
