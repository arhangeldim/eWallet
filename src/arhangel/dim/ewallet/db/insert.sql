insert OR REPLACE into users (name, password) values ("Alisa", "123");
insert OR REPLACE into users (name, password) values ("Bob", "321");

insert OR REPLACE into accounts (descr, user_name) values ("Alisa_main_acc", "Alisa");
insert OR REPLACE into accounts (descr, user_name) values ("Bob_general_acc", "Bob");

insert OR REPLACE into records (descr, amount, category_id, account_id) values ("A new car", 10000, 4,1);
insert OR REPLACE into records (descr, amount, category_id, account_id) values ("An old picture", 300,4, 1);

insert OR REPLACE into records (descr, amount, category_id, account_id) values ("A cup of tea", 20, 1, 2);
insert OR REPLACE into records (descr, amount, category_id, account_id) values ("A metro ticket", 35, 2, 2);

insert OR REPLACE into categories (name) values ("Food and Drinks"), ("Cafe"), ("Transport"), ("Health"), ("Other");