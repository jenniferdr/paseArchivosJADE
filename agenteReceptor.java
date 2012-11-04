/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package examples.PingAgent;
import java.io.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;

/**
 * This agent implements a simple Ping Agent that registers itself with the DF and 
 * then waits for ACLMessages.
 * If  a REQUEST message is received containing the string "ping" within the content 
 * then it replies with an INFORM message whose content will be the string "pong". 
 * 
 * @author Tiziana Trucco - CSELT S.p.A.
 * @version  $Date: 2010-04-08 13:08:55 +0200 (gio, 08 apr 2010) $ $Revision: 6297 $  
 */
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
		//ACLMessage reply = msg.createReply();
		String content = msg.getContent();
		myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Received Send Request from "+msg.getSender().getLocalName());
		reply.setPerformative(ACLMessage.REQUEST);
		reply.setContent("aja");
		/// Lo hice yo
		String fileName = msg.getUserDefinedParameter("file-name");
		byte[] fileContent = msg.getByteSequenceContent();
		File f;		
		f=new File(fileName);
		try{	
		    System.out.println("cree archivo ");
	
		    if(!f.exists()){
			f.createNewFile();
			System.out.println("cree archivo ");
		    }
		    FileOutputStream out = new FileOutputStream(f);
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

