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


/*Agente que se registra con el tipo de servicio "agenteReceptor"
 recibe los archivos que sean enviados a este servicio. */
public class agenteReceptor extends Agent {

    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    protected void setup() {
	// Registro con el DF 
	DFAgentDescription dfd = new DFAgentDescription();
	ServiceDescription sd = new ServiceDescription();   
	sd.setType("agenteReceptor"); 
	sd.setName(getName());
	sd.setOwnership("TILAB");
	dfd.setName(getAID());
	dfd.addServices(sd);
	try {
	    DFService.register(this,dfd);
	    ReciveFile ReceiverBehaviour = new  ReciveFile(this);
	    addBehaviour(ReceiverBehaviour);
	} catch (FIPAException e) {
	    myLogger.log(Logger.SEVERE, "Agent "+getLocalName()
			 +" - Cannot register with DF", e);
	    doDelete();
	}
    }

    /* Clase interna que describe el comportamiento de recepcion 
       de archivos de este agente */ 
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
		reply.setPerformative(ACLMessage.REQUEST);
		reply.setContent("aja");

		myLogger.log(Logger.INFO, "Agent "+getLocalName()
			     +" - Received File from "
			     +msg.getSender().getLocalName());
	
		// Obtener el nombre del archivo 
		String path = msg.getUserDefinedParameter("file-name");
		String dirs[]= path.split("/");
		String fileName = dirs[dirs.length -1];

		//Obtener el contenido del archivo
		FileOutputStream out = null;
		byte[] fileContent = msg.getByteSequenceContent();
	
		/*
			FALTA HACER PERMISOS Y DIRECTORIOS
		*/
	
		// Almacenar contenido			
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
    } // FIN de la clase interna ReciveFile
	

}

