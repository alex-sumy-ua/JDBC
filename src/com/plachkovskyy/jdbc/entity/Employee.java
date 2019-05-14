package com.plachkovskyy.jdbc.entity;

import java.util.Date;

public class Employee {
    private int     empno;
    private String  ename;
    private String  job;
    private int     mgr;
    private Date    hiredate;
    private float   sal;
    private float   comm;
    private int     deptno;
    private String  dname;
    private String  loc;
    private int     grade;

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public Date getHireDate() {
        return hiredate;
    }

    public void setHireDate(Date hireDate) {
        this.hiredate = hireDate;
    }

    public float getSal() {
        return sal;
    }

    public void setSal(float sal) {
        this.sal = sal;
    }

    public float getComm() {
        return comm;
    }

    public void setComm(float comm) {
        this.comm = comm;
    }

    public int getDeptNo() {
        return deptno;
    }

    public void setDeptNo(int deptNo) {
        this.deptno = deptNo;
    }

    public String getdName() {
        return dname;
    }

    public void setdName(String dName) {
        this.dname = dName;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Employee(int empno,
                    String ename,
                    String job,
                    int mgr,
                    Date hiredate,
                    float sal,
                    float comm,
                    int deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }

    public Employee(int empno,
                    String ename,
                    String job,
                    int mgr,
                    Date hiredate,
                    float sal,
                    float comm,
                    int deptno,
                    String dname,
                    String loc,
                    int grade) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", hiredate='" + hiredate + '\'' +
                ", sal=" + sal +
                ", comm=" + comm +
                ", deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                ", grade=" + grade +
                '}';
    }
}
