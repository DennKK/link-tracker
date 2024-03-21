/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables.records;

import edu.java.scrapper.domain.jooq.tables.Chats;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ChatsRecord extends UpdatableRecordImpl<ChatsRecord> implements Record2<Integer, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>CHATS.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>CHATS.CHAT_ID</code>.
     */
    @NotNull
    public Integer getChatId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>CHATS.REGISTERED_AT</code>.
     */
    public void setRegisteredAt(@NotNull OffsetDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>CHATS.REGISTERED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getRegisteredAt() {
        return (OffsetDateTime) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Integer, OffsetDateTime> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Integer, OffsetDateTime> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Integer> field1() {
        return Chats.CHATS.CHAT_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field2() {
        return Chats.CHATS.REGISTERED_AT;
    }

    @Override
    @NotNull
    public Integer component1() {
        return getChatId();
    }

    @Override
    @NotNull
    public OffsetDateTime component2() {
        return getRegisteredAt();
    }

    @Override
    @NotNull
    public Integer value1() {
        return getChatId();
    }

    @Override
    @NotNull
    public OffsetDateTime value2() {
        return getRegisteredAt();
    }

    @Override
    @NotNull
    public ChatsRecord value1(@NotNull Integer value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public ChatsRecord value2(@NotNull OffsetDateTime value) {
        setRegisteredAt(value);
        return this;
    }

    @Override
    @NotNull
    public ChatsRecord values(@NotNull Integer value1, @NotNull OffsetDateTime value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatsRecord
     */
    public ChatsRecord() {
        super(Chats.CHATS);
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    @ConstructorProperties({"chatId", "registeredAt"})
    public ChatsRecord(@NotNull Integer chatId, @NotNull OffsetDateTime registeredAt) {
        super(Chats.CHATS);

        setChatId(chatId);
        setRegisteredAt(registeredAt);
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    public ChatsRecord(edu.java.scrapper.domain.jooq.tables.pojos.Chats value) {
        super(Chats.CHATS);

        if (value != null) {
            setChatId(value.getChatId());
            setRegisteredAt(value.getRegisteredAt());
        }
    }
}