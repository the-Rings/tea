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

DROP TABLE IF EXISTS maker.t_sequence_segment;

CREATE TABLE maker.t_sequence_segment (
	id BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
	biz_name VARCHAR(128) NOT NULL COMMENT '业务名称',
    segment_name VARCHAR(128) NOT NULL COMMENT '序列号段的名称',
    current_value BIGINT NOT NULL COMMENT '当前值名称',
    max_value BIGINT NOT NULL COMMENT '当前序列号',
    step INT NOT NULL COMMENT '当前段的最大序列号',
    record_version INT NOT NULL COMMENT '乐观锁版本号',
    retry_times INT DEFAULT NULL COMMENT '乐观锁重试次数',
    PRIMARY KEY (id),
    UNIQUE INDEX idx_maker_sequence_biz_segment (`biz_name`,`segment_name`)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO maker.t_sequence_segment (biz_name, segment_name, current_value, max_value, step, record_version, retry_times)
values ('tea-maker', 'making', 10000, 999999999, 10, 1, 0);
