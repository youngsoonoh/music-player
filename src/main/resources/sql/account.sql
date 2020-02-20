insert into music_authority (name) values('ROLE_USER')
insert into music_authority (name) values('ROLE_ADMIN')

insert into music_user(id,password_hash,login,created_by,last_modified_by,created_date) values (1,'$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','user','admin','admin',now())


insert into music_user_authority(user_id,authority_name) values(1, 'ROLE_USER')
