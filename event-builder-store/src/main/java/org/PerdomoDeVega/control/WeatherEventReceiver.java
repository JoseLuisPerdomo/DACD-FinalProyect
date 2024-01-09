package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherEventReceiver implements EventReceiver{

    private static String brokerURL;
    private static String topicName;
    private static List<String> receivedMessages;


    public WeatherEventReceiver(String brokerURL, String topicName) {
        WeatherEventReceiver.brokerURL = brokerURL;
        WeatherEventReceiver.topicName = topicName;
        WeatherEventReceiver.receivedMessages = new ArrayList<>();
    }

    @Override
    public  List<String> ReceiveEvent() throws ReceiverException {

        receivedMessages.clear();
        TopicSubscriber durableSubscriber;
        Connection connection = null;

        try {
            connection = init_connection(connection);
            durableSubscriber = create_durableSubscriber(connection);
        } catch (JMSException e) {
            throw new ReceiverException(e.getMessage(), e);
        }

        try {
            durableSubscriber.setMessageListener(message -> {
                        if (message instanceof TextMessage) {
                            try {
                                String textMessage = ((TextMessage) message).getText();
                                receivedMessages.add(textMessage);

                                System.out.println("Received message: " + textMessage);
                            } catch (JMSException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
            );
        } catch (JMSException e) {
            throw new ReceiverException(e.getMessage(), e);
        }

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection.close();
        } catch (JMSException e) {
            throw new ReceiverException(e.getMessage(), e);
        }
        return receivedMessages;
    }

    private TopicSubscriber create_durableSubscriber(Connection connection) throws JMSException{
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic destination = session.createTopic(topicName);
        return session.createDurableSubscriber(destination, "Cliente1");
    }

    private static Connection init_connection(Connection connection) throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        connection = connectionFactory.createConnection();
        connection.setClientID("1001");

        connection.start();



        return connection;
    }

    public List<String> getReceivedMessages(){
        return receivedMessages;
    }

    public static void main(String[] args) {
        WeatherEventReceiver weatherEventReceiver = new WeatherEventReceiver("tcp://localhost:61616", "Weather.prediction.test");
        List<String> events = null;
        try {
            events =  weatherEventReceiver.ReceiveEvent();
        } catch (ReceiverException e) {
            System.out.println(e.getMessage());
        }
    }
}