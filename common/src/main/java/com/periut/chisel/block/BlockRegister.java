package com.periut.chisel.block;

import com.periut.chisel.block.blocks.VanillaChisel;

public class BlockRegister
{
    public static void Register()
    {
        VanillaChisel.Register();
        GeneratedRegister.Register();
    }
}
