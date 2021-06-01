package www.dream.com.framwork.test;

import java.util.ArrayList;
import java.util.List;

import www.dream.com.framwork.langPosAnalyzer.HashTarget;

@HashTarget
public class Party {
	private String name;
	
	@HashTarget
	private List<ContactPoint> listContactPoint = new ArrayList<>();
	
	@HashTarget
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addCP(ContactPoint cp) {
		listContactPoint.add(cp);
	}
}
