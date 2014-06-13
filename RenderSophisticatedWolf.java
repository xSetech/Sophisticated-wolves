package sophisticated_wolves;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import sophisticated_wolves.entity.SophisticatedWolf;

/**
 * Sophisticated Wolves
 *
 * @author metroidfood
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RenderSophisticatedWolf extends RenderWolf {

    public RenderSophisticatedWolf(ModelBase modelBase, ModelBase modelBase2) {
        super(modelBase, modelBase2, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        SophisticatedWolf wolf = (SophisticatedWolf) entity;
        if (SWConfiguration.customWolfTextures) {
            if (wolf.getSpecies() == 4) {
                if (wolf.isTamed())
                    return Resources.brownWolfTame;
                if (wolf.isAngry())
                    return Resources.brownWolfAngry;
                else
                    return Resources.brownWolf;
            }
            if (wolf.getSpecies() == 3) {
                if (wolf.isTamed())
                    return Resources.blackWolfTame;
                if (wolf.isAngry())
                    return Resources.blackWolfAngry;
                else
                    return Resources.blackWolf;
            }
            if (wolf.getSpecies() == 2) {
                if (wolf.isTamed())
                    return Resources.forestWolfTame;
                if (wolf.isAngry())
                    return Resources.forestWolfAngry;
                else
                    return Resources.forestWolf;
            } else {
                return super.getEntityTexture(wolf);
            }
        } else {
            return super.getEntityTexture(wolf);
        }
    }

    //Custom Functions below here
    //Function called by RenderLiving for special renders, used to call nametag function
    @Override
    protected void passSpecialRender(EntityLivingBase entity, double par2, double par3, double par4) {
        if (entity instanceof EntityWolf) {
            SophisticatedWolf wolf = (SophisticatedWolf) entity;

            if (wolf.isTamed() && Minecraft.isGuiEnabled() && StringUtils.isNotBlank(wolf.getCustomNameTag())) {
                if (wolf.getCustomNameTag().length() > 0) {
                    this.renderWolfName(wolf, par2, par3, par4);
                }
            }
        }
    }

    //renders wolf nametag
    public void renderWolfName(SophisticatedWolf wolf, double d, double d1, double d2) {
        float f = 1.6F;
        float f1 = 0.01666667F * f;
        float f2 = wolf.getDistanceToEntity(this.renderManager.livingPlayer);
        float f3 = wolf.isSitting() ? 32F : 64F;
        if (f2 < f3) {
            String wolfName = wolf.getCustomNameTag();
            FontRenderer fontRenderer = this.getFontRendererFromRenderManager();
            GL11.glPushMatrix();
            GL11.glTranslatef((float) d + 0.0F, (float) d1 + 1.5F, (float) d2);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            tessellator.startDrawingQuads();
            int var16 = fontRenderer.getStringWidth(wolfName) / 2;
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((double) (-var16 - 1), -1.0D, 0.0D);
            tessellator.addVertex((double) (-var16 - 1), 8.0D, 0.0D);
            tessellator.addVertex((double) (var16 + 1), 8.0D, 0.0D);
            tessellator.addVertex((double) (var16 + 1), -1.0D, 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            if (!wolf.isSitting()) {
                fontRenderer.drawString(wolfName, -fontRenderer.getStringWidth(wolfName) / 2, 0, getSitNameColor(wolf));
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                fontRenderer.drawString(wolfName, -fontRenderer.getStringWidth(wolfName) / 2, 0, getWolfNameColor(wolf));
            } else {
                GL11.glDepthMask(true);
                fontRenderer.drawString(wolfName, -fontRenderer.getStringWidth(wolfName) / 2, 0, getSitNameColor(wolf));
            }
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    //changes color of wolf's name based on health
    public int getWolfNameColor(EntityWolf wolf) {
        if (wolf.getHealth() < 20.0F) {
            if (wolf.getHealth() < 16.0F) {
                if (wolf.getHealth() < 11.0F) {
                    if (wolf.getHealth() < 6.0F) {
                        return 0xffff0202;
                    }
                    return 0xfffe5656;
                }
                return 0xffff9696;
            }
            return 0xfffecccc;
        } else {
            return 0xffffffff;
        }
    }

    //same function but with transparency
    public int getSitNameColor(EntityWolf wolf) {
        if (wolf.getHealth() < 20.0F) {
            if (wolf.getHealth() < 16.0F) {
                if (wolf.getHealth() < 11.0F) {
                    if (wolf.getHealth() < 6.0F) {
                        return 0x40ff0202;
                    }
                    return 0x40fe5656;
                }
                return 0x40ff9696;
            }
            return 0x40fecccc;
        } else {
            return 0x40ffffff;
        }
    }
}
