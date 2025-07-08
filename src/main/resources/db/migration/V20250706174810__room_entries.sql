
create table room_entries(
                             id bigserial primary key,
                             user_id bigint references users(id),
                             room_id bigint references chat_rooms(id)
);