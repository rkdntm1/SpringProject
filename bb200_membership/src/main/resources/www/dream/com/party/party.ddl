--Oracle 자료형 선택시 고민거리
--int, long -> number(9), (19)
--date -> 년월일(Date), 일시(timestamp)
--boolean -> char(1)
drop table s_contact_point;
drop table s_party;
drop table s_contact_point_type;
drop table s_party_type;
--Sequence
drop SEQUENCE seq_party_id;

CREATE SEQUENCE seq_party_id START WITH -990000000 MINVALUE -990000000;

--party_type, description
create table s_party_type (
	party_type			char(10),
	description			varchar2(100)
);
insert into s_party_type(party_type, description)
	values('Admin', '관리자');
insert into s_party_type(party_type, description)
	values('Manager', '운영자');
insert into s_party_type(party_type, description)
	values('User', '사용자');
	
--user_id, user_pwd, name, birth_dt, sex, enabled, reg_dt, upt_dt, descrim
create table s_party (
	user_id			varchar2(10)		primary key,
	user_pwd		varchar2(100)		not null, 		-- 100 : 암호화된 결과물까지 고려.
	name			varchar2(100)		not null,		-- 100 : 전 지구적인 사람의 이름 길이까지 고려.
	birth_dt		Date,						 		-- 년 월 일
	sex				char(1)				default 1 not null, -- true : male, false: female 
	enabled			char(1) 			default 1,
	reg_dt			timestamp			default sysdate not null, --등록시점
	upt_dt			timestamp			default sysdate not null, --수정시점
	descrim			varchar2(10)		not null
	--Admin용 속성 정의함
	--Manager용 속성 정의함
	--User용 속성 정의함
);	
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('admin', '1234', '김이박', '1999-01-30', '1', '1', 'Admin');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('mana1', '1234', '김이홍', '2010-01-30', '0', '1', 'Manager');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('mana2', '1234', '김이정', '2005-01-30', '1', '1', 'Manager');
	
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('hong', '1234', '홍길동', '2005-01-30', '1', '1', 'User');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('lee', '1234', '이순신', '2005-01-30', '0', '1', 'User');
insert into s_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
	values('gosth', '1234', '고스트', '2005-01-30', '0', '0', 'User');
	
-- 각 행위자별 여러 연락처 정보 관리
--party_type, description
create table s_contact_point_type (
	contact_point_type			char(10),
	description					varchar2(100)
);
insert into  s_contact_point_type(contact_point_type, description)
	values('address', '주소지');
insert into  s_contact_point_type(contact_point_type, description)
	values('phoneNum', '주소지에 있는 전화번호');
insert into  s_contact_point_type(contact_point_type, description)
	values('mobileNum', '핸드폰 번호');
insert into  s_contact_point_type(contact_point_type, description)
	values('nuTube', '누투브');
	
--연락처, 회원 탈퇴 시 연락처 정보는 실제 삭제 처리.
--user_id, contact_point_type, info
create table s_contact_point (
	user_id					varchar2(10),
	contact_point_type  	char(10),
	info					varchar2(50), --연락처 정보
	reg_dt					timestamp			default sysdate not null, --등록시점
	upt_dt					timestamp			default sysdate not null, --수정시점
	primary key (user_id, contact_point_type),
	CONSTRAINT fk_cp_party FOREIGN KEY (user_id) REFERENCES s_party(user_id)
);
insert into s_contact_point(user_id, contact_point_type, info)
	values('admin', 'address', '서울시 금천구 가산동 312호');
insert into s_contact_point(user_id, contact_point_type, info)
	values('admin', 'phoneNum', '02-123-2345');
insert into s_contact_point(user_id, contact_point_type, info)
	values('admin', 'mobileNum', '010-2232-2212');	
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana1', 'address', '서울시 금천구 가산동 314호');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana1', 'phoneNum', '02-133-2345');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana1', 'mobileNum', '010-6232-2212');	
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana2', 'address', '세종시 금천구 가산동 314호');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana2', 'phoneNum', '033-133-2345');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana2', 'mobileNum', '018-6232-2212');
insert into s_contact_point(user_id, contact_point_type, info)
	values('hong', 'address', '한양 남산동 333번지');
insert into s_contact_point(user_id, contact_point_type, info)
	values('lee', 'address', '조선 충청도 아산');
	


--Each(각각) 전략으로 행위자 개념을 Table로 만들기 예시
create table s_comInfo (
	user_id			varchar2(10)		primary key,
	reg_dt			timestamp			default sysdate not null, --등록시점
	upt_dt			timestamp			default sysdate not null --수정시점
);	

create table s_party (
	user_id			varchar2(10)		primary key,
	user_pwd		varchar2(100)		not null, 		-- 100 : 암호화된 결과물까지 고려.
	name			varchar2(100)		not null,		-- 100 : 전 지구적인 사람의 이름 길이까지 고려.
	birth_dt		Date,						 		-- 년 월 일
	sex				char(1)				default 1 not null, -- true : male, false: female 
	enabled			char(1) 			default 1
);	

create table s_admin (
	user_id			varchar2(10)		primary key,
);	
create table s_manager (
	user_id			varchar2(10)		primary key,
);	
create table s_user (
	user_id			varchar2(10)		primary key,
);	

select a.*, p.*, c.*
  from s_admin a, s_party p, s_comInfo c
 where a.user_id = p.user_id
   and p.user_id = c.user_id