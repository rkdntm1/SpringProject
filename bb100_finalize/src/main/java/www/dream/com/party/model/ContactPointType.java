package www.dream.com.party.model;

public enum ContactPointType {
	address("주소지"), phoneNum("주소지에 있는 전화번호"), mobileNum("핸드폰 번호");

	private String validatingRegEx; // 검사 유효성 검사 용도 정규식

	private ContactPointType(String validatingRegEx) {
		this.validatingRegEx = validatingRegEx;
	}
}
