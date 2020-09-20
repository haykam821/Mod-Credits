package io.github.haykam821.modcredits;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class ModComparator implements Comparator<ModContainer> {
	private static final Set<String> HIDDEN_MOD_IDS = ImmutableSet.of("minecraft");

	public int compare(ModContainer modA, ModContainer modB) {
		return modA.getMetadata().getName().compareTo(modB.getMetadata().getName());
	}

	public static List<ModContainer> getSortedMods() {
		return FabricLoader.getInstance().getAllMods().stream()
			.filter(mod -> {
				return !HIDDEN_MOD_IDS.contains(mod.getMetadata().getId());
			})
			.sorted(new ModComparator())
			.collect(Collectors.toList());
	}
}