package org.PerdomoDeVega.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Main {
    public static void main(String[] args) throws JMSException {
        // Configura la conexión a ActiveMQ
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Crea una sesión
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Crea una cola
        Destination destination = session.createQueue("mi_cola");

        // Crea un productor
        MessageProducer producer = session.createProducer(destination);

        // Crea un mensaje
        TextMessage message = session.createTextMessage("Hola, este es un mensaje de ejemplo.");

        // Envia el mensaje
        producer.send(message);

        // Cierra la conexión
        connection.close();
    }
}
