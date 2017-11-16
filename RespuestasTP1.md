**1) El servicio es escalable ? ¿ Qué ocurre con la escalabilidad en tamaño, geográfica y administrativa ? ¿ Qué límites de tiene ?**

    Sí, no es perfectamente escalable pero lo es. 
    En cuanto a TAMAÑO, nosotros realizamos el servicio en tres o cuatro máquinas distintas, si el tamaño de usuarios crece es cuestión de empezar a agregar algunas máquinas para soportar la carga. A su vez, al implementar el Registry, lo utilizamos como balanceador de cargas, ya que al realizar un pedido a un microservicio tiene una tarea muy sencilla que es responder a qué instancia de ese microservicio hacerle el pedido. En esta elección, elige entre los disponibles de manera random de modo que no se junte toda la carga siempre en la misma instancia. A su vez, tiene limitaciones ya que si sube mucho la cantidad de usuarios puede ser que el Registry no soporte la carga, y usar el sistemas en tantas máquinas puede resultar caro y habría que implementar distintas técnicas para la escabilidad a las que utilizamos.
    GEOGRÁFICAMENTE, el servicio está pensado para soportar distintos lugares con conexiones de latencia baja ya que priorizamos la disponibilidad y que eventualmente lo que se haya escrito estará escrito en todos los nodos (esa escritura la hacemos de manera asincrónica). Si no fuese de este modo, los usuarios localizados en lugares con baja latencia tendrían que esperar eternidades para recibir una respuesta del servicio. Tiene limitaciones claro que sí, que al ser la latencia muy baja la conexión entre el Registry y los distintos microservicios y viceversa, puede ser que un nodo se muera en el intento de comunicación y no haya una respuesta.

**2) ¿ Qué técnicas utilizó para mejorar la escalabilidad ?**
	
	Ocultar las latencias al no utilizar una base de datos, sino que guardarlos todos en memoria. En el caso de que hubiésemos utilizado una base de datos, una buena idea hubiese sido guardar los datos en Caché para tener una rápida respuesta y que no se sienta la latencia.
	Al no utilizar disco y solo memoria, no tendría mucho sentido particionar los datos ya que si perdemos un nodo perdemos todos los datos del mismo. Es por esto que elegimos replicar los datos para intentar lograr la mayor consistencia entre todos los nodos, aunque en un caso más real por ahí sería mejor particionar.

**3) ¿ Qué tipo de particionamiento de la funcionalidad utilizó ? (Vertical/Horizontal)**

	


**4) Teniendo en cuenta el teorema de CAP ¿ Qué propiedad tiene el servicio, consistencia o disponibilidad ?**

	Nuestro servicio tiene la propiedad de disponibilidad por sobre la de consistencia, ya que respondemos inmediatamente y luego intentamos lograr consistencia de manera asíncrona, de modo que nuestro usuario no tenga que esperar.

**5) Explicar si se logra o no los siguientes tipos de transparencia (Ubicación, Migración, Replicación) y cómo se logra.**

	En  nuestro caso tenemos sólo dos microservicios, un Wishlist y un Registry.
	En cuanto a ubicación, no se logra entre estos dos microservicios, ya que necesitamos que el Wishlist sepa donde está el Registry para poder comunicarse con él. Pero en el caso de que haya más microservicios, es lo bueno del Registry, no necesitaríamos saber donde están ubicados ya que él es el que se encarga de saber eso y nos lo comunica. Por otro lado, en vista al usuario se logra gracias al Registry porque el se quiere comunicar con el Wishlist y no tiene que saber donde está ubicado sino que el Registry se encarga.
	La migración  se logra ya que la transmisión de datos es transparente para el usuario y para los demás micro servicios ya que no se ven afectados por la migración de datos. Primero porque la migración a las demás instancias de un microservicio al escribir es de manera asincrónica y segundo porque al levantar un nodo, este no se publica al Registry hasta estar al día con los datos de otra instancia del microservicio, es decir, hasta replicarse por completo, por lo que el usuario no estaría enterado de que existe.
	La replicación sí se logra ya que mantenemos replicado el microservicio en múltiples nodos y mantenemos la transparencia al usuario y a futuros posibles microservicios que quieran comunicarse con Wishlist. Los demás saben que se comunican con Wishlist pero no que está replicado ni con qué instancia del microservicio se comunican. Se logra realizando la escritura en todos los nodos una vez que recibimos escritura en uno. Sin embargo, al priorizar la disponibilidad por sobre la consistencia, esto puede traer algunos problemas en cuanto a la veracidad de datos pero en un tiempo muy corto.

**6) ¿ Qué tipo de replicación se utilizó ? Sincrónica o Asincrónica ? ¿ Qué ventajas y desventajas tiene el tipo de replicación utilizada ?**

	Utilizamos el tipo de replicación asincrónica ya que, como dije antes, priorizamos la disponibilidad por sobre la consistencia. La gran ventaja de realizar la replicación de esta manera es la rápida respuesta al usuario, o al microservicio que se está comunicando con el mismo. La desventaja es que pueden surgir problemas de consistencia, ya que muchas veces el usuario puede ver datos que no son correctos, dado a que la replicación no fue hecha lo suficientemente rápida y al usuario se le comunicó que el dato ya estaba guardado.

**7) Si una instancia del servicio falla, funciona el fail-over, ¿ cómo ?**

	Mediante el Registry, el usuario se comunica con el Registry para comunicarse con otro microservicio y al comunicarse, si la instancia del servicio que el Registry nos comunicó falla volvemos a intentar con otra instancia diferente.

**8) Con respecto a los principios las arquitecturas SOA. ¿ Cómo se representa el contrato del servicio ? ¿ Qué puede decir con respecto a la encapsulamiento ?**


	Con respecto al encapsulamiento se puede decir que cada microservicio encapsula su comportamiento, logrando así bajar un poco la acoplación de los microservicios.
