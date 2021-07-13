package www.dream.com.party.model;

import lombok.Data;
import www.dream.com.common.model.CommonMngVO;

/**
 * 연락처 정보
 * @author qoddn987
 *
 */
@Data
public class ContactPoint extends CommonMngVO {
	private String contactPointType;//연락처 종류
	private String info;	//연락처 정보
}
