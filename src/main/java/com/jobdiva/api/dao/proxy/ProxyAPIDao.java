package com.jobdiva.api.dao.proxy;

import static java.net.URLEncoder.encode;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.proxy.ProxyHeader;
import com.jobdiva.api.model.proxy.ProxyParameter;
import com.jobdiva.api.model.proxy.Response;

@Component
public class ProxyAPIDao extends AbstractJobDivaDao {
	
	public static final String ENCODING_CHARSET = "UTF-8";
	
	private String urlEncode(String string) {
		if (string == null) {
			return null;
		}
		try {
			return encode(string, ENCODING_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Platform doesn't support " + ENCODING_CHARSET, e);
		}
	}
	
	private String toParameterString(ProxyParameter[] parameters) {
		//
		if (parameters == null || parameters.length == 0)
			return "";
		//
		StringBuilder parameterStringBuilder = new StringBuilder();
		for (int i = 0; i < parameters.length; i++) {
			ProxyParameter proxyParameter = parameters[i];
			if (i > 0) {
				parameterStringBuilder.append("&");
			}
			parameterStringBuilder.append(urlEncode(proxyParameter.getName()));
			parameterStringBuilder.append("=");
			parameterStringBuilder.append(urlEncode(proxyParameter.getValue()));
		}
		return parameterStringBuilder.toString();
	}
	
	public Response proxyAPI(JobDivaSession jobDivaSession, String method, String urlApi, ProxyHeader[] headers, ProxyParameter[] parameters, String body) throws NoSuchAlgorithmException, KeyManagementException, MalformedURLException, Exception {
		//
		//
		String parameterString = toParameterString(parameters);
		String finalParameterString = StringUtils.isBlank(parameterString) ? "" : ("?" + parameterString);
		//
		urlApi += finalParameterString;
		//
		//
		String rsp = "";
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
			}
			
			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
			}
			
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
		//
		//
		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		HttpsURLConnection.setFollowRedirects(true);
		HttpsURLConnection request = (HttpsURLConnection) (new URL(urlApi)).openConnection();
		//
		//
		//
		request.setUseCaches(false);
		request.setDoOutput(true);
		request.setDoInput(true);
		//
		//
		request.setInstanceFollowRedirects(true);
		request.setRequestProperty("Content-length", body != null ? String.valueOf(body.length()) : "0");
		request.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		request.setRequestProperty("Accept", "*/*");
		//
		if (headers != null) {
			for (int i = 0; i < headers.length; i++) {
				ProxyHeader proxyHeader = headers[i];
				request.setRequestProperty(proxyHeader.getName(), proxyHeader.getValue());
			}
		}
		//
		request.setRequestMethod(method);
		//
		//
		OutputStreamWriter post = new OutputStreamWriter(request.getOutputStream());
		if (body != null)
			post.write(body);
		post.flush();
		//
		int returnCode = request.getResponseCode();
		//
		String encoding = request.getContentEncoding();
		//
		InputStream is = null;
		if (returnCode < 400) {
			is = request.getInputStream();
		} else {
			is = request.getErrorStream();
		}
		//
		if (is != null) {
			if (encoding == null || encoding.indexOf("gzip") == -1) {
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				StringBuilder content = new StringBuilder();
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				rsp = content.toString();
			} else {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream zipis = new GZIPInputStream(is);
				byte[] buf = new byte[0xFFFF];
				for (int len = zipis.read(buf); len != -1; len = zipis.read(buf)) {
					baos.write(buf, 0, len);
				}
				rsp = new String(baos.toByteArray());
			}
		}
		//
		Response response = new Response(returnCode, rsp);
		//
		return response;
	}
}
