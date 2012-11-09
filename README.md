paseArchivosJADE
================

Ejemplo para la transferencia de  archivos usando el framework JADE

Instrucciones para la ejecucion del proyecto
============================================

*    Primero se debe compilar los archivos *agenteReceptor.java* y *agenteEmisor.java*
 y colocar los archivos .class generados en un directorio accesible desde el classpath. 

Ejemplo:

    javac -d /home/usuario/JADE/jade-bin/classes agenteEnviador.java agenteReceptor.java

En este caso el classpath debe incluir la ruta /home/usuario/JADE/jade-bin/classes
para que la plataforma de JADE pueda encontrar a los agentes e iniciarlos. 

*    Iniciar la plataforma de JADE. Se debe ejecutar el siguiente comando:

    java jade.Boot -gui -local-host <ip> 
 

*    Instanciar los agentes receptores. Como iniciamos la plataforma JADE con
la opcion -gui debemos ver una pantalla en la que podremos agregar los agentes.
Los agentes receptores deberan ser de la clase paseArchivos.AgenteReceptor.agenteReceptor.
Para mas informacion puede consultar la guia en version pdf ubicada en el repositorio.

*    Instanciar los agentes enviadores. Se sigue el mismo procedimiento que para iniciar
a los agentes receptores. Sin embargo la clase de los agentes que envian archivos 
es paseArchivos.AgenteEnviador.agenteEnviador. Ademas se debe indicar como argumento la
ruta o path del archivo que se desea enviar.

Si se quiere iniciar el agente enviador en otro host debemos iniciar un container en el
host remoto, dentro de él deberá instanciarse el agente enviador. Nota que debes tener 
las clases de los agentes compiladas en el host remoto también.
Un ejemplo de como iniciar el container es:

    java jade.Boot -container -local-host <ip-local> -host <ip-remota> -agents <agente>

En donde:

    <ip-remota> es la direccion ip del host que contiene el main container


     <agente> es la descripcion del nombre del agente, nombre de la clase y argumentos 

     Ejemplo: 'enviador:paseArchivos.AgenteEnviador.agenteEnviador(/home/usuario/file)'

Nota que las comillas incluidas son necesarias para la ejecución del comando en el bash
debido a que los parentesis podrian ser interpretados de otra forma.  

Al seguir estos pasos el resultado debe ser que el archivo enviado se haya transmitido 
al directorio en donde el conteiner haya sido iniciado.   
