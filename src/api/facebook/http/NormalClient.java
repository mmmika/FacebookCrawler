package api.facebook.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

/**
 * 生成常规的Client，以适用于使用VPN的情况
 * @author chenkedi
 *
 */
@Component
public class NormalClient extends ClientFactory
{
	@Override
	public CloseableHttpClient createClient(){
		
		SSLConnectionSocketFactory sslsf=buildSSLSocket();
		RequestConfig requestConfig = buildConfig();
		
		httpClient = HttpClients.custom()
				 .setSSLSocketFactory(sslsf)
				 .setDefaultRequestConfig(requestConfig)
				 .build();
		
		return httpClient;
	}
}
