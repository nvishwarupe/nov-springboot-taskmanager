package com.webtm.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webtm.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository <Task, Integer>  {

	List<Task> findByIsProjectTrue();
	
	List<Task> findByIsProjectFalse();
	
	List<Task> findByParent(int parentId);
	
	List<Task> findAllByParentAndIsProject(int parentId, boolean isProject);
	
	
	/*
	@Query(
			  value = "SELECT tasks \" +\r\n"
			  		+ "	 \"FROM Project p JOIN p.tasks \" +\r\n"
			  		+ "	 \"WHERE p.name = :projectName \" +\r\n ",
			  		 
			  nativeQuery = true)
	List<Task> findAllTasksWithProjectNative(@param(projectName));
	
	
	public List findProjectProfessors(String projectName) {
	    return em.createQuery("SELECT e " +
	                          "FROM Task p JOIN p.employees e " +
	                          "WHERE p.name = :project " +
	                          "ORDER BY e.name")
	             .setParameter("project", projectName)
	             .getResultList();
	}
		
	*/
	
}
