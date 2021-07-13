package www.dream.com.bulletinBoard.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.model.MultimediaType;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttachMapperTest {
	@Autowired
	private AttachFileVOMapper attachMapper;

	@Test
	public void test010GetList() {

		String postId = "TTTT2";
		List<AttachFileVO> list = new ArrayList<>();
		AttachFileVO avo = new AttachFileVO();
		avo.setUuid(UUID.randomUUID().toString());
		avo.setSavedFolderPath("asdd");
		avo.setPureFileName("qwer");
		avo.setMultimediaType(MultimediaType.image);
		list.add(avo);

		avo = new AttachFileVO();
		avo.setUuid(UUID.randomUUID().toString());
		avo.setSavedFolderPath("asdd");
		avo.setPureFileName("qwert");
		avo.setMultimediaType(MultimediaType.video);
		list.add(avo);

		attachMapper.insert(postId, list);

	}

	@Test
	public void test020Delete() {
		String postId = "TTTT3";
		attachMapper.delete(postId);
	}

}
