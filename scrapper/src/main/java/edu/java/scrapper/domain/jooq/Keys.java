/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.jooq.tables.Chats;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.LinksToChats;
import edu.java.scrapper.domain.jooq.tables.records.ChatsRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinksToChatsRecord;
import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatsRecord> CONSTRAINT_3 =
        Internal.createUniqueKey(Chats.CHATS, DSL.name("CONSTRAINT_3"), new TableField[] {Chats.CHATS.CHAT_ID}, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_4 =
        Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_4"), new TableField[] {Links.LINKS.LINK_ID}, true);
    public static final UniqueKey<LinksToChatsRecord> CONSTRAINT_5 =
        Internal.createUniqueKey(
            LinksToChats.LINKS_TO_CHATS,
            DSL.name("CONSTRAINT_5"),
            new TableField[] {LinksToChats.LINKS_TO_CHATS.CHAT_ID, LinksToChats.LINKS_TO_CHATS.LINK_ID},
            true
        );

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<LinksToChatsRecord, ChatsRecord> CONSTRAINT_50 = Internal.createForeignKey(
        LinksToChats.LINKS_TO_CHATS,
        DSL.name("CONSTRAINT_50"),
        new TableField[] {LinksToChats.LINKS_TO_CHATS.CHAT_ID},
        Keys.CONSTRAINT_3,
        new TableField[] {Chats.CHATS.CHAT_ID},
        true
    );
    public static final ForeignKey<LinksToChatsRecord, LinksRecord> CONSTRAINT_506 = Internal.createForeignKey(
        LinksToChats.LINKS_TO_CHATS,
        DSL.name("CONSTRAINT_506"),
        new TableField[] {LinksToChats.LINKS_TO_CHATS.LINK_ID},
        Keys.CONSTRAINT_4,
        new TableField[] {Links.LINKS.LINK_ID},
        true
    );
}
