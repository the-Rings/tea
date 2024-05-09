DROP TABLE IF EXISTS maker.t_tea_maker;

CREATE TABLE maker.t_tea_maker (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255),
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
	update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
);

ALTER TABLE maker.t_tea_maker ADD UNIQUE KEY (name);

INSERT INTO maker.t_tea_maker (name) values ('Li Lei');
INSERT INTO maker.t_tea_maker (name) values ('Musk');