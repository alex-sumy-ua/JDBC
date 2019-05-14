package com.plachkovskyy.jdbc.database;

import com.plachkovskyy.jdbc.entity.Employee;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class OracleDAO implements DAO {

    private static final OracleDAO instance = new OracleDAO();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private OracleDAO() {}

    public static OracleDAO getInstance() {
        return instance;
    }

    @Override
    public void connect() {
        try {
            Class.forName(SAXPars.parse().get("driver-class")); // Explicit loading
            connection = DriverManager.getConnection(SAXPars.parse().get("connection-url"),
                    SAXPars.parse().get("user-name"),
                    SAXPars.parse().get("password"));
//            Class.forName("oracle.jdbc.OracleDriver"); // Explicit loading
//            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "alex-ua",
//                    "prpf+911");
            if (!connection.isClosed()) System.out.println("Connected successfully.");
        } catch (ClassNotFoundException | SQLException | ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error connection: " + e.getMessage());
//            e.printStackTrace();
            disconnect();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null) { connection.close(); }
            if (preparedStatement != null) { preparedStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
            System.out.println("Disconnected successfully.");
        } catch (SQLException e) {
            System.out.println("Error disconnection: " + e.getMessage());
//            e.printStackTrace();
        }
    }

    @Override
    public void createEmployee(int empno,
                               String ename,
                               String job,
                               int mgr,
                               Date hiredate,
                               float sal,
                               float comm,
                               int deptno) {
        connect();
        try {
            preparedStatement =
              connection.prepareStatement("INSERT INTO EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)" +
                                                               " values (?, ?, ?, ?, ?, ?, ?, ? )");
            preparedStatement.setInt(1, empno);
            preparedStatement.setString(2, ename);
            preparedStatement.setString(3, job);
            preparedStatement.setInt(4, mgr);
            //java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // !!! convert java.util.Date to java.sql.Date
            preparedStatement.setDate(5, (new java.sql.Date(hiredate.getTime())));
            preparedStatement.setFloat(6, sal);
            preparedStatement.setFloat(7, comm);
            preparedStatement.setInt(8,deptno);
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("COMMIT");
            preparedStatement.execute();
            //connection.commit(); // alternative
            System.out.println("Employee adding was ok: " + ename);
        } catch (SQLException e) {
            //connection.rollback();
            System.out.println("Cannot add employee. " + e.getMessage());
//            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public void removeEmployee(int empno) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("DELETE EMP WHERE EMPNO = ?");
            preparedStatement.setInt(1, empno);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("COMMIT");
            preparedStatement.execute();
            //connection.commit(); // alternative
            System.out.println("Removing data was ok...");
        } catch (SQLException e) {
            //connection.rollback();
            System.out.println("Cannot remove employee. " + e.getMessage());
//            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public void showEmployee(int empno) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM EMP WHERE EMPNO = ?");
            preparedStatement.setInt(1, empno);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(parseEmployee(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Cannot execute the query. " + e.getMessage());
        }
        disconnect();
    }

    @Override
    public void showFullEmployee(int empno) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("SELECT A.*, " +
                                   "(SELECT B.DNAME FROM DEPT B WHERE B.DEPTNO = A.DEPTNO) DNAME, " +
                                   "(SELECT C.LOC FROM DEPT C WHERE C.DEPTNO = A.DEPTNO) LOC," +
                                   "(SELECT D.GRADE FROM SALGRADE D WHERE D.MINSAL < A.SAL and D.HISAL >= A.SAL) ETC " +
                                   "FROM EMP A WHERE A.EMPNO = ?");
            preparedStatement.setInt(1, empno);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(parseFullEmployee(resultSet));
            } else {
                System.out.println("Employee was not found in the DB: " + empno);
            }
        } catch (SQLException e) {
            System.out.println("Cannot execute the query. " + e.getMessage());
        }
        disconnect();
    }

    @Override
    public boolean checkEmployee(int empno) {
        connect();
        boolean exists = false;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM EMP WHERE EMPNO = ?");
            preparedStatement.setInt(1, empno);
            resultSet = preparedStatement.executeQuery();
            int recordCount = 0;
            while(resultSet.next()) { recordCount++; }
            if (recordCount > 0) {
                exists = true;
                System.out.println("Employee number " + empno + " was found in DB.");
            } else {
                System.out.println("Sorry, employee number " + empno + " was not found in DB.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return exists;
    }

    private Employee parseEmployee(ResultSet resultSet) {
        Employee result = null;
        try {
            int     empno       = resultSet.getInt("EMPNO");
            String  ename       = resultSet.getString("ENAME");
            String  job         = resultSet.getString("JOB");
            int     mgr         = resultSet.getInt("MGR");
            Date    hiredate    = resultSet.getDate("HIREDATE");
            float   sal         = resultSet.getFloat("SAL");
            float   comm        = resultSet.getFloat("COMM");
            int     deptno      = resultSet.getInt("DEPTNO");
            result = new Employee(empno, ename, job, mgr, hiredate, sal, comm, deptno);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Employee parseFullEmployee(ResultSet resultSet) {
        Employee result = null;
        try {
            int     empno       = resultSet.getInt("EMPNO");
            String  ename       = resultSet.getString("ENAME");
            String  job         = resultSet.getString("JOB");
            int     mgr         = resultSet.getInt("MGR");
            Date    hiredate    = resultSet.getDate("HIREDATE");
            float   sal         = resultSet.getFloat("SAL");
            float   comm        = resultSet.getFloat("COMM");
            int     deptno      = resultSet.getInt("DEPTNO");
            String  dname       = resultSet.getString("DNAME");
            String  loc         = resultSet.getString("LOC");
            int     etc         = resultSet.getInt("ETC");
            result = new Employee(empno, ename, job, mgr, hiredate, sal, comm, deptno, dname, loc, etc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
