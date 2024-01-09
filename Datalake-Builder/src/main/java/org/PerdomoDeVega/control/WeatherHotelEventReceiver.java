package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherHotelEventReceiver implements EventReceiver{

    private static String brokerURL;
    private static List<String> topicsName;
    private static List<List<String>> receivedMessages;
    private static List<String> clientsID;
    private static List<String> clientsNames;


    public WeatherHotelEventReceiver(String brokerURL, List<String> topicsName) {
        WeatherHotelEventReceiver.brokerURL = brokerURL;
        WeatherHotelEventReceiver.topicsName = topicsName;
        WeatherHotelEventReceiver.receivedMessages = new ArrayList<>();
        receivedMessages.add(new ArrayList<>());
        receivedMessages.add(new ArrayList<>());
        WeatherHotelEventReceiver.clientsID = new ArrayList<>();
        WeatherHotelEventReceiver.clientsID.add("1001");
        WeatherHotelEventReceiver.clientsID.add("1002");
        WeatherHotelEventReceiver.clientsNames = new ArrayList<>();
        WeatherHotelEventReceiver.clientsNames.add("Client1");
        WeatherHotelEventReceiver.clientsNames.add("Client2");

    }

    @Override
    public  List<List<String>> ReceiveEvent() throws ReceiverException {

        for (int i = 0; i < 2; i++) {
            receivedMessages.get(i).clear();
            TopicSubscriber durableSubscriber;
            Connection connection;

            try {
                connection = init_connection(i);
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
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        return receivedMessages;
    }

    private TopicSubscriber create_durableSubscriber(Connection connection, int i) throws JMSException{
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic destination = session.createTopic(topicsName.get(i));
        return session.createDurableSubscriber(destination, clientsNames.get(i));
    }

    private static Connection init_connection(int i) throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(clientsID.get(i));

        connection.start();



        return connection;
    }

    public List<List<String>> getReceivedMessages(){
        return receivedMessages;
    }

}