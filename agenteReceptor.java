
package examples.PingAgent;
import static java.nio.file.StandardCopyOption.*;
import java.io.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;

public class agenteReceptor extends Agent {

    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    private class ReciveFile extends Behaviour {

	public ReciveFile(Agent a) {
	    super(a);
	}
		
	public boolean done(){
	    return false;
	}

	public void action() {
	    ACLMessage  msg = myAgent.receive();
	    if(msg != null){
		ACLMessage reply = msg.createReply();
		String content = msg.getContent();
		myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Received Send Request from "+msg.getSender().getLocalName());
		reply.setPerformative(ACLMessage.REQUEST);
		reply.setContent("aja");
		/// Lo hice yo
		String path = msg.getUserDefinedParameter("file-name");
		String dirs[]= path.split("/");
		String fileName = dirs[dirs.length -1];
		FileOutputStream out = null;
		byte[] fileContent = msg.getByteSequenceContent();
		//File f;		
		//f=new File(fileName);
		//System.out.println(msg.getByteSequenceContent());	
		/*



			FALTA HACER PERMISOS Y DIRECTORIOS
*/	
					
		try{
			out = new FileOutputStream(fileName);	
			int cont=0;
			out.write(fileContent);
		  	
		}catch(Exception e ){
		    System.out.println("error");
		}
						
	    }
				
	    else {
		block();
	    }
	}
    } // END of inner class WaitPingAndReplyBehaviour
	
    protected void setup() {
	// Registration with the DF 
	DFAgentDescription dfd = new DFAgentDescription();
	ServiceDescription sd = new ServiceDescription();   
	sd.setType("agenteReceptor"); 
	sd.setName(getName());
	sd.setOwnership("TILAB");
	dfd.setName(getAID());
	dfd.addServices(sd);
	try {
	    DFService.register(this,dfd);
	    ReciveFile PingBehaviour = new  ReciveFile(this);
	    addBehaviour(PingBehaviour);
	} catch (FIPAException e) {
	    myLogger.log(Logger.SEVERE, "Agent "+getLocalName()+" - Cannot register with DF", e);
	    doDelete();
	}

    }
}

