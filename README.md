paseArchivosJADE
================

Pase de archivos usando el framework JADE

Instrucciones para la ejecucion del proyecto
============================================

*    Primero se deben compilar los archivos agenteReceptor.java y 
agenteEmisor.java y colocar los archivos .class en un directorio
accesible desde el classpath. 

Ejemplo:

    java -d /home/usuario/JADE/jade-bin/classes agenteEnviador.java agenteReceptor.java

En este caso el classpath debe incluir la ruta /home/usuario/JADE/jade-bin/classes
para que la plataforma de JADE pueda encontrar a los agentes e iniciarlos. 

*    Iniciar la plataforma de JADE. Se debe ejecutar el siguiente comando:

    java jade.Boot -gui -local-host <ip> 
 

*    Instanciar los agentes receptores. Como iniciamos la plataforma JADE con
la opcion -gui debemos ver una pantalla en la que podremos agregar los agentes.
Los agentes receptores deberan ser de la clase paseArchivos.AgenteReceptor.agenteReceptor.
Para mas informacion puede consultar la guia en version pdf ubicada en el repositorio.

*    Instanciar los agentes enviadores. Se sigue el mismo procedimiento que para 
iniciar a los agentes receptores. Sin embargo la clase de los agentes que envian archivos 
es paseArchivos.AgenteEnviador.agenteEnviador. Ademas se debe indicar como argumento la
ruta o path del archivo que se desea enviar.

Si se quiere iniciar el agente enviador en otro host debemos iniciar un container en el
host remoto y agregar el agente. Nota que debes tener las clases de los agentes
compiladas en el host remoto tambien. Un ejemplo de como iniciar el container es:

    java jade.Boot -local-host <ip-local> -host <ip-remota> -agent 

Al seguir estos pasos el resultado debe ser que el archivo enviado 
