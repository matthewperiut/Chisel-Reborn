package com.knowyourknot.chiseldecor.tags;

import java.io.OutputStreamWriter;

import net.devtech.arrp.impl.RuntimeResourcePackImpl;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.util.UnsafeByteArrayOutputStream;
import net.minecraft.util.Identifier;

public class ChiselRuntimeResourcePackImpl extends RuntimeResourcePackImpl {

	public ChiselRuntimeResourcePackImpl(Identifier id) {
		super(id);
	}

    public byte[] addTag(Identifier id, ChiselTag tag) {
		return this.addData(fix(id, "tags", "json"), serialize(tag));
	}

    private static byte[] serialize(Object object) {
		UnsafeByteArrayOutputStream ubaos = new UnsafeByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(ubaos);
		GSON.toJson(object, writer);
		try {
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ubaos.getBytes();
	}

    private static Identifier fix(Identifier identifier, String prefix, String append) {
		return new Identifier(identifier.getNamespace(), prefix + '/' + identifier.getPath() + '.' + append);
	}

	@Override
	public byte[] addLootTable(Identifier identifier, JLootTable table) {
		return this.addData(fix(identifier, "loot_tables", "json"), serialize(table));
	}
    
}
