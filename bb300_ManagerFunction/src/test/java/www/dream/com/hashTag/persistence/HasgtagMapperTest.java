package www.dream.com.hashTag.persistence;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.framework.util.StringUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HasgtagMapperTest {
	@Autowired
	private HashTagMapper hashTagMapper;

	@Test
	public void test010SelectMultipleId() {
		try {
			//,31,32,33,34,35   int[]
			System.out.println(StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(3)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
