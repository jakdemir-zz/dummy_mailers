package edu.jak.dummymailers.util;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.gg.java.util.LogUtil;
import com.gg.java.util.StringUtil;


public class JndiManager {
	private static InitialContext initialContext = null;
	private static Map<Class<?>, String> jndiNames = new HashMap<Class<?>, String>();
	
	/**	
	 * To obtain ear name from the deployment url
	 * E.g. jar:file:/C:/jboss-4.2.1.GA/server/default/tmp/deploy/tmp51753n3gservicesEar-0.0.1.ear-contents/...	--> n3gservicesEar-0.0.1
	 * or	file:/C:/jboss-4.2.1.GA/server/default/deploy/n3gservices.ear/n3gservicesEJB.jar/...				--> n3gservices
	 */
	private static final String JBOSS_DEPLOY_DIR_NAME 	= "/deploy/";
	private static final String EAR_SUFFIX 				= ".ear";
	private static final String REGEX_TMP				= "tmp\\d*";
	
	private static final String SUFFIX_LOCAL 	= "/local";
	private static final String SUFFIX_REMOTE 	= "/remote";
	
	// Project Specific Settings
	private static final String PROJECT_ROOT	= "edu/jak/dummymailers/";
	private static final String REGEX 			= ".*("+PROJECT_ROOT+").*(Local|Remote)\\.class";
	private static final Class<?> REFERENCE_CLASS 	= JndiManager.class;	 // must be in a package who's parent is PROJECT_ROOT for exploded deployment
	
	private static void putJndiNames(Class<?> clazz, String earName) {
		String name = clazz.getSimpleName()+".class";	
		
		name = name.replace("Local.class", SUFFIX_LOCAL);
		name = name.replace("Remote.class", SUFFIX_REMOTE);
		
		String value = earName+"/"+name;
		
		jndiNames.put(clazz, value);
		//LogUtil.getInstance(ApiConstants.API_LOG4J_LOGGER_NAME).debug(clazz.getName()+" --> "+value);	
	}

	// dynamic JNDI population. reads local and remote facades dynamically and add them to the JNDI
	static {
		try {
			Pattern pattern = Pattern.compile(REGEX);
			
			URL url = REFERENCE_CLASS.getResource("");
			
			String earName = getEarName(url.toString());
			
			if(url.getProtocol().equals("jar")) {
				String name = url.getFile().substring(0, url.getFile().indexOf(".jar!")+4);
				JarFile jarFile = new JarFile(name.replace("file:", ""));
				
				Enumeration<JarEntry> enumeration = jarFile.entries();
				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = (JarEntry) enumeration.nextElement();
					if(pattern.matcher(jarEntry.getName()).matches()) {
						String className = jarEntry.getName().replaceAll("/", ".").replace(".class", "");
						putJndiNames(Class.forName(className), earName);
					}
				}
			}else {
				File sbDirectory = new File(url.toURI()).getParentFile();
				
				if(sbDirectory.exists() && sbDirectory.isDirectory()) {					
					scanSessionBeans(sbDirectory, pattern, earName);					
				}
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getEarName(String urlStr) {
		String earName = "";
		
		int deployIndex = urlStr.indexOf(JBOSS_DEPLOY_DIR_NAME);
		if(deployIndex != -1) {
			int earIndex = urlStr.indexOf(EAR_SUFFIX, deployIndex);
			if(earIndex != -1) {
				earName = urlStr.substring(deployIndex+JBOSS_DEPLOY_DIR_NAME.length(), earIndex);

				String[] array = earName.split(REGEX_TMP);
				for(String element : array) {
					if(! StringUtil.isNullOrZeroLength(element)) {
						earName = element; 
						break;
					}
				}
			}
		}
		
		return earName;
	}
	
	private static void scanSessionBeans(File dir, Pattern pattern, String earName) throws ClassNotFoundException {
		File[] children = dir.listFiles();
		for(File file : children) {
			if(file.isDirectory()) {
				scanSessionBeans(file, pattern, earName);
			}else {
				String name = file.getAbsolutePath().replaceAll("\\\\", "/");
				if(pattern.matcher(name).matches()) {	
					name = name.substring(name.indexOf(PROJECT_ROOT));					
					name = name.replace("/", ".").replace(".class", "");
					putJndiNames(Class.forName(name), earName);
				}
			}
		}
	}
		
	// no public constructor
	private JndiManager() {
		super();
	}
	
	public static String getJndiName(Class<?> clazz) {
		return jndiNames.get(clazz);
	}
	
	// must be synchronized
	private static synchronized InitialContext getInitialContext() throws NamingException {
		if(initialContext == null) {
			initialContext = new InitialContext();
		}
		return initialContext;
	}
	
	public static Object lookup(String name) {
		Object object = null;
		try {
			object = getInitialContext().lookup(name);
		}catch(NamingException e) {
			throw new RuntimeException("error.ws.persistence", e.getCause());
		}
		return object;
	}

	public static Object lookup(Class<?> clazz) {
		String name = getJndiName(clazz);
		return lookup(name);
	}
}
