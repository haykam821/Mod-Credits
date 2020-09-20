package io.github.haykam821.modcredits.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.haykam821.modcredits.ModCreditsUtil;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Mixin(CreditsScreen.class)
public class CreditsScreenMixin {
	@Unique
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

	@Shadow
	private List<OrderedText> credits;

	@Shadow
	private IntSet field_24261;

	@Inject(method = "init", at = @At(value = "INVOKE", target = "Ljava/io/InputStream;close()V"))
	private void addModCredits(CallbackInfo ci) {
		for (int index = 0; index < 5; index++) {
			this.credits.add(new LiteralText("").asOrderedText());
		}

		// Header
		this.credits.add(new LiteralText("============").asOrderedText());
		this.credits.add(new LiteralText("Mod Developers").formatted(Formatting.YELLOW).asOrderedText());
		this.credits.add(new LiteralText("============").asOrderedText());

		this.field_24261.add(this.credits.size() - 1);
		this.field_24261.add(this.credits.size() - 2);
		this.field_24261.add(this.credits.size() - 3);

		this.credits.add(new LiteralText("").asOrderedText());

		// Mods
		for (Text line : ModCreditsUtil.getModCredits()) {
			this.credits.addAll(CLIENT.textRenderer.wrapLines(line, 274));
		}

		this.credits.add(OrderedText.EMPTY);
	}
}