package douban.playground;

import android.app.DownloadManager;
import android.content.Context;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.XmlObjectParser;
import douban.constants.DefaultConfigs;
import douban.model.app.AccessToken;
import douban.model.app.DoubanException;
import douban.model.app.RequestGrantScope;
import douban.model.common.DoubanAttributeObj;
import douban.model.common.DoubanTagObj;
import douban.model.shuo.DoubanShuoAttachementObj;
import douban.model.shuo.DoubanShuoMediaObj;
import douban.model.shuo.DoubanShuoStatusObj;
import douban.model.shuo.DoubanShuoUserObj;
import douban.model.subject.DoubanSubjectObj;
import douban.playground.service.DoubanBookMovieMusicServiceTest;
import douban.provider.OAuthDoubanProvider;
import douban.service.DoubanBookMovieMusicService;
import douban.service.DoubanMailService;
import douban.service.DoubanShuoService;
import net.htmlparser.jericho.Source;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by AAAAA on 2015/12/8.
 */
public class PlayGround {

    /**
     * <entry xmlns="http://www.w3.org/2005/Atom"
     * xmlns:db="http://www.douban.com/xmlns/"
     * xmlns:gd="http://schemas.google.com/g/2005"
     * xmlns:openSearch="http://a9.com/-/spec/opensearchrss/1.0/"
     * xmlns:opensearch="http://a9.com/-/spec/opensearchrss/1.0/"> @param args
     */
    public static void main(String[] args) {
//    //"douban_user_name":"刘方","douban_user_id":"137280810"
        String access_token="1eb7c6c007025b975c275440150712c5";
        String refresh_token="5db77c02dd7e738c19267408987b5aa4";
        String uid = "137280810";


        testAccessToken();


//    testAtomParse () ;
//    testGetDoubanShuoUser();
//    testFollowUser() ;
//    testGetBookInfo();
//        DoubanBookMovieMusicServiceTest doubanBookMovieMusicServiceTest = new DoubanBookMovieMusicServiceTest();
//        try {
//            doubanBookMovieMusicServiceTest.testGetBookInfoById();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }

    public static void testAtomParse() {
        FileInputStream fis = null;
        try {
            XmlNamespaceDictionary nameSpace = new XmlNamespaceDictionary().set("", "http://www.w3.org/2005/Atom").set("db", "http://www.douban.com/xmlns/").set("gd", "http://schemas.google.com/g/2005").set("openSearch", "http://a9.com/-/spec/opensearchrss/1.0/").set("opensearch", "http://a9.com/-/spec/opensearchrss/1.0/");
            XmlObjectParser parser = new XmlObjectParser(nameSpace);
            //JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
            fis = new FileInputStream("/home/zwei/doubantestxml");
            DoubanPeopleEntry result = parser.parseAndClose(fis, Charset.forName("utf-8"), DoubanPeopleEntry.class);
            System.out.println("result : " + result);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //自己改的
    //这是原来的public static String testAccessToken() {
    public AccessToken testAccessToken1(String name, String pwd, String captcha, String captchaid, Context context) {
        try {
            System.out.println("进入playgrond的testAccessToken1方法");
            OAuthDoubanProvider oauth = new OAuthDoubanProvider();
            oauth.setApiKey("04c0319d59f7e2370e4094d80de7a6a5").setSecretKey("830f91dc993dd2f3");
            oauth.addScope(RequestGrantScope.BASIC_COMMON_SCOPE).addScope(RequestGrantScope.SHUO_READ_SCOPE).addScope(RequestGrantScope.SHUO_WRITE_SCOPE)
                    .addScope(RequestGrantScope.BASIC_NOTE_SCOPE).addScope(RequestGrantScope.BOOK_READ_SCOPE).addScope(RequestGrantScope.EVENT_READ_SCOPE).addScope(RequestGrantScope.EVENT_WRITE_SCOPE)
                    .addScope(RequestGrantScope.MAIL_READ_SCOPE).addScope(RequestGrantScope.MAIL_WRITE_SCOPE).addScope(RequestGrantScope.MOVIE_READ_SCOPE).addScope(RequestGrantScope.MUSIC_READ_SCOPE);
            oauth.setRedirectUrl("http://myapp.com/callback");
            //BrowserLauncher.openURL(oauth.getGetCodeRedirectUrl());
            String url=oauth.getGetCodeRedirectUrl();

//
//
//            //模拟登录，打开登录界面，保存cookie
//            HttpPost httppost = new HttpPost("http://www.douban.com/accounts/login");
//            List<NameValuePair> namevaluepairs  = new ArrayList<NameValuePair>();//设置http post请求提交的数据
//            namevaluepairs.add(new BasicNameValuePair("source", "simple"));
//            namevaluepairs.add(new BasicNameValuePair("redir", "http://www.douban.com"));
//            namevaluepairs.add(new BasicNameValuePair("form_email", name));
//            namevaluepairs.add(new BasicNameValuePair("form_password", pwd));
//            namevaluepairs.add(new BasicNameValuePair("captcha-solution",captcha));
//            namevaluepairs.add(new BasicNameValuePair("captcha-id",captchaid));
//            namevaluepairs.add(new BasicNameValuePair("user_login", "登录"));
//            UrlEncodedFormEntity entity = null;
//            try {
//                entity = new UrlEncodedFormEntity(namevaluepairs,"utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            httppost.setEntity(entity);
//            DefaultHttpClient client = new DefaultHttpClient();  //  创建一个浏览器
//            HttpResponse response = null;  // 完成了用户登陆豆瓣的操作
//            try {
//                response = client.execute(httppost);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(response.getStatusLine().getStatusCode());//判断是否登录成功
//            Source source  = null;
//            try {
//                source = new Source(response.getEntity().getContent());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //System.out.println( source.toString());
//            CookieStore cookie =  client.getCookieStore(); // 获取登陆成功的cookie
//            //System.out.println( "cookie"+cookie.getCookies());
//            System.out.println( "------------------------------------");
//
//
//            //带着cookie访问豆瓣认证网站  模拟用户点击 同意按钮
//            //ck=Rw1e&oauth_token=6817c2017cc375dc38474604764a6194&
//            //oauth_callback=&ssid=9d9af9b0&confirm=%E5%90%8C%E6%84%8F
////            HttpGet gets= new HttpGet(url);
//            HttpPost post1 = new HttpPost(url);
//
////            String oauth_token =  url.substring(url.lastIndexOf("=")+1, url.length());
////            System.out.println(oauth_token);
//            List<NameValuePair> namevaluepairs1  = new ArrayList<NameValuePair>();
//            namevaluepairs1.add(new BasicNameValuePair("ck","R-OU"));
////            namevaluepairs1.add(new BasicNameValuePair("oauth_token",oauth_token));
////            namevaluepairs1.add(new BasicNameValuePair("oauth_callback",""));
//            namevaluepairs1.add(new BasicNameValuePair("ssid","df05cbd2"));
//            namevaluepairs1.add(new BasicNameValuePair("confirm","授权"));
//            UrlEncodedFormEntity entity1 = null;
//            try {
//                entity1 = new UrlEncodedFormEntity(namevaluepairs1,"utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            post1.setEntity(entity1);
//
//            DefaultHttpClient client2 = new DefaultHttpClient();
//            client2.setCookieStore(cookie);
//            HttpResponse  respose1 = null;
//            try {
//                respose1 = client2.execute(post1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            InputStream is = null;
//
//            try {
//                is = respose1.getEntity().getContent();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len= 0;
//            try {
//                while((len = is.read(buffer))!=-1){
//                    bos.write(buffer, 0, len);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println(new String(bos.toByteArray()));




//            function getParameter(parName){           var str = parName.toLowerCase() + "=";
//            var gvalue = "";
//            var HREF = location.href;
//            var upperHREF = location.href搜索.toLowerCase();
//            if(upperHREF.indexOf(str)>0){
//                gvalue = HREF.substring(upperHREF.indexOf(str) + str.length,upperHREF.length);
//                if(gvalue.indexOf('&')>0) gvalue = gvalue.substring(0,gvalue.indexOf('&'));
//                if(gvalue.indexOf("#")>0) gvalue = gvalue.split("#")[0];
//            }
//            return gvalue;
//            }

            //从返回的地址栏中获取code














            //使用内嵌浏览器完成授权，并拿到返回的code
//            //手工   登录授权获取code

            System.out.println(url);
            String code = "d6a7461d08da1497"; //这是最近一次的code，需要手动获取。

            AccessToken at = oauth.tradeAccessTokenWithCode(code);
            System.out.println("at : " + at.getAccessToken());
            System.out.println("uid : " + at.getDoubanUserId());
            return at;
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }







    public static String testAccessToken() {
        try {

            OAuthDoubanProvider oauth = new OAuthDoubanProvider();
            oauth.setApiKey("04c0319d59f7e2370e4094d80de7a6a5").setSecretKey("830f91dc993dd2f3");
            oauth.addScope(RequestGrantScope.BASIC_COMMON_SCOPE).addScope(RequestGrantScope.SHUO_READ_SCOPE).addScope(RequestGrantScope.SHUO_WRITE_SCOPE)
                    .addScope(RequestGrantScope.BASIC_NOTE_SCOPE).addScope(RequestGrantScope.BOOK_READ_SCOPE).addScope(RequestGrantScope.EVENT_READ_SCOPE).addScope(RequestGrantScope.EVENT_WRITE_SCOPE)
                    .addScope(RequestGrantScope.MAIL_READ_SCOPE).addScope(RequestGrantScope.MAIL_WRITE_SCOPE).addScope(RequestGrantScope.MOVIE_READ_SCOPE).addScope(RequestGrantScope.MUSIC_READ_SCOPE);
            oauth.setRedirectUrl("http://www.dongxuexidu.com");
            BrowserLauncher.openURL(oauth.getGetCodeRedirectUrl());
            System.out.println(oauth.getGetCodeRedirectUrl());
            System.out.print("Put the code you got here.[Enter]:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String code = br.readLine();
            System.out.println("code : " + code);
            AccessToken at = oauth.tradeAccessTokenWithCode(code);
            System.out.println("at : " + at.getAccessToken());
            System.out.println("uid : " + at.getDoubanUserId());

            return at.getAccessToken();
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void testSendingDoumail() {
        try {
            String accessToken = testAccessToken();
            DoubanMailService service = new DoubanMailService(accessToken);
            if (service.sendMail("abei", "test!", "ceshi")) {
                System.out.println("done!");
            } else {
                System.out.println("o shit");
            }
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testGetBookInfo() {
        try {
            //long bookId = 2023013;
            long bookId = 26664352;
            DoubanBookMovieMusicService service = new DoubanBookMovieMusicService();
            DoubanSubjectObj book = service.getMusicInfoById(2272292);
            System.out.println("title : " + book.getTitle());
            for (DoubanTagObj tag : book.getTags()) {
                System.out.println("tag, count : " + tag.getCount() + " , name : " + tag.getName());
            }
            System.out.println("rating, min : " + book.getRating().getMin() + ", max : " + book.getRating().getMax() + " , value : " + book.getRating().getValue() + " , count : " + book.getRating().getNumberOfRaters() + " , avg : " + book.getRating().getAverage());
            System.out.println("author : " + book.getAuthors().get(0).getName());
            for (DoubanAttributeObj att : book.getAttributes()) {
                System.out.println("att, name : " + att.getName() + " , value : " + att.getValue());
            }
            System.out.println("summary : " + book.getSummary());
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testGetDoubanShuoStatuses() {
        try {
            DoubanShuoService service = new DoubanShuoService();
            DoubanShuoStatusObj[] result = service.getStatusesByUserId("xxx");
            System.out.println("size : " + result.length);
            for (DoubanShuoStatusObj s : result) {
                System.out.println("text : " + s.getText() + " , title : " + s.getTitle());
            }
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testPostStatus() {
        try {
//      String accessToken = testAccessToken();
            String accessToken = "xxx";
            DoubanShuoService service = new DoubanShuoService();
            DoubanShuoAttachementObj att = generateAtt();
            if (service.postNewStatus("I like..", att, DefaultConfigs.API_KEY, accessToken)) {
                System.out.println("done!");
            } else {
                System.out.println("failed!");
            }
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testGetDoubanShuoUser() {
        try {
            DoubanShuoService service = new DoubanShuoService();
            DoubanShuoUserObj[] users = service.getFollowingUserByUserId("137280810");
            for (DoubanShuoUserObj user : users) {
                System.out.println("user name : " + user.getScreenName());
                System.out.println("user id : " + user.getUid());
                System.out.println("user full id : " + user.getId());
            }
            System.out.println("size : " + users.length);
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /*
    * 获取用户信息
    * eg：{"city":"天津","icon_avatar":"http:\/\/img3.douban.com\/pics\/icon\/user_icon.jpg","statuses_count":1,"screen_name":"刘方","following_count":0,"url":"http:\/\/www.douban.com\/people\/137280810\/","created_at":"2015-11-07 15:16:02","description":"","logged_in":true,"blocking":false,"following":false,"followers_count":0,"blocked":false,"location":"","small_avatar":"http:\/\/img3.douban.com\/icon\/user_normal.jpg","uid":"137280810","verified":false,"is_first_visit":true,"type":"user","id":"137280810","large_avatar":"http:\/\/img3.douban.com\/icon\/user_large.jpg"}*/
    public static void testFollowUser() {
        try {
//      String accessToken = testAccessToken();

            String accessToken = "1eb7c6c007025b975c275440150712c5";
            DoubanShuoService service = new DoubanShuoService();
//      boolean result = service.followUser("137280810", DefaultConfigs.API_KEY,accessToken);
            boolean result = service.followUser("137280810", DefaultConfigs.API_KEY, accessToken);
            if (result) {
                System.out.println("done!");
            } else {
                System.out.println("failed!");
            }
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /*
    * */
    public static void testGetRelationShip() {
        try {
            DoubanShuoService service = new DoubanShuoService();
            DoubanShuoService.DoubanShuoRelation relation = service.getRelationship("xxx", "xxx", DefaultConfigs.API_KEY);
            if (relation == DoubanShuoService.DoubanShuoRelation.FollowingOnly) {
                System.out.println("following");
            } else if (relation == DoubanShuoService.DoubanShuoRelation.BeFollowedOnly) {
                System.out.println("followed by");
            } else if (relation == DoubanShuoService.DoubanShuoRelation.Friend) {
                System.out.println("friend");
            } else {
                System.out.println("stranger");
            }
        } catch (DoubanException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void parseJson() {
        try {
            DoubanShuoAttachementObj att = generateAtt();
            JsonHttpContent content = new JsonHttpContent(new JacksonFactory(), att);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            content.writeTo(os);
            String result = new String(os.toByteArray());
            System.out.println("result ! : " + result);
            System.out.println("getdate : " + (String) content.getData());
        } catch (IOException ex) {
            Logger.getLogger(PlayGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static DoubanShuoAttachementObj generateAtt() {
        DoubanShuoMediaObj media = new DoubanShuoMediaObj();
        media.setHref("http://www.dongxuexidu.com");
        media.setSrc("http://www.dongxuexidu.com/img/logo75.jpg");
        media.setType("image");
        DoubanShuoAttachementObj att = new DoubanShuoAttachementObj();
        List<DoubanShuoMediaObj> ms = new ArrayList<DoubanShuoMediaObj>();
        ms.add(media);
        att.setMedias(ms);
        att.setDescription("http://www.dongxuexidu.com");
        att.setCaption("");
        att.setExpanedHref("http://www.dongxuexidu.com");
        att.setHref("http://www.dongxuexidu.com");
        att.setTitle("东学西读");
        att.setType("");
        return att;
    }

}
