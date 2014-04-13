package com.sirra.bootstrap;

import com.sirra.appcore.util.config.*;
import com.sirra.server.*;

/**
 * Environment variables.
 * 
 * @author aris
 */
public class InitConfig {
	
	public static void init() {
		Config config = Config.getInstance();
		
		if(Mode.get() == Mode.Development) {
			
		} else {
			
		}
	}
}
