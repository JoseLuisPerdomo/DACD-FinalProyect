package org.PerdomoDeVega.control;

import javax.jms.*;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.List;

public class WeatherEventStore implements WeatherStore{

    private Session session;
    private Connection connection;
    private MessageProducer producer;

    public WeatherEventStore() {
    }

    @Override
    public void prepareForStore(String url, List<String> topic) throws StoreException{

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            this.connection = connectionFactory.createConnection();
            connection.start();


            this.session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);


            Destination destination = session.createTopic(topic.get(0));


            this.producer = session.createProducer(destination);

        } catch (JMSException e) {
            throw new StoreException(e.getMessage(), e);
        }

    }
    //private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    //private static final String topic = "Prediction.Weather";


    @Override
    public void StoreData(String dbPath, String tableName, List<Weather> weatherData) throws StoreException {


        try {
            TextMessage message = session.createTextMessage("esta mierda");

            message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

            producer.send(message);

            System.out.println("JCG printing@@ '" + message.getText() + "'");

            connection.close();

        } catch (JMSException e) {
            throw new StoreException(e.getMessage(), e);
        }
    }
}