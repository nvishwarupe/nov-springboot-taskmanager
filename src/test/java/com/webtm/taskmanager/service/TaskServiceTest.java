package com.webtm.taskmanager.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.webtm.taskmanager.model.Task;
import com.webtm.taskmanager.repository.TaskRepository;



import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)    

class TaskServiceTest {

    @Mock
    TaskRepository taskRepoMock;  
    
    
    @InjectMocks
    TaskService service;
    
    

    @Test
    public void getTaskByIdTest()  {
        assertNotNull(taskRepoMock);
        Task mockTask = new Task();
        mockTask.setId(1);
        mockTask.setTitle("task for test");
        when(taskRepoMock.findById(1)).thenReturn(Optional.of(mockTask));
        
        Task result = service.findById(1);
        //System.out.println(result);
        assertEquals(service.findById(1).getTitle(), "task for test");
        
    }
    
    
    @Test
    public void findAllTest()  {
        assertNotNull(taskRepoMock);
        Task mockTask1 = new Task();
        mockTask1.setId(1);
        mockTask1.setTitle("task for test");
        
        Task mockTask2 = new Task();
        mockTask2.setId(2);
        mockTask2.setTitle("second task for test");
        
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(mockTask1);
        list.add(mockTask2);
        when(taskRepoMock.findAll()).thenReturn(list);
        
        List resultList = service.findAll();
        //resultList.forEach(System.out::println);
        assertEquals(resultList.size(), 2);
        
    }
    
    @Test
    public void findAllProjectsTest()  {
        assertNotNull(taskRepoMock);
        Task mockTask1 = new Task();
        mockTask1.setId(1);
        mockTask1.setTitle("task for test");
        mockTask1.setIsProject(true);
        
        
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(mockTask1);
        //list.add(mockTask2);
        
        when(taskRepoMock.findByIsProjectTrue()).thenReturn(list);
        
        List resultList = service.findProjects();
        //resultList.forEach(System.out::println);
        assertEquals(resultList.size(), 1);
        
    }
    
    
    @Test
    public void findAllTasksTest()  {
        assertNotNull(taskRepoMock);
        Task mockTask1 = new Task();
        mockTask1.setId(1);
        mockTask1.setTitle("task for test");
        mockTask1.setIsProject(false);
        
        
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(mockTask1);
        
        
        when(taskRepoMock.findByIsProjectFalse()).thenReturn(list);
        
        List resultList = service.findByIsProjectFalse();
        //resultList.forEach(System.out::println);
        assertEquals(resultList.size(), 1);
        
    }
    
    @Test
    public void findByParentIdTest()  {
        assertNotNull(taskRepoMock);
        Task mockTask1 = new Task();
        mockTask1.setId(1);
        mockTask1.setTitle("task for test");
        mockTask1.setIsProject(false);
        mockTask1.setParent(7);
        
        
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(mockTask1);
        
        
        when(taskRepoMock.findByParent(7)).thenReturn(list);
        
        List resultList = service.findByParentId(7);
        //resultList.forEach(System.out::println);
        assertEquals(resultList.size(), 1);
        
    }
    
   
    
    
    
}