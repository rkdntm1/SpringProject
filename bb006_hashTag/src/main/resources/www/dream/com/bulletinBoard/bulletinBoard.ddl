drop SEQUENCE seq_post_id;
drop table s_post;
drop table s_board;

--id, name, description
create table s_board( -- 게시판
	id			number(9)		primary key,
	name		varchar2(100),
	description	varchar2(1000),
	reg_dt			timestamp			default sysdate not null, --등록시점
	upt_dt			timestamp			default sysdate not null --수정시점
);
insert into s_board(id, name, description)
	values(1, '공지사항', '드림 회사에서 드리는 공지사항');
insert into s_board(id, name, description)
	values(2, 'FAQ', '자주 문의되는 사항에 대한 답변들');
insert into s_board(id, name, description)
	values(3, '자유게시판', '회원이면 누구나 자유롭게 자신의 의견을 밝힐 수 있어요');
	
CREATE SEQUENCE seq_post_id;
--id, board_id, writer_id, title, content, read_cnt, like_cnt, dislike_cnt, reg_dt, upt_dt	
create table s_post( -- 게시판
	id				varchar2(4000)		primary key,
	board_id		number(9)			REFERENCES s_board(id),
	writer_id		varchar2(10)		REFERENCES s_party(user_id),
	title			varchar2(100),
	content			varchar2(4000),
	read_cnt		number(9)			default 0,
	like_cnt		number(9)			default 0,
	dislike_cnt		number(9)			default 0,
	reg_dt			timestamp			default sysdate not null, --등록시점
	upt_dt			timestamp			default sysdate not null --수정시점	
);	


create index idx_post_board_id on s_post(board_id, id)



insert into s_post(id, board_id, writer_id, title, content)
	values('00000', 1, 'admin', '우리 Dream 사랑해주세요', 'IT 용어 전문 게시판 서비스 회사입니다.');
insert into s_post(id, board_id, writer_id, title, content)
	values('00001', 1, 'admin', '우리 회사의 2022년 목표 회원수는 20억명', '모든 IT 전문가를 초빙합니다.');
