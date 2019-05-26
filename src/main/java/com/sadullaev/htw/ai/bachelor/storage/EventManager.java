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
	
	private static List<Event> allEvents = null;

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
	
	public void loadApache() {
		
		
		
		
	}
	
	public void loadData() {
		Session session = sessionFactory.openSession();
		allEvents = session.createCriteria(Event.class).list();
		
		session.close();
	}
	
	@SuppressWarnings("unchecked")     
	public List<Event> getAllEvents (){
		return allEvents;
	}
	
}
