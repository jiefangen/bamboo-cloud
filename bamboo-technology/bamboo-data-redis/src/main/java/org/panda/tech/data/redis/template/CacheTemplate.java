package org.panda.tech.data.redis.template;

import org.panda.bamboo.core.context.ApplicationContextBean;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class CacheTemplate {

    private final RedisTemplate<String, ?> redisTemplate;

    protected CacheTemplate() {
        this.redisTemplate = ApplicationContextBean.getBean(getRedisBeanName());
    }

    public CacheTemplate(RedisTemplate<String, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CacheTemplate(String redisBeanName) {
        this.redisTemplate = ApplicationContextBean.getBean(redisBeanName);
    }

    protected abstract String getRedisBeanName();

    /** -------------------key相关操作--------------------- */

    /**
     * 删除key
     *
     * @param key
     */
    void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化key
     *
     * @param key
     * @return
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param date
     * @return
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key
     * @return
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @param unit
     * @return
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey
     * @param newKey
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key
     * @return
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /** -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public <V> void set(String key, V value) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        opeForValue.set(key, value);
    }

    public <V> void set(String key, V value, Integer seconds) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        opeForValue.set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public <V> V get(String key) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        return opeForValue.get(key);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key
     * @param value
     * @return
     */
    public <V> V getAndSet(String key, V value) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        return opeForValue.getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys
     * @return
     */
    public <V> List<V> multiGet(Collection<String> keys) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        return opeForValue.multiGet(keys);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key
     * @param postion 位置
     * @param value   值,true为1, false为0
     * @return
     */
    public boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public <V> void setEx(String key, V value, long timeout, TimeUnit unit) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        opeForValue.set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key
     * @param value
     * @return 之前已经存在返回false, 不存在返回true
     */
    public <V> boolean setIfAbsent(String key, V value) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        return opeForValue.setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key
     * @param value
     * @param offset 从指定位置开始覆写
     */
    public <V> void setRange(String key, V value, long offset) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        opeForValue.set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps
     */
    public <V> void multiSet(Map<String, V> maps) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        opeForValue.multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps
     * @return 之前已经存在返回false, 不存在返回true
     */
    public <V> boolean multiSetIfAbsent(Map<String, V> maps) {
        ValueOperations<String, V> opeForValue = (ValueOperations<String, V>)redisTemplate.opsForValue();
        return opeForValue.multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @param value
     * @return
     */
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key
     * @param value
     * @return
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /** -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key
     * @param field
     * @return
     */
    public <K, V> V hGet(String key, String field) {
        HashOperations<String, K, V> opeForHash = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return opeForHash.get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @return
     */
    public <K,V> Map<K, V> hGetAll(String key) {
        HashOperations<String, K, V> opeForHash = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return opeForHash.entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @param fields
     * @return
     */
    public <K,V> List<V> hMultiGet(String key, Collection<K> fields) {
        HashOperations<String, K, V> opeForHash = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return opeForHash.multiGet(key, fields);
    }

    public <K,V> void hPut(String key, K hashKey, V value) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, value);
    }

    public <K,V> void hPutAll(String key, Map<K, V> maps) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        hashOperations.putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public <K,V> Boolean hPutIfAbsent(String key, K hashKey, V value) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public <K,V> Long hDelete(String key, K... fields) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public <K,V> boolean hExists(String key, K field) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public <K,V> Long hIncrBy(String key, K field, long increment) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public <K,V> Double hIncrByFloat(String key, K field, double delta) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    public <K,V> Set<K> hKeys(String key) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    public <K,V> List<V> hValues(String key) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key
     * @param options
     * @return
     */
    public <K,V> Cursor<Map.Entry<K, V>> hScan(String key, ScanOptions options) {
        HashOperations<String, K, V> hashOperations = (HashOperations<String, K, V>)redisTemplate.opsForHash();
        return hashOperations.scan(key, options);
    }

    /** ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表中的元素
     *
     * @param key
     * @param index
     * @return
     */
    public <V> V lIndex(String key, long index) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return
     */
    public <V> List<V> lRange(String key, long start, long end) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.range(key, start, end);
    }

    /**
     * 存储在list头部
     *
     * @param key
     * @param value
     * @return
     */
    public <V> Long lLeftPush(String key, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPush(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public <V> Long lLeftPushAll(String key, V... value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPushAll(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public <V> Long lLeftPushAll(String key, Collection<V> value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key
     * @param value
     * @return
     */
    public <V> Long lLeftPushIfPresent(String key, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public <V> Long lLeftPush(String key, V pivot, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPush(key, pivot, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public <V> Long lRightPush(String key, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPush(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public <V> Long lRightPushAll(String key, V... value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPushAll(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public <V> Long lRightPushAll(String key, Collection<V> value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key
     * @param value
     * @return
     */
    public <V> Long lRightPushIfPresent(String key, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPushIfPresent(key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public <V> Long lRightPush(String key, V pivot, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key
     * @param index 位置
     * @param value
     */
    public <V> void lSet(String key, long index, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        listOperations.set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public <V> V lLeftPop(String key) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public <V> V lBLeftPop(String key, long timeout, TimeUnit unit) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public <V> V lRightPop(String key) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public <V> V lBRightPop(String key, long timeout, TimeUnit unit) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    public <V> V lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPopAndLeftPush(sourceKey,
                destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
    public <V> V lBRightPopAndLeftPush(String sourceKey, String destinationKey,
                                              long timeout, TimeUnit unit) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.rightPopAndLeftPush(sourceKey,
                destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    public <V> Long lRemove(String key, long index, V value) {
        ListOperations<String,V> listOperations = (ListOperations<String, V>)redisTemplate.opsForList();
        return listOperations.remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key
     * @param start
     * @param end
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key
     * @return
     */
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /** --------------------set相关操作-------------------------- */

    /**
     * set添加元素
     *
     * @param key
     * @param values
     * @return
     */
    public <V> Long sAdd(String key, V... values) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key
     * @param values
     * @return
     */
    public <V> Long sRemove(String key, V... values) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key
     * @return
     */
    public <V> V sPop(String key) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public <V> Boolean sMove(String key, V value, String destKey) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key
     * @return
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key
     * @param value
     * @return
     */
    public <V> Boolean sIsMember(String key, V value) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.isMember(key, value);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public <V> Set<V> sIntersect(String key, String otherKey) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <V> Set<V> sIntersect(String key, Collection<String> otherKeys) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <V> Set<V> sUnion(String key, String otherKeys) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <V> Set<V> sUnion(String key, Collection<String> otherKeys) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public <V> Set<V> sDifference(String key, String otherKey) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <V> Set<V> sDifference(String key, Collection<String> otherKeys) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sDifference(String key, Collection<String> otherKeys,
                                   String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public <V> Set<V> sMembers(String key) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key
     * @return
     */
    public <V> V sRandomMember(String key) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key
     * @param count
     * @return
     */
    public <V> List<V> sRandomMembers(String key, long count) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key
     * @param count
     * @return
     */
    public <V> Set<V> sDistinctRandomMembers(String key, long count) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.distinctRandomMembers(key, count);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public <V> Cursor<V> sScan(String key, ScanOptions options) {
        SetOperations<String,V> setOperations = (SetOperations<String, V>)redisTemplate.opsForSet();
        return setOperations.scan(key, options);
    }

    /**------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public <V> Boolean zAdd(String key, V value, double score) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.add(key, value, score);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public <V> Long zAdd(String key, Set<ZSetOperations.TypedTuple<V>> values) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.add(key, values);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public <V> Long zRemove(String key, V... values) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public <V> Double zIncrementScore(String key, V value, double delta) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @return 0表示第一位
     */
    public <V> Long zRank(String key, V value) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key
     * @param value
     * @return
     */
    public <V> Long zReverseRank(String key, V value) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public <V> Set<V> zRange(String key, long start, long end) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <V> Set<ZSetOperations.TypedTuple<V>> zRangeWithScores(String key, long start,
                                                                         long end) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public <V> Set<V> zRangeByScore(String key, double min, double max) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public <V> Set<ZSetOperations.TypedTuple<V>> zRangeByScoreWithScores(String key,
                                                                                double min, double max) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.rangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public <V> Set<ZSetOperations.TypedTuple<V>> zRangeByScoreWithScores(String key,
                                                                                double min, double max, long start, long end) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.rangeByScoreWithScores(key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <V> Set<V> zReverseRange(String key, long start, long end) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <V> Set<ZSetOperations.TypedTuple<V>> zReverseRangeWithScores(String key,
                                                                                long start, long end) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.reverseRangeWithScores(key, start,
                end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <V> Set<V> zReverseRangeByScore(String key, double min,
                                                  double max) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <V> Set<ZSetOperations.TypedTuple<V>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.reverseRangeByScoreWithScores(key,
                min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public <V>  Set<V> zReverseRangeByScore(String key, double min,
                                                   double max, long start, long end) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key
     * @param value
     * @return
     */
    public <V> Double zScore(String key, V value) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long zIntersectAndStore(String key, String otherKey,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public <V> Cursor<ZSetOperations.TypedTuple<V>> zScan(String key, ScanOptions options) {
        ZSetOperations<String,V> zsetOperations = (ZSetOperations<String, V>)redisTemplate.opsForZSet();
        return zsetOperations.scan(key, options);
    }
}
