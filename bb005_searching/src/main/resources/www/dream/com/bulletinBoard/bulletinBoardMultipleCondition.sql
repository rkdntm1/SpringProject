create table s_post( -- 게시판
	id				varchar2(4000)		primary key,
	board_id		number(9)			REFERENCES s_board(id),
	writer_id		varchar2(10)		REFERENCES s_party(user_id),
	title			varchar2(100),
  content			varchar2(4000),

    select p.*
	  from s_post p
	 where board_id = 3
	   and (
	   	   title like '%녕%'
	   	   or
	   	   content like '%안%'
	   	   )	  
 order by id desc
 OFFSET 0 ROWS FETCH FIRST 10 ROWS ONLY	 
 
  SELECT * FROM BLOG
  
   SELECT * FROM BLOG
    where
         state = #{state}
  
  SELECT * FROM BLOG
   where
         state = #{state}
        AND title like #{title}
        
SELECT * FROM BLOG
  where
        title like #{title}
        AND author_name like #{author.name}
        

Map<String, String> myMap = new HashMap<>();
myMap.put("T", "사랑");
myMap.put("C", "행복");

select *
  from s_post
<trim prefix="where (" suffix=")" prefixOverrides="or">
	<foreach collection="myMap" item="value" index="key">
		<trim prefix = "or">
			<if test="key == 'C'.tostring()">
				content = #{value}
			</if>
			<if test="key == 'T'.tostring()">
				title = #{value}
			</if>
		</trim>
	</foreach>
</trim>

select *
  from s_post
  where (
				content = '행복'
				or title = '사랑'
		)