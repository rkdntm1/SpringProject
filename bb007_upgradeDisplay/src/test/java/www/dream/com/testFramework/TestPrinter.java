package www.dream.com.testFramework;

import org.junit.Test;

import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.framwork.printer.TableHeader;

public class TestPrinter {
	@Test
	public void test() {
		System.out.println(TableHeader.print(PostVO.class));
	}
}
