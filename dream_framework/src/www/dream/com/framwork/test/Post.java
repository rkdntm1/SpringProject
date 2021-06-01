package www.dream.com.framwork.test;

import www.dream.com.framwork.langPosAnalyzer.HashTarget;

public class Post extends Reply {
	private String title;
	
	@HashTarget
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
