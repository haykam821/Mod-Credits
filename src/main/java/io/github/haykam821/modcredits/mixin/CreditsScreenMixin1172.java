package io.github.haykam821.modcredits.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.haykam821.modcredits.ModCreditsUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Mixin(CreditsScreen.class)
public abstract class CreditsScreenMixin1172 {
	@Unique
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

	@Shadow
	private static Text SEPARATOR_LINE;

	@Shadow
	private static String CENTERED_LINE_PREFIX;

	@Shadow
	protected abstract void addText(Text text, boolean centered);

	@Shadow
	protected abstract void addEmptyLine();

	@Inject(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
	private void addModCredits(CallbackInfo ci) {
		// Header
		this.addText(SEPARATOR_LINE, true);
		this.addText(new LiteralText("Mod Developers").formatted(Formatting.YELLOW), true);
		this.addText(SEPARATOR_LINE, true);
		this.addEmptyLine();
		this.addEmptyLine();

		// Mods
		for (Text line : ModCreditsUtil.getModCredits()) {
			if (line == LiteralText.EMPTY) {
				this.addEmptyLine();
			} else {
				this.addText(line, false);
			}
		}
	}
}