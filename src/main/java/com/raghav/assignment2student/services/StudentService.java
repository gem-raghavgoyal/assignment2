package com.raghav.assignment2student.services;

import com.raghav.assignment2student.Repository.StudentRepository;
import com.raghav.assignment2student.Repository.ProjectRepo;
import com.raghav.assignment2student.model.StudentProjectMapping;
import com.raghav.assignment2student.model.Student;
import com.raghav.assignment2student.model.StudentProjectDetails;
import com.raghav.assignment2student.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentRepository.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProjectRepo projectRepo;

    public Student getStudentById(Integer studentId){
        CompletableFuture<Student> completableFuture =  studentRepository.getStudentById(studentId);
        Student student = null;
        try{
            student = completableFuture.get();
        }catch (Exception exception){
            log.error("Exception occurred while fetching student details for studentId - " + studentId);
        }
        return student;
    }

    public List<Student> getAllStudent(){
        CompletableFuture<List<Student>> studentListFuture =  studentRepository.getAllStudents();
        List<Student> studentList = new ArrayList<>();
        try{
            studentList = studentListFuture.get();
        }catch (Exception ex){
            log.error("Exception occurred while fetching student list from future object");
        }
        return studentList;
    }

    public StudentProjectDetails getAllStudentProjectsById(Integer studentId){
        List<StudentProjectMapping> studentProjectMappings =  studentRepository.getAllProjectOfStudent(studentId);
        List<CompletableFuture<Project>> projectList = new ArrayList<>();
        studentProjectMappings.forEach(studentProjectMapping -> {
            CompletableFuture<Project> project = projectRepo.getProjectById(studentProjectMapping.getProjectId());
            projectList.add(project);
        });
        log.info("Waiting for all projects to be fetched from DB for studentId -" + studentId);
        CompletableFuture.allOf(projectList.toArray(new CompletableFuture[studentProjectMappings.size()])).join();

        Student student = getStudentById(studentId);
        List<Project> projects = new ArrayList<>();
        for(CompletableFuture<Project> projectCompletableFuture : projectList){
            try {
                Project project =  projectCompletableFuture.get();
                if(project != null)
                    projects.add(project);
            } catch (Exception e) {
                log.error("Exception occurred while getting projectList from future object");
            }
        }
        return mapStudentProjectDetails(student,projects);
    }

    public StudentProjectDetails mapStudentProjectDetails(Student student, List<Project> projectList){
        StudentProjectDetails studentProjectDetails = new StudentProjectDetails();
        if(student != null){
            studentProjectDetails.setStudentId(student.getStudentId());
            studentProjectDetails.setStudentName(student.getFirstName() + " " + student.getLastName());
            studentProjectDetails.setEmail(student.getEmail());
            studentProjectDetails.setPhoneNo(student.getPhoneNo());
            studentProjectDetails.setDestination(student.getDestination());
            studentProjectDetails.setLocation(student.getLocation());
        }
       if(projectList != null){
           studentProjectDetails.setProjects(projectList);
       }

       return studentProjectDetails;
    }
}
