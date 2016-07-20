package uk.co.shenghua.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;


public class App {
    public static void main(String[] args) throws NamingException, JMSException {

        String endpoint = args[0];
        String queueName = args[1];

        Connection connection = new ActiveMQConnectionFactory(endpoint).createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);

        MessageConsumer consumer = session.createConsumer(destination);

        connection.start();

        while (true) {
            TextMessage receivedMessage = (TextMessage) consumer.receive();

            String[] messages = receivedMessage.getText().split("\n");

            for (String message : messages) {
                System.out.println(message);
            }
        }
    }
}
