package org.panda.tech.data.model.entity;

import org.panda.bamboo.common.model.DomainModel;

/**
 * 实体模型，具有唯一标识属性的数据领域模型
 * 具有单独映射的持久化存储载体，一般为数据库表。
 */
public interface Entity extends DomainModel {
}
