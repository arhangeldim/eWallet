insert OR REPLACE into users (name, password) values ("Alisa", "123");
insert OR REPLACE into users (name, password) values ("Bob", "321");

insert OR REPLACE into accounts (descr, user_name) values ("Alisa_main_acc", "Alisa");
insert OR REPLACE into accounts (descr, user_name) values ("Bob_general_acc", "Bob");

insert OR REPLACE into records (descr, amount, account_id) values ("A new car", 10000, 1);
insert OR REPLACE into records (descr, amount, account_id) values ("An old picture", 300, 1);

insert OR REPLACE into records (descr, amount, account_id) values ("A cup of tea", 20, 2);
insert OR REPLACE into records (descr, amount, account_id) values ("A metro ticket", 35, 2);
