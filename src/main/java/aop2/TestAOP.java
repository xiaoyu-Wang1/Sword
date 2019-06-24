package aop2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAOP
{
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAop2.xml");
        EmployeeManager manager = ( EmployeeManager ) context.getBean("employeeManager");

        manager.getEmployeeById(1);

        manager.createEmployee(new EmployeeDTO());

        manager.deleteEmployee(100);
    }
}

/**
Output:

        EmployeeCRUDAspect.logBefore() : getEmployeeById
        EmployeeCRUDTransactionAspect.getEmployeeById() : getEmployeeById
        Method getEmployeeById() called
        EmployeeCRUDAspect.logAfter() : getEmployeeById


        EmployeeCRUDAspect.logBefore() : createEmployee
        Method createEmployee() called
        EmployeeCRUDAspect.logAfter() : createEmployee


        EmployeeCRUDAspect.logBefore() : deleteEmployee
        Method deleteEmployee() called
        EmployeeCRUDAspect.logAfter() : deleteEmployee
*/
