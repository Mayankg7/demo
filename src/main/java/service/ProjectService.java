package service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Service;

import model.Projects;
import model.ProjectInfo;

@Service
public class ProjectService {
	Hashtable<String, Projects> projects = new Hashtable<String, Projects>();

	public ProjectService() {
		Projects prj = new Projects();

		List<ProjectInfo> prjList = new ArrayList<ProjectInfo>();
		ProjectInfo prjInfo = new ProjectInfo();
		prjInfo.setProjectId(1);
		prjInfo.setProjectName("prj1");
		prjInfo.setUrl("prj1.com");
		prjList.add(prjInfo);
		prj.setProjectList(prjList);
		projects.put("Mayank", prj);

		ProjectInfo prjInfo1 = new ProjectInfo();
		List<ProjectInfo> prjList1 = new ArrayList<ProjectInfo>();
		Projects prj1 = new Projects();
		prjInfo1.setProjectId(2);
		prjInfo1.setProjectName("prj2");
		prjInfo1.setUrl("prj2.com");
		prjList1.add(prjInfo);
		prj1.setProjectList(prjList1);
		projects.put("User", prj1);

	}

	public Projects getUserProjects(String userName) {
		if (projects.containsKey(userName)) {
			return projects.get(userName);
		}
		return null;

	}

	public List<String> getProjectDetail(String userName, int projectId) {
		if (projects.containsKey(userName)) {
			List<ProjectInfo> prj = projects.get(userName).getProjectList();
			for (ProjectInfo prjName : prj) {
				if (prjName.getProjectId() == projectId) {
					List<String> projectAttr = new ArrayList<String>();
					projectAttr.add( prjName.getProjectName());
					projectAttr.add(prjName.getUrl());
					 return projectAttr;
				}
			}
		}
		return null;
	}
}