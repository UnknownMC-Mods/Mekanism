package mekanism.client.gui.element;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Rectangle;
import java.util.List;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.render.MekanismRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiElement {
    //TODO: Make this extend Widget?

    public static final Minecraft minecraft = Minecraft.getInstance();

    protected final ResourceLocation RESOURCE;
    protected final IGuiWrapper guiObj;
    protected final ResourceLocation defaultLocation;

    public GuiElement(ResourceLocation resource, IGuiWrapper gui, ResourceLocation def) {
        RESOURCE = resource;
        guiObj = gui;
        defaultLocation = def;
    }

    public void displayTooltip(ITextComponent component, int xAxis, int yAxis) {
        guiObj.displayTooltip(component, xAxis, yAxis);
    }

    public void displayTooltips(List<ITextComponent> list, int xAxis, int yAxis) {
        guiObj.displayTooltips(list, xAxis, yAxis);
    }

    public void offsetX(int xSize) {
        if (guiObj instanceof ContainerScreen) {
            ((ContainerScreen) guiObj).xSize += xSize;
        }
    }

    public void offsetY(int ySize) {
        if (guiObj instanceof ContainerScreen) {
            ((ContainerScreen) guiObj).ySize += ySize;
        }
    }

    public void offsetLeft(int guiLeft) {
        if (guiObj instanceof ContainerScreen) {
            ((ContainerScreen) guiObj).guiLeft += guiLeft;
        }
    }

    public void offsetTop(int guiTop) {
        if (guiObj instanceof ContainerScreen) {
            ((ContainerScreen) guiObj).guiTop += guiTop;
        }
    }

    public int drawString(ITextComponent component, int x, int y, int color) {
        //TODO: Check if color actually does anything
        return drawString(component.getFormattedText(), x, y, color);
    }

    public void renderScaledText(ITextComponent component, int x, int y, int color, int maxX) {
        renderScaledText(component.getFormattedText(), x, y, color, maxX);
    }

    public int drawString(String text, int x, int y, int color) {
        return getFontRenderer().drawString(text, x, y, color);
    }

    public void renderScaledText(String text, int x, int y, int color, int maxX) {
        int length = getFontRenderer().getStringWidth(text);

        if (length <= maxX) {
            drawString(text, x, y, color);
        } else {
            float scale = (float) maxX / length;
            float reverse = 1 / scale;
            float yAdd = 4 - (scale * 8) / 2F;

            GlStateManager.pushMatrix();
            GlStateManager.scalef(scale, scale, scale);
            drawString(text, (int) (x * reverse), (int) ((y * reverse) + yAdd), color);
            GlStateManager.popMatrix();
        }
        //Make sure the color does not leak from having drawn the string
        MekanismRenderer.resetColor();
    }

    public FontRenderer getFontRenderer() {
        return guiObj.getFont();
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseXOld, double mouseYOld) {
        return true;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int type) {
        return true;
    }

    public void mouseWheel(int x, int y, int delta) {
    }

    public abstract Rectangle4i getBounds(int guiWidth, int guiHeight);

    public abstract void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight);

    public abstract void renderForeground(int xAxis, int yAxis);

    public void preMouseClicked(double xAxis, double yAxis, int button) {
    }

    public boolean mouseClicked(double xAxis, double yAxis, int button) {
        return false;
    }

    public interface IInfoHandler {

        List<ITextComponent> getInfo();
    }

    public static class Rectangle4i {

        public final int x;
        public final int y;
        public final int width;
        public final int height;

        public Rectangle4i(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public Rectangle toRectangle() {
            return new Rectangle(x, y, width, height);
        }
    }

    protected boolean inBounds(double xAxis, double yAxis) {
        return false;
    }
}