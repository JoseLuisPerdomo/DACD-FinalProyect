package org.PerdomoDeVega.control;

import com.google.gson.Gson;
import org.PerdomoDeVega.exception.StoreException;
import org.PerdomoDeVega.model.HotelRatesEvent;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class XoteloEventStore implements HotelEventStore{
    private Session session;
    private Connection connection;
    private MessageProducer producer;
    private final String topic;

    public XoteloEventStore(String topic) {
        this.topic = topic;
    }


    public void prepareForStore(String topic) throws StoreException{
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            this.connection = connectionFactory.createConnection();
            connection.start();


            this.session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);


            Destination destination = session.createTopic(topic);


            this.producer = session.createProducer(destination);

        } catch (JMSException e) {
            throw new StoreException(e.getMessage(), e);
        }

    }

    public String SerializeHotelRates(HotelRatesEvent hotelRatesEvent){
        String SerializedHotelRates;
        Gson gson = new Gson();
        SerializedHotelRates = gson.toJson(hotelRatesEvent);
        return SerializedHotelRates;
        }

    @Override
    public void StoreHotelRates(HotelRatesEvent hotelRatesEvent) throws StoreException{
        prepareForStore(topic);
        String serializedWeathers = SerializeHotelRates(hotelRatesEvent);

        try {
            TextMessage message = session.createTextMessage(serializedWeathers);

            message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

            producer.send(message);

        } catch (JMSException e) {
            throw new StoreException(e.getMessage(), e);
        }
        try {
            connection.close();
        } catch (JMSException e) {
            throw new StoreException(e.getMessage(), e);
        }
    }
}
