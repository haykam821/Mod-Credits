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
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@SuppressWarnings("target")
@Mixin(CreditsScreen.class)
public abstract class CreditsScreenMixin117 {
	@Unique
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

	@Shadow(remap = false)
	private static StringVisitable field_33955;

	@Shadow
	private static String CENTERED_LINE_PREFIX;

	@Shadow(remap = false)
	protected abstract void method_37304(StringVisitable text, boolean centered);

	@Shadow
	protected abstract void addEmptyLine();

	@Inject(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
	private void addModCredits(CallbackInfo ci) {
		// Header
		this.method_37304(field_33955, true);
		this.addEmptyLine();
		this.method_37304(new LiteralText("Mod Developers").formatted(Formatting.YELLOW), true);
		this.addEmptyLine();
		this.method_37304(field_33955, true);
		this.addEmptyLine();
		this.addEmptyLine();
		this.addEmptyLine();

		// Mods
		for (Text line : ModCreditsUtil.getModCredits()) {
			if (line == LiteralText.EMPTY) {
				this.addEmptyLine();
			} else {
				this.method_37304(line, false);
			}
		}
	}
}