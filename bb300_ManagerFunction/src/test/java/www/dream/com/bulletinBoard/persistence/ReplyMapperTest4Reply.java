package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyMapperTest4Reply {
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private ReplyMapper postMapper;

	@Test
	public void test040GetList() {
		try {
			replyMapper.getReplyListWithPaging("00002", 
					"00002".length() + ReplyVO.ID_LENGTH,
					new Criteria())
			.forEach(reply->{
				System.out.println(reply);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void test000InsertReply() {
		try {
			//자게 최신글 찾아서 original(원글)로 삼자
			ReplyVO original = postMapper.getList(3, new Criteria()).get(0);
			//댓글 아무렇게 만들어서 insert
			Member lee = new Member("lee");
			IntStream.rangeClosed(0, 1).forEach(i->{
				ReplyVO reply = new ReplyVO("content" + i, lee);
				replyMapper.insertReply(original.getId(), reply);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test002InsertReplyOfReply() {
		try {
			//자게 최신글 찾아서 original(원글)로 삼자
			//댓글 아무렇게 만들어서 insert
			Member lee = new Member("lee");
			IntStream.rangeClosed(0, 1).forEach(i->{
				ReplyVO reply = new ReplyVO("content" + i, lee);
				replyMapper.insertReply("0000200006", reply);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test030FindById() {
		try {
			ReplyVO post = postMapper.findReplyById("0000200006");
			System.out.println(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}







