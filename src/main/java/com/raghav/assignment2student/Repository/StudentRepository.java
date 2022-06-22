package com.raghav.assignment2student.Repository;

import com.raghav.assignment2student.exception.CustomException;
import com.raghav.assignment2student.model.StudentProjectMapping;
import com.raghav.assignment2student.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class StudentRepository {

    private static final Logger log = LoggerFactory.getLogger(StudentRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate dbRepository;

    public static final String GET_STUDENT_DETAIL_BY_ID = "SELECT * FROM STUDENT WHERE studentId=:studentId";
    public static final String GET_ALL_STUDENT = "SELECT * FROM STUDENT";
    public static final String GET_ALL_PROJECTS_BY_STUDENTID = "SELECT * FROM StudentProjectMapping Where studentId=:studentId";

    @Async
    public CompletableFuture<Student> getStudentById(Integer studentId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("studentId", studentId);
        Student student;
        try {
            student = dbRepository.queryForObject(GET_STUDENT_DETAIL_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Student.class));
        } catch (EmptyResultDataAccessException e) {
            // if specified student id not exists
            throw new CustomException("No record exist for student id - " + studentId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(student);
    }

    @Async
    public CompletableFuture<List<Student>> getAllStudents(){
        List<Student> studentList = new ArrayList<>();
        try{
            studentList = dbRepository.query(GET_ALL_STUDENT,
                    new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Student.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(studentList);
    }

    public List<StudentProjectMapping> getAllProjectOfStudent(Integer studentId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("studentId", studentId);
        List<StudentProjectMapping> studentProjects = new ArrayList<>();
        try{
            studentProjects = dbRepository.query(GET_ALL_PROJECTS_BY_STUDENTID,
                    parameterSource,BeanPropertyRowMapper.newInstance(StudentProjectMapping.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return studentProjects;
    }
}
