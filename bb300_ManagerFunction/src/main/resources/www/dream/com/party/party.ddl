--Oracle 자료형 선택 시... 고민거리...
--int, long -> number(9), 19
--date -> 년월일(date), 일시(timestamp)
--boolean -> char(1)
drop table b_contact_point;
drop table b_party;
drop table b_contact_point_type;
--Sequence
drop SEQUENCE seq_party_id;

CREATE SEQUENCE seq_party_id START WITH -990000000 MINVALUE -990000000;

--party_type, description
create table b_party_type(
	party_type		char(10),
	description		varchar2(100)
);
insert into b_party_type(party_type, description)
	values('Admin', '관리자');
insert into b_party_type(party_type, description)
	values('Rider', '배달기사');
insert into b_party_type(party_type, description)
	values('Member', '사용자');
insert into b_party_type(party_type, description)
	values('Store', '가게');

--user_id, user_pwd, name, birth_dt, sex, enabled, reg_dt, upt_dt, descrim
create table b_party(
	user_id 		varchar2(10)	primary key,
	user_pwd 		varchar2(100) 	not null,	--100 : 암호화된 결과물까지 고려함
	name 			varchar2(100) 	not null,	--100 : 전지구적인 사람의 이름 길이까지 고려함
	birth_dt 		Date,						--생일 년월일
	sex 			char(1) 		default 1 not null,	--true male, false female
	enabled 		char(1)			default 1,
	reg_dt			timestamp		default sysdate not null,	--등록시점
	upt_dt			timestamp		default sysdate not null,	--수정시점
	descrim			varchar2(10)		not null
	--Admin용 속성 정의함
	--Manager용 속성 정의함
	--User용 속성 정의함
);
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('admin', '1234', '김이박', '1999-01-30', '1', '1', 'Admin');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('rider1', '1234', '김이홍', '2010-01-30', '0', '1', 'Rider');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('rider2', '1234', '김이정', '2005-01-30', '1', '1', 'Rider');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('store1', '1234', '김이홍', '2010-01-30', '0', '1', 'Rider');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('store2', '1234', '김이정', '2005-01-30', '1', '1', 'Rider');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('hong', '1234', '홍길동', '2005-01-30', '1', '1', 'Member');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('lee', '1234', '이순신', '2005-01-30', '0', '1', 'Member');
insert into b_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('ghost', '1234', '고스트', '2005-01-30', '0', '0', 'Member');

-- 각 행위자별 여러 연락처 정보 관리
--contact_point_type, description
create table b_contact_point_type(
	contact_point_type	char(10),
	description			varchar2(100)
);
insert into b_contact_point_type(contact_point_type, description)
	values('address', '주소지');
insert into b_contact_point_type(contact_point_type, description)
	values('addressNum', '전화번호');
insert into b_contact_point_type(contact_point_type, description)
	values('mobileNum', '핸드폰번호');

--연락처. 회원 탈퇴 시 연락처 정보는 실제 삭제 처리.
--user_id, contact_point_type, info, reg_dt, upt_dt
create table b_contact_point(
	user_id				varchar2(10),
	contact_point_type	char(10),
	info				varchar2(50),	--연락처 정보
	reg_dt				timestamp		default sysdate not null,	--등록시점
	upt_dt				timestamp		default sysdate not null,	--수정시점
	primary key (user_id, contact_point_type),
);
insert into b_contact_point(user_id, contact_point_type, info)
	values('admin', 'address', '서울시 금천구 가산동 312호');
insert into b_contact_point(user_id, contact_point_type, info)
	values('admin', 'addressNum', '02-123-2345');
insert into b_contact_point(user_id, contact_point_type, info)
	values('admin', 'mobileNum', '010-2232-2212');
insert into b_contact_point(user_id, contact_point_type, info)
	values('hong', 'address', '한양 남산동 333번지');
insert into b_contact_point(user_id, contact_point_type, info)
	values('hong', 'mobileNum', '010-0987-1234');
insert into b_contact_point(user_id, contact_point_type, info)
	values('lee', 'address', '조선 충청도 아산');
insert into b_contact_point(user_id, contact_point_type, info)
	values('lee', 'mobileNum', '010-8765-8593');
insert into b_contact_point(user_id, contact_point_type, info)
	values('rider1', 'address', '인천 주안동 25');
insert into b_contact_point(user_id, contact_point_type, info)
	values('rider1', 'mobileNum', '010-6789-5432');
insert into b_contact_point(user_id, contact_point_type, info)
	values('rider2', 'address', '인천 구월동 28');
insert into b_contact_point(user_id, contact_point_type, info)
	values('rider2', 'mobileNum', '010-2389-8789');	
insert into b_contact_point(user_id, contact_point_type, info)
	values('store1', 'address', '인천 주안동 25');
insert into b_contact_point(user_id, contact_point_type, info)
	values('store1', 'mobileNum', '010-6789-5432');
insert into b_contact_point(user_id, contact_point_type, info)
	values('store1', 'addressNum', '032-1234-1111');	
insert into b_contact_point(user_id, contact_point_type, info)
	values('store2', 'address', '인천 구월동 28');
insert into b_contact_point(user_id, contact_point_type, info)
	values('store2', 'mobileNum', '010-2389-8789');
	insert into b_contact_point(user_id, contact_point_type, info)
	values('store2', 'addressNum', '032-5678-8888');	

/*각 유저별 가질 수 있는 알러지 유형
--contact_allergy_type, allergy_type
create table b_contact_allergy_type(
	contact_allergy_type	varchar2(30),
	allergy_type			varchar2(30)
);	

insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('bean', '대두');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('pork', '돼지고기');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('beef', '소고기');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('crustacea', '갑각류');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('clam', '어패류');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('fish', '생선');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('milk', '우유');
insert into b_contact_allergy_type(contact_allergy_type, allergy_type)
	values('nuts', '견과류');

--user_id, contact_allergy_type, reg_dt, upt_dt
create table b_contact_allergy(
	user_id					varchar2(10),
	contact_allergy_type	varchar2(30),
	allergy_types_info		varchar2(200), --여러 알러지를 가지고 있는 사람들을 위해 
	reg_dt					timestamp		default sysdate not null,	--등록시점
	upt_dt					timestamp		default sysdate not null,	--수정시점
	primary key (user_id, contact_allergy_type)
);*/