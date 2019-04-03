package com.dingxin.generate.permission.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dingxin.common.properties.core.DxCoreProperties;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.PermissionVo;
import com.dingxin.generate.permission.feign.PermissionFeign;
import com.dingxin.generate.permission.util.AnnotationsScanUtil;

/**
 * 启动扫描 生成权限数据
 */
@Component
@Order(value = 1000)
public class PermissionCommandLineRunner implements CommandLineRunner {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DxCoreProperties dxCoreProperties;
	@Autowired
	public HttpServletRequest request;
	@Autowired
	private PermissionFeign permissionFeign;

	@Override
	public void run(String... strings) {
		boolean permissionGen = dxCoreProperties.getSystem().isPermissionGen();
		if (permissionGen) {
			gen();
		}
	}

	public ResultObject<?> gen() {
		String path = request.getContextPath();
		List<PermissionVo> list = AnnotationsScanUtil.scanByControl(path);
		String strlist = JSON.toJSONString(list);
		try {
			ResultObject<?> ok = null;
			logger.debug("提交参数 list {} ", strlist);
			if (path != null && path.contains("system")) { // 当前服务为system，feign调用失败

				RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(55000).build();
				CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

				HttpPost httpPost = new HttpPost(dxCoreProperties.getSystem().getPermissionGenUri()); // 拼接参数
				// httpPost.setHeader("Content-Type", "application/json");
				// httpPost.setEntity(new StringEntity(strlist.toString(),
				// Charset.forName("UTF-8")));
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("appName", path));
				params.add(new BasicNameValuePair("perjson", strlist));
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

				CloseableHttpResponse response = null;
				try {
					response = httpClient.execute(httpPost);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode != HttpStatus.SC_OK) {
						httpPost.abort();
						throw new RuntimeException("HttpClient,error status code :" + statusCode);
					}
					logger.debug("HttpResponseProxy：statusCode {}", statusCode);
					HttpEntity entity = response.getEntity();

					if (entity != null) {
						String result = EntityUtils.toString(entity, "UTF-8");
						logger.debug("permission/gen 接口返回:{}", result);
						ok = JSON.parseObject(result, ResultObject.class);
					}

					EntityUtils.consume(entity); // 销毁
				} catch (Exception e) {
					logger.error(e.getMessage());
				} finally {
					if (response != null) {
						try {
							response.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
					if (httpClient != null) {
						try {
							httpClient.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}

			} else {
				int i = 3;
				do { // 貌似得用token
					ok = permissionFeign.permissionGen(strlist, path);
					Thread.sleep(5000);
					i--;
					if (200 == ok.getCode()) {
						break;
					}
				} while (i >= 0);
			}

			if (ok != null && 200 == ok.getCode()) {
				logger.debug("权限生成完成");
			} else {
				logger.debug("权限生成失败");
			}
			return ok;
		} catch (Exception e) {
			logger.info(e.toString());
		}

		return ResultObject.fail("权限生成失败");
	}

}