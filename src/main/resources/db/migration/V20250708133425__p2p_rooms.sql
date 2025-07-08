alter table p2p_rooms drop constraint p2p_rooms_entry_id_fkey;

ALTER TABLE p2p_rooms
    ADD CONSTRAINT p2p_rooms_entry_id_fkey
        FOREIGN KEY (entry_id) REFERENCES chat_rooms(id);
