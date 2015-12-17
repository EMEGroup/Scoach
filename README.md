# Scoach
Bot para Slack hecho en Java para dar seguimiento a participantes de competencias de programacion.

# Descripción
Esta es una herramienta creada para dar seguimiento al estado de los participanted de competencias de programación competitiva tales como la ACM ICPC entre otras, se puede obtener información de jueces en línea (por el momento, solo está disponible codeforces) tales como las soluciones enviadas por los competidores o cualquier otro nick perteneciente al juez en cuestión.

# Instalación
Este software está hecho en su totalidad utilizando Java y se puede integrar con relativa facilidad a un servidor web J2EE, solo clone el repositorio y gener un .jar si lo desea e integrelo a su servidor web.

# Puesta en marcha
El funcionamiento del bot es tan simple como crear una instancia de la clase *Bot* y pasarle el mapa de cabeceras HTTP a su metodo *work* :

>Bot botInstance = new Bot();

>botInstance.work( request.getParameterMap() );

# Dependencias
Este proyecto hace uso de las librerías GSON de google para el manejo de documentos json y javamail para el envío de mensajes. Las dependencias no están incluidas con el software para evitar problemas de licencia.

# Licencia
Este software no está atado a ninguna licencia por el momento, pero se escogerá alguna licencia permisiva en el futuro cercano. (Nótese que no se incluyen las dependencias del software para gozar de esta libertad)

# Contacto
Este software cuenta con un canal de IRC en el servidor *Freenode* bajo el cual se discuten temas sobre su diseño y desarrollo, siéntase libre de preguntar en el mismo por bugs y otras cosas.

IRC: [#slackbotcoachdev](irc://irc.freenode.net/slackbotcoachdev "irc.freenode.net/slackbotcoachdev")
