package mekanism.api.infuse;

import mekanism.api.text.IHasTranslationKey;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

/**
 * The types of infuse currently available in Mekanism.
 *
 * @author AidanBrady
 */
public final class InfuseType implements IHasTranslationKey {

    /**
     * The name of this infusion.
     */
    public String name;

    /**
     * This infuse GUI's icon
     */
    public ResourceLocation iconResource;

    /**
     * The texture representing this infuse type.
     */
    public TextureAtlasSprite sprite;

    /**
     * The unlocalized name of this type.
     */
    public String unlocalizedName;

    public InfuseType(String s, ResourceLocation res) {
        name = s;
        iconResource = res;
    }

    public void setIcon(TextureAtlasSprite tex) {
        sprite = tex;
    }

    public InfuseType setTranslationKey(String name) {
        unlocalizedName = "infuse." + name;
        return this;
    }

    @Override
    public String getTranslationKey() {
        return unlocalizedName;
    }
}