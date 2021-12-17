package com.matthewperiut.chisel.block;

import com.matthewperiut.chisel.block.blocks.VanillaChisel;
import com.matthewperiut.chisel.block.blocks.circular.CircularChisel;
import com.matthewperiut.chisel.block.blocks.cut.CutChisel;
import com.matthewperiut.chisel.block.blocks.mosaic.MosaicChisel;
import com.matthewperiut.chisel.block.blocks.pillar.PillarChisel;
import com.matthewperiut.chisel.block.blocks.slant.SlantChisel;

public class BlockRegister
{
    public static void Register()
    {
        VanillaChisel.Register();
        PillarChisel.Register();
        SlantChisel.Register();
        MosaicChisel.Register();
        CutChisel.Register();
        CircularChisel.Register();
    }
}
