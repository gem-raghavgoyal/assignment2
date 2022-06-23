package com.raghav.assignment2student.Repository;

import com.raghav.assignment2student.exception.CustomException;
import com.raghav.assignment2student.model.Project;
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
public class ProjectRepo {
    private static final Logger log = LoggerFactory.getLogger(ProjectRepo.class);

    public static final String GET_PROJECT_BY_ID = "SELECT * FROM Project WHERE projectId=:projectId";
    public static final String GET_ALL_PROJECTS = "SELECT * FROM Project";

    @Autowired
    private NamedParameterJdbcTemplate dbRepository;

    @Async
    public CompletableFuture<List<Project>> getAllProjects(){
        List<Project> projectList = new ArrayList<>();
        try{
            projectList = dbRepository.query(GET_ALL_PROJECTS,
                    new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Project.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(projectList);
    }
    @Async
    public CompletableFuture<Project> getProjectById(Integer projectId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("projectId", projectId);
        Project project;
        try {
            project = dbRepository.queryForObject(GET_PROJECT_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Project.class));
        } catch (EmptyResultDataAccessException e) {
            // if specified project id does not exist
            throw new CustomException("No record exist for projectId - " + projectId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(project);
    }
    public Project getProjectByProjectId(Integer projectId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("projectId", projectId);
        Project project;
        try {
            project = dbRepository.queryForObject(GET_PROJECT_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Project.class));
        } catch (EmptyResultDataAccessException e) {
            // if specified project id does not exist
            throw new CustomException("No record exist for projectId - " + projectId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return project;
    }
}
