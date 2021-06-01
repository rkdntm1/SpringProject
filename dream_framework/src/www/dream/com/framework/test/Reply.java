package www.dream.com.framework.test;

import www.dream.com.framework.langPosAnalyzer.HashTarget;

public class Reply {
	private String content;
	
	@HashTarget
	private Party writer;
	
	@HashTarget	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Party getWriter() {
		return writer;
	}
	
	public void setWriter(Party writer) {
		this.writer = writer;
	}
	
		
	
}
