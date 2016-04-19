package douban.constants;

import com.google.api.client.xml.XmlNamespaceDictionary;

/**
 * Created by AAAAA on 2015/12/4.
 */
public class DefaultConfigs {
    public static final String API_KEY = "04c0319d59f7e2370e4094d80de7a6a5";
    public static final String SECRET_KEY = "830f91dc993dd2f3";
    public static final String API_URL_PREFIX = "https://api.douban.com";
    public static final String AUTH_URL = "https://www.douban.com/service/auth2/auth";
    public static final String ACCESS_TOKEN_URL = "https://www.douban.com/service/auth2/token";
    public static final String ACCESS_TOKEN_REDIRECT_URL = "http://myapp.com/callback";

    public static final XmlNamespaceDictionary DOUBAN_XML_NAMESPACE = new XmlNamespaceDictionary()
            .set("", "http://www.w3.org/2005/Atom")
            .set("db", "http://www.douban.com/xmlns/")
            .set("gd", "http://schemas.google.com/g/2005")
            .set("opensearch", "http://a9.com/-/spec/opensearchrss/1.0/");
}
