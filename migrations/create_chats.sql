CREATE TABLE chats
(
    chat_id       bigserial PRIMARY KEY,
    tg_chat_id bigint,
    registered_at timestamp with time zone not null
);
