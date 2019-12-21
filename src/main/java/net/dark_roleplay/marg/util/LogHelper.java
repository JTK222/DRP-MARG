package net.dark_roleplay.marg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

import net.dark_roleplay.marg.Marg;

public class LogHelper {
	private static final Logger logger = Logger.getLogger(Marg.MODID);
	private static final SimpleDateFormat	dateformat	= new SimpleDateFormat("HH:mm:ss");
	private static Handler					handler;

	public static void info(Object msg) {
		logger.log(Level.INFO, msg.toString());
		handler.flush();
	}

	public static void info(Object msg, Object... args){
		logger.log(Level.INFO, String.format(msg.toString(), args));
		handler.flush();
	}

	public static void error(Object msg) {
		logger.log(Level.WARNING, msg.toString());
		handler.flush();
	}

	public static void error(Object msg, Exception e) {
		logger.log(Level.SEVERE, msg.toString());
		logger.log(Level.SEVERE, msg.toString(), e);
		handler.flush();
	}

	public static void except(Exception e) {
		logger.log(Level.WARNING, e.getMessage(), e);
		handler.flush();
	}

	static {
		try {
			// PORT
			File	e		= Marg.FOLDER_LOGS;

			File	file	= new File(e, "marg_o-latest.log");
			File	lock	= new File(e, "marg_o-latest.log.lck");
			File	file1	= new File(e, "marg_o-1.log");
			File	file2	= new File(e, "marg_o-2.log");
			File	file3	= new File(e, "marg_o-3.log");
			if(lock.exists()) {
				lock.delete();
			}

			if(file3.exists()) {
				file3.delete();
			}

			if(file2.exists()) {
				file2.renameTo(file3);
			}

			if(file1.exists()) {
				file1.renameTo(file2);
			}

			if(file.exists()) {
				file.renameTo(file1);
			}

			handler = new StreamHandler(new FileOutputStream(file), new Formatter() {

				public String format(LogRecord record) {
					StackTraceElement	element	= Thread.currentThread().getStackTrace()[8];
					String				line	= "[" + element.getClassName() + ":" + element.getLineNumber() + "] ";
					String				time	= "[" + dateformat.format(new Date(record.getMillis())) + "][" + record.getLevel() + "]" + line;
					if(record.getThrown() != null) {
						StringWriter	sw	= new StringWriter();
						PrintWriter		pw	= new PrintWriter(sw);
						record.getThrown().printStackTrace(pw);
						return time + sw.toString();
					} else {
						return time + record.getMessage() + "\n";
					}
				}
			});
			handler.setLevel(Level.ALL);
			logger.addHandler(handler);
			logger.setUseParentHandlers(false);
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(handler.getFormatter());
			consoleHandler.setLevel(Level.WARNING);
			logger.addHandler(consoleHandler);
			logger.setLevel(Level.ALL);
			info( (new Date()).toString());
		} catch(SecurityException var7) {
			var7.printStackTrace();
		} catch(IOException var8) {
			var8.printStackTrace();
		}

	}
}