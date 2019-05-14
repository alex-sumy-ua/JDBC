package com.plachkovskyy.jdbc;

import com.plachkovskyy.jdbc.database.OracleDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {

        createEmp();

        showEmp();

        removeEmp();

        showEmp();

    }


    public static void createEmp() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Let's add a new employee...\nPlease, input his number: ");
        int empno = Integer.parseInt(reader.readLine());
        System.out.print("Please, input name: ");
        String ename = reader.readLine();
        System.out.print("Please, input job: ");
        String job = reader.readLine();
        System.out.print("Please, input manager number (default 7839): ");
        int mgr;
        try {
            mgr = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
                mgr = 7839;
        }
        System.out.print("Please, input hire date as MM/dd/yyyy: ");
        SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
        Date hiredate = null;
        try {
            hiredate = s.parse(reader.readLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.print("Please, input salary: ");
        float sal = Float.parseFloat(reader.readLine());
        System.out.print("Please, input comission: ");
        float comm = Float.parseFloat(reader.readLine());
        System.out.print("Please, input department number (default 10): ");
        int deptno;
        try {
            deptno = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
                deptno = 10;
        }
        OracleDAO.getInstance().createEmployee(empno, ename, job, mgr, hiredate, sal, comm, deptno);
    }

    public static void showEmp() throws IOException {
        System.out.print("Do you want to have a look at employee? (Y/N): ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String want = reader.readLine();
        if (want.equalsIgnoreCase("Y")) {
            System.out.print("Input employee number - ");
            int empno = Integer.parseInt(reader.readLine());
//            OracleDAO.getInstance().showEmployee(empno);
            OracleDAO.getInstance().showFullEmployee(empno);
        }
    }

    private static void removeEmp() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Let's remove an employee... Please, input his number: ");
        int empno       = Integer.parseInt(reader.readLine());
        if (OracleDAO.getInstance().checkEmployee(empno)) {
            OracleDAO.getInstance().removeEmployee(empno);
        } else {
            System.out.println("Employee number " + empno + " doesn't exist.");
        }
    }

}
