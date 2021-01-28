数据表介绍

--1.学生表
Student(SId,Sname,Sage,Ssex)
--SId 学生编号,Sname 学生姓名,Sage 出生年月,Ssex 学生性别

--2.课程表
Course(CId,Cname,TId)
--CId 课程编号,Cname 课程名称,TId 教师编号

--3.教师表
Teacher(TId,Tname)
--TId 教师编号,Tname 教师姓名

--4.成绩表
SC(SId,CId,score)
--SId 学生编号,CId 课程编号,score 分数


1. 查询" 01 "课程比" 02 "课程成绩高的学生的信息及课程分数

select * from student RIGHT JOIN(
	select t1.SId, class1, class2 from
	(SELECT SId, score as class1 from sc where sc.CId = '01')as t1,
	(SELECT SId, score as class2 from sc where sc.CId = '02')as t2
	where t1.SId = t2.SId and class1 > class2
)r
on student.SId = r.SId

1.1 查询同时存在" 01 "课程和" 02 "课程的学生情况

select * from student right join(
	select t1.SId, class1, class2 from
	(select SId, score as class1 from sc where sc.CId = '01')as t1,
	(select SId, score as class2 from sc where sc.CId = '02')as t2 
	where t1.SId = t2.SId
)r
on student.SId = r.SId

1.2 查询存在" 01 "课程但可能不存在" 02 "课程的情况(不存在时显示为 null )

select * from 
(SELECT * from sc where sc.CId = '01')as t1 
left JOIN
(select * from sc where sc.CId = '02')as t2
on t1.SId = t2.SId

left join 时 主表在左 以左表为准 右表没有的部分则为null
right join 时 主表在右 以右表为准 左表没有的部分则为null

1.3 查询不存在" 01 "课程但存在" 02 "课程的情况

select * from sc where sc.CId = '02' and 
sc.SId not in (
select SId from sc where sc.CId = '01'
)

2. 查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩

select student.SId, student.Sname, R.avg_score FROM student RIGHT join(
	select SId, AVG(score) as avg_score from sc GROUP BY sc.SId HAVING avg_score >= '60'
)as R 
on student.SId = R.SId

3. 查询在 SC 表存在成绩的学生信息

select * FROM student RIGHT JOIN
(select DISTINCT SId from sc)as R 
on student.SId = R.SId

4. 查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩(没成绩的显示为 null )

select student.SId, student.sname, class_num, total_score from student LEFT JOIN (
	select t1.SId, class_num, total_score from 
	(select SId, count(1) as class_num from sc group by sc.SId)as t1,
	(select SId, sum(score) as total_score from sc group by sc.SId) as t2
	where t1.SId = t2.SId
)as R 
on student.SId = R.SId

4.1 查有成绩的学生信息
使用right join即可 或者使用in
select student.SId, student.sname, class_num, total_score from student right JOIN (
	select t1.SId, class_num, total_score from 
	(select SId, count(1) as class_num from sc group by sc.SId)as t1,
	(select SId, sum(score) as total_score from sc group by sc.SId) as t2
	where t1.SId = t2.SId
)as R 
on student.SId = R.SId

5. 查询「李」姓老师的数量 

select count(1) from teacher where teacher.Tname LIKE '李%'

6. 查询学过「张三」老师授课的同学的信息 

select * from student where student.SId in (
 select sc.SId from sc,course,teacher where sc.CId = course.CId and course.TId = teacher.TId and teacher.Tname = '张三'
)

7. 查询没有学全所有课程的同学的信息 

查询学了所有课程的同学时比较所有课程数量和选课数量相等即可
select * from student where student.SId not in (
	select SId FROM sc GROUP BY SId HAVING count(sc.SId) = (select count(cid) from course)
)

8. 查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息 

select * from student where student.SId in (
	select SId FROM sc where sc.CId in (
	select CId from sc where sc.SId = '01'
	) and sc.SId <> '01'
)

9. 查询和" 01 "号的同学学习的课程完全相同的其他同学的信息 

	select sid from sc where sc.SId not in (
	select SId FROM sc where sc.CId not in (
	select CId from sc where sc.SId = '01'
	) )
	 and sc.SId <> '01' GROUP BY sc.SId 
	HAVING count(1) = (select count(1) from sc where SId = '01')
)

