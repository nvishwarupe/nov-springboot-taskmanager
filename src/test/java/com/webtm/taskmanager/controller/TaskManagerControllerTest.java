package com.webtm.taskmanager.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtm.taskmanager.model.Task;

import ch.qos.logback.core.spi.LifeCycle;






@ExtendWith(SpringExtension.class)
@SpringBootTest(
		 webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT 
)



public class TaskManagerControllerTest {
	
	

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@BeforeEach
	public  void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		// create couple of tasks for testing
		Task task1 = new Task();
		task1.setTitle("Cognizant Tasks 1");
		task1.setIsProject(false);

		Task task2 = new Task();
		task2.setTitle("Cognizant Project 2");
		task2.setIsProject(true);

		Task task3 = new Task();
		task3.setTitle("Cognizant Tasks 2");
		task3.setIsProject(false);
		task3.setParent(1000);
		// create task 1
		String jsonRequest = om.writeValueAsString(task1);

		MvcResult result = mockMvc.perform(post("/tasks").content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();				// create task 2
	    
		// create task 2
		jsonRequest = om.writeValueAsString(task2);

		result = mockMvc.perform(post("/tasks").content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		// create task 3
		jsonRequest = om.writeValueAsString(task3);

		result = mockMvc.perform(post("/tasks").content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

	}
	
	
	
	

	@Test
	public void addTaskTest() throws Exception {
		Task task = new Task();
		task.setTitle("Cognizant Project");
		task.setIsProject(true);
		
		
		String jsonRequest = om.writeValueAsString(task);
		MvcResult result = mockMvc.perform(post("/tasks").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		String location = (String)result.getResponse().getHeaderValue("Location");
		
		//ResponseEntity response = om.readValue(resultContent, ResponseEntity.class);
		//assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(location);
	}
	
	
	@Test
	public void updateTaskTest() throws Exception {
		Task task = new Task();
		task.setId(1);;
		task.setTitle("Cognizant Project");
		task.setIsProject(true);
		
		
		String jsonRequest = om.writeValueAsString(task);
		MvcResult result = mockMvc.perform(put("/tasks/1").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		//String location = (String)result.getResponse().getHeaderValue("Location");
		int status = result.getResponse().getStatus();
		
		//ResponseEntity response = om.readValue(resultContent, ResponseEntity.class);

		assertEquals(Integer.toString(status), "200");
		
	}

	@Test
	public void deleteTaskTest() throws Exception {
		Task task = new Task();
		task.setTitle("Cognizant Project");
		task.setIsProject(true);
		
		
		String jsonRequest = om.writeValueAsString(task);
		MvcResult result = mockMvc.perform(post("/tasks").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		String location = (String)result.getResponse().getHeaderValue("Location");
		String id = location.substring(location.length() - 1, location.length());
		
		System.out.println("id to delete is " + id );
		//ResponseEntity response = om.readValue(resultContent, ResponseEntity.class);
		//assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		//assertNotNull(location);
		
		
		result = mockMvc.perform(delete("/tasks/"+ id).content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(Integer.toString(status), "200");

		
	}
	
	

	@Test
	public void getAllTasksTest() throws Exception {
				
		MvcResult result = mockMvc
				.perform(get("/tasks").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		//Response response = om.readValue(resultContent, Response.class);
	    List responseList = om.readValue(resultContent, List.class);
		//List<Task> taskList = (List)response.getBody();
        
	    responseList.forEach(System.out::println);
 	    assertThat(responseList.isEmpty(),is(false));

	}
	
	
	@Test
	public void getOnlyTasksListTest() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/tasks?isProject=false").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		//Response response = om.readValue(resultContent, Response.class);
	   //List responseList = om.readValue(resultContent, List.class);
	    List<Task> taskList = om.readValue(resultContent, new TypeReference<List<Task>>() {});

		//List<Task> taskList = (List)response.getBody();
        //taskList.forEach(System.out::println);
 	    assertThat(taskList.isEmpty(),is(false));
 	    
 	   Task task = (Task) taskList.get(0);
 	   //System.out.println(task.toString());
 	   assertFalse(task.getIsProject());

	}

	@Test
	public void getOnlyProjectsListTest() throws Exception {
		
		MvcResult result = mockMvc
				.perform(get("/tasks?isProject=true").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		//Response response = om.readValue(resultContent, Response.class);
	   //List responseList = om.readValue(resultContent, List.class);
	    List<Task> taskList = om.readValue(resultContent, new TypeReference<List<Task>>() {});

		//List<Task> taskList = (List)response.getBody();
        //taskList.forEach(System.out::println);
 	    assertThat(taskList.isEmpty(),is(false));
 	    
 	   Task task = (Task) taskList.get(0);
 	   //System.out.println(task.toString());
 	   assertTrue(task.getIsProject());

	}

	@Test
	public void getTasksWithId() throws Exception {
		Task task = new Task();
		task.setTitle("Cognizant Project");
		task.setIsProject(true);
		
		
		String jsonRequest = om.writeValueAsString(task);
		MvcResult result = mockMvc.perform(post("/tasks").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
		String location = (String)result.getResponse().getHeaderValue("Location");
		String id = location.substring(location.length() - 1, location.length());
		
		System.out.println("getTaskWithIdTest() id for task is " + id );
		//ResponseEntity response = om.readValue(resultContent, ResponseEntity.class);
		//assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		//assertNotNull(location);
		
		
		result = mockMvc.perform(get("/tasks/"+ id).content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(Integer.toString(status), "200");
	}



	
	@Test
	public void getTasksForParentIdTest() throws Exception {
			Task task = new Task();
			task.setTitle("Cognizant Project with parent id");
			task.setIsProject(true);
			task.setParent(18180);
			
			// save this task
			String jsonRequest = om.writeValueAsString(task);
			MvcResult result = mockMvc.perform(post("/tasks").content(jsonRequest)
					.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
			String location = (String)result.getResponse().getHeaderValue("Location");
			String id = location.substring(location.length() - 1, location.length());
			
			System.out.println("getTaskWithParentIdTest() id for task is " + id );
			//ResponseEntity response = om.readValue(resultContent, ResponseEntity.class);
			//assertEquals(response.getStatusCode(), HttpStatus.CREATED);
			//assertNotNull(location);
			
			
			result = mockMvc.perform(get("/tasks?parentId="+ "18180" ).content(jsonRequest)
					.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
			
			int status = result.getResponse().getStatus();
			assertEquals(Integer.toString(status), "200");

	}


}