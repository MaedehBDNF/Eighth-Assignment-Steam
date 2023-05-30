PGDMP      %        
            {            steam    15.2    15.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16423    steam    DATABASE     �   CREATE DATABASE steam WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE steam;
                postgres    false            �            1259    16464 	   downloads    TABLE     �   CREATE TABLE public.downloads (
    account_id character varying,
    game_id character varying,
    download_count integer NOT NULL
);
    DROP TABLE public.downloads;
       public         heap    postgres    false            �            1259    16457    games    TABLE     �  CREATE TABLE public.games (
    id character varying NOT NULL,
    title character varying NOT NULL,
    developer character varying NOT NULL,
    genre character varying NOT NULL,
    price double precision NOT NULL,
    release_year integer NOT NULL,
    controller_support boolean NOT NULL,
    reviews integer NOT NULL,
    size integer NOT NULL,
    file_path character varying NOT NULL
);
    DROP TABLE public.games;
       public         heap    postgres    false            �            1259    16471    users    TABLE     �   CREATE TABLE public.users (
    id character varying NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    date_of_birth date
);
    DROP TABLE public.users;
       public         heap    postgres    false                      0    16464 	   downloads 
   TABLE DATA           H   COPY public.downloads (account_id, game_id, download_count) FROM stdin;
    public          postgres    false    215   �                  0    16457    games 
   TABLE DATA              COPY public.games (id, title, developer, genre, price, release_year, controller_support, reviews, size, file_path) FROM stdin;
    public          postgres    false    214                    0    16471    users 
   TABLE DATA           F   COPY public.users (id, username, password, date_of_birth) FROM stdin;
    public          postgres    false    216   �       m           2606    16463    games games_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.games DROP CONSTRAINT games_pkey;
       public            postgres    false    214            o           2606    16477    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    216            q           2606    16479    users users_username_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);
 B   ALTER TABLE ONLY public.users DROP CONSTRAINT users_username_key;
       public            postgres    false    216                  x^����� � �          i  x^͕�o�0���_���C*p��nm���)��4���+q6�m���Ⱥj��C���>��2��� �I�� ���œ�Kz�)wI6[���W8:����u˶'�*²�,#,c�H�x���AI�sh�:0�㔦��%�.{�
(���M�Kq�{SH�����R(���NVwX~�^�Dù5獐��W��%Ӄ�|Vw�����U�b�Va�[��k;u$aH����u�Ԗ����+%/$�f�5\q��n��Ҟ�X�Ǫ<�7��w<���,�~q�V�-:��Z�I$��ɔ���́8J�tJ��.1�yat][�H/���Ȇ�-R�tWJ'�����p���w��OX����7�#�P�0H�$	�7�T'��M�*	��8�5C�u�5;{'��,��$
Ƚ6�'��M��Mэn��}%w#�Z����e���3@��N u����H:Q��0�Y�Q��M��۽.��	3D��M�Û��^3�'�,��'�W���^���[��j#�B7$�L/{�Ƙ�9�Pz��k���ɢ8���.���M?M���M`D?�ӳ��y��k~p#� ǂ8Hp�|v0��w��Vo�5;�	i��W����`%	           x^=��N�@��5<G��ܙ�M��-%���0Øi�}z�F���'�T�V�x�q
�pb�^Rb�P<(�Չ#jFG�-oUg:���T����v�%+�ETo'�|��L����~���.(RB�BꜲ�#�-=pj����$aF�����š����/�n��UF�:\�f:��}OH����?0~kM@t� �t��
k��*�xYQ(! -�2Ŵ�%��ǯ�Ԥˣ_S=˔o�/�nm��p=�3��dew���w/4��~��a���-
����c�     