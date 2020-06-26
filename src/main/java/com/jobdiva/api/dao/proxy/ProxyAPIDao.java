package com.jobdiva.api.dao.proxy;

import static java.net.URLDecoder.decode;
import static java.util.Collections.emptyMap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.proxy.ProxyHeader;
import com.jobdiva.api.model.proxy.ProxyParameter;
import com.jobdiva.api.model.proxy.Response;

@Component
public class ProxyAPIDao extends AbstractJobDivaDao {
	
	public static final String ENCODING_CHARSET = "UTF-8";
	
	public static String urlDecode(String string) {
		if (string == null) {
			return null;
		}
		try {
			return decode(string, ENCODING_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Platform doesn't support " + ENCODING_CHARSET, e);
		}
	}
	
	public static Map<String, List<String>> extractParametersFromUrl(String url) {
		if (url == null) {
			return emptyMap();
		}
		Map<String, List<String>> parameters = new HashMap<>();
		String query = url;
		for (String param : query.split("&")) {
			String[] pair = param.split("=");
			String key = urlDecode(pair[0]);
			String value = "";
			if (pair.length > 1) {
				value = urlDecode(pair[1]);
			}
			List<String> values = parameters.computeIfAbsent(key, k -> new ArrayList<>());
			values.add(value);
		}
		return parameters;
	}
	
	public Response proxyAPI(String method, String urlApi, ProxyHeader[] headers, ProxyParameter[] parameters, String body) throws Exception {
		//
		//
		HttpClient httpclient = HttpClientBuilder.create().build();
		//
		try {
			//
			HttpResponse httpResponse = null;
			//
			HttpMessage httpMessage = null;
			if ("POST".equals(method)) {
				httpMessage = new HttpPost(urlApi);
			} else if ("PUT".equals(method)) {
				httpMessage = new HttpPut(urlApi);
			} else {
				String url = body != null && !body.trim().isEmpty() ? urlApi + "?" + body : urlApi;
				httpMessage = new HttpGet(url);
			}
			//
			//
			if (headers != null) {
				for (int i = 0; i < headers.length; i++) {
					ProxyHeader proxyHeader = headers[i];
					httpMessage.setHeader(proxyHeader.getName(), proxyHeader.getValue());
				}
			}
			//
			//
			//
			//
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (parameters != null) {
				logger.info("Parameters------------------");
				for (int i = 0; i < parameters.length; i++) {
					ProxyParameter proxyParameter = parameters[i];
					logger.info(proxyParameter.getName() + "-->" + proxyParameter.getValue());
					nvps.add(new BasicNameValuePair(proxyParameter.getName(), proxyParameter.getValue()));
				}
				logger.info("------------------");
			}
			//
			//
			if (body != null) {
				logger.info("BODY -- Params------------------");
				Map<String, List<String>> extractParametersFromUrl = extractParametersFromUrl(body);
				for (Map.Entry<String, List<String>> entry : extractParametersFromUrl.entrySet()) {
					logger.info(entry.getKey() + "-->" + entry.getValue().get(0));
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().get(0)));
				}
				logger.info("------------------");
			}
			//
			//
			if ("POST".equals(method)) {
				//
				((HttpPost) httpMessage).setEntity(new UrlEncodedFormEntity(nvps));
				//
				httpResponse = httpclient.execute((HttpPost) httpMessage);
			} else if ("PUT".equals(method)) {
				((HttpPut) httpMessage).setEntity(new UrlEncodedFormEntity(nvps));
				//
				httpResponse = httpclient.execute((HttpPut) httpMessage);
			} else {
				httpResponse = httpclient.execute((HttpGet) httpMessage);
			}
			//
			//
			//
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED) {
				//
				String strResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				//
				Response response = new Response(httpResponse.getStatusLine().getStatusCode(), strResponse);
				//
				return response;
			} else {
				//
				String strResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				//
				logger.warn("Register message sent failed. [Reason] : " + strResponse);
				//
				Response response = new Response(statusCode, strResponse);
				return response;
			}
		} finally {
		}
	}
	
	private static ProxyHeader createProxyHeader(String name, String value) {
		ProxyHeader proxyHeader = new ProxyHeader();
		proxyHeader.setName(name);
		proxyHeader.setValue(value);
		return proxyHeader;
	}
	
	public static void main(String[] args) throws Exception {
		ProxyAPIDao proxyAPIDao = new ProxyAPIDao();
		String method = "PUT";
		String urlApi = "https://www.reed.co.uk/recruiter/api/1.0/jobs/update/5258402";
		String body = "username=test@jobdiva.co.uk&jobType=1&workingHours=1&description=dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd&title=Test Job From JobDiva&townName=London&postingKey=80dd4f2b-cc32-495f-aff4-4e99f8690589&productId=2&isDemo=true";
		ProxyHeader[] headers = new ProxyHeader[6];
		headers[0] = createProxyHeader("Content-Type", "application/x-www-form-urlencoded");
		headers[1] = createProxyHeader("Method", "PUT ");
		headers[2] = createProxyHeader("X-ApiClientId", "895808");
		headers[3] = createProxyHeader("X-ApiSignature", "jtog7Nh9sRiAsXus4j/NrYsa45M=");
		headers[4] = createProxyHeader("X-TimeStamp", "2020-06-24T17:49:22.782+0300");
		headers[5] = createProxyHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
		//
		proxyAPIDao.proxyAPI(method, urlApi, headers, null, body);
	}
}
