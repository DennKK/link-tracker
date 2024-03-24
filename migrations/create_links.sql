create TABLE links (
    link_id bigserial PRIMARY KEY,
    url varchar(255) not null,
    updated_at timestamp with time zone not null
);
