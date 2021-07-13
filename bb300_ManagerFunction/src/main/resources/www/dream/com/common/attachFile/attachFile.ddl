drop table s_attach_file
drop table sm_post_attach

truncate table s_attach_file
truncate table sm_post_attach

create table s_attach_file(
	uuid				varchar2(36) 		primary key,
	saved_folder_path	varchar2(1000),
	pure_file_name		varchar2(100),
	multimedia_type		varchar2(100)
);


create table sm_post_attach(
	post_id			varchar2(4000),	 
	uuid			varchar2(36),
	primary key(post_id, uuid)
);
