
create table messages(
                         id bigserial primary key,
                         text text,
                         sender_id bigint references users(id),
                         room_id bigint references chat_rooms(id),
                         date date
);