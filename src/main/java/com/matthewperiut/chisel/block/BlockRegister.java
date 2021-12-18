package com.matthewperiut.chisel.block;

import com.matthewperiut.chisel.block.blocks.VanillaChisel;

public class BlockRegister
{
    public static void Register()
    {
        VanillaChisel.Register();
        GeneratedRegister.Register();
    }
}
