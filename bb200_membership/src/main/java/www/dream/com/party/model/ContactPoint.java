package www.dream.com.party.model;

import lombok.Data;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.langPosAnalyzer.HashTarget;

/**
 *  연락처정보
 * @author YongHoon Kim
 */
@Data
public class ContactPoint extends CommonMngVO {
	private String contactPointType; // 연락처 종류
	private String info; //연락처 정보
}
