package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.item.WeaponTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CombatEventHandler {
    public static void checkForOffhandAttack() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (Minecraft.getInstance().world != null && Minecraft.getInstance().currentScreen == null && !Minecraft.getInstance().isGamePaused() && player != null && !player.isActiveItemStackBlocking()) {
            ItemStack offhand = player.getHeldItemOffhand();
            if (offhand.getItem() instanceof ECWeaponItem) {
                if (((ECWeaponItem) offhand.getItem()).getType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD) {
                    float reach = (float) 3.0D;
                    if (mc.player != null) {
                        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
                            reach = (float) mc.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
                        } else {
                            reach = (float) mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
                        }
                    }
                    if (player.isCreative()) reach += 2.0;
                    RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
                    if (rayTrace instanceof EntityRayTraceResult) {
                        EntityRayTraceResult entityRayTrace = (EntityRayTraceResult) rayTrace;
                        Entity entityHit = entityRayTrace.getEntity();
                        if (entityHit != player && entityHit != player.getRidingEntity()) {
                            NetworkHandler.sendPacketToServer(new PacketOffhandAttack(entityHit.getEntityId()));
                        }

                    }
                }
            }
        }
    }

    private static RayTraceResult getEntityMouseOverExtended(float reach) {
        RayTraceResult result = null;
        Minecraft mc = Minecraft.getInstance();
        Entity viewEntity = mc.renderViewEntity;
        if (viewEntity != null && mc.world != null) {
            double reachDistance = (double)reach;
            RayTraceResult rayTrace = viewEntity.pick(reachDistance, 0.0F, false);
            Vector3d eyePos = viewEntity.getEyePosition(0.0F);
            boolean hasExtendedReach = false;
            double attackReach = reachDistance;
            if (mc.playerController != null) {
                if (mc.playerController.extendedReach() && reachDistance < 6.0D) {
                    attackReach = 6.0D;
                    reachDistance = attackReach;
                } else if (reachDistance > (double)reach) {
                    hasExtendedReach = true;
                }
            }

            attackReach = rayTrace.getHitVec().squareDistanceTo(eyePos);

            Vector3d lookVec = viewEntity.getLook(1.0F);
            Vector3d attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            AxisAlignedBB axisAlignedBB = viewEntity.getBoundingBox().expand(lookVec.scale(reachDistance)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityRayTrace = ProjectileHelper.rayTraceEntities(viewEntity, eyePos, attackVec, axisAlignedBB, (entity) -> !entity.isSpectator() && entity.canBeCollidedWith(), attackReach);
            if (entityRayTrace != null) {
                Vector3d hitVec = entityRayTrace.getHitVec();
                double squareDistanceTo = eyePos.squareDistanceTo(hitVec);
                if (hasExtendedReach && squareDistanceTo > (double)(reach * reach)) {
                    result = BlockRayTraceResult.createMiss(hitVec, Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z), new BlockPos(hitVec));
                } else if (squareDistanceTo < attackReach) {
                    result = entityRayTrace;
                }
            } else {
                result = BlockRayTraceResult.createMiss(attackVec, Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z), new BlockPos(attackVec));
            }
        }

        return (RayTraceResult)result;
    }
}
