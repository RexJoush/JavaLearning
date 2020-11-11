

select * from student;


-- 查询 姓名 和 年龄
select
    name, -- 姓名
    age --年龄
from
    student; -- 学生表

-- 去除重复的结果集
select distinct address from student;

-- 计算列
select name,math,english,math+english from student;

-- 如果有 null 参与计算，计算结果都为 null
    -- 则设置如果为空，则令为 0
select name,math,english, math + ifnull(english, 0) as 总分 from student;
select name,math 数学,english 英语, math + ifnull(english, 0) 总分 from student;

/*
    条件查询
 */
    -- 查询年龄不等于20岁的，两种均可
    select * from student where age != 20;
    select * from student where age <> 20;

    -- 查询年龄在20-30之间的人，三种写法
    select * from student where age >= 20 && age <= 30;
    select * from student where age >= 20 and age <= 30;
    select * from student where between 20 and 30;

    -- 查询年龄为22，18，25 的人，两种写法
    select * from student where age = 22 or age = 18 or age = 25;
    select * from student where age in (22,18,25);

    -- 查询英语成绩是 null 值的
    select * from student where english is null; -- null 值不能用 = 或者 != 来比较
    -- 查询英语成绩是非 null 值的
    select * from student where english is not null;


/*
    模糊查询
 */
    -- 查询班级里姓马的
    select * from student where name like '马%';
    -- 查询第二个字是 化的
    select * from student where name like '_化%';
    -- 查询姓名是三个字的人
    select * from student where name like '___';
    -- 查询姓名中包含马的人
    select * from student where name like '%马%';

/*
    排序查询
 */
    -- 按数学成绩降序排列
    select * from student order by math DESC -- 排序方式

    -- 按数学成绩排名，如果数学一样，就按照英语排名
    select * from student order by math ASC, english DESC;

/*
    聚合函数
 */
    /*
        计数
     */
    -- 查询有几个人
    select count(name) from student;
    -- 如果是 null ，就换成 0
    select count(ifnull(english,0)) from studetn;
    -- 只要此条数据有一列不为 null，就算一条数据
    select count(*) from student;


    /*
        查询最大最小值
     */
    select max(math) from student;

    select min(math) from student;

    select sum(math) from student;

    select avg(math) from student;

/*
    分组查询
 */

    -- 按照性别分组，分别查询男，女的平均分
    select sex,avg(math) from student group by sex;

    -- 按照性别分组，分别查询男，女的平均分和人数
    select sex,avg(math),count(id) from student group by sex;

    -- 按照性别分组，分别查询男，女的平均分和人数，要求，分数低于70的不参与分组
    select sex,avg(math),count(id) from student where math > 70 group by sex;

    -- 按照性别分组，分别查询男，女的平均分和人数，要求，分数低于70的不参与分组,分组之后人数大于两个人
    select sex,avg(math),count(id) 人数 from student where math > 70 group by sex having 人数 > 2;


/*
    分页查询
 */
    -- 查询前三条记录
    select * from student limit 0,3; -- 第一条开始，查三条
    select * from student limit 3,3; -- 第四条开始，查三条

/*
    多表查询
 */

    select
        t1.name, -- 员工名
        t1.sex,  -- 员工性别
        t2.name, -- 部门名字
    from
        emp t1,dept t2
    where
        t1.id = t2.id;


    /*
        自关联映射查询
        查询员工的直接上级
     */
    select
        *
    from
        emp t1, emp t2
    where
        t1.mgr = t2.id;

    -- 左关联查询
    select
        *
    from
        emp t1 left join emp t2
    where
        t1.mgr = t2.id;