package paseArchivos.AgenteEnviador;
	
import java.io.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.util.LinkedList;

/*Agente que se instancia para iniciar la transferencia de un archivo 
cuyo path debe ser pasado como argumento. El agente muere luego de 
finalizar su accion*/
public class agenteEnviador extends Agent{

    private String fileName;
    private Logger Log = Logger.getMyLogger(getClass().getName());
   

    protected void setup(){
	    
	// Obtener el nombre del archivo
	Object[] args= getArguments();
	if(args!=null && args.length>0){
	    this.fileName= (String) args[0];
	    addBehaviour(new SendFileBehaviour(this));
	    System.out.println("Nombre del archivo: "+fileName);
	}else{
	    System.out.println("No se especifico el nombre del archivo");
	    doDelete();
	} 
    }

    private class SendFileBehaviour extends Behaviour{
	private boolean finished;
	
	public SendFileBehaviour(Agent a){
	    super(a);
	    finished=false;
	}

	public boolean done(){
	    return finished;
	}

	public void action(){
		
	    // Cargar el contenido del archivo en la variable bytefileContent
	    FileInputStream in = null;
	    LinkedList<Integer> lista= new LinkedList<Integer>();
	    try {
		in = new FileInputStream(fileName);
		int c;
		int cont = 0;
		while ((c = in.read()) != -1) {
		    // Leer byte a byte e insertarlos en la lista
		    lista.add(c);
		}
	    } catch (Exception e) {
		System.err.println(e);
	    }catch(OutOfMemoryError b){
		System.out.println("Error: El archivo sobrepasa el limite de tamaño");
		myAgent.doDelete();
	    }
	    Object[] fileContent= lista.toArray();
	    byte[] bytefileContent= new byte[lista.size()];
	    for(int i=0; i<lista.size(); i++){
		bytefileContent[i]= (((Integer)fileContent[i]).byteValue());
	    }

	    /* Buscar agentes registrados para recibir archivos.
	       Los ID de los agentes se guardan en receiverAgents */
	    DFAgentDescription temp = new DFAgentDescription();
	    ServiceDescription sd = new ServiceDescription();
	    sd.setType("agenteReceptor");
	    temp.addServices(sd);
	    AID receiverAgents[]= new AID[0];
	    try {
		DFAgentDescription[] result = DFService.search(myAgent, temp);
		// Guardar los nombres de los agentes
		receiverAgents= new AID[result.length];
		for (int i = 0; i < result.length; ++i) {
		    receiverAgents[i] = result[i].getName();
		    System.out.println(receiverAgents[i].getName());
		}
	    }catch(FIPAException fe){
		fe.printStackTrace();
	    }

	    // Crear el mensaje para enviarlo a los agentes registrados
	    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	    msg.setByteSequenceContent(bytefileContent);
	    msg.addUserDefinedParameter("file-name", fileName);
	    for(int i=0; i< receiverAgents.length ;i++){
		msg.addReceiver(receiverAgents[i]);}
	    send(msg);
	    finished= true;
	    myAgent.doDelete();
	}// fin de metodo action
	
    }// FIN de clase privada SendFileBehaviour 

}
