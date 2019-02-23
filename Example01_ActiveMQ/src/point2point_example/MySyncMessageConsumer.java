package point2point_example;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MySyncMessageConsumer {
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer consumer;
	private Message message;
	private boolean useTransaction = false;

	public MySyncMessageConsumer() throws NamingException, JMSException {
		Context context = new InitialContext();
		connectionFactory = (ConnectionFactory) context.lookup("connectionFactoryName");
		connection = connectionFactory.createConnection();
		connection.start();
		session=connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
		destination=session.createQueue("MyQueue");

		consumer=session.createConsumer(destination);
		message=(TextMessage)consumer.receive(1000);
		System.out.println("Received message: " + message);

		consumer.close();
		session.close();
		connection.close();
	}

	public static void main(String[] args) {
		try {
			new MySyncMessageConsumer();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
