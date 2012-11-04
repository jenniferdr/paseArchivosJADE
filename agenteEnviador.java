package paseArchivo.agenteEnviador;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class agenteEnviador extends Agent{

    private String fileName;
    private Logger Log = Logger.getMyLogger(getClass().getName());

    protected void setup(){
	    
	// Obtener el nombre del archivo
	Object[] args= getArguments();
	if(args!=null && args.length>0){
	    this.fileName= (String) args[0];
	    
	    //addBehaviour(new SendFileBehaviour(this));
	    System.out.println(fileName);
	}else{
	    System.out.println("No se especifico el nombre del archivo");
	    doDelete();
	} 
		
    }
    private class SendFileBehaviour extends Behaviour{
	
	public SendFileBehaviour(Agent a){
	    super(a);
	}

	public boolean done(){
	    return true;
	}

	public void action(){
	    // Colocar aqui lo de buscar el archivo this.fileName
	    // Y mandarle el mansaje a ?? Quien. Ver en ejemplo
	}

    }

}