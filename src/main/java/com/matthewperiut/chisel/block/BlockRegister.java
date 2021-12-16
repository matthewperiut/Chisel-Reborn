package com.matthewperiut.chisel.block;

import com.matthewperiut.chisel.block.blocks.VanillaChisel;
import com.matthewperiut.chisel.block.blocks.pillar.PillarChisel;

public class BlockRegister
{
    public static void Register()
    {
        VanillaChisel.Register();
        PillarChisel.Register();
    }
}
