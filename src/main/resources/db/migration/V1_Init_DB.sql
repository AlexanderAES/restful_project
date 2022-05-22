create table category (
    id bigint not null,
    category_name varchar(255) not null,
    primary key (id));

create table hibernate_sequence (next_val bigint);

insert into hibernate_sequence values ( 1 );

create table images (
    id bigint not null,
    bytes LONGBLOB,
    content_type varchar(255),
    is_preview_image bit,
    name varchar(255),
    original_file_name varchar(255),
    size bigint,
    product_id bigint,
    primary key (id));

create table product (
    id bigint not null,
    city varchar(255),
    create_date datetime(6),
    description text,
    preview_image_id bigint,
    price integer, title varchar(255),
    category_id bigint,
    user_id bigint,
    primary key (id));

create table user_role (
    user_id bigint not null,
    roles integer);

create table users (
    id bigint not null auto_increment,
    activation_code varchar(255),
    active bit,
    create_date datetime(6),
    email varchar(255),
    name varchar(255) not null,
    password varchar(3000),
    phone_number varchar(255),
    username varchar(255),
    image_id bigint,
    primary key (id));

alter table users add constraint UK_email unique (email);

alter table users add constraint UK_phone_number unique (username);

alter table images add constraint FK_images_product foreign key (product_id) references product (id);

alter table product add constraint FK_product_category foreign key (category_id) references category (id);

alter table product add constraint FK_product_users foreign key (user_id) references users (id);

alter table user_role add constraint FK_user_role_users foreign key (user_id) references users (id);

alter table users add constraint FK_users_images foreign key (image_id) references images (id);