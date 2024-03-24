/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq_generated.tables;


import edu.java.scrapper.domain.jooq_generated.DefaultSchema;
import edu.java.scrapper.domain.jooq_generated.Keys;
import edu.java.scrapper.domain.jooq_generated.tables.records.LinksToChatsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LinksToChats extends TableImpl<LinksToChatsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>LINKS_TO_CHATS</code>
     */
    public static final LinksToChats LINKS_TO_CHATS = new LinksToChats();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinksToChatsRecord> getRecordType() {
        return LinksToChatsRecord.class;
    }

    /**
     * The column <code>LINKS_TO_CHATS.CHAT_ID</code>.
     */
    public final TableField<LinksToChatsRecord, Long> CHAT_ID = createField(DSL.name("CHAT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>LINKS_TO_CHATS.LINK_ID</code>.
     */
    public final TableField<LinksToChatsRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private LinksToChats(Name alias, Table<LinksToChatsRecord> aliased) {
        this(alias, aliased, null);
    }

    private LinksToChats(Name alias, Table<LinksToChatsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINKS_TO_CHATS</code> table reference
     */
    public LinksToChats(String alias) {
        this(DSL.name(alias), LINKS_TO_CHATS);
    }

    /**
     * Create an aliased <code>LINKS_TO_CHATS</code> table reference
     */
    public LinksToChats(Name alias) {
        this(alias, LINKS_TO_CHATS);
    }

    /**
     * Create a <code>LINKS_TO_CHATS</code> table reference
     */
    public LinksToChats() {
        this(DSL.name("LINKS_TO_CHATS"), null);
    }

    public <O extends Record> LinksToChats(Table<O> child, ForeignKey<O, LinksToChatsRecord> key) {
        super(child, key, LINKS_TO_CHATS);
    }

    @Override
    @NotNull
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<LinksToChatsRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_5;
    }

    @Override
    @NotNull
    public List<ForeignKey<LinksToChatsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_50, Keys.CONSTRAINT_506);
    }

    private transient Chats _chats;
    private transient Links _links;

    /**
     * Get the implicit join path to the <code>PUBLIC.CHATS</code> table.
     */
    public Chats chats() {
        if (_chats == null)
            _chats = new Chats(this, Keys.CONSTRAINT_50);

        return _chats;
    }

    /**
     * Get the implicit join path to the <code>PUBLIC.LINKS</code> table.
     */
    public Links links() {
        if (_links == null)
            _links = new Links(this, Keys.CONSTRAINT_506);

        return _links;
    }

    @Override
    @NotNull
    public LinksToChats as(String alias) {
        return new LinksToChats(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public LinksToChats as(Name alias) {
        return new LinksToChats(alias, this);
    }

    @Override
    @NotNull
    public LinksToChats as(Table<?> alias) {
        return new LinksToChats(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinksToChats rename(String name) {
        return new LinksToChats(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinksToChats rename(Name name) {
        return new LinksToChats(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinksToChats rename(Table<?> name) {
        return new LinksToChats(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
