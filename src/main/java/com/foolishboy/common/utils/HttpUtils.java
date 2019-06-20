package com.foolishboy.common.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.TypeReference;

/**
 * http请求工具类
 *
 * @author wang
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {}

    private final static CloseableHttpClient HTTP_CLIENT;

    /**
     * 非活动保持时间,单位ms
     */
    private static final int VALIDATE_AFTER_INACTIVITY_IN_MILLS = 30 * 1000;
    /**
     * 最大连接数
     */
    private static final int MAX_TOTAL_CONNECTION = 1000;
    /**
     * 同一host最大连接数
     */
    private static final int MAX_PER_ROUTE = 200;
    /**
     * 建立连接超时时间,单位ms
     */
    private static final int CONNECTION_TIMEOUT_IN_MILLS = 60 * 1000;
    /**
     * socket超时时间,单位ms
     */
    private static final int SOCKET_TIMEOUT_IN_MILLS = 30 * 1000;
    /**
     * 从连接池中请求连接超时时间,单位ms
     */
    private static final int CONNECTION_REQUEST_TIMEOUT_IN_MILLS = 30 * 1000;
    /**
     * 连接最大空闲时间,单位s
     */
    private static final int MAX_IDLE_TIME_IN_SECONDS = 5 * 60;

    /**
     * http成功状态码
     */
    private static final int HTTP_OK_STATUS = 200;

    static {

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[] {new MyX509TrustManager()}, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e1) {
            throw new RuntimeException("ssl init error!");
        }

        ConnectionSocketFactory sslConnectionSocketFactory =
            new SSLConnectionSocketFactory(sslContext, (hostname, session) -> true);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslConnectionSocketFactory).build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY_IN_MILLS);
        connMgr.setMaxTotal(MAX_TOTAL_CONNECTION);
        connMgr.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        connMgr.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT_IN_MILLS)
            .setSocketTimeout(SOCKET_TIMEOUT_IN_MILLS).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_IN_MILLS)
            .build();

        HTTP_CLIENT = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig)
            .evictIdleConnections(MAX_IDLE_TIME_IN_SECONDS, TimeUnit.SECONDS).build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                HTTP_CLIENT.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }));
    }

    private static ResponseHandler<String> responseHandler = response -> {

        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != HTTP_OK_STATUS) {
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new ClientProtocolException("Response contains no content.");
        }
        return EntityUtils.toString(entity, Consts.UTF_8);
    };

    /**
     * 发送get请求
     *
     * @param url 请求地址
     * @param describe 描述信息
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T get(String url, String describe, TypeReference<T> responseType) {

        return get(url, null, null, describe, true, true, responseType);
    }

    /**
     * 发送get请求,控制是否打印请求参数以及响应
     *
     * @param url 请求地址
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T get(String url, String describe, boolean logRequest, boolean logResponse,
        TypeReference<T> responseType) {

        return get(url, null, null, describe, logRequest, logResponse, responseType);
    }

    /**
     * 发送带有请求头的get请求
     *
     * @param url 请求地址
     * @param header 请求头map
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T get(String url, Map<String, String> header, String describe, boolean logRequest,
        boolean logResponse, TypeReference<T> responseType) {

        return get(url, null, header, describe, logRequest, logResponse, responseType);
    }

    /**
     * 发送带有请求头的get请求,会将参数拼接到url上
     *
     * @param url 请求地址
     * @param args 请求参数map
     * @param header 请求头map
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T get(String url, Map<String, String> args, Map<String, String> header, String describe,
        boolean logRequest, boolean logResponse, TypeReference<T> responseType) {

        String response = get0(url, args, header, describe, logRequest, logResponse);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return JsonUtils.toObj(response, responseType);
    }

    /**
     * 发送post请求
     *
     * @param url 请求地址
     * @param entity 请求参数实体类
     * @param describe 描述信息
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T post(String url, Object entity, String describe, TypeReference<T> responseType) {

        return post(url, entity, null, describe, true, true, responseType);
    }

    /**
     * 发送post请求,控制是否打印请求体以及响应
     *
     * @param url 请求地址
     * @param entity 请求参数实体类
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T post(String url, Object entity, String describe, boolean logRequest, boolean logResponse,
        TypeReference<T> responseType) {

        return post(url, entity, null, describe, logRequest, logResponse, responseType);
    }

    /**
     * 发送带有请求头的post请求
     *
     * @param url 请求地址
     * @param entity 请求参数实体类
     * @param header 请求头map
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T post(String url, Object entity, Map<String, String> header, String describe, boolean logRequest,
        boolean logResponse, TypeReference<T> responseType) {

        String response = post0(url, JsonUtils.toStr(entity), header, describe, logRequest, logResponse);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return JsonUtils.toObj(response, responseType);
    }

    /**
     * 发送delete请求
     *
     * @param url 请求地址
     * @param describe 描述信息
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T delete(String url, String describe, TypeReference<T> responseType) {

        return delete(url, null, null, describe, true, true, responseType);
    }

    /**
     * 发送delete请求,控制是否打印请求参数以及响应
     *
     * @param url 请求地址
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T delete(String url, String describe, boolean logRequest, boolean logResponse,
        TypeReference<T> responseType) {

        return delete(url, null, null, describe, logRequest, logResponse, responseType);
    }

    /**
     * 发送带有请求头的delete请求
     *
     * @param url 请求地址
     * @param header 请求头map
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T delete(String url, Map<String, String> header, String describe, boolean logRequest,
        boolean logResponse, TypeReference<T> responseType) {

        return delete(url, null, header, describe, logRequest, logResponse, responseType);
    }

    /**
     * 发送带有请求头的delete请求,会将参数拼接到url上
     *
     * @param url 请求地址
     * @param args 请求参数map
     * @param header 请求头map
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T delete(String url, Map<String, String> args, Map<String, String> header, String describe,
        boolean logRequest, boolean logResponse, TypeReference<T> responseType) {

        String response = delete0(url, args, header, describe, logRequest, logResponse);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return JsonUtils.toObj(response, responseType);
    }

    /**
     * 发送put请求
     *
     * @param url 请求地址
     * @param entity 请求参数实体类
     * @param describe 描述信息
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T put(String url, Object entity, String describe, TypeReference<T> responseType) {

        return put(url, entity, null, describe, true, true, responseType);
    }

    /**
     * 发送put请求,控制是否打印请求体以及响应
     *
     * @param url 请求地址
     * @param entity 请求参数实体类
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T put(String url, Object entity, String describe, boolean logRequest, boolean logResponse,
        TypeReference<T> responseType) {

        return put(url, entity, null, describe, logRequest, logResponse, responseType);
    }

    /**
     * 发送带有请求头的put请求
     *
     * @param url 请求地址
     * @param entity 请求参数实体类
     * @param header 请求头map
     * @param describe 描述信息
     * @param logRequest 是否打印请求
     * @param logResponse 是否打印响应
     * @param responseType 返回类型
     * @param <T> 返回对象类型
     * @return T 响应反序列化的实体对象
     */
    public static <T> T put(String url, Object entity, Map<String, String> header, String describe, boolean logRequest,
        boolean logResponse, TypeReference<T> responseType) {

        String response = put0(url, JsonUtils.toStr(entity), header, describe, logRequest, logResponse);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return JsonUtils.toObj(response, responseType);
    }

    private static String get0(String url, Map<String, String> args, Map<String, String> header, String describe,
        boolean logRequest, boolean logResponse) {

        String params = serialize(args);
        url = appendToUrl(url, params);
        String messageId = "default-messageId";
        if (logRequest) {
            messageId = UuidUtils.getUuid();
            logger.info("-> messageId:{} {} url:{} header:{}", messageId, describe, url, header);
        }
        String response = get0(url, header);
        if (logResponse) {
            logger.info("-> messageId:{} {} response:{}", messageId, describe, response);
        }
        return response;
    }

    private static String get0(String url, Map<String, String> header) {

        HttpGet httpGet = new HttpGet(url);
        buildWithHeader(httpGet, header);
        try {
            return execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(HttpErrorConstants.HTTP_GET_ERROR_MESSAGE, e);
        }
    }

    private static String post0(String url, String entity, Map<String, String> header, String describe,
        boolean logRequest, boolean logResponse) {

        String messageId = "default-messageId";
        if (logRequest) {
            messageId = UuidUtils.getUuid();
            logger.info("-> messageId:{} {} url:{} header:{} body:{}", messageId, describe, url, header, entity);
        }
        String response = post0(url, entity, header);
        if (logResponse) {
            logger.info("-> messageId:{} {} response:{}", messageId, describe, response);
        }
        return response;
    }

    private static String post0(String url, String entity, Map<String, String> header) {

        HttpPost httpPost = new HttpPost(url);
        buildWithHeader(httpPost, header);
        buildWithEntity(httpPost, entity, ContentType.APPLICATION_JSON.getMimeType());
        try {
            return execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(HttpErrorConstants.HTTP_POST_ERROR_MESSAGE, e);
        }
    }

    private static String put0(String url, String entity, Map<String, String> header, String describe,
        boolean logRequest, boolean logResponse) {

        String messageId = "default-messageId";
        if (logRequest) {
            messageId = UuidUtils.getUuid();
            logger.info("-> messageId:{} {} url:{} header:{} body:{}", messageId, describe, url, header, entity);
        }
        String response = put0(url, entity, header);
        if (logResponse) {
            logger.info("-> messageId:{} {} response:{}", messageId, describe, response);
        }
        return response;
    }

    private static String put0(String url, String entity, Map<String, String> header) {

        HttpPut httpPut = new HttpPut(url);
        buildWithHeader(httpPut, header);
        buildWithEntity(httpPut, entity, ContentType.APPLICATION_JSON.getMimeType());
        try {
            return execute(httpPut);
        } catch (IOException e) {
            throw new RuntimeException(HttpErrorConstants.HTTP_PUT_ERROR_MESSAGE, e);
        }
    }

    private static String delete0(String url, Map<String, String> args, Map<String, String> header, String describe,
        boolean logRequest, boolean logResponse) {

        String params = serialize(args);
        url = appendToUrl(url, params);
        String messageId = "default-messageId";
        if (logRequest) {
            messageId = UuidUtils.getUuid();
            logger.info("-> messageId:{} {} url:{} header:{}", messageId, describe, url, header);
        }
        String response = delete0(url, header);
        if (logResponse) {
            logger.info("-> messageId:{} {} response:{}", messageId, describe, response);
        }
        return response;
    }

    private static String delete0(String url, Map<String, String> header) {

        HttpDelete httpDelete = new HttpDelete(url);
        buildWithHeader(httpDelete, header);
        try {
            return execute(httpDelete);
        } catch (IOException e) {
            throw new RuntimeException(HttpErrorConstants.HTTP_DELETE_ERROR_MESSAGE, e);
        }
    }

    private static void buildWithEntity(HttpEntityEnclosingRequestBase request, String entity, String mimeType) {

        request.setEntity(new StringEntity(entity, ContentType.create(mimeType, Consts.UTF_8)));
    }

    private static void buildWithHeader(HttpRequestBase request, Map<String, String> header) {

        if (header != null) {
            header.forEach(request::addHeader);
        }
    }

    private static String execute(HttpUriRequest request) throws IOException {

        return HTTP_CLIENT.execute(request, responseHandler);
    }

    private static String serialize(Map<String, String> args) {
        if (args == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String key : args.keySet()) {
            if (i++ != 0) {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(args.get(key));
        }
        return sb.toString();
    }

    private static String appendToUrl(String url, String serialize) {

        if (serialize == null) {
            return url;
        }
        int i = url.indexOf('?');
        if (i > 0) {
            return url + "&" + serialize;
        } else {
            return url + "?" + serialize;
        }
    }

    private static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {

            return new X509Certificate[0];
        }
    }

    /**
     * http异常描述
     */
    private interface HttpErrorConstants {

        String HTTP_GET_ERROR_MESSAGE = "Http GET method invoke error!";
        String HTTP_POST_ERROR_MESSAGE = "Http POST method invoke error!";
        String HTTP_PUT_ERROR_MESSAGE = "Http PUT method invoke error!";
        String HTTP_DELETE_ERROR_MESSAGE = "Http DELETE method invoke error!";
    }

}