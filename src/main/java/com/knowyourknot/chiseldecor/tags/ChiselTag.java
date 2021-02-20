package com.knowyourknot.chiseldecor.tags;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Identifier;

// Custom implementation of https://github.com/Devan-Kerman/ARRP/blob/master/src/main/java/net/devtech/arrp/json/tags/JTag.java to allow for tags with the 'required' attribute set to false
public class ChiselTag {
    private Boolean replace;
	private List<Object> values = new ArrayList<>();

	/**
	 * @see #tag()
	 * @see #tag(Identifier)
	 */
	public ChiselTag() {
        //
    }

	public static ChiselTag replacingTag() {
		return tag().replace();
	}

	/**
	 * whether or not this tag should override all super tags
	 */
	public ChiselTag replace() {
		this.replace = true;
		return this;
	}

	public static ChiselTag tag() {
		return new ChiselTag();
	}

	/**
	 * add a normal item to the tag
	 */
	public ChiselTag add(Identifier identifier) {
		this.values.add(new ChiselTagEntry(identifier.toString(), false));
		return this;
	}

	/**
	 * add a tag to the tag
	 */
	public ChiselTag tag(Identifier tag) {
		this.values.add(new ChiselTagEntry('#' + tag.getNamespace() + ':' + tag.getPath(), false));
		return this;
	}

	public ChiselTag tagRequired(Identifier tag) {
		this.values.add('#' + tag.getNamespace() + ':' + tag.getPath());
		return this;
	}

    /*
	@Override
	public ChiselTag clone() {
		try {
			return (ChiselTag) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
    */

    private static class ChiselTagEntry {
		private final String id;
        private final boolean required;

        public ChiselTagEntry(String id, boolean required) {
            this.id = id;
            this.required = required;
        }
    }

}
