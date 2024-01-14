package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class SensorsReceiver implements SensorReciever{
    private static String brokerURL;
    private static List<String> topicsName;
    private static List<List<String>> receivedMessages;
    private static String clientID;
    private static String clientName;


    public SensorsReceiver(String brokerURL, List<String> topicsName) {
        SensorsReceiver.brokerURL = brokerURL;
        SensorsReceiver.topicsName = topicsName;
        SensorsReceiver.receivedMessages = new ArrayList<>();
        receivedMessages.add(new ArrayList<>());
        receivedMessages.add(new ArrayList<>());
        SensorsReceiver.clientID = "1001";
        SensorsReceiver.clientName = "Client3";

    }

    @Override
    public  List<List<String>> ReceiveEvent() throws ReceiverException {

        for (int i = 0; i < 2; i++) {
            receivedMessages.get(i).clear();
            TopicSubscriber durableSubscriber;
            Connection connection;

            try {
                connection = init_connection();
                durableSubscriber = create_durableSubscriber(connection, i);
            } catch (JMSException e) {
                throw new ReceiverException(e.getMessage(), e);
            }

            try {
                int finalI = i;
                durableSubscriber.setMessageListener(message -> {
                            if (message instanceof TextMessage) {
                                try {
                                    String textMessage = ((TextMessage) message).getText();
                                    receivedMessages.get(finalI).add(textMessage);

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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        return receivedMessages;
    }

    private TopicSubscriber create_durableSubscriber(Connection connection, int i) throws JMSException{
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic destination = session.createTopic(topicsName.get(i));
        return session.createDurableSubscriber(destination, clientName);
    }

    private static Connection init_connection() throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(clientID);

        connection.start();



        return connection;
    }
}
