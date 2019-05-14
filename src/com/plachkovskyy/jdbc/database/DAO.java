package com.plachkovskyy.jdbc.database;

import java.util.Date;

public interface DAO {

    void connect();
    void disconnect();
    void createEmployee(int empno, String ename, String job, int mgr, Date hiredate, float sal, float comm, int deptno);
    void removeEmployee(int empno);
    void showEmployee(int empno);
    void showFullEmployee(int empno);
    boolean checkEmployee(int empno);

}
