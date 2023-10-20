create table chats (
    id  bigserial,
    recipient_id bigint,
    sender_id bigint,
    primary key (id)
);

create table chats_messages (
    chat_id bigint not null,
    messages_id bigint not null UNIQUE
);

create table messages (
    id  bigserial,
    sending_time timestamp,
    text varchar(255),
    chat_id bigint,
    sender_id bigint,
    primary key (id)
);

alter table if exists
    chats add constraint fk_chats_user_recipient
    foreign key (recipient_id) references usr;

alter table if exists
    chats add constraint fk_chats_user_sender
    foreign key (sender_id) references usr;

alter table if exists
    chats_messages add constraint fk_chats_messages_messages
    foreign key (messages_id) references messages;

alter table if exists
    chats_messages add constraint fk_chats_messages_chats
    foreign key (chat_id) references chats;

alter table if exists
    messages add constraint fk_messages_chats
    foreign key (chat_id) references chats;

alter table if exists
    messages add constraint fk_messages_users
    foreign key (sender_id) references usr;