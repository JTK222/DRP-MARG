package net.dark_roleplay.marg.util;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

public final class DistConsumer {

	private DistConsumer(){}

	public interface SafeConsumer<T, R> extends DistExecutor.SafeReferent, Function<T, R>, Serializable {}

	public static <T, R> R safeConsumeForDist(T value, Supplier<SafeConsumer<T, R>> clientTarget, Supplier<SafeConsumer<T, R>> serverTarget) {
		//Internal use only, I'll pass on validation
		//validateSafeReferent(clientTarget);
		//validateSafeReferent(serverTarget);
		switch (FMLEnvironment.dist)
		{
			case CLIENT:
				return clientTarget.get().apply(value);
			case DEDICATED_SERVER:
				return serverTarget.get().apply(value);
			default:
				throw new IllegalArgumentException("UNSIDED?");
		}
	}

}
