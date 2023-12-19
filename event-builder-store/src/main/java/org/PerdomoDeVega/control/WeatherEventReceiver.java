package org.PerdomoDeVega.control;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.List;

public class WeatherEventReceiver {

    private static String brokerURL = "tcp://localhost:61616";
    private static String topicName = "Weather.prediction.test";
    private static List<String> receivedMessages;

    public static void RecieveEvent() implements EventReceiver {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("1001");

        connection.start();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic destination = session.createTopic(topicName);
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(destination, "ElMejor");

        receivedMessages.clear();


        durableSubscriber.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    try {
                        String textMessage = ((TextMessage) message).getText();
                        receivedMessages.add(textMessage);

                        System.out.println("Received message: " + textMessage);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        );

        try {
             Thread.sleep(100000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

        connection.close();
    }

    public List<String> getReceivedMessages(){
        return receivedMessages;
    }
}
