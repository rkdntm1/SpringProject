package www.dream.com.framwork.test;

import www.dream.com.framwork.langPosAnalyzer.HashTarget;

public class Reply {
	private String content;
	
	private Party writer;
	
	@HashTarget
	public String getContent() {
		return content;
	}
	
	@HashTarget
	public Party getWriter() {
		return writer;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setWriter(Party writer) {
		this.writer = writer;
	}
}
