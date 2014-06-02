insert OR REPLACE into users (name, password) values ("Alisa", "123");
insert OR REPLACE into users (name, password) values ("Bob", "321");

insert OR REPLACE into accounts (descr, user_name) values ("General", "Alisa");
insert OR REPLACE into accounts (descr, user_name) values ("2014", "Bob");

insert OR REPLACE into records (descr, amount, is_put, category_id, account_id) values ("A new car", 10000, 0, 4, 1);
insert OR REPLACE into records (descr, amount, is_put, category_id, account_id) values ("An old picture", 300, 0, 4, 1);

insert OR REPLACE into records (descr, amount, is_put, category_id, account_id) values ("A cup of tea", 20, 0, 1, 2);
insert OR REPLACE into records (descr, amount, is_put, category_id, account_id) values ("A metro ticket", 35, 0, 2, 2);

insert OR REPLACE into categories (name) values ("Food and Drinks"), ("Cafe"), ("Transport"), ("Health"), ("Other"), ("Salary");