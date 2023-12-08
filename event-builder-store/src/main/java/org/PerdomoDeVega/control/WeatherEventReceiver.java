package org.PerdomoDeVega.control;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class WeatherEventReceiver {

    private static String brokerURL = "tcp://localhost:61616";
    private static String topicName = "Weather.prediction.test";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("1001");

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        TopicSubscriber durableSubscriber = session.createDurableSubscriber((Topic) destination, "ElMejor");


        durableSubscriber.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Received message: " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // No es necesario el tiempo de espera
        try {
             Thread.sleep(100000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

        connection.close();
    }
}
