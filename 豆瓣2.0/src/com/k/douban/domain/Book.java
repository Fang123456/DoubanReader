package com.k.douban.domain;

/**
 * Created by AAAAA on 2015/12/14.
 */
public class Book {
//	id:<id>http://api.douban.com/collection/986155592</id>
//	title:<title>���� �ڶ� ����������������ѵ���</title>
// 	<updated>2015-12-11T13:20:24+08:00</updated>
//	subjectId:<link href="https://api.douban.com/book/subject/26665498" rel="https://www.douban.com/2007#subject"/>
//	summary:<summary>����������������ѵ��� ��̸�ע: </summary>

//	title1:<title>����������������ѵ���</title>
//	<name>����������?��˹��?����ʿ ��</name>
//	<link rel="image" href="https://img1.doubanio.com/spic/s28342288.jpg"/>>
//	summary1:<summary>����С����ˮ����Ӿ��ͻȻ����һ���Ӷ������������������ǵ�ͷ�ʺã� ���簡��С�����ǡ�ˮ���������� С�������ǰ����һ���������һ�������̲�ס�ˣ���������һ�����ʵ��� ��ˮ�Ǹ�ʲô���⣿�� �����������Ҵ���?��˹��?����ʿ2005���ڿ���ѧԺ��ҵ�����ϵ��ݽ��������������Զ��׼���ƽ��֮�����֣�������ΰ��������е��ظ�����������������ɣ�������ʶ�������ʻ����ʿ�������ǣ��ճ�����������Ǳ����Ⱦ��������⣬��Ҫ���ڷ������ĵ��ճ��У��ո�һ�յر����Ծ��뾯�ѣ����ѵò������󡱡� �ⳡ�ݽ���ʱĬĬ���ţ�֮��ȴͻȻ��Ϯ���ݽ�¼��ͨ���ʼ��Ͳ���������Ȧ������ת�������㷺���������й����Ҹ���¼�������˶���Ƶ����Youtube�ϣ�һ�ܾͳ���400�˵��ġ�ͼ�����֮���ܵ�����Ĺ�ע������ʱ����־����Ϊ�ǡ���֪ʶ���������ݽ����������ǲ�˹���ݽ�һ����ѡ���������Ӱ������ʮ���ҵ�ݽ����� �ź����ǣ���λ��ŵ�����ȴ��3��������ص�����֢��ɱ����Ҳ����ƪ�ݽ������𺳣����������һ����˳������Ҫѧ�������Լ��߳�˼ά���Ƶ����ӣ�������ߵ��˶��ռ䡪����Ϊ�㲻֪��������������������ࡣ</summary>
//	<db:attribute name="publisher">����ʱ��������֡����ⲩ��</db:attribute>
//	<db:attribute name="pubdate">2015-12</db:attribute>
//	<gd:rating numRaters="29" min="0" max="10" average="8.8"/>

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary1() {
        return summary1;
    }

    public void setSummary1(String summary1) {
        this.summary1 = summary1;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }



    private String title;
    private String updated;
    private String subjectId;
    private String summary;

    private String title1;
    private String name;
    private String imageUrl;
    private String summary1;
    private String publisher;
    private String pubdate;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private float rating;


}
