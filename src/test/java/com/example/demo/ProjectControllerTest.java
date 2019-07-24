package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.research.ws.wadl.Application;

import controller.ProjectController;
import model.ProjectInfo;
import model.Projects;
import service.ProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectController.class)
@ContextConfiguration(classes = Application.class)
public class ProjectControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProjectService prjService;

	@Test
	public void testGetUsersProjects() throws Exception {

		Hashtable<String, Projects> projects = new Hashtable<String, Projects>();
		Projects mockProject = new Projects();

		List<ProjectInfo> prjList = new ArrayList<ProjectInfo>();
		ProjectInfo prjInfo = new ProjectInfo();
		prjInfo.setProjectId(1);
		prjInfo.setProjectName("prj1");
		prjList.add(prjInfo);
		mockProject.setProjectList(prjList);
		projects.put("Mayank", mockProject);

		Mockito.when(prjService.getUserProjects(Mockito.anyString())).thenReturn(mockProject);
		String URL = "/project/Mayank";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockProject);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	@Test
	public void testGetProject() throws Exception {

		Hashtable<String, Projects> projects = new Hashtable<String, Projects>();
		Projects mockProject = new Projects();
		String prjctName = null;
		List<ProjectInfo> prjList = new ArrayList<ProjectInfo>();
		List<String> prjAttr = new ArrayList<String>();
		ProjectInfo prjInfo = new ProjectInfo();
		prjInfo.setProjectId(1);
		prjInfo.setProjectName("prj1");
		prjList.add(prjInfo);
		mockProject.setProjectList(prjList);
		projects.put("Mayank", mockProject);
		if (projects.containsKey("Mayank")) {
			List<ProjectInfo> prj = projects.get("Mayank").getProjectList();
			for (ProjectInfo prjName : prj) {
				if (prjName.getProjectId() == 1) {
					prjctName = prjName.getProjectName();
					prjAttr.add(prjctName);
					prjAttr.add(prjName.getUrl());
					//prjctName= prjName.getUrl();
				}
			}
		}

		Mockito.when(prjService.getProjectDetail((Mockito.anyString()), (Mockito.anyInt()))).thenReturn(prjAttr);
		String URL = "/project/Mayank/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(prjctName);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);

	}
}
