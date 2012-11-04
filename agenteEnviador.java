
package examples.AgenteEnviador;

	
import java.io.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;

public class agenteEnviador extends Agent{

    private String fileName;
    private Logger Log = Logger.getMyLogger(getClass().getName());
    private byte[] fileContent;

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

	    StringBuffer strContent = new StringBuffer(""); 
	    
	    // Cargar el contenido del archivo en el buffer strContent
	    try {
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String str;
		while ((str = br.readLine()) != null) {
		    //fileContent = str.getBytes("UTF-16LE");
		    strContent.append(str);	     
		    //System.out.println(str);
		}
		in.close();
	    } catch (Exception e) {
		System.err.println(e);
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

	    msg.setByteSequenceContent((strContent.toString()).getBytes());
	    msg.addUserDefinedParameter("file-name", fileName);
	    for(int i=0; i< receiverAgents.length ;i++){
		msg.addReceiver(receiverAgents[i]);
	    }
	    send(msg);
	    finished= true;
	    myAgent.doDelete();
	}// fin de metodo action
	
    }// FIN de clase privada SendFileBehaviour 

}
