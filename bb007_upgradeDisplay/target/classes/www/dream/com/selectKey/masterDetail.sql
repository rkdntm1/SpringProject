drop table s_detail;
drop table s_master;
drop SEQUENCE seq_master;

CREATE SEQUENCE seq_master;

create table s_master (
	id				number(9)			primary key,
	name			varchar2(100)		not null,		-- 100 : 전 지구적인 사람의 이름 길이까지 고려.
	reg_dt			timestamp			default sysdate not null --등록시점
);	

create table s_detail (
	m_id				number(9),
	id					number(9) 			primary key,
	info				varchar2(50) --연락처 정보
);
