create table p2p_rooms(
                          id bigserial primary key,
                          user1_id bigint references users(id),
                          user2_id bigint references users(id),
                          entry_id bigint references room_entries(id)
);