package gruppe1.moranti.restcontrollers;

import gruppe1.moranti.models.Employee;
import gruppe1.moranti.models.Responsibility;
import gruppe1.moranti.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class Employees {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).get();
    }

    @GetMapping("/employees/sanitor")
    public List<Employee> getSanitor() {
        return employeeRepository.findAllByResponsibilityContaining(Responsibility.SANITOR);
    }

    @GetMapping("/employees/fol")
    public List<Employee> getFol() {
        return employeeRepository.findAllByResponsibilityContaining(Responsibility.FOL);
    }

    @GetMapping("/employees/shiftleader")
    public Employee getShiftLeader() {
        System.out.println(employeeRepository.findAllByResponsibilityContaining(Responsibility.VAGTLEDER));

        try {
            return getEmployeeById(Employee.shiftLeaderId);
        } catch (Exception e) {
            return employeeRepository.findAllByResponsibilityContaining(Responsibility.VAGTLEDER).get(0);
        }
    }

    @GetMapping("/employees/shiftleaders")
    public List<Employee> getShiftLeaders() {
        return employeeRepository.findAllByResponsibilityContaining(Responsibility.VAGTLEDER);
    }

    @PostMapping("/employees/shiftleader/{id}")
    public Employee setShiftLeader(@PathVariable Long id) {
        Employee.shiftLeaderId = id;
        return getEmployeeById(Employee.shiftLeaderId);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @PutMapping("/employees/{id}")
    public String updateEmployeeById(@PathVariable Long id, @RequestBody Employee employeeToUpdateWith) {
        if (employeeRepository.existsById(id)) {
            employeeToUpdateWith.setId(id);
            employeeRepository.save(employeeToUpdateWith);
            return "Car was created";
        } else {
            return "Car not found";
        }
    }

    @PatchMapping("/employees/{id}")
    public String patchEmployeeById(@PathVariable Long id, @RequestBody Employee employeeToUpdateWith) {
        return employeeRepository.findById(id).map(foundEmployee -> {
            if (employeeToUpdateWith.getWorkPhoneNumber() != null) foundEmployee.setWorkPhoneNumber(employeeToUpdateWith.getWorkPhoneNumber());
            if (employeeToUpdateWith.getEmployeeName() != null) foundEmployee.setEmployeeName(employeeToUpdateWith.getEmployeeName());
            if (employeeToUpdateWith.getResponsibility() != null) foundEmployee.setResponsibility(employeeToUpdateWith.getResponsibility());

            employeeRepository.save(foundEmployee);
            return "Employee was updated";
        }).orElse("Employee was not found");
    }

    @DeleteMapping("/employees/{id}")
    public void deleteCarByCarNumber(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
}