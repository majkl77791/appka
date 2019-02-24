CREATE TABLE public.user_account
(
  id serial PRIMARY KEY,
  facebook_id VARCHAR (50),
  name VARCHAR (50)
);

CREATE TABLE public.user_photo
(
  id serial PRIMARY KEY,
  facebook_URL VARCHAR (255),
  image_URL VARCHAR (255),
  album_name VARCHAR (255),

  user_account_id integer,
  constraint fk_user_account
  foreign key (user_account_id)
  REFERENCES public.user_account (id)
);



