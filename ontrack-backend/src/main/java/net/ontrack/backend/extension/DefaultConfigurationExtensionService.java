package net.ontrack.backend.extension;

import net.ontrack.backend.dao.ConfigurationDao;
import net.ontrack.backend.db.StartupService;
import net.ontrack.extension.api.ExtensionManager;
import net.ontrack.extension.api.configuration.ConfigurationExtension;
import net.ontrack.extension.api.configuration.ConfigurationExtensionField;
import net.ontrack.extension.api.configuration.ConfigurationExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

@Service
public class DefaultConfigurationExtensionService implements ConfigurationExtensionService, StartupService {

    private final Logger logger = LoggerFactory.getLogger(ConfigurationExtensionService.class);
    private final ExtensionManager extensionManager;
    private final ConfigurationDao configurationDao;

    @Autowired
    public DefaultConfigurationExtensionService(ExtensionManager extensionManager, ConfigurationDao configurationDao) {
        this.extensionManager = extensionManager;
        this.configurationDao = configurationDao;
    }

    @Override
    public Collection<? extends ConfigurationExtension> getConfigurationExtensions() {
        return extensionManager.getConfigurationExtensions();
    }

    @Override
    @Transactional(readOnly = true)
    public void start() {
        logger.info("Loading all configurations");
        // Gets the list of configurations
        Collection<? extends ConfigurationExtension> extensions = getConfigurationExtensions();
        for (ConfigurationExtension extension : extensions) {
            load(extension);
        }
    }

    protected void load(ConfigurationExtension extension) {
        for (ConfigurationExtensionField field : extension.getFields()) {
            // Configuration key
            String key = String.format("x-%s-%s-%s", extension.getExtension(), extension.getName(), field.getName());
            // Gets the value
            String value = configurationDao.getValue(key);
            // Sets the value
            extension.configure(field.getName(), value);
        }
    }

    @Override
    @Transactional
    public String saveExtensionConfiguration(String extension, String name, Map<String, String> parameters) {
        // Gets the configuration extension
        ConfigurationExtension configurationExtension = extensionManager.getConfigurationExtension(extension, name);
        // For all fields
        for (ConfigurationExtensionField field : configurationExtension.getFields()) {
            // Gets the new value
            String value = parameters.get(field.getName());
            // Controls the value
            field.validate(value);
            // Configuration key
            String key = String.format("x-%s-%s-%s", extension, name, field.getName());
            // Saves the value
            configurationDao.setValue(key, value);
        }
        // Updates the configuration in memory
        load(configurationExtension);
        // OK
        return configurationExtension.getTitleKey();
    }
}
