drop table if exists food_items; 
create table food_items (
    id integer primary key,
    product_id text not null,
    product_status text not null, 
    keywords text not null,
    main_ingredient text not null,
    processing text not null,
    is_vegan boolean not null,
    created_at timestamp default current_timestamp
);
insert into food_items (
    product_id,
    product_status, 
    keywords,
    main_ingredient,
    processing,
    is_vegan
    )
    values(
    '1234567890',
    'found',
    "['bean', 'refried']", 
    "stuff", 
    "overprocessed", 
    false
);