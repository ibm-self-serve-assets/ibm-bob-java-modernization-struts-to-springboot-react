package com.pks.insurance.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener implementation class AttributeListener
 * 
 * For Future Use only
 */
public class AttributeListener implements HttpSessionAttributeListener,
		ServletRequestAttributeListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AttributeListener.class);

	/**
	 * Default constructor.
	 */
	public AttributeListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("SESSION D:   NAME: " + event.getName() + "\tVALUE: "
				+ event.getValue() + "\t");
	}

	/**
	 * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
	 */
	@Override
	public void attributeAdded(ServletRequestAttributeEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("REQUEST A:   NAME: " + event.getName() + "\tVALUE: "
				+ event.getValue() + "\t");
	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("SESSION A:   NAME: " + event.getName() + "\tVALUE: "
				+ event.getValue() + "\t");
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("SESSION R:   NAME: " + event.getName() + "\tVALUE: "
				+ event.getValue() + "\t");
	}

	/**
	 * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
	 */
	@Override
	public void attributeRemoved(ServletRequestAttributeEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("REQUEST D:   NAME: " + event.getName() + "\tVALUE: "
				+ event.getValue() + "\t");
	}

	/**
	 * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
	 */
	@Override
	public void attributeReplaced(ServletRequestAttributeEvent event) {
		// TODO Auto-generated method stub
		LOGGER.info("REQUEST R:   NAME: " + event.getName() + "\tVALUE: "
				+ event.getValue() + "\t");
	}

}
