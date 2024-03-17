create TABLE links (
    link_id serial PRIMARY KEY,
    url varchar(255) not null,
    updated_at timestamp not null
);
