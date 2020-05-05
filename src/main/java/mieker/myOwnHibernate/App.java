package mieker.myOwnHibernate;

import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class App {
	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;

	private static SessionFactory configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure();

		Properties properties = configuration.getProperties();

		serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		return sessionFactory;
	}

	public static void main(String[] args) {

		configureSessionFactory();

		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			
			/*
			// To save data into database.
			tx = session.beginTransaction();
			
			// Creating entity that will be save to the sqlite database
			Person personA = new Person("Jesica");
			
			Pup pup1 = new Pup("Szarik");
			List<Person> personList = session.createQuery("from Person").list();
			for (Person person : personList) {
				if (person.getName().equals("Alicja")) {
					person.addPup(pup1);
					Transaction transaction = session.beginTransaction();
					session.save(pup1);
					session.save(person);
					transaction.commit();
				}
			}

			// Saving to the database
			session.save(personA);

			// Committing the change in the database.
			session.flush();
			tx.commit();
			*/
			
			// Fetching saved data
			
			printPersons(session);
			
			// printPups(session);

		} catch (Exception ex) {
			ex.printStackTrace();

			// Rolling back the changes to make the data consistent in case of any failure
			// in between multiple database write operations.
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void printPups(Session session) {
		String puphql = "from Pup";
		List<Pup> pupList = session.createQuery(puphql).list();
		
		System.out.println("\nPups from database: ");
		for (Pup pup : pupList) {
			System.out.println(pup);
		}
	}

	public static void printPersons(Session session) {
		String hql = "from Person";
		List<Person> personsList = session.createQuery(hql).list();

		System.out.println("\nPersons from database: ");
		for (Person person : personsList) {
			System.out.println(person);
			System.out.println("  pups:");
			for (Pup pup : person.getPups()) {
				System.out.println("    " + pup);
			}
		}
	}
		
}
