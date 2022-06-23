package com.raghav.assignment2student.controller;

import com.raghav.assignment2student.model.Student;
import com.raghav.assignment2student.model.StudentProjectDetails;
import com.raghav.assignment2student.model.Response;
import com.raghav.assignment2student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller class for student related API
 */

@RestController
@RequestMapping("/async")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/id")
    public Response<Student> getStudentById(@PathVariable int id){
        Response<Student> response = new Response<>();
        Student student = studentService.getStudentById(id);
        if(student != null){
            response.setData(student);
            response.setSuccessMessage("Fetched student successfully");
        }else{
            response.setErrorMessage("Not found any data for given student Id");
        }
        return response;
    }

    @GetMapping("/all")
    public Response<List<Student>> getAllStudents(){
        Response<List<Student>> response = new Response<>();
        List<Student> studentList = studentService.getAllStudent();
        if(studentList != null && studentList.size() >0){
            response.setData(studentList);
            response.setSuccessMessage("Got all active record successfully");
        }else{
            response.setErrorMessage("Not found any active records");
        }
        return response;
    }

    @GetMapping("/student/projects/studentId")
    public Response<StudentProjectDetails> getAllProjectsOfStudent(@PathVariable int studentId){
        Response<StudentProjectDetails> response = new Response<>();
        StudentProjectDetails studentProjectDetails = studentService.getAllStudentProjectById(studentId);
        if(studentProjectDetails != null){
            response.setData(studentProjectDetails);
            response.setSuccessMessage("Got all projects of " + studentProjectDetails.getStudentName());
        }else{
            response.setErrorMessage("Not found any projects for student id - " + studentId);
        }
        return response;
    }


}
