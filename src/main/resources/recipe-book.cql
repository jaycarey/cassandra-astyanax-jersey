
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