package com.webtm.taskmanager.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.webtm.taskmanager.model.Task;
import com.webtm.taskmanager.service.TaskService;



@RestController

public class TaskController {
	
	
    @Autowired
    private TaskService service;

    
   /* @GetMapping("/tasks")
    public List<Task> findAll() {
       System.out.println("Calling findAll");
    	return service.findAll();
    }
*/
    
    @GetMapping( "/tasks")
    public ResponseEntity <List<Task>> findAllProjects(@RequestParam (name = "isProject", required= false) Boolean isProject,
    		@RequestParam (name ="parent", required = false ) Integer parent )
    {
    	 List taskList = null;
    	//System.out.println(" here printing all projects ******************" + service.findByIsProjectTrue());
    	if(isProject == null && parent == null ) {
    	    
    		
    		taskList= service.findAll();
    		if (taskList != null)
    		{
    			 if (taskList.isEmpty())
	 	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    			 else 
    				 return new ResponseEntity<>(taskList, HttpStatus.OK);
    		}
    	    else 
    			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    	  
    	} else   
    	{
    		if(isProject != null && parent != null)
    		{
	    		System.out.println("here both are not null");
    			taskList = service.findByParentAndIsProject(parent, isProject);
	    		System.out.println("tasklist is " + taskList);
	    		System.out.println("tasklist is empty =  " + taskList.isEmpty());

    			if ((taskList != null) || !taskList.isEmpty())
	    		{	
	    			 if (taskList.isEmpty())
		 	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	    			 else 
	    				 return new ResponseEntity<>(taskList, HttpStatus.OK);
	    		} else 
	    			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	    	
	    		
    		}
    		
    		if(isProject != null)
    		{
	    	 if (isProject == true )
	    	 {
	    		taskList = service.findByIsProjectTrue();
	    		if ((taskList != null) || !taskList.isEmpty())
	    		{	
	    			 if (taskList.isEmpty())
		 	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	    			 else 
	    				 return new ResponseEntity<>(taskList, HttpStatus.OK);
	    		} else 
	    			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	    		
	    		
	         } else if(isProject == false)
	         {
	        	 taskList =  service.findByIsProjectFalse();
	        	 if ((taskList != null))
	        	 {
	        		 if (taskList.isEmpty())
		 	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	    			 else 
	    				 return new ResponseEntity<>(taskList, HttpStatus.OK);
	        	 }else 
	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	         }
	    	 
    		} 
	    	 if (parent != null)
	         {
	        	 //System.out.println("calling parentId");
	        	 taskList =  service.findByParentId(parent.intValue());
	        	 
	        	 if ((taskList != null))
	        	 {
	        		 if (taskList.isEmpty())
		 	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	        		 else 
	        			    return new ResponseEntity<>(taskList, HttpStatus.OK);
	        	 }
	     		 else {
	     			 

	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	     		 }
	     	  }  
    	}
         
    	taskList =  service.findAll();
    	if (taskList != null)
    	{	
    		 if (taskList.isEmpty())
	     			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
			 else 
				 return new ResponseEntity<>(taskList, HttpStatus.OK);
    	}
    	else 
 			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
         
     }
    
    
    
    @GetMapping(value = "tasks/{id}")
    public ResponseEntity<Task> findById(@PathVariable("id") Integer id) {
        Task task = service.findById(id);
    	if (task != null) {
    	    return new ResponseEntity<>(task, HttpStatus.OK);

       } 
       
    	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Task task) {
        
		if (task.getId() != 0 || task.getTitle() == null ) { // Reject -
			// we'll
			// assign
			// the
			// task
			// id
			return ResponseEntity.badRequest().build();
		}
    	
		Task newTask = service.saveTask(task);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()

				.path("/{id}").buildAndExpand(newTask.getId()).toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;

        
        
    }

    @PutMapping(value = "tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@PathVariable( "id" ) Long id, @RequestBody Task task) {
        //Preconditions.checkNotNull(task);
        if (task.getId() != id ) {
    			return ResponseEntity.badRequest().build();
    		}
   
        task =  service.updateTask(task);
        if(task == null)
        {
        	return ResponseEntity.notFound().build();
        }
		return ResponseEntity.ok().build();

    
    }

    @DeleteMapping(value = "tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
    }

 

}
