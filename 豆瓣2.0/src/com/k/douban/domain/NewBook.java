package com.k.douban.domain;

public class NewBook {
//	<id>http://api.douban.com/book/subject/26665498</id>
//
//	<title>生命中最简单又最困难的事</title>
//
//	<category term="https://www.douban.com/2007#book" scheme="https://www.douban.com/2007#kind"/>
//
//
//			-<author>
//
//	<name>【美】大卫•福斯特•华莱士 著</name>
//
//	</author>
//
//
//			-<author>
//
//	<name>焉沁 绘</name>
//
//	</author>
//
//	<link rel="self" href="https://api.douban.com/book/subject/26665498"/>
//
//	<link rel="alternate" href="https://book.douban.com/subject/26665498/"/>
//
//	<link rel="image" href="https://img1.doubanio.com/spic/s28342288.jpg"/>
//
//	<link rel="mobile" href="https://m.douban.com/book/subject/26665498/"/>
//
//	<summary>两条小鱼在水里游泳，突然碰到一条从对面游来的老鱼向他们点头问好： “早啊，小伙子们。水里怎样？” 小鱼继续往前游了一会儿，其中一条终于忍不住了，他望着另一条，问道： “水是个什么玩意？” 这是美国作家大卫•福斯特•华莱士2005年在肯扬学院毕业典礼上的演讲，从生活中最显而易见的平常之事入手，讨论如何摆脱生命中的重复单调，获得内心自由，保持意识的清醒鲜活。华莱士提醒我们，日常生活就是我们本身，既绝望又禅意，而要“在繁琐无聊的日常中，日复一日地保持自觉与警醒，困难得不可想象”。 这场演讲当时默默无闻，之后却突然逆袭，演讲录音通过邮件和博客在朋友圈不断流转，引发广泛共鸣，后有工作室根据录音制作了短视频，在Youtube上，一周就超过400人点阅。图书出版之后受到更大的关注，被《时代杂志》认为是“对知识分子最后的演讲”，并与乔布斯的演讲一起入选“美国最具影响力的十大毕业演讲”。 遗憾的是，这位天才的作家却在3年后因严重的抑郁症自杀，这也让这篇演讲更加震撼：生活不会总是一帆风顺，我们要学会提醒自己走出思维定势的泥沼；给你身边的人多点空间——因为你不知道他们正面对怎样的困苦。</summary>
//
//	<db:attribute name="subtitle">日常生活就是我们的本身，既绝望又禅意</db:attribute>
//
//	<db:attribute name="isbn10">7569902920</db:attribute>
//
//	<db:attribute name="isbn13">9787569902921</db:attribute>
//
//	<db:attribute name="title">生命中最简单又最困难的事</db:attribute>
//
//	<db:attribute name="pages">152</db:attribute>
//
//	<db:attribute name="translator">龙彦</db:attribute>
//
//	<db:attribute name="translator">马磊</db:attribute>
//
//	<db:attribute name="author">【美】大卫•福斯特•华莱士 著</db:attribute>
//
//	<db:attribute name="author">焉沁 绘</db:attribute>
//
//	<db:attribute name="price">32.00</db:attribute>
//
//	<db:attribute name="publisher">北京时代华文书局·阳光博客</db:attribute>
//
//	<db:attribute name="binding">精装</db:attribute>
//
//	<db:attribute name="pubdate">2015-12</db:attribute>
//
//	<db:attribute name="author-intro">大卫•福斯特•华莱士（David Foster Wallace，1962-2008），美国最好的当代作家之一，与乔纳森•弗兰茨并称为美国当代文学“双璧”。 24岁时华莱士的过人天赋即得以展现，英语文学专业毕业论文也是他的第一部小说——《系统的笤帚》（The Broom of the System），让华莱士初现文坛即大放光彩。1993年凭借《无尽的玩笑》（Infinite Jest）获得麦克阿瑟基金（Mac Arthur Foundation）奖励，2005年《无尽的玩笑》被《时代杂志》评选为“1923年以来世界百部最佳英语长篇小说之一”，与詹姆士•乔伊斯的《尤利西斯》、威廉•加迪斯的《承认》和托马斯•品钦的《万有引力之虹》等相提并论。 在作品广受赞誉的同时，华莱士也饱受抑郁症折磨。2008年，华莱士在加州的家中上吊自杀，年仅46岁。 死后留下未完成的小说手稿《苍白帝王》，经编辑整理出版后，荣获2012年普利策奖小说奖提名。 生前唯一一次公开演讲的录音也在朋友圈和互联网广泛流传，出版后引起更大关注，被《时代杂志》评为“对知识分子的最后演讲”，与乔布斯一起入选“美国最具影响力的十大毕业典礼演讲”。</db:attribute>
//
//	<db:tag name="心理学" count="67"/>
//
//	<db:tag name="心灵成长" count="37"/>
//
//	<db:tag name="散文" count="25"/>
//
//	<db:tag name="成长" count="25"/>
//
//	<db:tag name="治愈性励志成长" count="19"/>
//
//	<db:tag name="温暖的书" count="17"/>
//
//	<db:tag name="David_Foster_Wallace" count="16"/>
//
//	<db:tag name="美国文学" count="14"/>
//
//	<gd:rating numRaters="29" min="0" max="10" average="8.8"/>

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	private String name;    //title
	private String description;    //
	private String summary;     //summary
	private String iconpath;    //link(2)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getIconpath() {
		return iconpath;
	}
	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}
	
	
}
