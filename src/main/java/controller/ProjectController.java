package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Projects;
import service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
@Autowired
ProjectService ps;
@RequestMapping("{userName}")
public Projects getUserProjects(@PathVariable("userName") String userName) {
	return ps.getUserProjects(userName);
}

@RequestMapping("{userName}/{projectId}")
public List<String> getProjectDetail(@PathVariable("userName") String userName, @PathVariable("projectId") int projectId) {
	return ps.getProjectDetail(userName,projectId);
}
}
