CREATE TABLE "LINKS_TO_CHATS"
(
    "CHAT_ID" bigint not null,
    "LINK_ID" bigint not null,
    primary key ("CHAT_ID", "LINK_ID"),
    foreign key ("CHAT_ID") references "CHATS" ("CHAT_ID"),
    foreign key ("LINK_ID") references "LINKS" ("LINK_ID")
);
