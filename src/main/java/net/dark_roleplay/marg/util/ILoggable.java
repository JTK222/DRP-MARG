package net.dark_roleplay.marg.util;

import java.io.IOException;
import java.io.Writer;

public interface ILoggable {
    public void LogToStream(Writer writer, String prefix) throws IOException;
}
