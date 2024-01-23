package mod.linguardium.fixyungend.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.adventurez.init.ConfigInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EnderDragonFight.class, priority = 1500)
public class adventurez {
        @Shadow @Final private ServerWorld world;

        @Shadow @Nullable private BlockPos exitPortalLocation;

        @TargetHandler(
                mixin = "com.yungnickyoung.minecraft.betterendisland.mixin.EndDragonFightMixin",
                name = "betterendisland_setDragonKilled"
        )
        @ModifyExpressionValue(method = "@MixinSquared:Handler", at = @At(value="FIELD",opcode = Opcodes.GETFIELD,target="Lnet/minecraft/entity/boss/dragon/EnderDragonFight;previouslyKilled:Z"))
        private boolean checkAdventurezConfigForEggSpawn(boolean originallyKilled) {
                return originallyKilled && !(ConfigInit.CONFIG.resummoned_ender_dragon_drops_egg && world.getBlockState(exitPortalLocation.up()).getBlock() != Blocks.DRAGON_EGG);
        }

}
