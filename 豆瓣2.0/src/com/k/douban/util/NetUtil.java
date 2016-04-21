package com.k.douban.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.k.douban.R;
import com.k.douban.domain.NewBook;
import douban.model.app.AccessToken;
import douban.playground.PlayGround;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.google.gdata.client.douban.DoubanService;

/**
 * Created by AAAAA on 2015/11/25.
 */

//������Ҫ������������صĲ���
public class NetUtil {
    /**
    * �ж��Ƿ���Ҫ��֤��,�����ؽڵ��id*/
    public static String isNeedCaptcha(Context context) throws Exception {
        String loginurl = context.getResources().getString(R.string.loginurl);
        URL url = new URL(loginurl);
        URLConnection urlConnection = url.openConnection();
        //���üܰ�jericho-html����url�з��ص�element��ͨ���ж� captcha-id�Ĵ��ڴӶ��ж��Ƿ�����֤��
        Source source = new Source(urlConnection);
        List<Element> elements = source.getAllElements("input");
        for (Element element : elements) {
            String result = element.getAttributeValue("name");
            if (result.equals("captcha-id")) {
                return element.getAttributeValue("value");
            }
        }
        return null;
    }

    /**
    * �õ���֤��ͼƬ*/
    public static Bitmap getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        return BitmapFactory.decodeStream(inputStream);
    }

    /**
     * ��accesstoken refreshtoken userid���浽 SharedPreferences��*/
    public static boolean Login(String name, String pwd, String captcha, String captchaid, Context context) throws Exception {
        //�����޷��ֶ��õ�code����������һ��
        SharedPreferences sp1 = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putString("username", "2797037168@qq.com");//�����޷��ֶ��õ�code����������һ��
        editor1.putString("userpwd", "111");//�����޷��ֶ��õ�code����������һ��
        editor1.commit();
        if (name.equals("2797037168@qq.com") && pwd.equals("111")) {
            return true;
        }
        return false;



//        System.out.println("����NETUtil��login����");
//        String apikey = "04c0319d59f7e2370e4094d80de7a6a5";
//        String secret = "830f91dc993dd2f3";
//        System.out.println("����PlayGround����");
//        PlayGround playGround = new PlayGround();
//        AccessToken accessToken = playGround.testAccessToken1(name,pwd,captcha,captchaid,context);
//        System.out.println("�õ�tokens");
//
////        System.out.println("���AccessToken" + accessToken.getAccessToken());
////        System.out.println("���RefreshToken" + accessToken.getRefreshToken());
////        System.out.println("���DoubanUserId" + accessToken.getDoubanUserId());
//
//        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("accesstoken", accessToken.getAccessToken());
//        editor.putString("refreshtoken", accessToken.getRefreshToken());
//        editor.putString("userid", accessToken.getDoubanUserId());
//        editor.commit();





//        //���������apiKey��secret
//        if (name.equals("2797037168@qq.com")&&pwd.equals("2797037168liufang")&&context.getResources().getString(R.string.access_token).equals("a79a9a020803df5df6cfce06180b9e17")){
//            return true;
//        }
//        System.out.println("������login����");
//        String apiKey="04c0319d59f7e2370e4094d80de7a6a5";
//        String secret="830f91dc993dd2f3";
//        String url="https://www.douban.com/service/auth2/auth?"+"client_id="+apiKey+"&redirect_uri=https://myapp.com/callback&"+"response_type=code&"+"scope=shuo_basic_r,shuo_basic_w";
//        //�ֶ���ȡcode��
//        String code="e0aa3bf527e71916";
//        String url2="https://www.douban.com/service/auth2/token?client_id="+apiKey+"&client_secret="+secret+"&redirect_uri=https://myapp.com/callback&grant_type=authorization_code&code="+code;
//        //post�����ȡaccess code
//        //���� POST ����
//        String sr=sendPost(url2);
//        System.out.println(sr);
//        String access_token="a79a9a020803df5df6cfce06180b9e17";
//        String refresh_token="84b6fe1e3fc19d288e24fa5abf44d778";
//        return  true;


        //��������������Ȩ��
          //�򿪵�¼���棬����cookie�����ѵ�¼�����cookie������֤
//        HttpPost httppost = new HttpPost("http://www.douban.com/accounts/login");
//        List<NameValuePair> namevaluepairs  = new ArrayList<NameValuePair>();
//        namevaluepairs.add(new BasicNameValuePair("source", "None"));
//        namevaluepairs.add(new BasicNameValuePair("redir", "http://www.douban.com"));
//        namevaluepairs.add(new BasicNameValuePair("form_email", name));
//        namevaluepairs.add(new BasicNameValuePair("form_password", pwd));
//        namevaluepairs.add(new BasicNameValuePair("captcha-solution",captcha));
//        namevaluepairs.add(new BasicNameValuePair("captcha-id",captchaid));
//        namevaluepairs.add(new BasicNameValuePair("user_login", "��¼"));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(namevaluepairs,"utf-8");
//        httppost.setEntity(entity);
//        //  ����һ�������
//        DefaultHttpClient client = new DefaultHttpClient();
//        // ������û���½����Ĳ���
//        HttpResponse response = client.execute(httppost);
//        System.out.print("����״̬��");
//        System.out.println(response.getStatusLine().getStatusCode());
//
//
//        System.out.println(SendGET(url));
//
//                // ��ȡ��½�ɹ���cookie,��ģ��ͬ�ⰴť
//        CookieStore cookie =  client.getCookieStore();
//        HttpPost post1 = new HttpPost(url);
////        String oauth_token =  url.substring(url.lastIndexOf("=")+1, url.length());
////        System.out.println(oauth_token);
//        List<NameValuePair> namevaluepairs1  = new ArrayList<NameValuePair>();
//        namevaluepairs1.add(new BasicNameValuePair("ck","DqzM"));
////        namevaluepairs1.add(new BasicNameValuePair("oauth_token",oauth_token));
////        namevaluepairs1.add(new BasicNameValuePair("oauth_callback",""));
//        namevaluepairs1.add(new BasicNameValuePair("ssid", "9861e703"));
//        namevaluepairs1.add(new BasicNameValuePair("confirm", "��Ȩ"));
//        UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(namevaluepairs1,"utf-8");
//        post1.setEntity(entity1);
//        DefaultHttpClient client2 = new DefaultHttpClient();
//        client2.setCookieStore(cookie);
//        HttpResponse  respose1 =   client2.execute(post1);
//
//        System.out.print("��ӡrespose1");
//        System.out.print(respose1);
        //��ȡcode


//        HttpGet httpRequest =new HttpGet(url);
//        try
//        {
//            //ȡ��HttpClient����
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//            //����HttpClient��ȡ��HttpResponse
//            HttpResponse httpResponse = httpclient.execute(httpRequest);
//            //����ɹ�
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
//            {
//                //ȡ�÷��ص��ַ���
//                String strResult = EntityUtils.toString(httpResponse.getEntity());
//                System.out.print(strResult);
//            }
//            else
//            {
//                System.out.print("�������!");
//            }
//        }
//        catch (ClientProtocolException e)
//        {
//            System.out.print(e.getMessage().toString());
//        }
//        catch (IOException e)
//        {
//            System.out.print(e.getMessage().toString());
//        }
//        catch (Exception e)
//        {
//            System.out.print(e.getMessage().toString());
//        }


//




        //
//        DoubanService myService=new DoubanService("�������douban",apiKey,secret);
//        System.out.print("please:");
//        String url1=myService.getAuthorizationUrl(null);
//        System.out.print(url1);


//        DoubanService myService=new DoubanService("�������douban",apiKey,secret);
//        System.out.print("please:");
//        String url=myService.getAuthorizationUrl(null);
//        byte buffer[]=new byte[1];
//        System.in.read(buffer);
//        ArrayList<String> tokens=myService.getAccessToken();
//        System.out.println(tokens.get(1));
//        System.out.println(tokens.get(2));


//
//
//
//        //��ȡ��Ȩ��ַ
//
//        HttpPost httppost = new HttpPost("http://www.douban.com/accounts/login");
//        //����http post�����ύ������
//        List<NameValuePair> namevaluepairs  = new ArrayList<NameValuePair>();
//        namevaluepairs.add(new BasicNameValuePair("source", "simple"));
//        namevaluepairs.add(new BasicNameValuePair("redir", "http://www.douban.com"));
//        namevaluepairs.add(new BasicNameValuePair("form_email", name));
//        namevaluepairs.add(new BasicNameValuePair("form_password", pwd));
//        namevaluepairs.add(new BasicNameValuePair("captcha-solution",captcha));
//        namevaluepairs.add(new BasicNameValuePair("captcha-id",captchaid));
//
//        namevaluepairs.add(new BasicNameValuePair("user_login", "��¼"));
//
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(namevaluepairs,"utf-8");
//        httppost.setEntity(entity);
//        //  ����һ�������
//        DefaultHttpClient client = new DefaultHttpClient();
//        // ������û���½����Ĳ���
//        HttpResponse response = client.execute(httppost);
//        System.out.println(response.getStatusLine().getStatusCode());
//        Source source  = new Source(response.getEntity().getContent());
//        System.out.println( source.toString());
//
//
//        // ��ȡ��½�ɹ���cookie
//        CookieStore cookie =  client.getCookieStore();
//
//
//        //����cookie���ʶ�����֤��վ
//        // ģ���û���� ͬ�ⰴť
//        //ck=Rw1e&oauth_token=6817c2017cc375dc38474604764a6194&
//        //oauth_callback=&ssid=9d9af9b0&confirm=%E5%90%8C%E6%84%8F
//
//        HttpPost post1 = new HttpPost(url);
//
//        String oauth_token =  url.substring(url.lastIndexOf("=")+1, url.length());
//        System.out.println(oauth_token);
//        List<NameValuePair> namevaluepairs1  = new ArrayList<NameValuePair>();
//        namevaluepairs1.add(new BasicNameValuePair("ck","Rw1e"));
//        namevaluepairs1.add(new BasicNameValuePair("oauth_token",oauth_token));
//        namevaluepairs1.add(new BasicNameValuePair("oauth_callback",""));
//        namevaluepairs1.add(new BasicNameValuePair("ssid","9d9af9b0"));
//        namevaluepairs1.add(new BasicNameValuePair("confirm","ͬ��"));
//        UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(namevaluepairs1,"utf-8");
//        post1.setEntity(entity1);
//        DefaultHttpClient client2 = new DefaultHttpClient();
//        client2.setCookieStore(cookie);
//        HttpResponse  respose1 =   client2.execute(post1);
//        InputStream is = respose1.getEntity().getContent();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len= 0;
//        while((len = is.read(buffer))!=-1){
//            bos.write(buffer, 0, len);
//        }
//        is.close();
//        System.out.println(new String( bos.toByteArray()));
//
//        //3. ��ȡ����Ȩ������ƺ���Կ
//        ArrayList<String>  tokens = myService.getAccessToken();
//        String accesstoken = tokens.get(0);
//        String tokensecret = tokens.get(1);
//        System.out.println(accesstoken);
//        System.out.println(tokensecret);
//        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("accesstoken", accesstoken);
//        editor.putString("tokensecret", tokensecret);
//        editor.commit();
//        return true;

    }

    /**
     * ��ȡ�������� */
    public static List<NewBook> getNewBooks(Context context ) throws Exception{
        String path =context.getResources().getString(R.string.newbookpath);
        System.out.println("��ȡ��������");
        URL url = new URL(path);
        URLConnection conn = url.openConnection();
        Source source = new Source(conn);
        List<NewBook> newbooks = new ArrayList<NewBook>();
        List<Element> lielements  = source.getAllElements("li");
        System.out.println("lielements: " + lielements.size());
        for(Element lielement : lielements){
            List<Element> childrenlists = lielement.getChildElements();
            if(childrenlists.size() == 2){

                if("detail-frame".equals(childrenlists.get(0).getAttributeValue("class"))){
                    NewBook newbook = new NewBook();
                    //��Ŀ��Ӧ��div��Ϣ
                    Element div = childrenlists.get(0);
                    List<Element> divChildren = div.getChildElements();
                    String name = divChildren.get(0).getTextExtractor().toString();
                    newbook.setName(name);
                    String description = divChildren.get(1).getTextExtractor().toString();
                    newbook.setDescription(description);
                    String summary = divChildren.get(2).getTextExtractor().toString();
                    newbook.setSummary(summary);

                    Element achild = childrenlists.get(1);
                    String iconpath = achild.getChildElements().get(0).getAttributeValue("src");
                    newbook.setIconpath(iconpath);
                    System.out.println("iconpath" + iconpath);

                    String subjectUrl = childrenlists.get(1).getAttributeValue("href");
                    subjectUrl=subjectUrl.substring(0,subjectUrl.length()-1);
                    System.out.println("subjectUrl"+subjectUrl);
                    int start=subjectUrl.lastIndexOf("/")+1;
                    String subjectid=subjectUrl.substring(start,subjectUrl.length());
                    newbook.setId(subjectid);
                    System.out.println("subjectid"+subjectid);
                    newbooks.add(newbook);
                }

            }

        }
        return newbooks;
    }


























    /*
    * ����url���һ�ȡ���ؽ��*/
    public static String SendGET(String url) {
        String result = "";//���ʷ��ؽ��
        BufferedReader read = null;//��ȡ���ʽ��

        try {
            //����url
            System.out.print(111);
            System.out.print(url);
            URL realurl = new URL(url);
            //������
            URLConnection connection = realurl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("<a href="https://www.baidu.com/s?wd=user-agent&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1dWmWwBnWKWnhcznAc3uW7h0AP8IA3qPjfsn1bkrjKxmLKz0ZNzUjdCIZwsrBtEXh9GuA7EQhF9pywdQhPEUiqkIyN1IA-EUBtkrjDLPWTkPjn3nHbLrHTLn163" target="_blank" class="baidu-highlight">user-agent</a>",
//            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //��������
            connection.connect();


            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶΣ���ȡ��cookies��
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;//ѭ����ȡ
            while ((line = read.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {//�ر���
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.print(555);
        return result;
    }

    /*
    * ����post����*/
    public static String sendPost(String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            //out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��" + e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
//
//    /*
//    * ��ȡ�鼮��Ϣ*/
//    public static List<NewBook> getNewBooks() throws Exception {
//        String path = "http://book.douban.com/latest";
//        URL url = new URL(path);
//        URLConnection conn = url.openConnection();
//        Source source = new Source(conn);
//        List<NewBook> newbooks = new ArrayList<NewBook>();
//        List<Element> lielements = source.getAllElements("li");
//        System.out.println(lielements.size());
//        for (Element lielement : lielements) {
//            List<Element> childrenlists = lielement.getChildElements();
//            if (childrenlists.size() == 2) {
//                if ("detail-frame".equals(childrenlists.get(0).getAttributeValue("class"))) {
//                    NewBook newbook = new NewBook();
//                    //��Ŀ��Ӧ��div��Ϣ
//                    Element div = childrenlists.get(0);
//                    List<Element> divChildren = div.getChildElements();
//                    String name = divChildren.get(0).getTextExtractor().toString();
//                    newbook.setName(name);
//                    String description = divChildren.get(1).getTextExtractor().toString();
//                    newbook.setDescription(description);
//                    String summary = divChildren.get(2).getTextExtractor().toString();
//                    newbook.setSummary(summary);
//
//                    Element achild = childrenlists.get(1);
//                    String iconpath = achild.getChildElements().get(0).getAttributeValue("src");
//                    newbook.setIconpath(iconpath);
//                    newbooks.add(newbook);
//
//                    System.out.println("name" + name);
//                    System.out.println("description" + description);
//                    System.out.println("summary" + summary);
//                    System.out.println("src" + iconpath);
//                    System.out.println("-----------------");
//
//                }
//
//            }
//
//        }
//        return newbooks;
//    }

}

