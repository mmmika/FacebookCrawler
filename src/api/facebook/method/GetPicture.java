package api.facebook.method;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;


public class GetPicture extends GraphAPI
{
	String PATH = "picture";
	/**
	 * 获取公共主页的源码，并用正则找出头像的地址
	 * @param node
	 * @return
	 */
	public String callMainPage(String node){
		
		String pregPattern="profilePic img.+src=\"([^\"]+)\"";
		String profileImgUrl=null;
		CloseableHttpClient httpClient=clientFactory.createClient();
		//动态创建API链接地址
		URI uri=null;
		try {
			uri = new URIBuilder()
			.setScheme("https")
			.setHost("www.facebook.com")
			.setPath("/"+node)
			.build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		//请求URL并获取response
		CloseableHttpResponse response= getResponse(httpClient,uri);
		//获得请求的实体
		HttpEntity entity = response.getEntity();
		//打印请求状态
		log.info("获取 "+node+"的主页源码的状态："+response.getStatusLine().toString());
		//将实体转为字符串
		String entityString=null;
		try {
			entityString = getEntityString(entity);
		} catch (Exception e) {
			log.error("vpn或代理在SSL隧道正在传输数据时突然断开！睡眠1分钟再试！"+e.getCause()+": "+e.getMessage());
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			profileImgUrl=callMainPage(node);
			e.printStackTrace();
		}
		log.info("================获得的"+node+"的前200字符================");
		log.info(entityString.substring(0,200));
		if(entityString.contains("class=\"no_js\"")){
			//开始正则匹配
			Pattern p = Pattern.compile(pregPattern);
			Matcher m = p.matcher(entityString);
			
			while(m.find()){
				profileImgUrl=m.group(1);
			}
		}else{
			log.error("请求返回的不是Facebook字符串！可能是代理软件或者VPN虽然开启，但是与境外服务器连接错误，导致返回错误的HTML实体！");
			log.error("睡眠5分钟后再重试请求API!");
			try {
				Thread.sleep(300*1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			//先关闭callAPI已经创建的httpClient对象，释放资源
			try {  
	            httpClient.close();  
	        } catch (IOException e1) {  
	            e1.printStackTrace();  
	        }
			
			//callAPI(node,fields)的写法属于递归调用，如果不向下面这样进行赋值的话，最终代理正常后返回的json不会通过方法的栈一层一层的返回的最初出错的那一层方法
			profileImgUrl=callMainPage(node);
		}
		
		return profileImgUrl;		
	}
	
	/**
	 * 获取图片字节流
	 * @param url
	 * @return
	 */
	public byte[] obtainPicture(String url){
		
		CloseableHttpClient httpClient=clientFactory.createClient();
		//动态创建API链接地址
		URI uri=null;
		try {
			//此处要特别注意，从源码中得到的URL有可能包含HTML实体字符，即将&替换成&amp; 或者其他情况，可以使用apache 的 commons-lang-*.jar 包中的StringEscapeUtils.unescapeHtml(str)解决
			//此处我直接手动替换
			url=url.replaceAll("&amp;", "&");
			uri= new URI(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//请求URL并获取response
		CloseableHttpResponse response= getResponse(httpClient,uri);
		log.info("获取图片链接的状态："+response.getStatusLine().toString());
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			log.info("图片请求成功！");
			//获得请求的实体
			HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                try {
					return EntityUtils.toByteArray(entity);
				} catch (IOException e) {
					log.error("字符流转换失败！"+e.getMessage()+"\n\n");
					e.printStackTrace();
				}  
            }  
		}
		log.info("请求失败！\n\n");
		
		try {  
            httpClient.close();  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }
		return new byte[0]; 

	}
	
	/**
	 * 保存图片字节流至文件
	 * @param id
	 * @param bit
	 */
	 public void save(int id,byte[] bit){  
        String fileName = String.valueOf(id)+".jpg";  
        String filePath = PATH + "/" + fileName;  
        BufferedOutputStream out = null;   
        if (bit.length > 0) {  
            try {  
                
            	out = new BufferedOutputStream(new FileOutputStream(filePath));				
				out.write(bit);			
                out.flush();  
                log.info("创建文件成功！[" + filePath + "]\n\n");  
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {  
                if (out != null)
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
            }  
        }  
	  }  
	
	/**
	 * 从picture文件夹读取最后一个已经爬取头像的ID，然后从该ID后开始读取种子
	 * @return
	 */
	public int obtainLastId(){

		File file=new File(PATH);
		//从数据库中的起始ID开始，由于统一使用大于号，所以比7小一
		int lastId=6;
		if(file.isDirectory()){
			File[] fileList=file.listFiles();
			Comparator<File> c = new Comparator<File>(){
				@Override
				public int compare(File f1, File f2) {
					if(f2.getName().length() > f1.getName().length())
						return 1;
					else 
						return f2.getName().compareTo(f1.getName());
				}
			};
			Arrays.sort(fileList,c);
			
			if(fileList.length>0){
				String fileName=fileList[0].getName();
				lastId=Integer.valueOf(fileName.substring(0,fileName.lastIndexOf(".")));
			}			
		}
		
		return lastId;
	}
	

	
	
}
