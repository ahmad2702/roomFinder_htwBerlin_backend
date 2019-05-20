package com.sadullaev.htw.ai.bachelor.storage;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sadullaev.htw.ai.bachelor.model.Event;

public class EventManager {
	
protected SessionFactory sessionFactory;
	
	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
		    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
		    StandardServiceRegistryBuilder.destroy(registry);
		    System.out.println("Datenbank wurde nicht gefunden.");
		}
    }
	
	@SuppressWarnings("unchecked")     
	public List<Event> getAllEvents (){
		List<Event> collection = null;
		
		Session session = sessionFactory.openSession();
		collection = session.createCriteria(Event.class).list();
		
		session.close();
		return collection;
	}
	
}
