package net.dark_roleplay.marg.util;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.dark_roleplay.marg.Marg;

import java.util.Optional;

public class CodecUtil {

	public static <T> T decodeCodec(Codec<T> codec, JsonElement json){
		DataResult<Pair<T, JsonElement>> result = codec.decode(JsonOps.INSTANCE, json);
		Optional<T> optional = result.resultOrPartial(Marg.LOGGER::error).flatMap(pair -> Optional.of(pair.getFirst()));
		return optional.isPresent() ? optional.get() : null;
	}
}
