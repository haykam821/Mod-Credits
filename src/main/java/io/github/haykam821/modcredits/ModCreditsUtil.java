package io.github.haykam821.modcredits;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModCreditsUtil {
	public static List<Text> getModCredits() {
		List<Text> credits = new ArrayList<>();

		for (ModContainer mod : ModComparator.getSortedMods()) {
			ModMetadata metadata = mod.getMetadata();
			credits.add(new LiteralText(metadata.getName()).formatted(Formatting.GRAY));

			for (Person author : metadata.getAuthors()) {
				credits.add(new LiteralText("          " + author.getName()));
			}
			for (Person contributor : metadata.getContributors()) {
				credits.add(new LiteralText("          " + contributor.getName()).formatted(Formatting.ITALIC));
			}

			credits.add(new LiteralText(" "));
		}

		return credits;
	}
}