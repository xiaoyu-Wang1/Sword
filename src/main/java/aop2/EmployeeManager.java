package aop2;

import java.util.List;

public interface EmployeeManager
{
    EmployeeDTO getEmployeeById(Integer employeeId);

    List<EmployeeDTO> getAllEmployee();

    void createEmployee(EmployeeDTO employee);

    void deleteEmployee(Integer employeeId);

    void updateEmployee(EmployeeDTO employee);
}