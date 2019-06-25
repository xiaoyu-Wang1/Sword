package aop2;

import org.aspectj.lang.JoinPoint;

public class EmployeeCRUDTransactionAspect
{
    public void getEmployeeById(JoinPoint joinPoint)
    {
        System.out.println("EmployeeCRUDTransactionAspect.getEmployeeById() : "
                + joinPoint.getSignature().getName());
    }
}
