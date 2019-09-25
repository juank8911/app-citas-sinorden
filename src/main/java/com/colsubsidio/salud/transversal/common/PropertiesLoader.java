package com.colsubsidio.salud.transversal.common;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
  * @author Oscar Efren
 */
public class PropertiesLoader {
    private static PropertiesLoader instance;
    private static Properties properties = new Properties();
    
    public PropertiesLoader()
    {
        load();
    }

    public static PropertiesLoader getInstance()
    {
        if(instance == null)
            synchronized(PropertiesLoader.class)
            {
                if(instance == null)
                    instance = new PropertiesLoader();
            }
        return instance;
    }

    public Properties load()
    {
        try
        {
        	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("props/salud.properties");
        	if(in == null){
        	    properties.load(new FileInputStream("props/salud.properties") );
        	}else{
        	    properties.load(in);
        	}
        }
        catch(IOException e)
        {
            System.err.println("error cargando archivo de propiedades" + e.getMessage());
         }
        return properties;
    }

    public String getProperty(String prop)
    {   
        String value = properties.getProperty(prop);
        if (value == null)
            System.err.println("ERROR propiedad NULA "+prop);
        return value;
    }
 

    public Enumeration<?> getPropertyNames()
    {
        return properties.propertyNames();
    }




}
