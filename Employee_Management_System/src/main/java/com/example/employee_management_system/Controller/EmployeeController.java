package com.example.employee_management_system.Controller;

import com.example.employee_management_system.Api.ApiResponse;
import com.example.employee_management_system.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<>();

    //GET
    @GetMapping("/get")
    public ArrayList<Employee> getEmployees(){
        return employees;
    }
    //ADD
    @PostMapping("/add")
    public ResponseEntity addEmployees(@RequestBody @Valid Employee employee , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Added successfully"));
    }
    //UPDATE
    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployees(@PathVariable int index,@RequestBody @Valid Employee employee , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.set(index,employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
    }

    //DELETE
    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index){
        if(index > employees.size()){
            return ResponseEntity.status(500).body("Not Found");
        }
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }

    //SEARCH
    @GetMapping("/search/{position}")
    public ResponseEntity searchEmployee(@PathVariable String position){
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if(employee.getPosition().equalsIgnoreCase(position)){
                return ResponseEntity.status(200).body(employee);
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    //GET EMPLOYEES BY AGE RANGE
    @GetMapping("/get/{age}")
    public ResponseEntity ageRange(@PathVariable int age){ // i put it if i want select age
        ArrayList<Employee> ageRange = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if(employee.getAge() > 20 && employee.getAge() < 50) {// i put the range between 20 to 50 or i put employee.getAge() == age for select any age i want
                ageRange.add(employee);
            }
        }if(ageRange.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Not found!"));
        }
        return ResponseEntity.status(200).body(ageRange);
    }
    //APPLY FOR ANNUAL LEAVE
    @GetMapping("/annualLeave")
    public ResponseEntity annualLeave(){
        ArrayList<Employee> annualLeave = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if(!employee.isOnLeave() && employee.getAnnualLeave()>0){
                employee.setOnLeave(true);
                employee.setAnnualLeave(employee.getAnnualLeave() -1);
                annualLeave.add(employee);
            }
            if(annualLeave.isEmpty()){
                return ResponseEntity.status(400).body(new ApiResponse("Not found!"));
            }
        }
        return ResponseEntity.status(200).body(annualLeave);
    }

    //GET EMPLOYEES WITH NO ANNUAL LEAVE
    @GetMapping("/noAnnualLeave")
    public ResponseEntity noAnnualLeave(){
        ArrayList<Employee> noAnnulLeave = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if(employee.getAnnualLeave() == 0){
                noAnnulLeave.add(employee);
            }
            if(noAnnulLeave.isEmpty()){
                return ResponseEntity.status(400).body(new ApiResponse("Not found!"));
            }
        }
        return ResponseEntity.status(200).body(noAnnulLeave);
    }

    //PROMOTE EMPLOYEE
    @PutMapping("/promote/{id}/{idToSuper}")
    public ResponseEntity promoteEmployee(@PathVariable int id, @PathVariable int idToSuper){
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (employee.getPosition().equalsIgnoreCase("supervisor")&&employee.getId() == id);
          //  return ResponseEntity.status(200).body(new ApiResponse("The supervisor" + employee));
        }
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if(employee.getAge() >= 30 && !employee.isOnLeave()&& employee.getId() == idToSuper){
                employee.setPosition("supervisor");
                return ResponseEntity.status(200).body(employee);
            }
        }



        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }




}
