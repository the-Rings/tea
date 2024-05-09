DROP TABLE IF EXISTS binarytea.t_order;
CREATE TABLE binarytea.t_order (
    id bigint NOT NULL AUTO_INCREMENT,
	amount_discount integer,
	amount_pay bigint,
	amount_total bigint,
	maker_id bigint,
	status integer,
	create_time timestamp DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS binarytea.t_order_item;
CREATE TABLE binarytea.t_order_item (
   item_id bigint not null,
   order_id bigint not null
);


DROP TABLE IF EXISTS binarytea.t_menu;
CREATE TABLE binarytea.t_menu (
    id bigint NOT NULL AUTO_INCREMENT,
	name varchar(255),
	price bigint,
	`size` varchar(255),
	create_time timestamp DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
);

ALTER TABLE t_menu ADD UNIQUE KEY (name, `size`);

INSERT INTO t_menu (name, `size`, price) values ('Coffee', 'MEDIUM', 12);
INSERT INTO t_menu (name, `size`, price) values ('Coffee', 'LARGE', 15);
INSERT INTO t_menu (name, `size`, price) values ('Green Tea', 'SMALL', 5);
INSERT INTO t_menu (name, `size`, price) values ('Green Tea', 'MEDIUM', 6);
INSERT INTO t_menu (name, `size`, price) values ('Green Tea', 'LARGE', 7);

