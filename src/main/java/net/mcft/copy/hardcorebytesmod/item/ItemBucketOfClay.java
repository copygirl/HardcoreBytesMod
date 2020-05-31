package net.mcft.copy.hardcorebytesmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import com.codetaylor.mc.artisanworktables.api.recipe.IToolHandler;

public class ItemBucketOfClay extends Item {

    public static final int CLAY_BLOCK_VALUE = 16;
    
    protected final String emptyItem;

    public ItemBucketOfClay(String emptyItem, int maxDamage) {
        this.emptyItem = emptyItem;

        this.setMaxStackSize(1);
        this.setMaxDamage(maxDamage - 1);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) { return true; }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return this.reduceUses(stack, 1);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World world,
                               List<String> tooltip, ITooltipFlag flag) {
        int clayUsesLeft = stack.getMaxDamage() - stack.getItemDamage() + 1;
        String s = (clayUsesLeft == 1) ? "" : "s";
        tooltip.add("Contains " + clayUsesLeft + " unit"+s+" of clay");
    }

    // TODO: Add bucket handler to allow picking up from empty bucket.
    //       Without that, the following isn't really all that useful.

    // @Override
    // public EnumActionResult onItemUse(
    //         EntityPlayer player, World world, BlockPos pos, EnumHand hand,
    //         EnumFacing sideHit, float hitX, float hitY, float hitZ) {
    //     ItemStack stack = player.getHeldItem(hand);
    //     IBlockState blockState = world.getBlockState(pos);
    //     Block block = blockState.getBlock();
    //
    //     // TODO: Allow placing down clay from the bucket?
    //     boolean hasSpace = stack.getItemDamage() >= CLAY_BLOCK_VALUE;
    //     if (hasSpace && (block == Blocks.CLAY)) {
    //         if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, sideHit, stack))
    //             return EnumActionResult.FAIL;
    //
    //         world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
    //         SoundType sound = block.getSoundType(blockState, world, pos, player);
    //         player.playSound(sound.getBreakSound(), sound.getVolume() * 0.8F, sound.getPitch() * 0.6F);
    //         return EnumActionResult.SUCCESS;
    //     } else return EnumActionResult.PASS;
    // }


    private ItemStack reduceUses(ItemStack input, int amount) {
        int newDamage = input.getItemDamage() + amount;
        if (newDamage > input.getMaxDamage()) {
            Item item = Item.getByNameOrId(this.emptyItem);
            return (item != null) ? new ItemStack(item) : ItemStack.EMPTY;
        } else {
            ItemStack result = input.copy();
            result.setItemDamage(input.getItemDamage() + amount);
            return result;
        }
    }


    public static final IToolHandler ARTISAN_TOOL_HANDLER = new IToolHandler() {

        @Override
        public boolean matches(ItemStack itemStack) {
            return (itemStack.getItem() instanceof ItemBucketOfClay);
        }

        @Override
        public boolean matches(ItemStack tool, ItemStack toolToMatch) {
            return (tool.getItem() == toolToMatch.getItem());
        }

        @Override
        public boolean canApplyUses(ItemStack stack, int uses, boolean restrictDurability) {
            int clayUsesLeft = stack.getMaxDamage() - stack.getItemDamage() + 1;
            return (clayUsesLeft >= uses);
        }

        @Override
        public ItemStack applyUses(World world, ItemStack stack, int uses, EntityPlayer player) {
            ItemBucketOfClay item = (ItemBucketOfClay)stack.getItem();
            return item.reduceUses(stack, uses);
        }

    };

}