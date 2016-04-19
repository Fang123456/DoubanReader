package com.k.douban.domain;

//import com.google.gdata.data.douban.NoteEntry;

import douban.model.note.DoubanNoteEntryObj;

public class Note {
	private String title;
	private String content;
	private String can_reply;
	private String privacy ;
	private String pubdate;
	private String noteid;

	public String getNoteid() {
		return noteid;
	}

	public void setNoteid(String noteid) {
		this.noteid = noteid;
	}

	public DoubanNoteEntryObj getNoteEntry() {
		return noteEntry;
	}

	public void setNoteEntry(DoubanNoteEntryObj noteEntry) {
		this.noteEntry = noteEntry;
	}

	private DoubanNoteEntryObj noteEntry;
//	private NoteEntry noteEntry;
	public Note(){

	}
	public Note(String title, String content, String can_reply, String privacy,
			String pubdate) {
		this.title = title;
		this.content = content;
		this.can_reply = can_reply;
		this.privacy = privacy;
		this.pubdate = pubdate;
	}

//	public NoteEntry getNoteEntry() {
//		return noteEntry;
//	}
//	public void setNoteEntry(NoteEntry noteEntry) {
//		this.noteEntry = noteEntry;
//	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCan_reply() {
		return can_reply;
	}
	public void setCan_reply(String can_reply) {
		this.can_reply = can_reply;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

}
