package net.dark_roleplay.marg.util;

public class CastHelper {

	public static <T extends Object> T cast(Class<T> type, Object obj){
		return type.cast(obj);
	}
}
