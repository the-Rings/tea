package rain.mocking.teamaker.util;

/**
 * @author mao
 * @date 2024/6/23 19:00
 */
public class SnowflakeIdGenerator {
    // 起始时间戳（可以根据需要设置）
    private final long twepoch = 1288834974657L;
    // 数据中心标识位数
    private final long datacenterIdBits = 5L;
    // 机器标识位数
    private final long workerIdBits = 5L;
    // 序列号位数
    private final long sequenceBits = 12L;

    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args){
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 5);
    System.out.println(snowflakeIdGenerator.nextId());
    System.out.println(snowflakeIdGenerator.nextId());
    System.out.println(snowflakeIdGenerator.nextId());
    System.out.println(snowflakeIdGenerator.nextId());
    }
}

