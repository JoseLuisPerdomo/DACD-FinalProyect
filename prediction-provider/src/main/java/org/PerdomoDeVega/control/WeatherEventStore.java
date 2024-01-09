package org.PerdomoDeVega.control;

import javax.jms.*;

import com.google.gson.Gson;
import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.ArrayList;
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

        List<String> serializedWeathers = SerializeWeathers(weatherData);

        for (int i = 0; i < weatherData.size();i++) {
            try {
                TextMessage message = session.createTextMessage(serializedWeathers.get(i));

                message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

                producer.send(message);

                System.out.println("Message sent '" + message.getText() + "'");

            } catch (JMSException e) {
                throw new StoreException(e.getMessage(), e);
            }
        }
    }

    public List<String> SerializeWeathers(List<Weather> weatherData){
        List<String> SerializedData = new ArrayList<>();
        for (Weather weatherDatum : weatherData) {
            Gson gson = new Gson();
            SerializedData.add(gson.toJson(weatherDatum));
        }
        return SerializedData;
    }
}