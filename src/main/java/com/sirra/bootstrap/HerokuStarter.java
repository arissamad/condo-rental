package com.sirra.bootstrap;

import org.eclipse.jetty.annotations.*;
import org.eclipse.jetty.plus.webapp.*;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.webapp.*;

import com.sirra.server.*;
import com.sirra.server.persistence.*;
import com.sirra.server.rest.*;
import com.sirra.server.staticfiles.filter.*;
import com.sirra.server.staticfiles.filter.common.*;
import com.sirra.appcore.dailyprocess.*;
import com.sirra.appcore.email.*;
import com.sirra.appcore.firebase.*;
import com.sirra.appcore.hibernatetypes.*;
import com.sirra.appcore.plans.*;
import com.sirra.appcore.sql.*;

public class HerokuStarter {
	
	public static void main(String[] args)
	throws Exception 
	{
		System.out.println("Starting condo-rental... ");
		System.out.println("Mode: " + Mode.get().name());

    	// Initialize the Config environment variables
    	InitConfig.init();
    	
    	CacheFilter cacheFilter = new CacheFilter("/images/", "jquery-");
    	FilterEngine.addFilter(cacheFilter);
    	
    	System.out.println("Server Configured. Now starting webserver.");
    	
    	String webPort = System.getenv("PORT");
        int port = isBlank(webPort) ? 8080 : Integer.parseInt(webPort);
        
		Server server = new Server(port);

		String wardir = "target/condo-rental-1.0/";

		WebAppContext context = new WebAppContext();
		
		context.setResourceBase(wardir);
		context.setDescriptor(wardir + "WEB-INF/web.xml");
		
		context.setConfigurations(new Configuration[] {
				new AnnotationConfiguration(), new WebXmlConfiguration(),
				new WebInfConfiguration(), new TagLibConfiguration(),
				new PlusConfiguration(), new MetaInfConfiguration(),
				new FragmentConfiguration(), new EnvConfiguration() });

		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		
		server.setHandler(context);
		server.start();
		server.join();
		
		System.out.println("condo-rental has terminated.");
	}
	
	private static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }
}
