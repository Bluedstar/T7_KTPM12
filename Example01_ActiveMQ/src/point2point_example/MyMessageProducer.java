package point2point_example;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyMessageProducer {
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageProducer producer;
	private Message message;
	private boolean useTransaction = false;

	public MyMessageProducer() throws NamingException, JMSException {

		//Thiết lập kết nối tới JMS provider,
		//thông tin cần thiết trong file jndi.properties
		Context context = new InitialContext();
		connectionFactory = (ConnectionFactory) context.lookup("connectionFactoryName");
		connection = connectionFactory.createConnection();
		connection.start();
		session=connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
		destination=session.createQueue("MyQueue");

		producer=session.createProducer(destination);
		message=session.createTextMessage("This is a test message");
		producer.send(message);
//		queue.MyQueue=jms.MyQueue 
		producer.close();
		session.close();
		connection.close();
	}
	public static void main(String[] args) {
		try {
			new MyMessageProducer();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
