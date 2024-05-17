-- changeset:create_chats_table
create table if not exists chats
(
    chat_id serial PRIMARY KEY
);

-- changeset:create_links_table
create TABLE if not exists links
(
    link_id serial PRIMARY KEY,
    url     varchar(255) not null,
    updated timestamp    not null
);


-- changeset:create_links_to_chats_table
create table if not exists  links_to_chats
(
    chat_id bigint not null,
    link_id bigint not null,
    primary key (chat_id, link_id),
    foreign key (chat_id) references chats (chat_id),
    foreign key (link_id) references links (link_id)
);
