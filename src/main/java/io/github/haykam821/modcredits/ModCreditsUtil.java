package io.github.haykam821.modcredits;

import java.util.ArrayList;
import java.util.List;

import io.github.haykam821.modcredits.mixin.ModCreditsMixinConfigPlugin;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModCreditsUtil {
	private static final boolean IS_117 = ModCreditsMixinConfigPlugin.is117();
	private static final String SPACE_116 = "          ";
	private static final String SPACE_117 = "           ";

	public static List<Text> getModCredits() {
		List<Text> credits = new ArrayList<>();

		for (ModContainer mod : ModComparator.getSortedMods()) {
			ModMetadata metadata = mod.getMetadata();
			credits.add(new LiteralText(metadata.getName()).formatted(Formatting.GRAY));

			for (Person author : metadata.getAuthors()) {
				addNameText(author.getName(), null, credits);
			}
			for (Person contributor : metadata.getContributors()) {
				addNameText(contributor.getName(), Formatting.ITALIC, credits);
			}

			if (IS_117) {
				credits.add(LiteralText.EMPTY);
				credits.add(LiteralText.EMPTY);
			} else {
				credits.add(new LiteralText(" "));
			}
		}

		return credits;
	}

	private static void addNameText(String name, Formatting formatting, List<Text> credits) {
		if (IS_117) {
			credits.add(LiteralText.EMPTY);
		}

		MutableText nameText = new LiteralText((IS_117 ? SPACE_117 : SPACE_116) + name);
		if (formatting != null) {
			nameText.formatted(formatting);
		}

		credits.add(nameText);
	}
}