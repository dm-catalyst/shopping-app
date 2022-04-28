package pl.catalyst.utils.properties;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.catalyst.utils.properties.exception.ConfigPropertiesException;

@Component
@PropertySource("classpath:application.properties")
@AllArgsConstructor
public class ConfigProperties {

    private final Environment env;

    public String getConfigValue(String configKey) {
        if (configKey == null) {
            throw new IllegalArgumentException("ConfigKey cannot be null");
        }
        String property = env.getProperty(configKey);
        assert property != null;
        if (property.equalsIgnoreCase("null")) {
            throw new ConfigPropertiesException("Cannot find property from key: " + configKey);
        }

        return property;
    }


}
