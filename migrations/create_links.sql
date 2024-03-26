CREATE TABLE "LINKS"
(
    "LINK_ID"    bigserial PRIMARY KEY,
    "URL"        varchar(255)             not null,
    "UPDATED_AT" timestamp with time zone not null
);
