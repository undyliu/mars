package com.javadocking.model.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.javadocking.DockingManager;
import com.javadocking.model.DockModel;
import com.javadocking.model.DockingPathModel;
import com.javadocking.util.PropertiesUtil;

/**
 * This class encodes the {@link com.javadocking.model.DockModel} and {@link com.javadocking.model.DockingPathModel} 
 * into a java.util.Properties object.
 * After that, it encodes this properties object in a file with extension <b>.dck</b>.
 * 
 * @author Heidi Rakels.
 */
public class DockModelPropertiesEncoder implements DockModelEncoder
{

	// Static fields.

	/** The extension of the files generated by this dock model group encoder. */
	public static final String 		EXTENSION 			= "dck";

	/** The name of the <code>dockModelClass</code> property of a dock model. */
	private static final String 	PROPERTY_CLASS 		= "dockModelClass";
	/** The name of the <code>version</code> property of the file. */
	private static final String 	PROPERTY_VERSION 	= "version";
	/** The current version of the file. */
	private static final String 	VERSION 			= "1.1";
	/** The comment that is written at the top of the properties files generated by this class. */
	private static final String 	COMMENT 			= "Properties for a com.javadocking.model.DockModel generated by DockModelPropertiesEncoder";

	// Implementations of DockModelEncoder.

	public boolean canExport(DockModel dockModel, String destinationName)
	{
		// The name should have extension EXTENSION.
		if (!(destinationName.endsWith(EXTENSION)))
		{
			return false;
		}

		return true;
	}

	public boolean canSave(DockModel dockModel)
	{

		return true;

	}
	
	public void export(DockModel dockModel, String destinationName) throws IOException, IllegalArgumentException
	{
		// Check if the model can be saved or exported.
		if (!canExport(dockModel, destinationName))
		{
			throw new IllegalArgumentException("This dock model cannot be saved.");
		}
		
		// Save the dock model into a properties object.
		Properties properties = saveProperties(dockModel);
		
		// Store the properties.
		PropertiesUtil.saveProperties(properties, destinationName, COMMENT);
		
	}

	public void save(DockModel dockModel) throws IOException, IllegalArgumentException
	{
		if (dockModel.getSource() == null)
		{
			throw new IOException("The source path is null.");
		}
		export(dockModel, dockModel.getSource());
	}


	// Private methods.

	/**
	 * Saves the dock model and docking path model in a properties object.
	 */
	private Properties saveProperties(DockModel dockModel)
	{
		// Create the properties object.
		Properties properties = new Properties();
		
		// Save the version of the file.
		PropertiesUtil.setString(properties, PROPERTY_VERSION, VERSION);
		
		// Save the class of the dock model.
		PropertiesUtil.setString(properties, PROPERTY_CLASS, dockModel.getClass().getName());
		
		// The mapping with the keys that will be used for saving the docks.
		Map dockKeys = new HashMap();
		
		// The docking path model.
		DockingPathModel dockingPathModel = DockingManager.getDockingPathModel();
		
		// Save the properties.
		saveProperties(dockModel, dockingPathModel, properties, dockKeys);

		return properties;
	}
	
	protected void saveProperties(DockModel dockModel, DockingPathModel dockingPathModel, Properties properties, Map dockKeys) {
		
		// Save the properties of the dock model.
		dockModel.saveProperties("dockModel.", properties, dockKeys);
		
		// Save the properties of the docking paths.
		
		if (dockingPathModel != null)
		{
			dockingPathModel.saveProperties("dockingPathModel.", properties, dockKeys);
		}
	}

}