10. 查询没学过"张三"老师讲授的任一门课程的学生姓名 

select student.Sname from student where student.SId not in (
	select sc.SId from sc, course, teacher where sc.CId = course.CId and 
	teacher.TId = course.TId and teacher.Tname = '张三'
)

11. 查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩 

select student.SId, student.Sname, avg_score from student RIGHT JOIN 
(
	SELECT t.SId, AVG(score) as avg_score  from (select * from sc where score < 60) as t GROUP BY t.SId having count(1) >= 2
)as R
on student.SId = R.SId

12. 检索" 01 "课程分数小于 60，按分数降序排列的学生信息

select * from student RIGHT JOIN (
	SELECT SId, score from sc where sc.CId = '01' and score < 60 
	ORDER BY score DESC
)as R 
on student.SId = R.SId

13. 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩

存疑
select * from student RIGHT JOIN (
	select sc.*,avg_score from sc LEFT JOIN(
		select SId, AVG(score) as avg_score from sc GROUP BY sc.SId
	)as T 
	ON sc.SId = T.SId

	ORDER BY avg_score DESC
)as R 
on student.SId = R.SId

14. 查询各科成绩最高分、最低分和平均分：
	
    以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
    及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
    要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
	
	select course.TId 课程ID, course.Cname 课程名, max_score 最高分, min_score 最低分,
avg_score 平均分, pass_rate 及格率, medium_rate 中等率, fine_rate 优良率, excellent_rate 优秀率, num 选修人数 from course left join(
SELECT CId, Max(score) as max_score , Min(score) as min_score , avg(score) as avg_score,
sum(case when sc.score >= '60' THEN 1 ELSE 0 END)/count(*) as pass_rate,
sum(case when sc.score >= '70' and sc.score < '80' then 1 else 0 end)/count(*) as medium_rate,
sum(case when sc.score >= '80' and sc.score < '90' then 1 else 0 end)/count(*) as fine_rate,
sum(case when sc.score >= '90' and sc.score <='100' then 1 else 0 end)/count(*) as 
excellent_rate,
count(1) as num
from sc GROUP BY sc.CId
)as R 
on course.CId = R.CId
ORDER BY num desc, course.TId asc

15. 按各科成绩进行排序，并显示排名， Score 重复时保留名次空缺

15.1 按各科成绩进行排序，并显示排名， Score 重复时合并名次

16.  查询学生的总成绩，并进行排名，总分重复时保留名次空缺

16.1 查询学生的总成绩，并进行排名，总分重复时不保留名次空缺

17. 统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比

select course.TId 课程ID, course.Cname 课程名, A_rate , B_rate , C_rate , D_rate from course left join(
SELECT CId,
sum(case when sc.score < '60' THEN 1 ELSE 0 END)/count(*) as D_rate,
sum(case when sc.score >= '60' and sc.score < '70' then 1 else 0 end)/count(*) as C_rate,
sum(case when sc.score >= '70' and sc.score < '85' then 1 else 0 end)/count(*) as B_rate,
sum(case when sc.score >= '85' and sc.score <='100' then 1 else 0 end)/count(*) as 
A_rate 
from sc GROUP BY sc.CId
)as R 
on course.CId = R.CId

18. 查询各科成绩前三名的记录

19. 查询每门课程被选修的学生数 

SELECT * from course LEFT JOIN (
SELECT sc.CId, count(*) as 选修数 from sc GROUP BY sc.CId
)as R 
on course.CId = R.CId

20. 查询出只选修两门课程的学生学号和姓名 

select student.SId, student.Sname FROM student RIGHT JOIN(
 SELECT sc.SId, count(*) from sc GROUP BY sc.SId HAVING count(*) = 2
)as R 
on student.SId = R.SId

21. 查询男生、女生人数

SELECT count(*) as 人数, student.Ssex from student GROUP BY student.Ssex

22. 查询名字中含有「风」字的学生信息

SELECT * FROM student where student.Sname LIKE '%风%'

23. 查询同名同性学生名单，并统计同名人数

SELECT student.* , count(1) from student GROUP BY student.Sname, student.Ssex having count(1) > 1

24. 查询 1990 年出生的学生名单

