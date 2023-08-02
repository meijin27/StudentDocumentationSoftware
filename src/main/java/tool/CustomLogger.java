package tool;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CustomLogger {
	private static Logger logger;

	public static synchronized Logger getLogger(Class<?> className) {
		if (logger == null) {
			logger = Logger.getLogger(className.getName());
			try {
				String userHome = System.getProperty("user.home");
				String appFolderPath = userHome + "/StudentDocumentationSoftwareLog";
				File appFolder = new File(appFolderPath);
				if (!appFolder.exists()) {
					appFolder.mkdir();
				}
				Handler fileHandler = new FileHandler(appFolderPath + "/logfile.log", true);

				// Create a custom Formatter
				Formatter formatter = new Formatter() {
					private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					@Override
					public String format(LogRecord record) {
						return String.format("%s [%s] %s: %s%n",
								sdf.format(new Date(record.getMillis())),
								record.getLevel().getName(),
								record.getSourceClassName(),
								record.getMessage());
					}
				};

				fileHandler.setFormatter(formatter);
				logger.addHandler(fileHandler);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Failed to set up logger.", e);
			}
		}
		return logger;
	}
}
