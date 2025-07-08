alter table messages
alter column date type timestamp
using date::timestamp;