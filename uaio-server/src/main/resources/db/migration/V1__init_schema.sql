CREATE TABLE t_mock
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    method     VARCHAR(255) COMMENT '请求方法',
    path       VARCHAR(255) COMMENT '请求路径',
    headers    JSON COMMENT '请求头',
    args       JSON COMMENT '请求参数',
    template   VARCHAR(255) COMMENT 'Mock模板内容',
    created_at TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP COMMENT '更新时间'
);
