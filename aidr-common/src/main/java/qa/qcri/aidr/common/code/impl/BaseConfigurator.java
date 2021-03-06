package qa.qcri.aidr.common.code.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import qa.qcri.aidr.common.code.ConfigurationPropertiesUtil;
import qa.qcri.aidr.common.code.ConfigurationProperty;
import qa.qcri.aidr.common.code.Configurator;
import qa.qcri.aidr.common.exception.ConfigurationPropertyFileException;
import qa.qcri.aidr.common.exception.ConfigurationPropertyNotRecognizedException;
import qa.qcri.aidr.common.exception.ConfigurationPropertyNotSetException;

public abstract class BaseConfigurator implements Configurator {

	private static final Logger LOGGER = Logger
			.getLogger(BaseConfigurator.class);

	private Map<String, String> propertyMap;

	@Override
	public void initProperties(String configLoadFileName,
			ConfigurationProperty[] configurationProperties)
			throws ConfigurationPropertyNotSetException,
			ConfigurationPropertyNotRecognizedException,
			ConfigurationPropertyFileException {
		LOGGER.info("Initializing Properties: " + configurationProperties
				+ " from file : " + configLoadFileName);
		propertyMap = ConfigurationPropertiesUtil.readConfigurations(
				configurationProperties, configLoadFileName);
	}

	@Override
	public String getProperty(ConfigurationProperty property) {
		return propertyMap.get(property.getName());
	}

}
