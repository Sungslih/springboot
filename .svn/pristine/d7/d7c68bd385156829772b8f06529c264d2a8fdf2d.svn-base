CREATE TABLE employee_xwbin(
  id NUMBER(4) DEFAULT 1 ,
  name VARCHAR2(20),
  gender CHAR(1) DEFAULT  'M',
  biith DATE,
  salary NUMBER(6,2),
  job VARCHAR2(30),
  deptno NUMBER(2)
);
DROP TABLE name4;
NOT NULL 约束可以确保指定的字段不允许为null
CREATE TABLE employ_xwbing(
  id NUMBER(4) DEFAULT 1,
  name VARCHAR2(20) NOT NULL,
  gender CHAR(1) DEFAULT 'M',
  birth DATE,
  salary NUMBER(6,2) DEFAULT 1,
  job VARCHAR2(30) DEFAULT 'M',
  deptno NUMBER(2) DEFAULT 1
)
DESC xwbing;
RENAME employ_xwbing TO xwbing;
DESC xwbing;
修改表的
向表中添加新的字段,只能在当前表的末尾追加,可以同时追加多个列,只需要使用逗号隔开即可,与创建声明列的时候语法一样
ALTER TABLE XWBING ADD(
  HIREDATE DATE DEFAULT SYSDATE
)
删除表中现有字段


DML
用于修改表中数据,分为:增 删 改
插入数据
INSERT INTO XWBING (id,name,job,salary)
VALUES(1,'JACK','CLERK',5000)
COMMIT
插入日期类型,建议使用TO-DATE函数
可以使用字符串 但是格式必须是'DD-MON-RR'
由于有语言差异,不推荐
INSERT INTO XWBING(id,name,job,birth)
VALUES(2,'ROSE','CLERK',TO_DATE('1992-08-02','YYYY-MM-DD'))
SELECT *FROM XWBING;

ALTER TABLE XWBING DROP(HIREDATE)

修改表中现有字段
可以修改字段的类型,长度,默认值,非空
ALTER TABLE XWBING MODIFY(
  job VARCHAR2(40) DEFAULT 'CLERK'
)

修改表中现有数据
将rose的工资改为5500
UPDATE XWBING SET SALARY=50000 WHERE name='ROSE'   字符串里区分大小写,用''

修改表中的数据的时候,通常要使用WHERE下限定条件,这样只会将满足条件的记录进行修改,
若不在指定WHERE则是全表所有的数据都修改

删除表中数据
将rose删除
DELETE FROM EMP_XWBING WHERE name='ROSE'
DELETE FROM EMP_XWBING
删除表中数据同样要用WHERE添加过滤条件 否则就是清空表操作

CREATE TABLE emp_xwbing(
  empno NUMBER(4,0),
  ename VARCHAR2(10),
  job VARCHAR2(9),
  mgr NUMBER(4,0),
  hiredate DATE,
  sal NUMBER(7,2),
  comm NUMBER(7,2),
  deptno NUMBER(2,0)
)
SELECT *FROM EMP_XWBING;
DESC emp_xwbing;
CREATE TABLE dept_xwbing(
  deptno NUMBER(2,0),
  dname VARCHAR2(14 BYTE),
  loc VARCHAR2(13 BYTE)
)

INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7369,'SMITH','CLERK',7902,TO_DATE('1980/12/17','YYYY-MM-DD'),800.00,20);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,comm,deptno)
VALUES(7499,'ALLEN','SALESMAN',7698,TO_DATE('1981/02/20','YYYY-MM-DD'),1600.00,300.00,30);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,comm,deptno)
VALUES(7521,'WARD','SALESMAN',7698,TO_DATE('1981/02/22','YYYY-MM-DD'),1250.00,500.00,30);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7566,'JONES','MANAGER',7839,TO_DATE('1980/4/2','YYYY-MM-DD'),2975.00,20);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,comm,deptno)
VALUES(7654,'MARTIN','SALESMAN',7698,TO_DATE('1981/9/28','YYYY-MM-DD'),1250.00,1400.00,30);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7698,'BLACK','MANAGER',7839,TO_DATE('1980/5/1','YYYY-MM-DD'),2850.00,30);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7782,'CLARK','MANAGER',7839,TO_DATE('1980/6/9','YYYY-MM-DD'),2450.00,10);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7788,'SCOTT','ANALYST',7566,TO_DATE('1987/4/19','YYYY-MM-DD'),3000.00,20);
INSERT INTO emp_xwbing(empno, ename,job,hiredate,sal,deptno)
VALUES(7839,'KING','PRESIDENT',TO_DATE('1981/11/17','YYYY-MM-DD'),5000.00,10);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,comm,deptno)
VALUES(7844,'TURNER','SALESMAN',7698,TO_DATE('1981/9/8','YYYY-MM-DD'),1500.00,0.00,30);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7876,'ADAMS','CLERK',7788,TO_DATE('1987/5/23','YYYY-MM-DD'),1100.00,20);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7900,'JAMES','CLERK',7698,TO_DATE('1981/12/3','YYYY-MM-DD'),9500.00,30);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7902,'FORD','ANALYST',7566,TO_DATE('1981/12/3','YYYY-MM-DD'),3000.00,20);
INSERT INTO emp_xwbing(empno, ename,job,mgr,hiredate,sal,deptno)
VALUES(7934,'MILLER','CLERK',7782,TO_DATE('1982/1/23','YYYY-MM-DD'),1300.00,10);

INSERT INTO dept_xwbing(deptno,dname,loc)
VALUES(10,'ACCOUNTING','NEW YORK');
INSERT INTO dept_xwbing(deptno,dname,loc)
VALUES(20,'RESEARCH','DALLAS');
INSERT INTO dept_xwbing(deptno,dname,loc)
VALUES(30,'SALES','CHICAGO');
INSERT INTO dept_xwbing(deptno,dname,loc)
VALUES(40,'OPERATIONS','BOSTON');
select *from dept_xwbing;


create table name2 (
  deptno NUMBER(2,0),
  dname VARCHAR2(14 BYTE),
  loc VARCHAR2(13 BYTE)
);
INSERT INTO name2(deptno,dname,loc)
VALUES(10,'ACCOUNTING','NEW YORK');
INSERT INTO name2(deptno,dname,loc)
VALUES(20,'RESEARCH','DALLAS');
INSERT INTO name2(deptno,dname,loc)
VALUES(30,'SALES','CHICAGO');

SELECT *FROM name4;
alter table name2 modify(deptno NUMBER(4,0),
  dname VARCHAR2(14 BYTE))