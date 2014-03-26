
drop keyspace if exists recipe_book;

create keyspace if not exists recipe_book
    with replication = {'class':'SimpleStrategy','replication_factor':1};

create columnfamily recipe_book.recipe (
    id TimeUUID,
	name text,
	PRIMARY KEY (id)
);

create columnfamily recipe_book.ingredient_by_recipe (
    recipe_id TimeUUID,
	name text,
	quantity int,
	unit text,
	PRIMARY KEY (recipe_id, name)
);

INSERT INTO recipe_book.recipe (id, name) VALUES (8ef14fc0-b52a-11e3-94d0-b96230eb081e, 'Cheese and Pickle Sandwich');

INSERT INTO recipe_book.ingredient_by_recipe (recipe_id, name, quantity, unit) VALUES (8ef14fc0-b52a-11e3-94d0-b96230eb081e, 'Branston Pickle', 2, 'tablespoons');
INSERT INTO recipe_book.ingredient_by_recipe (recipe_id, name, quantity, unit) VALUES (8ef14fc0-b52a-11e3-94d0-b96230eb081e, 'Bread', 2, 'slices');
INSERT INTO recipe_book.ingredient_by_recipe (recipe_id, name, quantity, unit) VALUES (8ef14fc0-b52a-11e3-94d0-b96230eb081e, 'Cheese', 3, 'slices');