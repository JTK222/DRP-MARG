package net.dark_roleplay.marg.util;

import com.mojang.serialization.DataResult;

public class EnumDecoder<T extends Enum<T>> {
	Class<T> type;

	public EnumDecoder(Class<T> type){
		this.type = type;
	}

	public DataResult<T> decode(String val){
		try{
			return DataResult.success(T.valueOf(type, val));
		}catch(Exception e){
			return DataResult.error("Invalid Value");
		}
	}
}