SELECT * from student where YEAR(Sage) = '1990'

25. 查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列

SELECT sc.CId, avg(sc.score) from sc group by sc.CId ORDER BY avg(sc.score) DESC, sc.CId asc 

26. 查询平均成绩大于等于 85 的所有学生的学号、姓名和平均成绩 

SELECT student.SId, student.Sname, avgScore FROM student RIGHT JOIN (
 SELECT sc.SId, avg(sc.score) as avgScore from sc GROUP BY sc.SId HAVING avg(sc.score) >= 85
)as R 
on student.SId = R.SId

27. 查询课程名称为「数学」，且分数低于 60 的学生姓名和分数 

SELECT student.Sname, sc.score from student, sc, course where student.SId = sc.SId and sc.CId = course.CId and course.Cname = '数学' and sc.score < 60

28. 查询所有学生的课程及分数情况（存在学生没成绩，没选课的情况）

SELECT * from student LEFT JOIN (
 SELECT sc.CId, sc.SId, sc.score, course.Cname from course LEFT JOIN sc
 ON course.CId = sc.CId
)as R 
on student.SId = R.SId

29. 查询任何一门课程成绩在 70 分以上的姓名、课程名称和分数

SELECT student.Sname, course.Cname, sc.score from student, sc, course WHERE student.SId = sc.SId and sc.CId = course.CId and 
sc.score > 70

30. 查询不及格的课程

select student.Sname, sc.score, course.Cname from student, course, sc where sc.score < 60
and student.SId = sc.SId and sc.CId = course.CId

31. 查询课程编号为 01 且课程成绩在 80 分以上的学生的学号和姓名

select student.Sname, student.SId from student, sc where sc.score >= 80
and sc.CId = '01' and student.SId = sc.SId

32. 求每门课程的学生人数 

SELECT * FROM course LEFT JOIN (
SELECT count(1) as '人数', sc.CId FROM sc GROUP BY CId
)as R 
on course.CId = R.CId

33. 成绩不重复，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩

按照成绩排序后limit 1即可
SELECT student.*, score from student right join(
SELECT  score, sc.SId FROM sc, course, teacher where sc.CId = course.CId 
and course.TId = teacher.TId and teacher.Tname = '张三' ORDER BY score DESC LIMIT 1
)as R 
on student.SId = R.SId

34. 成绩有重复的情况下，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩

找出最高成绩后in这个最高成绩即可
SELECT student.*, score from student, sc where student.SId = sc.SId and sc.score in (
SELECT max(score) from sc, course, teacher where sc.CId = course.CId 
and course.TId = teacher.TId and teacher.Tname = '张三'
)

35. 查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩 

要求课程不同且成绩相同
SELECT * from sc as a where (
SELECT count(1) from sc as b where a.score = b.score and a.CId <> b.CId 
)>1

36. 查询每门功成绩最好的前两名

首先 a.CId = b.CId and a.score < b.score 自关联查询成绩小于原成绩的所有记录
GROUP BY a.CId , a.SId 通过cid sid分组可以得到该分数比多少个其他记录低
其中 第一的count是0 第二的count是1
select * from sc as a LEFT JOIN sc as b 
on a.CId = b.CId and a.score < b.score
GROUP BY a.CId , a.SId
HAVING count(b.CId) < 2
ORDER BY a.CId

37. 统计每门课程的学生选修人数（超过 5 人的课程才统计）。

SELECT * from course RIGHT JOIN (
SELECT count(1) as '人数', sc.CId from sc GROUP BY sc.CId HAVING count(1) > 5
)as R 
on course.CId = R.CId

38. 检索至少选修两门课程的学生学号 

SELECT sc.SId, count(1) as '课程数' from sc GROUP BY sc.SId HAVING count(1) >= 2

39. 查询选修了全部课程的学生信息

SELECT student.* from student where student.SId in (
SELECT sc.SId from sc GROUP BY sc.SId HAVING count(1) = (
SELECT count(1) from course 
)
)

40. 查询各学生的年龄，只按年份来算 

41. 按照出生日期来算，当前月日 < 出生年月的月日则，年龄减一

42. 查询本周过生日的学生

43. 查询下周过生日的学生

44. 查询本月过生日的学生

45. 查询下月过生日的学生


