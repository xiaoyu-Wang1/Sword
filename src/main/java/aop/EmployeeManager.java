package aop;

import org.springframework.stereotype.Component;

@Component
public class EmployeeManager
{
    public EmployeeDTO getEmployeeById(Integer employeeId) {
        System.out.println("Method getEmployeeById() called, employeeId:" + employeeId);
        return new EmployeeDTO();
    }
}