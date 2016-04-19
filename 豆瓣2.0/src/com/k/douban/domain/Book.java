package com.k.douban.domain;

/**
 * Created by AAAAA on 2015/12/14.
 */
public class Book {
//	id:<id>http://api.douban.com/collection/986155592</id>
//	title:<title>刘方 在读 生命中最简单又最困难的事</title>
// 	<updated>2015-12-11T13:20:24+08:00</updated>
//	subjectId:<link href="https://api.douban.com/book/subject/26665498" rel="https://www.douban.com/2007#subject"/>
//	summary:<summary>生命中最简单又最困难的事 简短附注: </summary>

//	title1:<title>生命中最简单又最困难的事</title>
//	<name>【美】大卫?福斯特?华莱士 著</name>
//	<link rel="image" href="https://img1.doubanio.com/spic/s28342288.jpg"/>>
//	summary1:<summary>两条小鱼在水里游泳，突然碰到一条从对面游来的老鱼向他们点头问好： “早啊，小伙子们。水里怎样？” 小鱼继续往前游了一会儿，其中一条终于忍不住了，他望着另一条，问道： “水是个什么玩意？” 这是美国作家大卫?福斯特?华莱士2005年在肯扬学院毕业典礼上的演讲，从生活中最显而易见的平常之事入手，讨论如何摆脱生命中的重复单调，获得内心自由，保持意识的清醒鲜活。华莱士提醒我们，日常生活就是我们本身，既绝望又禅意，而要“在繁琐无聊的日常中，日复一日地保持自觉与警醒，困难得不可想象”。 这场演讲当时默默无闻，之后却突然逆袭，演讲录音通过邮件和博客在朋友圈不断流转，引发广泛共鸣，后有工作室根据录音制作了短视频，在Youtube上，一周就超过400人点阅。图书出版之后受到更大的关注，被《时代杂志》认为是“对知识分子最后的演讲”，并与乔布斯的演讲一起入选“美国最具影响力的十大毕业演讲”。 遗憾的是，这位天才的作家却在3年后因严重的抑郁症自杀，这也让这篇演讲更加震撼：生活不会总是一帆风顺，我们要学会提醒自己走出思维定势的泥沼；给你身边的人多点空间——因为你不知道他们正面对怎样的困苦。</summary>
//	<db:attribute name="publisher">北京时代华文书局·阳光博客</db:attribute>
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
