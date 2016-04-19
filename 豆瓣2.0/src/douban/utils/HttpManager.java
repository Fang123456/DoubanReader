package douban.utils;

import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.xml.XmlObjectParser;
import douban.constants.DefaultConfigs;
import douban.model.IDoubanObject;
import douban.model.app.DoubanException;
import douban.model.user.DoubanUserObj;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * Created by AAAAA on 2015/12/8.
 */
public class HttpManager {

    private static final ApacheHttpTransport APACHE_HTTP_TRANSPORT = new ApacheHttpTransport();
    private static final String CHARSET = "UTF-8";
    private String accessToken = null;
    HttpRequestFactory requestFactory = null;

    public HttpManager() {
        requestFactory = APACHE_HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
                hr.setParser(new XmlObjectParser(DefaultConfigs.DOUBAN_XML_NAMESPACE));
                HttpHeaders header = new HttpHeaders();
                header.setUserAgent("Dongxuexidu - Douban Java SDK");
                hr.setHeaders(header);
                hr.setNumberOfRetries(3);
            }
        });
    }

    public HttpManager(String accessToken) {
        this();
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private boolean hasAccessTokenBeenSet() {
        return !(this.accessToken == null || this.accessToken.isEmpty());
    }

    public <T extends IDoubanObject> T getResponse(String url, List<NameValuePair> params, Class<T> responseType, boolean needAccessToken) throws DoubanException, IOException {
        if (params != null && params.size() > 0) {
            String encodedParams = encodeParameters(params);
            url = url + "?" + encodedParams;
            System.out.println("这是url"+url);
        }
        HttpRequest method = requestFactory.buildGetRequest(new GenericUrl(url));
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public <T extends IDoubanObject> T getResponseInJson(String url, List<NameValuePair> params, Class<T> responseType, boolean needAccessToken) throws DoubanException, IOException {
        if (params != null && params.size() > 0) {
            String encodedParams = encodeParameters(params);
            url = url + "?" + encodedParams;
        }
        HttpRequest method = requestFactory.buildGetRequest(new GenericUrl(url));
        method.setParser(new JsonObjectParser(new JacksonFactory()));
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public <T> T getResponseInJsonArray(String url, List<NameValuePair> params, Class<T> responseType, boolean needAccessToken) throws DoubanException, IOException {
        if (params != null && params.size() > 0) {
            String encodedParams = encodeParameters(params);
            url = url + "?" + encodedParams;
        }
        HttpRequest method = requestFactory.buildGetRequest(new GenericUrl(url));
        method.setParser(new JsonObjectParser(new JacksonFactory()));
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public String postEncodedEntry(String url, Map<String, String> params, boolean needAccessToken) throws DoubanException, IOException {
        UrlEncodedContent content = new UrlEncodedContent(params);
        HttpRequest method = requestFactory.buildPostRequest(new GenericUrl(url), content);
        return httpRequest(method, needAccessToken).parseAsString();
    }

    public String postMultipartEntry(String url, Map<String, String> params, boolean needAccessToken) throws DoubanException, IOException {
        UrlEncodedContent uec = new UrlEncodedContent(params);
        MultipartRelatedContent content = new MultipartRelatedContent(uec);
        HttpRequest method = requestFactory.buildPostRequest(new GenericUrl(url), content);
        return httpRequest(method, needAccessToken).parseAsString();
    }

    public <T, W extends IDoubanObject> W postResponse(String url, T requestObj, Class<W> responseType, boolean needAccessToken) throws DoubanException, IOException {
        AtomContent content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, requestObj);
        HttpRequest method = requestFactory.buildPostRequest(new GenericUrl(url), content);
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public <T, W extends IDoubanObject> W postResponseInJson(String url, T requestObj, Class<W> responseType, boolean needAccessToken) throws DoubanException, IOException {
        AtomContent content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, requestObj);
        HttpRequest method = requestFactory.buildPostRequest(new GenericUrl(url), content);
        method.setParser(new JsonObjectParser(new JacksonFactory()));
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public <T extends IDoubanObject> int postResponseCodeOnly(String url, T requestObj, boolean needAccessToken) throws DoubanException, IOException {
        AtomContent content = null;
        if (requestObj != null) {
            content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, requestObj);
        } else {
            //Obviously the null content (proved) is not accecptable to Douban's API. Therefore, this empty obj is added for fooling Douban around, they don't care what's inside it anyway.
            content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, new DoubanUserObj());
        }
        HttpRequest method = requestFactory.buildPostRequest(new GenericUrl(url), content);
        HttpResponse response = httpRequest(method, needAccessToken);
        return response.getStatusCode();
    }

    public <T, W extends IDoubanObject> W putResponse(String url, T requestObj, Class<W> responseType, boolean needAccessToken) throws DoubanException, IOException {
        AtomContent content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, requestObj);
        HttpRequest method = requestFactory.buildPutRequest(new GenericUrl(url), content);
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public <T, W extends IDoubanObject> W putResponseInJson(String url, T requestObj, Class<W> responseType, boolean needAccessToken) throws DoubanException, IOException {
        AtomContent content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, requestObj);
        HttpRequest method = requestFactory.buildPutRequest(new GenericUrl(url), content);
        method.setParser(new JsonObjectParser(new JacksonFactory()));
        return httpRequest(method, needAccessToken).parseAs(responseType);
    }

    public <T extends IDoubanObject> int putResponseCodeOnly(String url, T requestObj, boolean needAccessToken) throws DoubanException, IOException {
        AtomContent content = AtomContent.forEntry(DefaultConfigs.DOUBAN_XML_NAMESPACE, requestObj);
        HttpRequest method = requestFactory.buildPutRequest(new GenericUrl(url), content);
        return httpRequest(method, needAccessToken).getStatusCode();
    }

    public int deleteResponse(String url, boolean needAccessToken) throws DoubanException, IOException {
        HttpRequest method = requestFactory.buildDeleteRequest(new GenericUrl(url));
        return httpRequest(method, needAccessToken).getStatusCode();
    }

    private HttpResponse httpRequest(HttpRequest method, boolean needToken) throws DoubanException, IOException {
        try {
            if (needToken) {
                if (!hasAccessTokenBeenSet()) {
                    throw ErrorHandler.accessTokenNotSet();
                }
                HttpHeaders headers = method.getHeaders();
                headers.setAuthorization("Bearer " + this.accessToken);
            }
            HttpResponse res = method.execute();
            System.out.println("adjfhgjkahdjkg你好"+res.getContent());
            return res;
        } catch (HttpResponseException ex) {
            throw ErrorHandler.handleHttpResponseError(ex);
        }
    }

    private static String encodeParameters(List<NameValuePair> params) {
        StringBuilder buf = new StringBuilder();
        int j = 0;
        for (NameValuePair nvp : params) {
            if (j != 0) {
                buf.append("&");
            }
            j++;
            try {
                buf.append(URLEncoder.encode(nvp.getName(), CHARSET)).append("=").append(URLEncoder.encode(nvp.getValue(), CHARSET));
            } catch (UnsupportedEncodingException ex) {
                System.out.println("Shouldn't go this far");
            }
        }
        return buf.toString();
    }
}
