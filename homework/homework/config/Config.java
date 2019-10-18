package homework.homework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
	private static final String configFileLocation = "/homework/homework/config/config.properties";

	protected static final Logger log = Logger.getLogger("Config");
	private Properties props;
	private static Config config;

	private void init() {
		props = new Properties();
		InputStream in = Config.class.getResourceAsStream(configFileLocation);
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Failed to read config file from homework.homework.config.config.properties");
		}
	}

	private Config() {
	}

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
			config.init();
		}
		return config;
	}

	public String getProperty(String property) {
		return props.getProperty(property);
	}
}
