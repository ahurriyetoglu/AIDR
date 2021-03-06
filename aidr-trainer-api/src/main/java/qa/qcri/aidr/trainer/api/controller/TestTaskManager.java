package qa.qcri.aidr.trainer.api.controller;


import javax.ws.rs.core.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qa.qcri.aidr.dbmanager.dto.DocumentDTO;
import qa.qcri.aidr.task.ejb.TaskManagerRemote;


@Path("/test")
@Component
public class TestTaskManager {

	@Context
	private UriInfo context;

	private static final String remoteEJBJNDIName = "java:global/AIDRTaskManager/aidr-task-manager-1.0/TaskManagerBean!qa.qcri.aidr.task.ejb.TaskManagerRemote";
	//@EJB(mappedName=remoteEJBJNDIName)

	@Autowired TaskManagerRemote<DocumentDTO, Long> taskManager;

	public TestTaskManager() {
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/remoteEJB")
	public Response test() {
		StringBuilder respString = new StringBuilder().append("Fetched new doc details = ");
		try {
			long startTime = System.currentTimeMillis();
			/*
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
			props.setProperty("java.naming.factory, url.pkgs", "com.sun.enterprise.naming");
			props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
			props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

			InitialContext ctx = new InitialContext();

			taskManager = (TaskManagerRemote<qa.qcri.aidr.task.entities.Document, Long>) ctx.lookup(remoteEJBJNDIName);
			 */
			System.out.println("taskManager: " + taskManager + ", time taken to initialize = " + (System.currentTimeMillis() - startTime));
			if (taskManager != null) {
				System.out.println("Success in connecting to remote EJB to initialize taskManager");
			}
			long elapsed = 0L;

			DocumentDTO dto = taskManager.getTaskById(4579257L);
			elapsed = System.currentTimeMillis() - startTime;
			if (dto != null) {
				respString.append("documentID: ").append(dto.getDocumentID()).append(", crisisID: ").append(dto.getCrisisDTO().getCrisisID());
				respString.append(", taskAssignment: ").append(dto.getTaskAssignmentsDTO() != null ? dto.getTaskAssignmentsDTO().size() : null);
				//respString.append(", nominalLabel size: ").append(document.getNominalLabelCollection() != null ? document.getNominalLabelCollection().size() : 0);
			} else {
				respString.append("null");
			}
			System.out.println("[main] " + respString.toString() + ", time taken = " + elapsed);
		} catch (Exception e) {
			System.err.println("Error in JNDI lookup");
			respString.append("Error in JNDI lookup");
			e.printStackTrace();
		}
		return Response.ok(respString.toString()).build();
	}

	public static void main(String[] args) throws Exception {
		TestTaskManager tc = new TestTaskManager(); 
		System.out.println("Result: " + tc.test().toString());
	}
}
