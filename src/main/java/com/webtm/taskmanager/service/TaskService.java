package com.webtm.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webtm.taskmanager.model.Task;
import com.webtm.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired 
	TaskRepository taskRepo;

	public List<Task> findAll() {
		// TODO Auto-generated method stub
		return taskRepo.findAll();
	}
	
	public List<Task> findProjects(){
		return taskRepo.findByIsProjectTrue();
	}

	public Task findById(Integer id) {
	    Optional <Task> optTask = taskRepo.findById(id);
		if(optTask.isPresent())
		{
			Task task = (Task)optTask.get();
			return task;
		}
		return null;
	}
	
	
	 public Task saveTask(Task task) {
	        return taskRepo.save(task);
	    }

	 
	 public Task updateTask(Task task) {
			// TODO Auto-generated method stub
			 if (taskRepo.findById(task.getId()).isPresent()){
				   
		            Task updatedTask = taskRepo.save(task);
	                return updatedTask;   
			 } else 
			 {
				 return null;
			 }
			
		}
		
	 
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		taskRepo.deleteById(id);
		
	}

	//find only tasks that are not projects
	public List<Task> findByIsProjectFalse() {
		// TODO Auto-generated method stub
		return taskRepo.findByIsProjectFalse();
	}

	public List<Task> findByIsProjectTrue() {
		// TODO Auto-generated method stub
		return taskRepo.findByIsProjectTrue();
	}
	
	public List <Task> findByParentId(int parent){
		return taskRepo.findByParent(parent);
	}
	
	public List <Task>  findByParentAndIsProject( int parentId, boolean isProject){
		return taskRepo.findAllByParentAndIsProject(parentId, isProject);
	}

	
	
	

}
